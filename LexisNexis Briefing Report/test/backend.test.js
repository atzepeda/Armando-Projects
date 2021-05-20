var arrayConstructor = require("../backend/arrayConstructor");
const inputArray = [{'id': 1}, {'id': 2}, {'id': 3}];
const expectedOutput = [1,2,3];
test("should be able to format the array", () => {
    const actualOutput = arrayConstructor(inputArray);
    expect(actualOutput).toEqual(expectedOutput);
});

var emailer = require("../backend/emailer");
//userID, activityID, address, subject
const userID = 2;
const activityID = 2;
const address = 'sdcTeam22@gmail.com';
const subject = 'Testing emailer function';

test("should send an email", async () =>{
    expect.assertions(1);
    await expect(emailer(userID, activityID, address, subject)).resolves.toEqual('resolved');
});

test("should not send an email", async () =>{
    expect.assertions(1);
    await expect(emailer(userID, activityID, 2, subject)).resolves.toEqual('error');
});


var contactSort = require("../backend/contactSort");
const creatorID = "00004cc9-0002-0000-0000-000000000000";

const people ={
      "people": [
        {
          "id": "00004ccb-0002-0000-0000-000000000000",
          "fullName": "Gaige Fitzpatrick",
          "emailAddresses": [
            {
              "address": "dcfitzp3@azure.interaction.com"
            }
          ],
          "phoneNumbers": [],
          "connectionsWithContact": {
            "totalModels": 7,
            "models": [
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00001ef7-0002-0000-0000-000000000000",
                  "lastName": "Doe"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004ccb-0002-0000-0000-000000000000",
                  "lastName": "Fitzpatrick"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00001ef7-0002-0000-0000-000000000000",
                    "lastName": "Doe",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004ccb-0002-0000-0000-000000000000",
                  "lastName": "Fitzpatrick"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc8-0002-0000-0000-000000000000",
                    "lastName": "Joyner",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004ccb-0002-0000-0000-000000000000",
                  "lastName": "Fitzpatrick"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cca-0002-0000-0000-000000000000",
                    "lastName": "Zepeda",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004ccb-0002-0000-0000-000000000000",
                  "lastName": "Fitzpatrick"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cc8-0002-0000-0000-000000000000",
                  "lastName": "Joyner"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cca-0002-0000-0000-000000000000",
                  "lastName": "Zepeda"
                }
              }
            ]
          }
        },
        {
          "id": "00004cc8-0002-0000-0000-000000000000",
          "fullName": "Kipnerter Joyner",
          "emailAddresses": [
            {
              "address": "scjoyner@azure.interaction.com"
            }
          ],
          "phoneNumbers": [],
          "connectionsWithContact": {
            "totalModels": 8,
            "models": [
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00001ef7-0002-0000-0000-000000000000",
                  "lastName": "Doe"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004ccb-0002-0000-0000-000000000000",
                  "lastName": "Fitzpatrick"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cc8-0002-0000-0000-000000000000",
                  "lastName": "Joyner"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00009efd-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Known By"
                  }
                ],
                "targetContact": {
                  "id": "00004cc8-0002-0000-0000-000000000000",
                  "lastName": "Joyner"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00001ef7-0002-0000-0000-000000000000",
                    "lastName": "Doe",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cc8-0002-0000-0000-000000000000",
                  "lastName": "Joyner"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004ccb-0002-0000-0000-000000000000",
                    "lastName": "Fitzpatrick",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cc8-0002-0000-0000-000000000000",
                  "lastName": "Joyner"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cca-0002-0000-0000-000000000000",
                    "lastName": "Zepeda",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cc8-0002-0000-0000-000000000000",
                  "lastName": "Joyner"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cca-0002-0000-0000-000000000000",
                  "lastName": "Zepeda"
                }
              }
            ]
          }
        },
        {
          "id": "00004cca-0002-0000-0000-000000000000",
          "fullName": "Gman Zepeda",
          "emailAddresses": [
            {
              "address": "atzepeda@azure.interaction.com"
            }
          ],
          "phoneNumbers": [],
          "connectionsWithContact": {
            "totalModels": 7,
            "models": [
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00001ef7-0002-0000-0000-000000000000",
                  "lastName": "Doe"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004ccb-0002-0000-0000-000000000000",
                  "lastName": "Fitzpatrick"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cc8-0002-0000-0000-000000000000",
                  "lastName": "Joyner"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc9-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cca-0002-0000-0000-000000000000",
                  "lastName": "Zepeda"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00001ef7-0002-0000-0000-000000000000",
                    "lastName": "Doe",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cca-0002-0000-0000-000000000000",
                  "lastName": "Zepeda"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004ccb-0002-0000-0000-000000000000",
                    "lastName": "Fitzpatrick",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cca-0002-0000-0000-000000000000",
                  "lastName": "Zepeda"
                }
              },
              {
                "baseContacts": [
                  {
                    "id": "00004cc8-0002-0000-0000-000000000000",
                    "lastName": "Joyner",
                    "relationshipName": "Co-worker"
                  }
                ],
                "targetContact": {
                  "id": "00004cca-0002-0000-0000-000000000000",
                  "lastName": "Zepeda"
                }
              }
            ]
          }
        }
      ]
    };
