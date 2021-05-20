var express = require("express");
var router = express.Router();
var query = require("./query");

router.get("/profile", function (req, res, next) {
  
  res.render("profile", {
    title: "profile",
    user: JSON.stringify(req.openid.user),
    tokens: JSON.stringify(req.session.openidTokens),
  });
});

module.exports = router;
