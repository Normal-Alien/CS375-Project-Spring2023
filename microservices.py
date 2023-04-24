import time
import sqlite3
from flask import Flask, request, jsonify, abort, make_response
from Item_Entry import *
from sqlite_database import SqliteDb

app = Flask(__name__)

def query_sql(input, database="database.db"):
    """
    Queries an SQL database with an inputted command.
    Useful if you don't want to type out these same exact lines everytime
        you touch an SQL database

    Parameters
    ----------
    input string - command to send to the sql database
    database string = "entries.db" - is the database to be accesssed; defaults to entries database
    
    Return
    ------
    result if the SQL command returns information, it gets returned
    """
    try:
        f = open(database)
    except:
        print("Error: file does not exists")
        abort(400) #if database file DNE, abort out
    sql_conn = sqlite3.connect(database)
    curs = sql_conn.cursor()
    result = curs.execute(input)
    sql_conn.commit()
    sql_conn.close()
    f.close()
    return result

def check_list(arr, item):
    """
    Checks if the given item is in the given array. If it is not, return None.
    Does so by iterating through the loop and setting the return value if an
        array index is found to be equal to the item.

    Somehow, this is not a base python list method...

    Parameters
    ----------
    arr 1D array - array being checked to see if it contains 'item'
    item - object of whatever type that will be compared to each index in 'arr'

    Return
    ------
    ret if 'item' is in 'arr', it returns the value in 'arr' that's equal to 'item'. otherwise, returns zero
    """
    ret = None
    for i in range(len(arr)):
        if arr[i] == item:
            ret = arr[i]
    return ret

@app.route("/", methods=['GET'])
def home_test():
    """
    Acts as a guide to anyone interested in using the microservices associated with
        this file. List has to be updated manually and will give the user a json
        file containing each available method along with what type of microservice
        it is (GET, POST, PUT).

    Return
    ------
    returns a lit of the available microservices and how to access them & a "200 OK" message
    """
    type = ["GET", "GET", "GET", "POST", "GET", "GET", "POST"]
    methods = ["/", "/database/methods/create_db", "/database/methods/query_database/<code>", "/database/methods/add_item_entry", "/database/methods/check_entry/<name>", "/database/methods/print_db", "/database/methods/modify_entry/<name>"]
    ret = []
    for i in range(len(methods)): #makes object that gets sent look nicer
        print(type[i], " : ", methods[i])
        ret.append(str(type[i]) + ":" + str(methods[i]))
    return jsonify(ret, "200")

@app.route("/database/methods/create_db", methods=['GET'])
def create_database():
    """
    Creates/overwrites the entries.db file.

    Return
    ------
    returns an HTTP "200 OK"
    """
    f = open("database.db", "w+")
    print('Database Created/Overwritten')

    selfDb = SqliteDb("database.db")
    selfDb.initDatabase()

    createScript.close()
    f.close()
    return "200"

#dev method due to debugging purposes
@app.route("/database/dev/methods/query_database/<code>", methods=['GET'])
def query_database(code):
    """
    Queries the database with a given SQL command
    
    Primarily for debugging purposes
    
    Return
    ------
    returns an HTTP "200 OK"
    """
    query_sql(code)
    return "200"

#{"data": {"name":"'example'", "store":id_num, "picture":"pic_as_txt", "cost":eg_price, "taxable":0or1, "active":0or1}}
@app.route("/database/methods/add_item_entry", methods=['POST'])
def add_item_entry():
    """
    Queries the SQL database with a command to specifically create the 'Items'
        table if it DNE and then to add the given item json information to the table.

    Parameters
    ----------
    POST - takes in a json object containing item entry information to be added to the database

    Return
    ------
    returns an HTTP "200 OK"
    """
    entry = request.json['data']
    name = entry['name']
    store = entry['store_id']
    pic = entry['picture']
    cost = entry['cost']
    taxable = entry['taxable']
    active = entry['active']

    sql_input = "INSERT INTO Items (name, store, pic, cost, taxable, active) VALUES (" + name + "," + store + "," + pic + "," + cost + "," + taxable + "," + active + ")"
    query_sql(sql_input)

    return "200"

