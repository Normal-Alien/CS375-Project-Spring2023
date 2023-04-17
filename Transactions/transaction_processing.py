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

def verify_student_id(student_id_number):
    """
    Compares the given id number to the id number on record and determines if the student has 
    adequate balance for transaction.
    Args
    ----------
    student_id_number: double
        must be X digits long and exist to be verified
    """
    # TODO: Verify that the ID number is associated with the user
    valid_id = True

    # TODO: Verify that the student has enough swipes or other currency
    valid_balance = True

    if valid_id && valid_balance == True:
        res = True
    else:
        # TODO: Prompt 'INVALID ID' if ID does not exist

        # TODO: Prompt 'INVALID BALANCE' if student balance not enough to cover final amount

    return res

# TODO: Return a JSON file with information instead of interacting directly with the database.
def order_receipt(student_id_number, items, total_amount):
    """
    Log user's transaction to the transaction database with relevant information.
    Args
    ----------
    user_id: int
        Student ID number (read from ID card)
    items: list
        Items in student's order
    total_amount: float
        Final amount due (including tax?)
    """

    # TODO: verify that the ID and balance valid
    if verify_student_id(student_id_number) == True:
        conn = sqlite3.connect('transactions.db')
        c = conn.cursor()
        c.execute("INSERT INTO transactions(user_id, items, total_amount, time_purchased) VALUES(?,?,?,?)",
        (user_id,','.join(items), total_amount)) 
        conn.commit()
        conn.close()

# order_receipt(020102,['item1', 'item2', 'item3'], 35.24)
