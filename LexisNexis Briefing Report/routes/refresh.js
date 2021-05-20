var express = require("express");
var router = express.Router();

/* GET home page. */
router.get("/", async function (req, res, next) {
  oldTokens = req.openid.makeTokenSet(req.session.openidTokens);
  newTokens = await req.openid.client.refresh(oldTokens);

  req.session.openidTokens = newTokens;
  console.log(req.session.openidTokens)
  req.appSession.claims = newTokens.claims;

  res.redirect("/");
});

module.exports = router;
