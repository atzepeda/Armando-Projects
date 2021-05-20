var Request = require("request");

describe("Server", () => {
    var server;
    beforeAll(() => {
        server = require('../../app');
    });
    afterAll(() => {
        //do nothing express server terminates itself
    });
    describe("GET /", () => {
        var data = {};
        beforeAll((done) => {
            Request.get("http://localhost:3000/", (error, response, body) => {
                data.status = response;
                data.body = body;
                done();
            });
        });
        it("Status 200", () => {
            console.log(JSON.stringify(data.status));
        });
        it("Body", () => {
            console.log(JSON.stringify(data.body));
        });
    });
});