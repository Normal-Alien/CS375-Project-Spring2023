class Item_Entry:
    def __init__(self, name, store, picture, cost, taxable, addons, active):
        self.name = name
        self.store = store #assumed to be a store object
        self.picture = picture
        self.cost = cost
        self.taxable = taxable
        self.addons = addons #assumed to be addon objects
        self.active = active

class Addon:
    def __init__(self, name, store, cost):
        self.name = name
        self.store = store #assumed to be a store object
        self.cost = cost

class Store:
    def __init__(self, name):
        self.name = name

class Order:
    def __init__(self, items):
        self.items = items #assumed to be item objects
        self.total_tax, self.subtotal, self.total = self.compile_payment(items)
    def compile_payment(items):
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
        return tax_total, subtotal, total