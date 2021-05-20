'use strict';

var app = require('../../app');
var request = require('supertest')(app);
//var assert = require('assert');

describe('GET /query', function() {

    it('should require authorization', function(done) {
        request
            .get('/query')
            .expect(401)
            .end(function(err, res) {
                console.log(JSON.stringify(res));
                if (err) return done(err);
                done();
            });
    });

    var auth = {};
    beforeEach(loginUser(auth));

    it('should respond with JSON array', function(done) {
        request
            .get('/query')
            .set('Authorization', 'bearer ' + auth.token)
            .expect(200)
            .expect('Content-Type', /json/)
            .end(function(err, res) {
                console.log(JSON.stringify(res));
                if (err) return done(err);
                
                done();
            });
    });

});

function loginUser(auth) {
    return function(done) {
        request
            .post('/login')
            .send({
                email: 'tjbowes@azure.interaction.com',
                password: '5.egur0Fot0s'
            })
            .expect(200)
            .end(onResponse);

        function onResponse(err, res) {
            auth.token = res.body.token;
            return done();
        }
    };
}