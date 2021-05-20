/**
 * Reconstructs the array of IDs into an acceptable array format for GraphQL queries.
 * The array passed in is of the format [{'id' : xxxx}, {'id' : xxxx}]
 * This method converts it to the format [xxxx, xxxx]
 * @param {*} arrayIDs An array of IDs
 */
var arrayConstructor = function(arrayIDs){
    let peopleIDs = [];

    arrayIDs.forEach(element => {
        peopleIDs.push(element.id);
    });
    
    return peopleIDs;
}

module.exports = arrayConstructor;