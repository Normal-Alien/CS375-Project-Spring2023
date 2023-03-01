import time
import sqlite3
from flask import Flask, request, jsonify, abort, make_response

app = Flask(__name__)

def query_sql(input):
    f = open("entries.db")
    if not f:
        abort(400)
    sql_conn = sqlite3.connect("entries.db")
    curs = sql_conn.cursor()
    result = curs.execute(input)
    sql_conn.commit()
    return result

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

@app.route("/database/methods/create_db")
def create_database():
    f = open("entries.db", "w+")
    print('Database Created/Overwritten')
    f.close()
    return "200"

#{"data": {"name":"example", "cost":"eg_price", "taxable":True}}
@app.route("/database/methods/add_entry", methods=['POST'])
def add_entry():
    entry = request.json['data']
    name = entry['name']
    cost = entry['cost']
    taxable = entry['taxable']
    sql_input = "CREATE TABLE IF NOT EXISTS Items (ID Integer PRIMARY KEY, Name TEXT, Cost INTEGER, Taxable BOOLEAN)"
    query_sql(sql_input)
    sql_input = "INSERT INTO Items (Name, Cost, Taxable) VALUES (" + name + "," + cost + "," + taxable + ")"
    query_sql(sql_input)

    return "200"

@app.route("/database/methods/check_entry/<name>", methods=['GET'])
def check_entry(name):
    query = "SELECT * FROM Items"
    table = query_sql(query).fetchall()
    #ids = index+1
    names = []
    costs = []
    taxable = []
    found = False
    for i in range(len(table)):
        names.append(table[i][1])
        costs.append(table[i][2])
        taxable.append(table[i][3])
        if names[i] == name:
            ret = {"Item Name":names[i],"Item Cost":str(costs[i]),"Taxable?":str(taxable[i])}
            found = True
    if not found:
        print("Item not found!")
    return make_response(jsonify(ret), "200")


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)