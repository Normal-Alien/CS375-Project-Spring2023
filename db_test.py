import sqlite3
from flask import Flask, jsonify, request

app = Flask(__name__)

def query(sql):
  conn = sqlite3.connect('test.db')
  cur = conn.cursor()
  result = cur.execute(sql)
  conn.commit()
  return result

# curl localhost:5000/prepared -X POST -d '{"First": "Bill", "Last": "Mongan", "GradYear": 2005}'
@app.route('/prepared', methods=['POST'])
def insert_prepared_function():
  body = request.get_json(force=True) # {'First': 'Bill', 'Last': 'Mongan', 'GradYear': 2005}; Content-Type: application/json

  firstname = body['First']
  lastname = body['Last']
  gradyear = body['GradYear']

  conn = sqlite3.connect('test.db')
  cur = conn.cursor()
  cur.execute("INSERT INTO Students ('First', 'Last', 'GradYear') VALUES (?, ?, ?)", (firstname, lastname, gradyear))
  conn.commit()

  return jsonify({'result': 'success'}), 200

@app.route('/insert', methods=['GET'])
def insert_function():
  sql = "INSERT INTO Students (First, Last, GradYear) VALUES ('Bill', 'Mongan', 2005)"
  query(sql)

  sql = "INSERT INTO Majors (MajorName) VALUES ('Computer Science')"
  query(sql)

  sql = "INSERT INTO Declarations (StudentID, MajorID) VALUES (1, 1)"
  query(sql)
  
  return jsonify({'result': 'success'}), 200

@app.route('/select', methods=['GET'])
def select_function():
  sql = "SELECT * FROM Students WHERE Last = 'Mongan'"
  
  result = query(sql)
  
  return jsonify(result.fetchall()), 200

@app.route('/join', methods=['GET'])
def join_function():
  sql = "SELECT * FROM Students INNER JOIN Declarations ON Students.ID = Declarations.StudentID INNER JOIN Majors ON Majors.ID = Declarations.MajorID"
  
  result = query(sql)
  
  return jsonify(result.fetchall()), 200

sql = "DROP TABLE IF EXISTS Students"
query(sql)

sql = "DROP TABLE IF EXISTS Majors"
query(sql)

sql = "DROP TABLE IF EXISTS Declarations"
query(sql)

sql = "CREATE TABLE IF NOT EXISTS Students (ID INTEGER PRIMARY KEY, First TEXT, Last TEXT, GradYear INTEGER)"
query(sql)

sql = "CREATE TABLE IF NOT EXISTS Majors (ID INTEGER PRIMARY KEY, MajorName TEXT)"
query(sql)

sql = "CREATE TABLE IF NOT EXISTS Declarations (ID INTEGER PRIMARY KEY, StudentID INTEGER, MajorID INTEGER)"
query(sql)

app.run(host="127.0.0.1", port=5000, debug=True)