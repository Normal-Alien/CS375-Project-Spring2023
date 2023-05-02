import time
from datetime import date
import sqlite3
from flask import Flask, request, jsonify, abort, make_response
from Item_Entry import *
from sqlite_database import SqliteDb
from waitress import serve
import shutil
import os

app = Flask(__name__)

class Order:
    def __init__(self, id):
        self.id = id
        self.cost = None
        self.stores = None #array of stores the order accesses
        self.items = None #array of items the order contains
        
class Item:
    def __init__(self, id, name):
        self.id = id
        self.name = name
        self.addons = None #array of addons the Item has applied

class Addon:
    def __init__(self, id, name):
        self.id = id
        self.name = name

def query_sql(input, database="database.db"):
    """Queries an SQL database with an inputted command.
    Useful if you don't want to type out these same exact lines everytime
        you touch an SQL database

    Args:
        input (string): command to send to the sql database
        database (string) = "entries.db": is the database to be accesssed; defaults to entries database
    
    Returns:
        SQL ret: result if the SQL command returns information, it gets returned
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
    f.close()
    return result

def check_list(arr, item):
    """Checks if the given item is in the given array. If it is not, return None.
    Does so by iterating through the loop and setting the return value if an
        array index is found to be equal to the item.

    Somehow, this is not a base python list method...

    Args:
        arr (1D array): array being checked to see if it contains 'item'
        item (???): object of whatever type that will be compared to each index in 'arr'

    Returns:
        ret (same as 'item'): if 'item' is in 'arr', it returns the value in 'arr' that's equal to 'item'. otherwise, returns zero
    """
    ret = None
    for i in range(len(arr)):
        if arr[i] == item:
            ret = arr[i]
    return ret

@app.route("/", methods=['GET'])
def home_test():
    """Acts as a guide to anyone interested in using the microservices associated with
        this file. List has to be updated manually and will give the user a json
        file containing each available method along with what type of microservice
        it is (GET, POST, PUT).

    Return
    ------
    returns a lit of the available microservices and how to access them & a "200 OK" message
    """
    print("Function Listing Call")
    
    type = ["GET", "GET", "GET", "GET", "GET", "GET", "GET", "POST", "GET", "GET"] #parallel to methods
    methods = ["/", "/database/methods/create_db", "/database/methods/dump_db", "/database/methods/view_old_orders/<date>", "/database/methods/query_database/<code>", "/database/methods/fetch_active_orders", "/database/methods/rm_order/<ID>", "/database/methods/add_entries", "/database/methods/print_db/", "/database/methods/print_table/<table>"]
    ret = []
    for i in range(len(methods)): #makes object that gets sent look nicer
        ret.append(str(type[i]) + ":" + str(methods[i]))
    return jsonify(ret, "200")

@app.route("/database/methods/create_db", methods=['GET'])
def create_db():
    """Creates/overwrites the database.db file

    Returns:
        http_response: returns an http "OK"
    """
    print("Database Creation/Overwriting Call")
    
    f = open("database.db", "w+")
    selfDb = SqliteDb("database.db")
    selfDb.initDatabase()
    
    f.close()
    return "200"

@app.route("/database/methods/dump_db", methods=['GET'])
def dump_db():
    """Makes a copy of the current database and empties it

    Returns:
        http response: returns a copy of the current database and an http "OK"
    """
    response = print_db()
    today = date.today()
    today = str(today)
    renam = "database-" + today + ".db"
    shutil.copy('database.db',renam)
    query = "DROP TABLE Orders; DROP TABLE Order_Items; DROP TABLE Order_Addons;"
    f = open("MainQueries/SQLiteCreateTables.sql", "r")
    content = f.readlines()
    for line in content:
        query += str(line)
    queries = query.split(';')
    for quer in queries:
        query_sql(quer)
    f.close()
    return response

@app.route("/database/methods/view_old_orders/<date>", methods=['GET'])
def view_old_orders(date):
    """Prints a backup/copy of an older database based on the date it was made

    Args:
        date (string): 'YEAR-MONTH-DAY' format; the date the database was dumped

    Returns:
        http response: Effectively the same as "print_db" but prints an older database copy rather than the current live database
    """
    print("Print Old Database Call")
    
    database = "database-" + date + ".db"
    db = {}
    
    #array with whole db select statements
    queries = ["SELECT * FROM Store;", "SELECT * FROM Item;", "SELECT * FROM Addon;", "SELECT * FROM Orders;","SELECT * FROM Order_Items;" ,"SELECT * FROM Order_Addons;"]
    table_names = ["Store","Item","Addon","Orders","Order_Items","Order_Addons"] #parallel to queries
    for i in range(len(queries)):
        cur_table = query_sql(queries[i], database).fetchall()
        db.update({table_names[i]:cur_table})
    
    return make_response(jsonify(db), "200")

@app.route("/database/dev/methods/query_database/<code>", methods=['GET'])
def query_database(code):
    """
    Queries the database with a given SQL command; primarily for debugging purposes
    
    Returns:
        http_response: returns an http "OK"
    """
    print("Database Query Call")
    
    query_sql(code)
    return "200"

@app.route("/database/methods/fetch_active_orders", methods=['GET'])
def fetch_active_orders():
    """Sorts through the orders in the database to gather the active orders
    and then returns the active orders and their items and addons
    
    Final output:
    {[Order_ID:[
            ###Stores:[store_id,store_id,etc.] ###probably not gonna be used
            [Item_ID:
                Addon_ID:[addon name]
                Addon_ID_2:[addon name]
                etc.
            Item_ID_2:
                Addon_ID:[addon name]
                etc.
            ]
        ]
    Order_ID_2:etc.
    ]}
    Compressed: {[Order_ID:[Cost:price,Stores:[store_id,store_id,etc.],[Item_ID:[Addon_ID:addon name,Addon_ID_2:addon name,etc.],Item_ID_2:[Addon_ID:addon name],etc.]],Order_ID_2:etc.]}

    Returns:
        http_response: json object containing the active orders and their items and addons, and an http "OK"
    """
    print("Fetch Active Orders Call")
    
    #tables to access: Orders, Order_Items, Order_Addons, Items, Addons, Stores
    #methods to access: transaction calculation
    
    select =        "SELECT Orders.id AS Orders, Item.title AS Item, Addon.title AS Addon "
    int =           "FROM (Orders "
    inner_join1 =   "INNER JOIN Order_Items ON Orders.id = Order_Items.order_id) "
    inner_join2 =   "INNER JOIN Item ON Item.id = Order_Items.item_id "
    join1 =         "LEFT JOIN Order_Addons ON Order_Addons.order_id = Orders.id "
    join2 =         "LEFT JOIN Addon ON Order_Addons.addon_id = Addon.id "
    where =         "WHERE Orders.active = 1 "
    rest =          "ORDER BY Orders.id, Item.id ASC;"
    query = select + int + inner_join1 + inner_join2 + join1 + join2 + where + rest
    ret = {}
    
    ret = query_sql(query).fetchall()
    print(ret)
    return make_response(jsonify(ret), "200")

@app.route("/database/methods/rm_order/<ID>", methods=['GET'])
def rm_order(ID):
    """Updates an order in the database to be set to inactive (as in the order is complete)

    Returns:
        http_response: 200 "OK"
    """
    print("Deactivate Order Call")
    
    query = "UPDATE Orders SET active=False WHERE ID=" + str(ID)
    query_sql(query)
    return "200"

@app.route("/database/methods/add_entries", methods=['POST'])
def add_entries():
    """Adds one or more entries to the SQL database based on their information in the json object
    JSON information should be organized as seen:
    {"data" :
        [
            {"[name of table]":
                [ "row 1 data", "row 2 data", "row etc data"]
            },
            
            {"[name of second table]":
                ["data",etc.]
            },
            
            {etc
                etc
            }
        ]
    }
    Compressed Example:
    eg: {"data":[{"Item":["0","'example'","1","10101","8.00","True","'[1,2]'","True"]},{"Item":["1","'test'","2","11100","7.50","True","'[0,1]'","True"]}]}

    Returns:
        http_response: 200 "OK"
    """
    print("Add Entries Call")
    
    dataDict = request.json["data"]
    dataDict = list(dataDict)
    
    #loop through each table
    for i in range(len(dataDict)):
        query = "INSERT INTO " + str(list(dataDict[i].keys())[0]) + " VALUES ("

        arr = dataDict[i].get(list(dataDict[i].keys())[0])
        #loop through each table's data entries
        for i in range(len(arr)):
            query += arr[i]
            if i < len(arr) - 1:
                query += ", "
        query += ");"
        query_sql(query)

    return "200"

@app.route("/database/methods/print_db", methods=['GET'])
def print_db():
    """Prints the entire SQL database as a dictionary with each table having its name as its key

    Returns:
        http_response: Returns the entire database as a json object (which gets unpacked to a python dictionary) and an HTTP "200 OK"
    """
    print("Print Database Call")
    
    db = {}
    
    #array with whole db select statements
    queries = ["SELECT * FROM Store;", "SELECT * FROM Item;", "SELECT * FROM Addon;", "SELECT * FROM Orders;","SELECT * FROM Order_Items;" ,"SELECT * FROM Order_Addons;"]
    table_names = ["Store","Item","Addon","Orders","Order_Items","Order_Addons"] #parallel to queries
    for i in range(len(queries)):
        cur_table = query_sql(queries[i]).fetchall()
        db.update({table_names[i]:cur_table})
        
    return make_response(jsonify(db), "200")

@app.route("/database/methods/print_table/<table>", methods=['GET'])
def print_table(table):
    """Prints a specific table from the SQL database

    Args:
        table (string): the name of the table to be printed

    Returns:
        http_response: returns the table as a json object along with an "OK"
    """
    print("Print Table Call")
    
    query = "SELECT * FROM " + table
    table = query_sql(query).fetchall()
    return make_response(jsonify(table), "200")


if __name__ == '__main__':
    serve(app, host='0.0.0.0')