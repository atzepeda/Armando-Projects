/*
people(ids: $personIDs) {
        id #person of interest
        fullName
        emailAddresses(type: Email,usage: Business){address}
        phoneNumbers(type: Phone){number}
        connectionsWithContact(relatedContactId: $creatorID, relationshipType: All, limit: 10){
          totalModels
          models{
            baseContacts{ #everybody who is both a base and target are the mutual contacts
              id
              lastName
              relationshipName
            } #
            targetContact{
              id
              lastName
              
            }
          }
        }
*/
var contactSort = function(peopleArray, creatorID){
    // find everyone who appears both as a base contact and as a target contact
    // OR find everyone who appears as a base contact and the target contact is the person of interest
    // equvalent to eliminated all elements whose target contact is not the person of interest
    // we also then want to remove all base contacts that are the creator so the creator does not show up in the
    // mutual contact list
    let personID = "";

    //make a recursive function would be smart

    peopleArray.forEach(person => {
        personID = person.id;
        let removeInd = [];
        let counter = 0;
        person.connectionsWithContact.models.forEach(connection => {
            if(connection.targetContact.id != personID){
                // we want to delete this element from this persons list of connections
                // array of indices to delete
                removeInd.push(counter);
            }else if(connection.baseContacts[0].id == creatorID){
                removeInd.push(counter);
            }
            counter++; // update the index counter
        });
        // now we want to remove the elements from this person object
        console.log(removeInd);
        // as we remove elements the prevously found indices will decrease by one
        // this is also gaurenteed to work because indices are already sorted from least to greatest
        // as long as the next indice is greater we must decrease it by the number of removed elements
        let removeCounter = 0;
        let removedElement = null;
        removeInd.forEach(index => {
            removedElement = person.connectionsWithContact.models.splice(index - removeCounter, 1);
            //console.log("Removed Connection: ", removedElement);
            removeCounter++;
        });
    });
    
    return peopleArray;
}

module.exports = contactSort;