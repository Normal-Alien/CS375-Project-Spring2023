import sqlite3
import json

# database file name must be entered with '.db' file name extension
class SqliteDb():
    
    #boostrapping for db initialization
    def __init__(self, dbPath):
        self.dbPath = dbPath

    #Perform bootstrapping tasks for the database
    def initDatabase(self):
        #database script to create database
        createScript = open("MainQueries/SQLiteCreateTables.sql", "r")
        conn = self.openConnection()
        cursor = conn.cursor()

        stmtArr=createScript.read().split(";")
        for stmt in stmtArr:
            cursor.execute(stmt + ";")

        createScript.close()
        conn.commit()
        conn.close()

    def openConnection(self):
        conn = sqlite3.connect(self.dbPath)
        #remember to close the database connection
        return conn

    # This method executes queries
    # queries here must be formed into an array or list
    # ex.
    # query = ["query1", "query2"]
    def queryDb(self, queryArr):
        conn = self.openConnection()
        cursor = conn.cursor()
        cursReturn = []

        for stmt in queryArr:
            cursor.execute(stmt)
            cursReturn.append(cursor.fetchall())
        

        conn.commit()
        conn.close()
        return cursReturn

    # prints all data entries from all tables of the database
    def printDb(self):
        stmts = ["SELECT * FROM Store;", "SELECT * FROM Item;", "SELECT * FROM Addon;", "SELECT * FROM [Order];","SELECT * FROM [Order_Items];" ,"SELECT * FROM [Order_Addons];"]
        print(self.queryDb(stmts))
    
    # WARNING: this method assumes that the input data already has the proper amount of data fields in the proper data types
    # data should be organized into a python dictionary
    # example below:
    #           {"table" :  {
    #                           "tblName" : "name of table"
    #                           "tblData" : { "entryData", "moreData", "evenMOARDATA" }
    #                       }
    #           }
    # only single entries to a single table are allowed to be input
    def insertEntry(self, data):
        conn = sqlite3.connect(self.dbPath)
        dataDict = json.loads(data)
        cursor = conn.cursor()
        execstmt = "INSERT INTO " + dataDict["table"]["tblName"] + " VALUES ("

        for element in range(len(dataDict["table"]["tblData"])-1):
            execstmt += dataDict["table"]["tblData"][element] + ", "

        #for item in dataDict["table"]["tblData"]:
        #    execstmt += item + ", "

        execstmt += dataDict["table"]["tblData"][len(dataDict["table"]["tblData"])-1] + ");"
        print(execstmt)
        cursor.execute(execstmt)

        conn.commit()
        conn.close()