@app.route("/database/methods/add_store_entry", methods=['GET'])
def add_store_entry():
    return "200"

'''
Possible other method for entry input, this method intends to add an entry to any table
that is input as a JSON object
JSON form example:
{"data" :   
    {"table" :  {
                    "tblName" : "nameOfTable"
                    "tblData" : [ "entryData", "moreData", "evenMOARDATA"]
                }
    }
}
'''
@app.route("/database/methods/add_entry", methods=['POST']
def add_entry():
    dataDict = requests.json["data"]
    
    execstmt = "INSERT INTO " + dataDict["table"]["tblName"] + " VALUES ("

    # loop through elements in JSON data
    for element in range(len(dataDict["table"]["tblData"])-1):
        execstmt += dataDict["table"]["tblData"][element] + ", "

    execstmt += dataDict["table"]["tblData"][len(dataDict["table"]["tblData"])-1] + ");"
    query_sql()

    return "200"

@app.route("/database/methods/check_entry/<name>", methods=['GET'])
def check_entry(name):
    '''
    Given an entries name, the SQL database will be checked and the entry
        assosicated with the name will be returned in a json object to the
        requester. If the entry DNE, prints an error and returns an empty
        json object.

    Parameters
    ----------
    name String - the item entry's name that the SQL database will be queried for
    
    Return
    ------
    Returns a json object containing the entries information and an HTTP "200 OK" 
    '''
    query = "SELECT * FROM Items"
    table = query_sql(query).fetchall()
    #ids = index+1
    names = []
    costs = []
    taxable = []
    store = []
    pic = []
    found = False
    ret = None
    for i in range(len(table)):
        names.append(table[i][1])
        costs.append(table[i][2])
        taxable.append(table[i][3])
        store.append(table[i][4])
        pic.append(table[i][5])
        if names[i] == name:
            ret = {"Item Name":names[i],"Item Cost":str(costs[i]),"Store":str(store[i]),"Pic":str(pic[i]),"Taxable":str(taxable[i])}
            found = True
    if not found:
        print("Item not found!")
        abort(404)
    return make_response(jsonify(ret), "200")


@app.route("/database/methods/print_db", methods=['GET'])
def print_db():
    '''
    Prints the entire SQL database

    Return
    ------
    Returns the entire database as a json object and an HTTP "200 OK"
    '''
    query = "SELECT * FROM Items"
    
    array with whole db select statements
    stmts = ["SELECT * FROM Store;", "SELECT * FROM Item;", "SELECT * FROM Addon;", "SELECT * FROM [Order];","SELECT * FROM [Order_Items];" ,"SELECT * FROM [Order_Addons];"]

    table = query_sql(query).fetchall()
    return make_response(jsonify(table), "200")

'''
This method prints the entirety of a database table that is input through the url call
'''
@app.route("database/methods/print_table/<table>", methods=['GET'])
def print_table(table):
    query = "SELECT * FROM " + table

    table = query_sql(query).fetchall()
    return make_response(jsonify(table), "200")

#{"data": {"cost":"6","taxable":"True"}}
@app.route("/database/methods/modify_entry/<name>", methods=['POST'])
def modify_entry(name):
    '''
    Modifies an entry in the SQL database based on the given name
        and by the POST information. Not all parameters in the object
        need be modified.
    
    Parameters
    ----------
    name String - the name of the entry in the SQL database to be modified
    POST - json object containing the parameters of the entry that are being updated

    Return
    ------
    Returns an HTTP "200 OK"
    '''
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
    if check_list(arr, 'taxable'): #only the most beautiful of code
        mod_tax = input['taxable']
    else:
        mod_tax = entry[0][3]
    if check_list(arr, 'store'):
        mod_store = input['store']
    else:
        mod_store = entry[0][4]
    if check_list(arr,'pic'):
        mod_pic = input['pic']
    else: 
        mod_pic = entry[0][5]

    #send UPDATE request to SQL database
    query = "UPDATE Items SET Name='" + str(mod_name) + "',Cost=" + str(mod_cost) + ",Store=" + str(mod_store) + ",Pic=" + str(mod_pic) + ",Taxable=" + str(mod_tax) + " WHERE ID=" + str(entry[0][0])
    query_sql(query)
    return "200"


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)
