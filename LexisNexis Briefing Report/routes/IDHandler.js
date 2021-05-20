// format of idObject
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

function IDHandler(idObject) {
    this._creatorID = idObject.activity.creatorID;
    this._persons = idObject.activity.persons;
    this._companies = idObject.activity.companies;
}


IDHandler.prototype.get = function provideIDInformation() {
    return [this._creatorID, this._persons, this.companies];
};
