CREATE TABLE IF NOT EXISTS [Store](
	id int identity(1,1),
	name varchar(20),
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS [Item](
	id int identity (1,1),
	title text,
	store_id int,
	picture image,
	cost float,
	Taxable bit,
	item_addons text,
	active bit,
	PRIMARY KEY (id),
	FOREIGN KEY (store_id) REFERENCES Store(id)
);
CREATE TABLE IF NOT EXISTS [Addon](
	id int identity(1,1),
	title text,
	item_id int,
	cost float,
	PRIMARY KEY (id),
	FOREIGN KEY (item_id) REFERENCES Item(id)
);
CREATE TABLE IF NOT EXISTS [Orders](
	id int identity(1,1),
	total_cost float,
	subtotal_cost float,
	active bit,
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS [Order_Items](
	order_id int,
	item_id int,
	PRIMARY KEY (item_id, order_id),
	FOREIGN KEY (item_id) REFERENCES Item(id),
	FOREIGN KEY (order_id) REFERENCES [Order](id)
);
CREATE TABLE IF NOT EXISTS [Order_Addons](
	order_id int,
	item_id int,
	addon_id int,
	PRIMARY KEY (order_id, item_id, addon_id),
	FOREIGN KEY (item_id) REFERENCES Order_Items(item_id),
	FOREIGN KEY (order_id) REFERENCES Order_Items(order_id),
	FOREIGN KEY (addon_id) REFERENCES Addon(id)
);