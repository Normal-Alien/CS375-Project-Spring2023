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

def check_list(arr, item):
    #how is this not a base python list method?????
    ret = None
    for i in range(len(arr)):
        if arr[i] == item:
            ret = arr[i]
    return ret

@app.route("/", methods=['GET'])
def home_test():
    type = ["GET", "GET", "GET", "GET", "POST", "GET", "GET", "POST"]
    methods = ["/methods/mult_by_126/<val>", "/methods/add_one/<val>", "/", "/database/methods/create_db", "/database/methods/add_entry", "/database/methods/check_entry/<name>", "/database/methods/print_db", "/database/methods/modify_entry/<name>"]
    ret = []
    for i in range(len(methods)):
        print(type[i], " : ", methods[i])
        ret.append(str(type[i]) + ":" + str(methods[i]))
    return jsonify(ret, "200")

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

#{"data": {"variables": "'sql_code_here'"}}
@app.route("/database/methods/add_table", methods=['POST'])
def add_table():
    data = request.json['data']
    sql = data['']
    sql_input = "" 
    query_sql(sql_input)
    return "200"

#{"data": {"name":"'example'", "cost":"eg_price", "taxable":"True"}}
@app.route("/database/methods/add_item_entry", methods=['POST'])
def add_item_entry():
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

@app.route("/database/methods/print_db", methods=['GET'])
def print_db():
    query = "SELECT * FROM Items"
    table = query_sql(query).fetchall()
    return make_response(jsonify(table), "200")

#{"data": {"cost":"6","taxable":"True"}}
@app.route("/database/methods/modify_entry/<name>", methods=['POST'])
def modify_entry(name):
    #fetch item
    query = "SELECT * FROM Items WHERE Name='" + name + "'"
    entry = query_sql(query).fetchall()

    #process POST requests
    input = request.json['data']
    arr = list(input.keys())
    if check_list(arr, 'name'):
        mod_name = input['name']
    else:
        mod_name = entry[0][1]
    if check_list(arr, 'cost'):
        mod_cost = input['cost']
    else:
        mod_cost = entry[0][2]
    if check_list(arr, 'taxable'):
        mod_tax = input['taxable']
    else:
        mod_tax = entry[0][3]
    
    #send UPDATE request to SQL database
    query = "UPDATE Items SET Name='" + str(mod_name) + "',Cost=" + str(mod_cost) + ",Taxable=" + str(mod_tax) + " WHERE ID=" + str(entry[0][0])
    query_sql(query)

    return "200"


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)
