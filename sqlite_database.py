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

        self.queryDb(createScript.read().split(";"))

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

    def clearDb(self):
        return
    
    # Method may not be in the final project
    # WARNING: this method assumes that the input data already has the proper amount of data fields 
    # in the proper data types
    # data should be organized into a python dictionary
    # example below:
    #           {"table" :  {
    #                           "tblName" : "nameOfTable"
    #                           "tblData" : [ "entryData", "moreData", "evenMOARDATA"]
    #                       }
    #           }
    # only single entries to a single table are allowed to be input
    # code here to be used in microservices file
    def insertEntry(self, data):
        return

    # this method is to be used if there will be multiple entries added to a single table in the sqlite db
    # ex of JSON data:
    #   {"data" { "table1" : {
    #                           "tblName: "nameOfTable"
    #                           "tblData: {["entry1Data"],["entry2Data"],["entry3Data"]}
    #                        }
    #             "table2" : {"sameFormatAsTable1"}
    #           }
    #   }
    # this will (likely) use the cursor.executemany() method
    def insertMulEntry():
        return
