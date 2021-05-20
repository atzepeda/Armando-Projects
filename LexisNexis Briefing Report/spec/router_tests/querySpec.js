
var app = require('../../app');
var request = require('supertest');
var rToken = '-yLtMMwfeyCaXBx4pyzMxXqXvubHOyCQYSporz7Ifyg';

describe('GET /query', function() {


  beforeAll(function() {

    // Refresh flow, mount function to the express app
    app.use('/', async function (req, res, next) {
      //console.log("****************This method ran**********************")
      const newTokens = await req.openid.client.refresh(rToken);
    
      req.session.openidTokens = newTokens;
      req.appSession.claims = newTokens.claims;
    });

    // execute the function to refresh the token by performing a request
    request(app).get('/').set('Accept', 'text/html')
    .expect('Content-Type', /html/)
    .expect(200)
    .then(response => {
      console.log(JSON.stringify(response));
      console.log("\n************************************\n")
    });
  });

  it('responds with json', function(done) {
    //console.log(JSON.stringify(app.get('/query')));
    console.log("\n************************************\n")
    request(app)
      .get('/query')
      .set('Accept', 'text/html')
      .expect('Content-Type', /html/)
      .expect(302)
      .then(response => {
        console.log(JSON.stringify(response));
        //assert(JSON.stringify(response.activity), '{"creatorId":"00004cc9-0002-0000-0000-000000000000","activityDate":"2020-09-30","subject":"TestingMultiSubscription"}')
      });
  });
});