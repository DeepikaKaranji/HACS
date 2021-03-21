from flask import Flask
import json
import requests

app = Flask(__name__)
@app.route("/api/v1/read", methods = ["GET"])
def get_dataStore_dump():
    with open("rules.json") as f:
        data = json.load()
        return data

if __name__ =="__main__":
    app.debug = True
    app.run()
