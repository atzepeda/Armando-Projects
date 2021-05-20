const express = require("express");
const { GraphQLClient, gql } = require("graphql-request");
const nodemailer = require('nodemailer');
const email = require("../backend/emailer");

const router = express.Router();

const API_URL = "https://loblaw-qa.azure.interaction.com/gateway/api/graphql";
//const initialIDList = ["00004cc8-0002-0000-0000-000000000000", "00004cc9-0002-0000-0000-000000000000", "00004ccb-0002-0000-0000-000000000000", "00004cca-0002-0000-0000-000000000000"];

var arrayID = null;

/*
const subscription = async (req, res, next) => {
  const tokens = req.openid.makeTokenSet(req.session.openidTokens);

  const client = new GraphQLClient(API_URL, {
    headers: {
      authorization: `Bearer ${tokens.access_token}`,
    },
  });

  const query = gql`
    subscription activityStream($idList: [ID!]!) {
      activityCreated(
        contactIds: $idList
        includeUserCreatedActivities:true
      ) {
        id
        subject
      }
    }
  `;

  const variables = {
    idList: initialIDList
  };
  console.log("ID list", initialIDList)
  const gqlResponse = await client.request(query, variables);
  //do something with that response
  //we need to find who created that activity add it to a dictionary of key creator ID: ActivityIDs
  
  {
    "data": {
      "activityCreated": {
        "id": "00019d10-0000-0000-0000-000000000000",
        "subject": "TestingSubscription"
      }
    }
  }
  req.data = gqlResponse.json();
  console.log(req.data);
  next()
  //createdActivityID = gqlResponse.data.activityCreated.id;
  //console.log("ID of created activity: ", createdActivityID)
  
  // now we use this id to find the creator
}*/

const activityID = "00019d11-0000-0000-0000-000000000000"; //hard coded activity ID

/**
 * Should be called when an activity is created.
 * 
 * Gets the creatorID and date of the activity
 * saves the information in the req object
 * sends the information to the next gql query sendUserEmail 
 * @param {*} req empty express request object
 * @param {*} res empty response request object
 * @param {*} next express variable used to call the next router function
 */
const getDateAndCreatorID = async (req, res, next) => {
  const tokens = req.openid.makeTokenSet(req.session.openidTokens);

  const client = new GraphQLClient(API_URL, {
    headers: {
      authorization: `Bearer ${tokens.access_token}`,
    },
  });

  const query = gql`
    query getLinkedActivityContactsIDs($id: ID!) {
      activity(id: $id) {
        creatorId
        activityDate
        subject
      }
    }
  `;

  const variables = {
    id: activityID,
  };

  const gqlResponse = await client.request(query, variables);
  console.log("GQL query response: ", gqlResponse);
  req.data = gqlResponse;

  //save these ids in req object
  //when the time comes we send an email but for now we do it anyways
  /*
  "activity": {
      "creatorId": "00004cc9-0002-0000-0000-000000000000",
      "activityDate": "2020-09-30",
      "summary": "Summary"
    }*/
  //

  next();
}



/**
 * Sends an email to the user upon activity creation.
 * currently sends emails to the activity creators email.
 * @param {*} req request object with data from prior query
 * @param {*} res response object with data from prior query
 * @param {*} next express variable used to call the next router function
 */
const sendUserEmail = async (req, res, next) => {
  const tokens = req.openid.makeTokenSet(req.session.openidTokens);

  const client = new GraphQLClient(API_URL, {
    headers: {
      authorization: `Bearer ${tokens.access_token}`,
    },
  });

  const query = gql`
    query getUserEmail($id: ID!) {
      contact(id: $id){
        emailAddresses{
          address
        }
      }
    }
  `;
  
  const userID = req.data.activity.creatorId;

  // assume creator is user
  const variables = {
    id: userID,
  };

  const gqlResponse = await client.request(query, variables);
  
  console.log("Creator Email: ", gqlResponse);
  console.log("Email Address", gqlResponse.contact.emailAddresses[0].address);
  //send the email to the user
  //do not know if azure.interaction emails are real emails


  // emailer needs userID, activityID, receiver address, subject
  const result = await email(userID, activityID, gqlResponse.contact.emailAddresses[0].address, req.data.activity.subject);
  console.log(result);
  
  next();
}

/* GET home page. */
router.get("/", getDateAndCreatorID, sendUserEmail, function (req, res) {
  res.send(req.data); // don't need to display anything
});

module.exports = router;