const expectedPeopleOut = [
        {
        "id": "00004ccb-0002-0000-0000-000000000000",
        "fullName": "Gaige Fitzpatrick",
        "emailAddresses": [
            {
            "address": "dcfitzp3@azure.interaction.com"
            }
        ],
        "phoneNumbers": [],
        "connectionsWithContact": {
            "totalModels": 7,
            "models": [
            {
                "baseContacts": [
                {
                    "id": "00001ef7-0002-0000-0000-000000000000",
                    "lastName": "Doe",
                    "relationshipName": "Co-worker"
                }
                ],
                "targetContact": {
                "id": "00004ccb-0002-0000-0000-000000000000",
                "lastName": "Fitzpatrick"
                }
            },
            {
                "baseContacts": [
                {
                    "id": "00004cc8-0002-0000-0000-000000000000",
                    "lastName": "Joyner",
                    "relationshipName": "Co-worker"
                }
                ],
                "targetContact": {
                "id": "00004ccb-0002-0000-0000-000000000000",
                "lastName": "Fitzpatrick"
                }
            },
            {
                "baseContacts": [
                {
                    "id": "00004cca-0002-0000-0000-000000000000",
                    "lastName": "Zepeda",
                    "relationshipName": "Co-worker"
                }
                ],
                "targetContact": {
                "id": "00004ccb-0002-0000-0000-000000000000",
                "lastName": "Fitzpatrick"
                }
            }
            ]
        }
        },
        {
        "id": "00004cc8-0002-0000-0000-000000000000",
        "fullName": "Kipnerter Joyner",
        "emailAddresses": [
            {
            "address": "scjoyner@azure.interaction.com"
            }
        ],
        "phoneNumbers": [],
        "connectionsWithContact": {
            "totalModels": 8,
            "models": [
            {
                "baseContacts": [
                {
                    "id":  "00009efd-0002-0000-0000-000000000000",
                    "lastName": "Bowes",
                    "relationshipName": "Known By"
                }
                ],
                "targetContact": {
                "id": "00004cc8-0002-0000-0000-000000000000",
                "lastName": "Joyner"
                }
            },
            {
                "baseContacts": [
                {
                    "id": "00001ef7-0002-0000-0000-000000000000",
                    "lastName": "Doe",
                    "relationshipName": "Co-worker"
                }
                ],
                "targetContact": {
                "id": "00004cc8-0002-0000-0000-000000000000",
                "lastName": "Joyner"
                }
            },
            {
                "baseContacts": [
                {
                    "id": "00004ccb-0002-0000-0000-000000000000",
                    "lastName": "Fitzpatrick",
                    "relationshipName": "Co-worker"
                }
                ],
                "targetContact": {
                "id": "00004cc8-0002-0000-0000-000000000000",
                "lastName": "Joyner"
                }
            },
            {
                "baseContacts": [
                {
                    "id": "00004cca-0002-0000-0000-000000000000",
                    "lastName": "Zepeda",
                    "relationshipName": "Co-worker"
                }
                ],
                "targetContact": {
                "id": "00004cc8-0002-0000-0000-000000000000",
                "lastName": "Joyner"
                }
            }
            ]
        }
        },
        {
        "id": "00004cca-0002-0000-0000-000000000000",
        "fullName": "Gman Zepeda",
        "emailAddresses": [
            {
            "address": "atzepeda@azure.interaction.com"
            }
        ],
        "phoneNumbers": [],
        "connectionsWithContact": {
            "totalModels": 7,
            "models": [
            {
                "baseContacts": [
                {
                    "id": "00001ef7-0002-0000-0000-000000000000",
                    "lastName": "Doe",
                    "relationshipName": "Co-worker"
                }
                ],
                "targetContact": {
                "id": "00004cca-0002-0000-0000-000000000000",
                "lastName": "Zepeda"
                }
            },
            {
                "baseContacts": [
                {
                    "id": "00004ccb-0002-0000-0000-000000000000",
                    "lastName": "Fitzpatrick",
                    "relationshipName": "Co-worker"
                }
                ],
                "targetContact": {
                "id": "00004cca-0002-0000-0000-000000000000",
                "lastName": "Zepeda"
                }
            },
            {
                "baseContacts": [
                {
                    "id": "00004cc8-0002-0000-0000-000000000000",
                    "lastName": "Joyner",
                    "relationshipName": "Co-worker"
                }
                ],
                "targetContact": {
                "id": "00004cca-0002-0000-0000-000000000000",
                "lastName": "Zepeda"
                }
            }
            ]
        }
        }
    ]
    test("should be able to parse the people's connections", () => {
        const actualOutput = contactSort(people.people, creatorID);
        expect(actualOutput).toEqual(expectedPeopleOut);
    });
