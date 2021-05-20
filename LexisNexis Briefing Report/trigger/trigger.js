var createError = require("http-errors");
var express = require("express");
var session = require("express-session");
var path = require("path");
var logger = require("morgan");

const { auth } = require("express-openid-connect");

var indexRouter = require("./routes/index");
var queryRouter = require("./routes/query");
var refreshRouter = require("./routes/refresh");
var meetingRouter = require("./routes/meeting");
var contactsIDRouter = require("./routes/contactsID");
var app = express();

// session setup

app.use(
  session({
    secret: "some secret string.",
    resave: false,
    saveUninitialized: false,
    cookie: {
      maxAge: 15 * 60 * 1000, // 15 minutes
    },
  })
);

// authentication setup - see https://github.com/auth0/express-openid-connect/blob/master/EXAMPLES.md

const config = {
  required: true,
  appSession: {
    secret: "some secret string.",
  },
  authorizationParams: {
    response_type: "code id_token",
    response_mode: "form_post",
    scope: "openid profile gizmo_api offline_access",
  },
  baseURL: "http://localhost:3000",
  clientID: "ncsu",
  clientSecret: "KsVJysIb86VvJPDNqhSY",
  issuerBaseURL: "https://loblaw-qa.azure.interaction.com/auth/",
  handleCallback: async function (req, res, next) {
    req.session.openidTokens = req.openidTokens;
    next();
  },
};

app.use(auth(config));

// view engine setup
app.set("views", path.join(__dirname, "views"));
app.set("view engine", "jade");

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, "public")));

app.use("/", indexRouter);
app.use("/name", indexRouter);
app.use("/query", queryRouter);
app.use("/refresh", refreshRouter);
app.use("/home", indexRouter);
app.use("/meeting", indexRouter);
app.use("/contact", contactsIDRouter);


// catch 404 and forward to error handler
app.use(function (req, res, next) {
  next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get("env") === "development" ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render("error");
});

module.exports = app;
