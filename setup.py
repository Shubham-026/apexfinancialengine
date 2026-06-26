import os
import csv
import random
from datetime import datetime, timedelta

def get_dataset_dir():
    """Returns the path to the dataset directory and ensures it exists."""
    path = os.path.join("src", "dataset")
    if not os.path.exists(path):
        os.makedirs(path)
    return path

def generate_mock_transactions(count, start_id_num=1):
    """Generates a list of mock transaction rows."""
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

    start_time = datetime(2025, 7, 1, 9, 0, 0)
    current_time = start_time
    rows = []

    for i in range(start_id_num, start_id_num + count):
        txn_id = f"TXN{i:04d}"
        is_deposit = random.random() < 0.3
        txn_type = "DEPOSIT" if is_deposit else "EXPENSE"

        if is_deposit:
            category, desc_options = random.choice(deposits)
            amount = round(random.uniform(10.00, 500.00), 2)
        else:
            category, desc_options = random.choice(expenses)
            if category == "Rent":
                amount = round(random.uniform(150.00, 1000.00), 2)
            else:
                amount = round(random.uniform(2.50, 75.00), 2)

        description = random.choice(desc_options)

        # Increment timestamp incrementally to ensure strict chronological order
        minutes_to_add = random.randint(10, 1080)
        current_time += timedelta(minutes=minutes_to_add)
        timestamp_ms = int(current_time.timestamp() * 1000)

        rows.append([txn_id, txn_type, amount, category, description, timestamp_ms])

    return rows

def write_csv_file(filepath, rows):
    """Writes transaction rows to a CSV file with a standard header."""
    header = ["id", "type", "amount", "category", "description", "timestamp"]
    with open(filepath, mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(header)
        writer.writerows(rows)

def write_empty_csv(filepath):
    """Writes an empty CSV file containing only the header."""
    write_csv_file(filepath, [])

def collect_user_details():
    """Prompts and collects personal account details with clean validation."""
    print("\n--- Configure Account Profile Details ---")
    
    # Account Number
    account_num = input("Enter Account Number (default: APEX0001): ").strip()
    if not account_num:
        account_num = "APEX0001"

    # Name
    name = input("Enter Full Name (e.g. Bill Gates): ").strip()
    while not name:
        name = input("Name cannot be empty. Please enter your name: ").strip()

    # Date of Birth
    dob = input("Enter Date of Birth (DD-MM-YYYY): ").strip()
    while True:
        try:
            datetime.strptime(dob, "%d-%m-%Y")
            break
        except ValueError:
            dob = input("Invalid format! Please enter DOB in DD-MM-YYYY format: ").strip()

    # Gender
    gender = input("Enter Gender (M/F/O): ").strip().upper()
    while gender not in ["M", "F", "O"]:
        gender = input("Please enter a valid option (M, F, or O): ").strip().upper()

    # Phone Number
    phone = input("Enter 10-digit Phone Number: ").strip()
    while not (phone.isdigit() and len(phone) == 10):
        phone = input("Invalid phone! Please enter a valid 10-digit phone number: ").strip()

    return [account_num, name, dob, gender, phone]

def save_account_details(filepath, details):
    """Saves the collected account details in standard line-by-line format."""
    with open(filepath, mode='w', encoding='utf-8') as file:
        for detail in details:
            file.write(f"{detail}\n")

def main():
    print("==========================================")
    print("   APEX FINANCIAL ENGINE SETUP UTILITY    ")
    print("==========================================\n")

    # Ask user for setup mode preference
    mode = ""
    while mode not in ["personal", "testing"]:
        user_choice = input("Select project setup mode (personal / testing): ").strip().lower()
        if user_choice in ["personal", "testing", "p", "t"]:
            if user_choice.startswith("p"):
                mode = "personal"
            else:
                mode = "testing"
        else:
            print("Invalid selection. Please type 'personal' or 'testing'.")

    dataset_dir = get_dataset_dir()
    dataset_path = os.path.join(dataset_dir, "dataset.csv")
    sample_data_path = os.path.join(dataset_dir, "sampleData.csv")
    account_detail_path = os.path.join(dataset_dir, "accountDetail.txt")

    print(f"\nSetting up files for '{mode}' use case inside: {dataset_dir}...")

    if mode == "personal":
        # 1. sampleData.csv filled with exactly 50 entries
        print("- Generating exactly 50 transaction history records for sampleData.csv...")
        sample_rows = generate_mock_transactions(50)
        write_csv_file(sample_data_path, sample_rows)

        # 2. dataset.csv must be empty (only headers)
        print("- Initializing dataset.csv as empty...")
        write_empty_csv(dataset_path)

        # 3. Collect accountDetails and save
        user_details = collect_user_details()
        save_account_details(account_detail_path, user_details)

    else:  # testing mode
        # 1. Ask how much data to load into dataset.csv
        num_records = 100
        while True:
            user_input = input("\nEnter the number of transaction records to load in dataset.csv (e.g. 100): ").strip()
            if not user_input:
                print("Using default of 100 records.")
                break
            try:
                num_records = int(user_input)
                if num_records > 0:
                    break
                else:
                    print("Please enter a positive number greater than 0.")
            except ValueError:
                print("Invalid number. Please enter a valid whole number.")

        # Load N entries into dataset.csv
        print(f"- Loading {num_records} transaction records into dataset.csv...")
        dataset_rows = generate_mock_transactions(num_records)
        write_csv_file(dataset_path, dataset_rows)

        # 2. Twice the amount of data loaded in sampleData.csv
        double_records = num_records * 2
        print(f"- Loading {double_records} transaction records into sampleData.csv...")
        # Generating a distinct timeline starting after dataset's transactions
        sample_rows = generate_mock_transactions(double_records, start_id_num=num_records + 1)
        write_csv_file(sample_data_path, sample_rows)

        # 3. Collect accountDetails as normal
        user_details = collect_user_details()
        save_account_details(account_detail_path, user_details)

    print("\n==========================================")
    print("🎉 Project Setup Completed Successfully! 🎉")
    print("All datasets are healthy and configured.")
    print("==========================================")

if __name__ == "__main__":
    main()