
class Item_Entry:
    def __init__(self, name, store, picture, cost, taxable, addons, categories, active):
        self.name = name
        self.store = store #assumed to be a store object
        self.picture = picture
        self.cost = cost
        self.taxable = taxable
        self.addons = addons #assumed to be addon objects
        self.categories = categories #tres_sides, tres, etc.; is an array
        self.active = active

class Addon:
    def __init__(self, name, store, cost):
        self.name = name
        self.cost = cost

class Order:
    def __init__(self):
        self.items = [] #assumed to be item objects
        self.total_tax, self.subtotal, self.total = 0
        self.ID = None
    def compile_payment(self, items):
        tax_total = 0
        subtotal = 0
        cur_item = 0
        for item in items:
            cur_item = 0
            for addon in item.addons:
                cur_item += addon.cost
            cur_item += item.cost
            subtotal += cur_item
            if item.taxable:
                tax_total += subtotal * 0.06
        total = subtotal + tax_total
        self.total_tax = tax_total
        self.subtotal = subtotal
        self.total = total
    def add_item(self, item):
        self.items.append(item)
        self.compile_payment(self.items)