import csv
import random
import os
from datetime import datetime

def generate_test_dataset(filename="src/dataset/dataset.csv", num_records=100):
    """
    Generates a mock CSV dataset containing transaction histories for testing.
    Highly optimized to handle large-scale benchmarks (like 10M+ rows) efficiently.
    """
    # Define realistic categories and matching descriptions based on transaction type
    deposits = [
        ("Pocket Money", ["Monthly allowance from parents", "Weekly pocket money"]),
        ("Gift", ["Birthday gift from family", "Holiday present"]),
        ("Refund", ["Refund from online bookstore", "Subscription refund"]),
        ("Rewards", ["Academic achievement prize", "Cashback reward"])
    ]

    expenses = [
        ("Food", ["Cafeteria lunch", "Pizza party with friends", "Snacks and drinks"]),
        ("Books", ["School textbook purchase", "Comic book collection", "Reference guide"]),
        ("Entertainment", ["Movie ticket", "Online game pass subscription", "Board game"]),
        ("Transport", ["Monthly bus pass", "Train fare to campus", "Bicycle repair"]),
        ("Rent", ["Hostel fee", "Shared room rent"])
    ]

    # Pre-determine folder path to ensure directories exist
    dir_name = os.path.dirname(filename)
    if dir_name and not os.path.exists(dir_name):
        os.makedirs(dir_name, exist_ok=True)

    print(f"Generating {num_records:,} mock transactions in '{filename}'...")

    # OPTIMIZATION: Use pure integer millisecond math instead of creating 10M+ datetime objects
    start_time = datetime(2025, 7, 1, 9, 0, 0)
    epoch = datetime(1970, 1, 1)
    start_ms = int((start_time - epoch).total_seconds() * 1000)
    
    # Target a total timeline span of exactly 2 years in milliseconds (63,072,000,000 ms)
    total_span_ms = 63072000000
    avg_interval_ms = max(1, total_span_ms // num_records)
    
    # Create sensible randomization bounds around the average millisecond interval
    min_interval_ms = max(1, int(avg_interval_ms * 0.1))
    max_interval_ms = max(2, int(avg_interval_ms * 1.9))

    # Pre-cache random choices to optimize loop lookups
    random_random = random.random
    random_choice = random.choice
    random_randint = random.randint
    random_uniform = random.uniform

    try:
        # Buffer writes in memory and write in batches to speed up I/O operations
        batch_size = 100000
        row_buffer = []
        current_ms = start_ms

        # Determine dynamic padding length for ID depending on the total record size
        id_padding = max(4, len(str(num_records)))

        with open(filename, mode='w', newline='', encoding='utf-8', buffering=1024*1024) as file:
            writer = csv.writer(file)
            # Write the header line
            writer.writerow(["id", "type", "amount", "category", "description", "timestamp"])
            
            for i in range(1, num_records + 1):
                txn_id = f"TXN{i:0{id_padding}d}"
                
                # Decide transaction type (roughly 70% Expenses, 30% Deposits)
                is_deposit = random_random() < 0.3
                
                if is_deposit:
                    txn_type = "DEPOSIT"
                    category, desc_options = random_choice(deposits)
                    amount = round(random_uniform(10.00, 500.00), 2)
                else:
                    txn_type = "EXPENSE"
                    category, desc_options = random_choice(expenses)
                    if category == "Rent":
                        amount = round(random_uniform(150.00, 1000.00), 2)
                    else:
                        amount = round(random_uniform(2.50, 75.00), 2)
                
                description = random_choice(desc_options)
                
                # Increment the timeline sequentially in pure milliseconds
                current_ms += random_randint(min_interval_ms, max_interval_ms)
                
                # Append to current memory write buffer
                row_buffer.append([txn_id, txn_type, amount, category, description, current_ms])
                
                # Periodically write batch to disk to prevent RAM bloat
                if i % batch_size == 0:
                    writer.writerows(row_buffer)
                    row_buffer.clear()
                    print(f" -> Progress: {i:,} / {num_records:,} transactions created...")

            # Write any remaining records left in the buffer
            if row_buffer:
                writer.writerows(row_buffer)
                row_buffer.clear()
                
        print("Dataset generated successfully!")
        
    except FileNotFoundError:
        # Fallback directly to current working folder if target directory setup fails
        fallback_path = os.path.basename(filename)
        print(f"Target directory path not found. Saving directly to '{fallback_path}' instead.")
        generate_test_dataset(filename=fallback_path, num_records=num_records)

if __name__ == "__main__":
    try:
        user_input = input("Enter the number of transaction records to generate (e.g. 150): ").strip()
        if not user_input:
            print("No value entered. Defaulting to 100 records.")
            num_records = 100
        else:
            # Strip out any commas if the user enters formatted numbers like 10,000,000
            num_records = int(user_input.replace(",", ""))
            if num_records <= 0:
                print("The number must be greater than 0. Defaulting to 100 records.")
                num_records = 100
    except ValueError:
        print("That wasn't a valid whole number! Defaulting to 100 records.")
        num_records = 100

    # Execute generator
    generate_test_dataset(num_records=num_records)