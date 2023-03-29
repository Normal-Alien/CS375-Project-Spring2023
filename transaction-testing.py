import unittest
import os
from transaction_processing import add_transaction

class TestTransactions(unittest.TestCase):
    def make_db(self):
        make_temp_db()
    
    def test_add_transaction(self):
        """
        Test adding a transaction to the transaction.db database with user_id, list of items, and total_amount.
        """
        user_id = 123456789
        items = ['apple', 'water', 'sandwhich']
        total_amount = 20.32
        add_transaction(user_id, items, total_amount)
        conn = sqlite3.connect('transactions.db')
        c = conn.cursor()
        c.execute("SELECT * FROM transactions WHERE user_id=?", (user_id,))
        transaction_data = c.fetchone()
        conn.close()
        self.assertIsNotNone(transaction_data)
        self.assertEqual(len(transaction_data), 4)
        self.assertEqual(transaction_data[0], user_id)
        self.assertEqual(transaction_data[1],','.join(items))
        self.assertAlmostEqual(transaction_data[2], total_amount)

    def clean_temporary_db(self):
        """
        Removes the temporary transactions.db database from system after unit testing finished.
        """
        os.remove('transactions.db')

    if __name__ == '__main__':
        unittest.main()
