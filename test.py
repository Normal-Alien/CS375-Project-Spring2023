import requests

host = "http://localhost:5000/methods"

method = input("Enter method to use: ")
method = "/" + method + "/"
io = input("Enter method input: ")
req = host + method + str(io)
response = requests.get(req)
print(response.json())