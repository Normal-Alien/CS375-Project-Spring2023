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
        conn = sqlite3.connect(self.dbPath)
        cursor = conn.cursor()

        stmtArr=createScript.read().split(";")
        for stmt in stmtArr:
            print(stmt + ";")
            cursor.execute(stmt + ";")

        createScript.close()
        conn.close()

    def openConnection(self):
        conn = sqlite3.connect(self.dbPath)
        cursor = conn.cursor()
        #remember to close the database connection
        return conn

    #queries here must be formed into an array
    def queryDb(self, queryArr):
        conn = sqlite3.connect(self.dbPath)
        cursor = conn.cursor()

        for stmt in queryArr:
            cursor.execute(stmt)

        conn.close()

    #prints all data entries from all tables of the database
    def printDb(self, conn):
        stmts = ['''Select * from Store;''', '''Select * from Item;''', '''Select * from Addon;''', '''Select * from [Order];''','''Select * from [Order_Items];''' ,'''Select * from [Order_Addons];''']
        self.queryDb(stmts)
    
    #this assumes that the input data already has the proper amount of data fields in the proper data types
    #data should be organized into a python dictionary
    #like below:
    #   {"data" :
    #           "table" : {
    #               "tblName" : { "tblData" : {}
    #               },
    #               "otherTbl" : { "tblData" : {}
    #               }
    #           }
    #   }
    def insertEntry(self, data):
       conn = sqlite3.connect(self.dbPath)
       cursor = conn.cursor()

       cursor.execute("INSERT INTO " + data["data"]["table"] + ";")
