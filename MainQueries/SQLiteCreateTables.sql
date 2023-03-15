CREATE TABLE IF NOT EXISTS Store(
	ID int identity(1,1),
	name varchar(20),
	PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS Item(
	ID int identity (1,1),
	Name text,
	Store_ID int,
	Picture image,
	Cost float,
	Taxable bit,
	Item_Addons text,
	Active bit,
	PRIMARY KEY (ID),
	FOREIGN KEY (Store_ID) REFERENCES Store(ID)
);

CREATE TABLE IF NOT EXISTS Addon(
	ID int identity(1,1),
	Store_ID int,
	Cost float,
	PRIMARY KEY (ID),
	FOREIGN KEY (Store_ID) REFERENCES Store(ID)
);

CREATE TABLE IF NOT EXISTS Orders(
	ID int identity(1,1),
	Total_Cost float,
	Subtotal_Cost float,
	PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS Order_Items(
	Item_ID int,
	Order_ID int,
	PRIMARY KEY (Item_ID, Order_ID),
	FOREIGN KEY (Item_ID) REFERENCES Item(ID),
	FOREIGN KEY (Order_ID) REFERENCES Orders(ID),
);

CREATE TABLE IF NOT EXISTS Order_Addons(
	Order_ID int,
	Item_ID int,
	Addon_ID int,
	PRIMARY KEY (Order_ID, Item_ID, Addon_ID),
	FOREIGN KEY (Order_ID) REFERENCES Orders(ID),
	FOREIGN KEY (Item_ID) REFERENCES Item(ID),
	FOREIGN KEY (Addon_ID) REFERENCES Addon(ID)
);