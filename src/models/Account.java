package models;

import java.util.ArrayList;

/**
 * Represents a user's financial account.
 * This class tracks the current account balance and maintains a historical log 
 * of all transactions (deposits and expenses) associated with the account.
 */
public class Account {
    // The current net balance of the account
    private double balance;
    // The collection of all historical transactions recorded
    private ArrayList<Transaction> history;

    /**
     * Initializes a new, empty Account with a starting balance of 0.0 
     * and an empty transaction history list.
     */
    public Account(){
        this.balance = 0.0;
        this.history = new ArrayList<>();
    }

    /**
     * Retrieves the last recorded balance of the account.
     * @return The cached account balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Manually updates the cached balance of the account.
     * @param balance The new balance to set.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Retrieves the list of all transactions associated with this account.
     * @return An ArrayList containing the transaction history.
     */
    public ArrayList<Transaction> getHistory() {
        return history;
    }

    /**
     * Sets or overwrites the transaction history of this account.
     * @param history The new list of transactions to assign.
     */
    public void setHistory(ArrayList<Transaction> history) {
        this.history = history;
    }

    /**
     * Recalculates the net account balance by iterating through the entire 
     * transaction history. Deposits are added to the total, while expenses 
     * are subtracted. Updates and returns the final balance.
     * @return The newly computed total balance of the account.
     */
    public double calculateBalance(){
        double balance = 0.0;
        // Loop through each recorded transaction to compute the current net sum
        for (Transaction transaction : history) {
            // Add funds if it's a deposit, subtract if it's an expense
            if(transaction.getType() == TransactionType.DEPOSIT){
                balance = balance + transaction.getAmount();
            }
            else{
                balance = balance - transaction.getAmount();
            }
        }
        // Cache the newly calculated balance in the instance variable
        setBalance(balance);
        return balance;
    }
}