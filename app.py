import time
from flask import Flask, request, jsonify, redirect, url_for, render_template

app = Flask(__name__)

@app.route("/")
def home_test():
    print("Hello World")
    return jsonify("Hello World")

@app.route("/methods/mult_by_126/<val>")
def mult_by_126(val):
    val = int(val)
    val *= 126
    return jsonify(val)

@app.route("/methods/add_one/<val>")
def add_one(val):
    val = int(val)
    val += 1
    return jsonify(val)

if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)