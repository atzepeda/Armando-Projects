var express = require("express");
var router = express.Router();

/* GET home page. */
router.get("/", function (req, res, next) {
  res.render("index", {
    title: "HomePage",
    user: JSON.stringify(req.openid.user),
    tokens: JSON.stringify(req.session.openidTokens),
  });
});

router.get("/meeting", function (req, res, next) {
  res.render("meeting", {
    title: "Meeting",
    user: JSON.stringify(req.openid.user),
    tokens: JSON.stringify(req.session.openidTokens),
  });
});

router.get("/name", async function (req, res, next) {
  const tokens = req.openid.makeTokenSet(req.session.openidTokens);

  const client = new GraphQLClient(API_URL, {
    headers: {
      authorization: `Bearer ${tokens.access_token}`,
    },
  });

  const query = gql`
    query searchByLastName($name: String) {
      contacts(type: Person, filter: [{ field: lastName, value: $name }]) {
        models {
          id
          displayName
          emailAddresses {
            address
          }
        }
      }
    }
  `;

  const variables = {
    name: "McCarthy",
  };

  const gqlResponse = await client.request(query, variables);
  console.log(gqlResponse);
  res.render("meeting", {
    title: "Meeting",
    user: JSON.stringify(req.openid.user),
    tokens: JSON.stringify(req.session.openidTokens),
    gqlResponse: gqlResponse,
  });
});

module.exports = router;
