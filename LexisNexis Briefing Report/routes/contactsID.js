const express = require("express");
const { GraphQLClient, gql } = require("graphql-request");

const router = express.Router();

const API_URL = "https://loblaw-qa.azure.interaction.com/gateway/api/graphql";

/* GET home page. */
router.get("/", async function (req, res, next) {
  const tokens = req.openid.makeTokenSet(req.session.openidTokens);
  console.log(req.session)
  const client = new GraphQLClient(API_URL, {
    headers: {
      authorization: `Bearer ${tokens.access_token}`,
    },
  });
  const id = "00004cc8-0002-0000-0000-000000000000";
  const query = gql`
  query getLinkedActivityContactsIDs($id: ID!) {
    activity(id: $id) {
      persons: linkedActivityContacts(contactType: Person) {
        id
        visibility
      }
      companies: linkedActivityContacts(contactType: Company) {
        id
        visibility
      }
    }
  }
  `;

  const gqlResponse = await client.request(query, id);

  res.send(gqlResponse);
});

module.exports = router;
