var express = require("express");
var router = express.Router();

/* GET home page. */
router.get("/home", function (req, res, next) {
  res.render("home", {
    title: "HomePage",
    user: JSON.stringify(req.openid.user),
    tokens: JSON.stringify(req.session.openidTokens),
  });
  next()
  
});

module.exports = router;
