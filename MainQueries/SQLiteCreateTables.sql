CREATE TABLE IF NOT EXISTS [Store](
	id int identity(1,1),
	name varchar(20),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS [Item](
	id int identity (1,1),
	Name text,
	Store_id int,
	Picture image,
	Cost float,
	Taxable bit,
	Item_Addons text,
	Active bit,
	PRIMARY KEY (id),
	FOREIGN KEY (store_id) REFERENCES Store(id)
);

CREATE TABLE IF NOT EXISTS [Addon](
	id int identity(1,1),
	store_id int,
	cost float,
	PRIMARY KEY (id),
	FOREIGN KEY (store_id) REFERENCES Store(id)
);

CREATE TABLE IF NOT EXISTS [Order](
	id int identity(1,1),
	total_cost float,
	subtotal_cost float,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS [Order_Items](
	order_id int,
	item_id int,
	PRIMARY KEY (item_id, order_id),
	FOREIGN KEY (item_id) REFERENCES Item(id),
	FOREIGN KEY (order_id) REFERENCES [Order](id),
);

CREATE TABLE IF NOT EXISTS [Order_Addons](
	order_id int,
	item_id int,
	addon_id int,
	PRIMARY KEY (order_id, item_id, addon_id),
	FOREIGN KEY (item_id, order_id) REFERENCES Order_Items(item_id, order_id),
	FOREIGN KEY (addon_id) REFERENCES Addon(id)
);
