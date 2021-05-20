const cron = require("node-cron")
const express = require("express")
const nodemailer = require("nodemailer")
const dotenv = require('dotenv')
dotenv.config()
const {SENDER_EMAIL} = process.env
const {SENDER_PASS} = process.env
const {RECEIVER_LIST} = process.env
const {CC_LIST} = process.env
const {SUBJECT} = process.env
const HTML = './email.html';



// console.log(SENDER_EMAIL, SENDER_PASS, RECEIVER_LIST, CC_LIST, SUBJECT, HTML)
app = express()

let transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
        user: SENDER_EMAIL,
        pass: SENDER_PASS
    }
})

let mailOptions = {
    from: SENDER_EMAIL,
    to: RECEIVER_LIST,
    cc: "blkscj@gmail.com",
    subject: SUBJECT,
    html: HTML
}

cron.schedule("* * * * *", () => {
    console.log("Running Email Cron Job" + new Date())
    sendEmail()
})

cron.schedule("59 23 * * *", () => {
    console.log("Running Cron Job " + new Date())
    getActivities()
})

getActivities = () => {
    
}

sendEmail = () => {
    transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            throw error
        } else {
            console.log(`
            Email successfully sent at ${new Date()}
            Info: ${info.response}
            `)
        }
    })
}

app.listen("3128")

