const express = require("express");

const router = express.Router();

const { GraphQLClient, gql } = require("graphql-request");
const arrayConstructor = require("../backend/arrayConstructor");
const API_URL = "https://loblaw-qa.azure.interaction.com/gateway/api/graphql";

const getIDs = async (req, res, next) => {
  console.log("URL Parameters User/Meeting: ", req.params);

  const tokens = req.openid.makeTokenSet(req.session.openidTokens);

  const client = new GraphQLClient(API_URL, {
    headers: {
      authorization: `Bearer ${tokens.access_token}`,
    },
  });

  //use meeting id to acquire list of people
  const getMeetingAttendees = gql`
    query getMeetingAttendees($id: ID!) {
      activity(id: $id) {
        id
        summary
        persons: linkedActivityContacts(contactType: Person) {
          id
        }
      }
    }
  `;

  const variables = {
    id: JSON.stringify(req.params.meetingID)
  };
  console.log(JSON.stringify(req.params.meetingID));
  const meetingAttendeesResponse = await client.request(getMeetingAttendees, variables);
  console.log("GQL query response: ", meetingAttendeesResponse);
  req.data = meetingAttendeesResponse;
  next();
}

const getInfo = async (req, res, next) => {
  const tokens = req.openid.makeTokenSet(req.session.openidTokens);

  const client = new GraphQLClient(API_URL, {
    headers: {
      authorization: `Bearer ${tokens.access_token}`,
    },
  });

  // array constructor requires the persons array and returns peopleIds
  let peopleIds = arrayConstructor(req.data.activity.persons);


  const getPeoplesBasicInfo = gql`
    query getlinkedActivityContactsBasicInfo($personIDs: [ID!]!) {
      people(ids: $personIDs) {
        fullName
        emailAddresses(type: Email,usage: Business){address}
        phoneNumbers(type: Phone){number}
      }
    }
  `;

  const basicInfoResponse = await client.request(getPeoplesBasicInfo, {personIDs: peopleIds});
  //console.log("GQL query response: ", basicInfoResponse);
  basicInfoResponse.people = contactSort(basicInfoResponse.people);
  //console.log("New people array", basicInfoResponse.people);
  //console.log("New People Array Connections", basicInfoResponse.people[0].connectionsWithContact.models);
  //console.log("New People Base Contact", basicInfoResponse.people[0].connectionsWithContact.models[0].baseContacts);
  req.data2 = basicInfoResponse;
  next();
}


/* GET home page. */
router.get("/user/:userID/meeting/:meetingID", getIDs, getInfo, async function (req, res, next) {
  //console.log("Request Parameters: ", req.params, "Data:", req.data)
  //console.log(req.data2.people[0].emailAddresses.address)
  //console.log(req.data2.people[0].connectionsWithContact.models[0].baseContacts)
  res.render("meeting", {
    title: "Meeting Briefing",
    user: JSON.stringify(req.openid.user),
    tokens: JSON.stringify(req.session.openidTokens),
    activity: req.data.activity,
    people: req.data2.people,
  });
});

module.exports = router;