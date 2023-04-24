from item_class import * #Item entry, addon, and order classes
import requests
import json

STORE_IPS = {"tres":"256.256.256.256", "grill":"255.255.255.255", "example":"localhost"}
PORT = 4126
connect = "http://localhost:5000"

class menu:
    def __init__(items):
        self.items = items

def load_menu():
    #connects with database to fetch all categories
    #connects to front-end to display category options
 
 
    order = Order()
    pass

def load_category(full_menu, category):
    ret = []
    for item in full_menu.items:
        for cat in item.categories:
            if cat == category:
                ret.append(item)
    #front-end GUI stuff here


    return ret

def load_item(item):
    #GUI front-end stuff here
    
    
    pass
    
def add_to_order(order, item):
    #GUI front-end request for addons


    order.add_item(item)
    return order

def compile_order(order):
    #GUI stuff to load checkout
    order.compile_payments(order.items)
    
    pass

def send_order(order):
    #fetch a list of stores that the items in the order need to be sent to
    #basically, if there's more than one store in the order then make a list of stores to communicate with
    stores = []
    
    #add the order to the database
    
    #fetch its primary key to use as the order ID
    
    #encrypt the order with the newly added primary key
    
    order.jsonify()
    #connect to the appropriate store(s)
    for store in stores:
        response = requests.post(connect, data = None, json = order)
        pass #connect to each store 1-by-1 to send order to all needed
    
    #catch acknowledgements from each store