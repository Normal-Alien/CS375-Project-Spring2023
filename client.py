import requests
import json

#example options: "/database/methods/add_entry"
#example json: "{"data":{"name":"'burrito_bowl'", "cost":"7", "taxable":"True"}}"

ip = input("Please enter the IP to connect to: ")
port = input("Please enter the port of the IP: ")
host = "http://" + ip + ":" + port
rest = input("Please enter the url options: ")
url = host + rest
post = input("What type of request is this? (POST/GET) ")
if post == "POST":
    io = input("Please enter your json data: ")
    json_conv = json.loads(io)
    response = requests.post(url, data={}, json=json_conv)
    print(response.text)
else:
    response = requests.get(url)
    print(response.text)
