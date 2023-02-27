
class Item_Class:
    
    def __init__(self, name, cost, path, time, taxable, image, options):
        self.name = name
        self.cost = cost
        self.category_path = path
        self.sale_time = time
        self.isTaxable = taxable
        self.image = image
        self.options = options
        
    def set_Name(self, name):
        self.name = name
    
    def set_Cost(self, cost):
        self.cost = cost
        
    def set_Path(self, path):
        self.category_path = path
        
    def set_Time(self, Time):
        self.sale_time = Time
        
    def set_Taxable(self, taxable):
        self.isTaxable = taxable
        
    def set_Image(self, image):
        self.image = image
        
    def set_Options(self, options):
        self.options = options
    
    def get_Name(self):
        return self.name
        
    def get_Cost(self):
        return self.cost
    
    def get_Path(self):
        return self.category_path
    
    def get_Time(self):
        return self.sale_time
    
    def get_Taxable(self):
        return self.isTaxable
    
    def get_Image(self):
        return self.image
    
    def get_Options(self):
        return self.options
    
        
class Options:
    
    def __init__(self, name, cost):
        self.name = name
        self.cost = cost
        
    def set_Name(self, name):
        self.name = name
    
    def set_Cost(self, cost):
        self.cost = cost
    
    def get_Name(self):
        return self.name
        
    def get_Cost(self):
        return self.cost
    
