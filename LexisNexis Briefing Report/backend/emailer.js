const nodemailer = require('nodemailer');

//emailer needs userID, activityID, receiver address, subject
var email = function(userID, activityID, address, subject){

    return new Promise(resolve => {

        // create the object that sends the email
        const transporter = nodemailer.createTransport({
            service: 'gmail',
            auth: {
              user: 'sdcTeam22@gmail.com',
              pass: 'P3nGuiN^9'
            }
          });
          
          //url has the meeting id and user id
          // meeting id is hard coded as activityID
          // user id is req.data.creatorId
          var reportLink = 'http://localhost:3000/user/' + userID + '/meeting/' + activityID;
          console.log("Report Link: ", reportLink);
          // initialize mail options
          var mailOptions = {
            from: 'sdcTeam22@gmail.com',
            to: address, //substitute with sdcTeam22@gmail.com for testing
            cc: "scjoyner@ncsu.edu",
            subject: subject,
            text: reportLink,
          };
          // send the email
          transporter.sendMail(mailOptions, function(error, info){
            if (error) {
              console.log(error);
              resolve('error');
            } else {
              console.log('Email sent: ' + info.response);
              resolve('resolved');
            }
          });
      });
  }

module.exports = email;
