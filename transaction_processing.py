import sqlite3
import datetime

def make_temp_db():
    """
    Temporary database for transaction handling pseudocode. To be replaced with the main database, whenever it's finished.
    """
    conn = sqlite3.connect(transactions.db)
    c = conn.cursor()
    c.execute('''CREATE TABLE IF NOT EXISTS transactions (
        user_id INTEGER,
        items TEXT,
        total_amount REAL
    )''')
    conn.commit()
    conn.close()

def add_transaction(user_id, items, total_amount):
    """
    Log user's transaction to the transaction database with relevant information.
    Parameters
    ----------
    user_id: int
        Student ID number (read from ID card)
    items: list
        Items in student's order
    total_amount: float
        Final amount due (including tax?)
    """
    # time_purchased = datetime.now()
    conn = sqlite3.connect('transactions.db')
    c = conn.cursor()
    c.execute("INSERT INTO transactions(user_id, items, total_amount, time_purchased) VALUES(?,?,?,?)",
    (user_id,','.join(items), total_amount)) 
    conn.commit()
    conn.close()

# add_transaction(020102,['item1', 'item2', 'item3'], 35.24)
