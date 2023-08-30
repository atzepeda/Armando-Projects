from flask import Flask, jsonify, make_response
from py2neo import Graph

import logging

logging.basicConfig(level=logging.INFO)
app = Flask(__name__)
graph = Graph("bolt://localhost:7687", auth=("neo4j", "asdf1234"))

@app.route('/v1/characters', methods=["GET"])
def get_all_characters():
    query_result = graph.run("MATCH (n:Character) return n")
    app.logger.info(f"eden, type is {type(query_result)}")
    characters = [character[0] for character in query_result]
    return make_response(jsonify(characters), 200)

@app.route("/v1/movies/<id>", methods=["GET"])
def get_movie_by_id(id):
    print("here is the id: ",id)
    query_run = graph.run(
        "MATCH (m:Movie) WHERE ID(m) = $position_id RETURN m", position_id=int(id))
    movie = query_run.evaluate()
    movie['id'] = id
    return make_response(jsonify(movie))

@app.route("/v1/characters/<name>/betrayed_by", methods=["GET"])
def get_betrayls(name: str):
    character = graph.run(
        "MATCH (c:Character) WHERE c.name= $name return c", name=name) .evaluate()
    
    if not character:
        return make_response(
            jsonify(error=f"could not find character with {name}"), 400)
        
    cypher_query = "MATCH (trator)-[betrayed]->(victim:Character{ name:$name }) RETURN trator,betrayed,victim"
    query_result = graph.run(cypher_query, name=name)
    betrayels = [betrayel for betrayel in query_result]

    return make_response(jsonify(betrayels))

if __name__ == '__main__':
    app.run()