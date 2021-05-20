const express = require("express");
const fetch = require("cross-fetch");
const { ApolloClient, InMemoryCache, createHttpLink } =  require("@apollo/client/core");
const gql = require("graphql-tag");
const setContext = require("@apollo/client/link/context");
const router = express.Router();

const initialIDList = ["00004cc8-0002-0000-0000-000000000000", "00004cc9-0002-0000-0000-000000000000", "00004ccb-0002-0000-0000-000000000000", "00004cca-0002-0000-0000-000000000000"];


const API_URL = "https://loblaw-qa.azure.interaction.com/gateway/api/graphql";




router.get("/", async function (req, res, next) {
    const tokens = req.openid.makeTokenSet(req.session.openidTokens);

    // code taken from apollo client guides 
    // Instantiate required constructor fields
    const cache = new InMemoryCache();

    const httpLink = createHttpLink({
        uri: API_URL, fetch
    });
    
    const authLink = setContext.setContext((_, { headers }) => {

    // return the headers to the context so httpLink can read them
    return {
        headers: {
        ...headers,
        authorization: token ? `Bearer ${tokens.access_token}` : "",
        }
    }
    });
    

    const client = new ApolloClient({
        // Provide required constructor fields
        cache: cache,
        link: authLink.concat(httpLink),
    });

    //potentially change this to not take a param and just hard code the list of ID's to make it easier
    const activityStream = gql`
        subscription activityStream($idList: [ID!]!) {
            activityCreated(
                contactIds: $idList
                includeUserCreatedActivities:true
            ) {
                id
                subject
            }
        }
    `;
    //No clue what this does lets find out
    client.subscribe({query: activityStream}, {variables: {idList: initialIDList}}).subscribe({
        next(x) { console.log(x) },
        error(err) { console.log(`Finished with error: ${ err }`) },
        complete() { console.log('Finished') }
      });
    
});
module.exports = router;