"""
File specifically for storing presently unused methods in case of an arisen need to reimplement them



##### Former Microservice Methods

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



''' <-- formerly triple quote comment block
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
@app.route("/database/methods/add_entry", methods=['POST'])
def add_entry():
    dataDict = request.json["data"]
    
    execstmt = "INSERT INTO " + dataDict["table"]["tblName"] + " VALUES ("

    # loop through elements in JSON data
    for i in range(len(dataDict["table"]["tblData"])):
        execstmt += dataDict["table"]["tblData"][i]
        if i < len(dataDict["table"]["tblData"]) - 1:
            execstmt += ", "
    execstmt += ");"
    query_sql(execstmt)

    return "200"




@app.route("/database/methods/add_store_entry", methods=['GET'])
def add_store_entry():
    return "200"




#{"data": {"name":"'example'", "store":id_num, "picture":"pic_as_txt", "cost":eg_price, "taxable":0or1, "active":0or1}}
@app.route("/database/methods/add_item_entry", methods=['POST'])
def add_item_entry():
    ''' <-- former comment block
    Queries the SQL database with a command to specifically create the 'Items'
        table if it DNE and then to add the given item json information to the table.

    Parameters
    ----------
    POST - takes in a json object containing item entry information to be added to the database

    Return
    ------
    returns an HTTP "200 OK"
    '''
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















"""