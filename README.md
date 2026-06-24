# Apex Financial Engine

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/status-in%20development-yellow)
![Type](https://img.shields.io/badge/type-console%20app-blue)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

A console-based personal finance tracker built from scratch in Java — a portfolio project for the **Google STEP internship application**. The focus isn't just shipping a finished app, it's understanding every line of it: file I/O, enums, data modeling, and clean console UX, built incrementally rather than scaffolded.

---

## Table of contents

- [Features](#features)
- [Tech stack](#tech-stack)
- [Project structure](#project-structure)
- [Getting started](#getting-started)
- [How to use](#how-to-use)
- [Menu walkthrough](#menu-walkthrough)
- [Roadmap](#roadmap)
- [What I'm learning](#what-im-learning)
- [Author](#author)

---

## Features

- [x] Type-safe `Transaction` model backed by a `TransactionType` enum
- [x] `Account` class with balance calculation
- [x] CSV storage layer — load, append, and reset operations
- [x] **View Balance and History** screen (fixed-width columns, `%.2f` formatting, formatted timestamps)
- [x] **Reset All Data** flow with typed confirmation guard (no accidental wipes)
- [x] 50-row sample dataset for testing
- [ ] **New Transaction** flow wired to storage (in progress — auto-generating IDs from history)
- [ ] `loadSampleData()` on startup
- [ ] Full menu flow + remaining features

> Progress is tracked live in this checklist as the project grows — check back as boxes fill in.

## Tech stack

| Layer | Choice |
|---|---|
| Language | Java (pure JDK, no frameworks) |
| Storage | Flat CSV files |
| Interface | Console (text-based menu) |
| Data formatting | `String.format`, `DateTimeFormatter` |

## Project structure

<details>
<summary>Click to expand directory tree</summary>

```
ApexFinancialEngine/
├── src/
│   ├── models/
│   │   ├── Transaction.java       # Core transaction record (getter-only, no setters)
│   │   ├── TransactionType.java   # Enum: INCOME, EXPENSE, TRANSFER...
│   │   └── Account.java           # Holds balance + calculateBalance()
│   ├── storage/
│   │   ├── CSVHandler.java        # resetFile(), loadTransactions(), appendTransaction()
│   │   └── DetailsHandler.java    # detailLoader() — reads account details from flat file
│   └── Main.java                  # Menu loop + program entry point
├── data/
│   ├── transactions.csv
│   └── sampleData.csv             # 50-row seed dataset
└── README.md
```

</details>

## Getting started

<details>
<summary>Compile and run</summary>

```bash
# from the project root
javac -d bin src/models/*.java src/storage/*.java src/Main.java

# run it
java -cp bin Main
```

Requires JDK 17+. No external dependencies.

</details>

## How to use

1. **Clone or download** the project to your machine.
2. **Compile** the source files (see [Getting started](#getting-started) for the exact command).
3. **Run** the program with `java -cp bin Main`.
4. You'll land on the **main menu** — choose an option by typing its number:
   ```
   === Apex Financial Engine ===
   1. View Balance and History
   2. New Transaction
   3. Reset All Data
   4. Exit
   ```
5. **Check your balance** — select option `1` to see your current balance and a full formatted transaction history.
6. **Log a transaction** — select option `2` to record a new income, expense, or transfer *(this flow is still being wired up — see [Roadmap](#roadmap))*.
7. **Start fresh** — select option `3` if you want to clear all stored data. You'll be asked to type a confirmation phrase before anything is deleted, so it's safe from accidental taps.
8. **Exit** — select option `4` (or close the terminal) when you're done. All changes are already saved to CSV as you go, so there's no separate "save" step.

> Tip: the included `sampleData.csv` gives you 50 ready-made transactions to explore options 1 and 3 with, even before option 2 is fully wired up.

## Menu walkthrough

<details>
<summary>1. View Balance and History</summary>

Displays current account balance and a formatted transaction history table — fixed-width columns, two-decimal currency formatting, and human-readable timestamps converted from epoch millis.

</details>

<details>
<summary>2. New Transaction <em>(in progress)</em></summary>

Will prompt for transaction details, auto-generate a new ID based on existing history, and append the entry via `CSVHandler.appendTransaction()`.

</details>

<details>
<summary>3. Reset All Data</summary>

Wipes all stored transactions — guarded by a typed confirmation step (e.g. typing `CONFIRM`) so it can't be triggered by accident.

</details>

## Roadmap

- [x] Phase 1 — Data model & storage layer
- [x] Phase 2 — View balance & history
- [x] Phase 3 — Reset flow with safeguard
- [ ] Phase 4 — New transaction flow + ID generation *(current)*
- [ ] Phase 5 — Sample data loader
- [ ] Phase 6 — Full menu polish & edge-case handling

## What I'm learning

- Enum-based type safety and `valueOf()` for parsing
- `==` vs `.equals()` — primitives/enums vs. objects
- `FileWriter` overwrite vs. append modes
- Line-by-line reading with `BufferedReader` + header-row skipping
- `String.format` for aligned numeric/column output
- `DateTimeFormatter` with `Instant.ofEpochMilli()`
- Returning empty collections vs. `null` on failure
- Catching specific exceptions (`IOException`) instead of broad `Exception`

## Author

Built by **Shubham** 

