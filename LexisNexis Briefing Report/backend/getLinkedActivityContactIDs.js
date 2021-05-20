async function getLinkedActivityContactsIDs(activityID, req) {
    const tokens = req.openid.makeTokenSet(req.session.openidTokens);
  
    const client = new GraphQLClient(API_URL, {
      headers: {
        authorization: `Bearer ${tokens.access_token}`,
      },
    });
  
    const query = gql`
      query getLinkedActivityContactsIDs($id: ID!) {
        activity(id: $id) {
          creatorId
          persons: linkedActivityContacts(contactType: Person) {
            id
            visibility
          }
          companies: linkedActivityContacts(contactType: Company) {
            id
            visibility
          }
        }
      }
    `;
  
    const variables = {
      id: activityID,
    };
  
    const gqlResponse = await client.request(query, variables);
    console.log("GQL query response: ", gqlResponse);
    rep = new IDHandler(gqlResponse);
    console.log("ID handler object: ", rep);
  
    //Do something with this
    //use this stuff in another query
    //save these ids in rep object
    //when the time comes we send an email but for now we do it anyways
  
    /*
    {
      "data": {
        "activity": {
          "creatorId": "00004cc9-0002-0000-0000-000000000000",
          "persons": [
            {
              "id": "00004cc9-0002-0000-0000-000000000000",
              "visibility": "Public"
            }
          ],
          "companies": [
            {
              "id": "00001ef6-0002-0000-0000-000000000000",
              "visibility": "Public"
            }
          ]
        }
      }
    }*/
    //
  }