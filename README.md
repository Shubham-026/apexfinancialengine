# Apex Financial Engine

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/status-complete-brightgreen)
![Type](https://img.shields.io/badge/type-console%20app-blue)
![Tested](https://img.shields.io/badge/stress--tested-1M%2B%20rows-blueviolet)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

A console-based personal finance tracker, built from scratch in pure Java — no frameworks, no scaffolding. Every layer (data model, file storage, search, analytics, console UI) was designed and written incrementally, with a deliberate focus on understanding *why* each piece works the way it does, not just shipping something that runs.

```
+=============================================================+
|                 APEX FINANCIAL ENGINE                       |
+=============================================================+
|  Name: Shubham Gupta       Account Number: APEX0001         |
| Gender: M     DOB: 06-02-2007  Phone Number: 9204420738     |
+=============================================================+
|  --> MAIN MENU                                              |
+=============================================================+
|   1. View Balance and History                               |
|   2. New Transaction (Deposit/Expense)                      |
|   3. Search Transaction                                     |
|   4. Run Spend Analytics                                    |
|   5. More                                                   |
|   6. EXIT                                                   |
+=============================================================+
```

---

## Table of contents

- [Features](#features)
- [Why it's built this way](#why-its-built-this-way)
- [Tech stack](#tech-stack)
- [Project structure](#project-structure)
- [Getting started](#getting-started)
- [First-time setup](#first-time-setup)
- [Generating a large benchmark dataset](#generating-a-large-benchmark-dataset)
- [Menu walkthrough](#menu-walkthrough)
- [Design notes](#design-notes)
- [What I learned building this](#what-i-learned-building-this)
- [Author](#author)

---

## Features

- [x] Type-safe `Transaction` model backed by a `TransactionType` enum — no string-typo bugs possible
- [x] Single-account model with running balance calculation
- [x] CSV storage layer — load, append, and reset, with real exception handling (not swallowed `catch` blocks)
- [x] **View Balance & History** — fixed-width formatted table, two-decimal currency, human-readable timestamps
- [x] **New Transaction** — guided deposit/expense entry with automatic sequential ID generation
- [x] **Search Transaction** — binary search by transaction ID (see [Design notes](#design-notes) for why)
- [x] **Spend Analytics** — category-wise totals, deposit/expense totals, highest and average transaction values, all computed in a single pass over the data
- [x] **Reset All Data** — guarded by a typed confirmation phrase, so it can't be triggered by a stray keypress
- [x] **Load Sample Data** — repopulate `dataset.csv` from a bundled 50-row dataset in one step
- [x] **Edit Profile** — update name, date of birth, or phone number, persisted back to disk
- [x] Setup wizard (`setup.py`) for first-run configuration
- [x] Standalone dataset generator (`datasetGenerator.py`) for stress-testing at any scale
- [x] Stress-tested against **1,000,000 transactions** with no failures

## Why it's built this way

This project intentionally avoids a database, a GUI framework, or any external library. The constraint was the point: build the storage layer, the search, and the analytics by hand, understand every line, and make deliberate trade-offs rather than reaching for a library that hides them. A few of those trade-offs are written up in [Design notes](#design-notes) below.

## Tech stack

| Layer | Choice |
|---|---|
| Language | Java 21 (pure JDK, zero external dependencies) |
| Storage | Flat CSV files + plain-text account profile |
| Interface | Console (text-based menu) |
| Data formatting | `String.format`, `DateTimeFormatter`, `Instant` |
| Tooling | Python scripts for setup and large-scale dataset generation |

## Project structure

<details>
<summary>Click to expand directory tree</summary>

```
apexFinancialEngine/
├── src/
│   ├── Main.java                      # Menu loop, program entry point, all screen rendering
│   ├── models/
│   │   ├── Transaction.java           # Core transaction record (id, type, amount, category, description, timestamp)
│   │   ├── TransactionType.java       # Enum: DEPOSIT, EXPENSE
│   │   ├── Account.java               # Holds transaction history + calculateBalance()
│   │   ├── UserDetails.java           # Account holder's profile data
│   │   └── SpendAnalysisResult.java   # Bundled output of one analytics pass (category/type/avg/highest)
│   ├── storage/
│   │   ├── CSVHandler.java            # loadTransactions(), appendTransaction(), resetFile(), loadSampleData()
│   │   └── DetailsHandler.java        # detailLoader(), editProfile() — reads/writes the account profile file
│   ├── processing/
│   │   ├── SearchHandler.java         # Binary search by transaction ID
│   │   └── SpendingAnalytics.java     # Category totals, type totals, averages, highest values
│   └── dataset/
│       ├── dataset.csv                # Live transaction data
│       ├── sampleData.csv             # 50-row seed dataset for demos
│       └── accountDetail.txt          # Account holder's profile (name, DOB, gender, phone, account number)
├── bin/                                # Compiled output (created when you build)
├── setup.py                            # First-run setup wizard
├── datasetGenerator.py                 # Standalone tool to generate large benchmark datasets
└── README.md
```

</details>

## Getting started

### 1. Clone the repository

```bash
git clone https://github.com/Shubham-026/apexFinancialEngine.git
cd apexFinancialEngine
```

### 2. Compile the source

From the project root:

```bash
javac -d bin src/models/*.java src/storage/*.java src/processing/*.java src/Main.java
```

This compiles every `.java` file and places the resulting `.class` files into the `bin/` folder, mirroring the same package structure (`bin/models`, `bin/storage`, `bin/processing`).

Requires **JDK 17 or newer** (developed and tested on JDK 21). No external libraries — the standard library is all that's needed.

### 3. Run it

```bash
java -cp bin Main
```

`-cp bin` tells the JVM where to find the compiled classes. `Main` is the entry point. You should immediately see the main menu.

> **Note:** the program reads and writes files using paths relative to the project root (`src/dataset/...`), so make sure you run the `java` command from the project's **root folder**, not from inside `bin/` or `src/`.

## First-time setup

Before running the app for the first time, run the setup wizard — it generates your account profile and prepares the dataset files so the app has something to work with immediately:

```bash
python3 setup.py
```

You'll be asked to choose a mode:

- **`personal`** — sets up a clean slate: an empty `dataset.csv` (ready for your real transactions), a 50-row `sampleData.csv` you can load anytime from the **More** menu, and a profile built from details you enter (name, date of birth, gender, phone number, account number).
- **`testing`** — same profile setup, but lets you specify how many mock transactions to pre-load into `dataset.csv` directly, plus a separate, larger `sampleData.csv` for further testing.

Either way, once it finishes, `src/dataset/` will have everything the app expects: `dataset.csv`, `sampleData.csv`, and `accountDetail.txt`.

> Don't have Python installed, or want to skip this step? You can also just compile and run the Java app directly — it'll work against whatever's already in `src/dataset/`, including the sample files already bundled in this repo.

## Generating a large benchmark dataset

`datasetGenerator.py` is a separate, standalone tool — not part of the app itself — built specifically to stress-test the storage and search layers at scale. This is the script used to validate the app against 1,000,000+ transactions.

```bash
python3 datasetGenerator.py
```

It will prompt you for how many records to generate:

```
Enter the number of transaction records to generate (e.g. 150):
```

Enter a number (commas like `1,000,000` are accepted and stripped automatically) and it writes directly to `src/dataset/dataset.csv`, overwriting whatever's there. For large counts, it batches writes (100,000 rows at a time) to avoid loading the entire dataset into memory while generating it, and prints progress as it goes.

To try it yourself:

```bash
python3 datasetGenerator.py
# enter: 1000000
javac -d bin src/models/*.java src/storage/*.java src/processing/*.java src/Main.java
java -cp bin Main
# choose option 1 to load and display all million rows
```

> Heads-up: pushing this well past a few million rows can exceed the JVM's default heap size, since the entire dataset is loaded into memory at once (see [Design notes](#design-notes)). For a personal finance tracker's actual use case, this ceiling is never realistically reached — but it's a real, known limit of the current design, not an oversight.

## Menu walkthrough

<details>
<summary><b>1. View Balance and History</b></summary>

Displays the current balance (deposits minus expenses, recalculated fresh from the full transaction list) followed by a formatted table of every transaction — fixed-width columns, two-decimal currency formatting, and timestamps converted from raw epoch milliseconds into a readable `dd-MM-yy HH:mm:ss` format.

</details>

<details>
<summary><b>2. New Transaction (Deposit/Expense)</b></summary>

Prompts for transaction type, amount, category, and description. Generates the next sequential transaction ID automatically by reading the last existing ID and incrementing it (with a safe fallback if the history is empty, e.g. right after a reset). The new transaction is written to disk **and** added to the in-memory history immediately, so the balance and history screens reflect it without needing a restart.

</details>

<details>
<summary><b>3. Search Transaction</b></summary>

Looks up a transaction by its exact ID (e.g. `TXN0001`) using **binary search**. This works correctly and efficiently because transaction IDs are guaranteed to be sequential and assigned in strict chronological order — see [Design notes](#design-notes) for why this guarantee matters.

</details>

<details>
<summary><b>4. Run Spend Analytics</b></summary>

Computes, in a single pass over the transaction history:
- Category-wise spending totals
- Total deposits and total expenses
- Highest single deposit and highest single expense
- Average deposit and average expense amounts

</details>

<details>
<summary><b>5. More</b></summary>

- **Reset All Data** — wipes `dataset.csv` back to just its header row. Guarded by a typed confirmation phrase (you must type out a specific sentence exactly) so it can never be triggered by an accidental tap.
- **Load Sample Data** — copies the bundled 50-row `sampleData.csv` into `dataset.csv`, replacing whatever's there. Useful for demos or starting over with realistic-looking data.
- **Edit Profile** — update your name, date of birth, or phone number. Changes are written back to `accountDetail.txt` immediately.

</details>

<details>
<summary><b>6. Exit</b></summary>

Closes the program. There's no separate "save" step anywhere in the app — every change is written to disk the moment it happens, so there's nothing left to save on exit.

</details>

## Design notes

A few deliberate trade-offs worth knowing about, since they were genuine decisions rather than defaults:

- **Binary search is used only for ID lookups, not every search type.** Transaction IDs and timestamps increase together by construction — every new transaction gets the next sequential ID *and* the current timestamp, so sorting by one means the list is also sorted by the other. That guarantee is what makes binary search correct here. Category, type, and amount-based searches don't have a natural sort order to exploit, so they're handled with a straightforward linear scan instead — using binary search there would mean re-sorting the list by a different key each time, which costs more than it saves at this data size.
- **The entire dataset loads into memory at once.** This keeps the code simple and was never a problem up to 1,000,000 rows in testing. It does mean memory use scales with row count — at around 10,000,000 rows, the JVM's default heap size was exceeded in testing. For an app meant to track one person's real transactions, this ceiling is well beyond any realistic dataset size, so it was a conscious choice not to add the complexity of streaming/chunked processing for a scale this app will never actually see.
- **CSV over a database.** A flat file is transparent (you can open `dataset.csv` and read it directly), requires no setup, and was deliberately chosen so the storage layer itself — reading, writing, parsing, append vs. overwrite modes — had to be built and understood from scratch, rather than delegated to a database driver.
- **Returning `null` vs. an empty collection.** Methods that can return *many* results (like loading all transactions) return an empty list on failure, never `null` — an empty list is still a safe, valid thing to loop over. Methods that return *one specific* result (like a profile lookup, or a single search match) return `null` when nothing is found, since there's no meaningful "empty but valid" version of a single object — the caller is expected to check for it explicitly.

## What I learned building this

- Enum-based type safety, and why it beats raw strings for fixed sets of values
- `==` vs `.equals()` — when each is actually correct (primitives and enums vs. objects)
- File I/O from first principles: `FileWriter` overwrite vs. append modes, `BufferedReader` line-by-line reading, and the exact bugs that come from getting newline placement wrong
- Binary search implementation details that are easy to get subtly wrong (off-by-one boundaries, infinite loops from incorrect boundary updates, recalculating midpoint every iteration)
- When binary search is actually justified versus when a linear scan is the more honest, appropriate choice
- `HashMap`-based aggregation for grouped totals (category breakdowns, type-wise sums)
- Designing small "result bundle" classes to cleanly return multiple related values from one calculation
- Keeping in-memory state and on-disk state in sync — and the specific bugs that show up when you forget to
- Catching specific exceptions instead of broad ones, and why it matters for not hiding unrelated bugs

## Author

Built by **Shubham Gupta**, as a hands-on project to learn Java, file-based storage, and core data structures from first principles.