package models;

import java.util.ArrayList;

public class Account {
    private double balance;
    private ArrayList<Transaction> history;

    public Account(){
        this.balance = 0.0;
        this.history = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Transaction> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Transaction> history) {
        this.history = history;
    }

    public double calculateBalance(){
        double balance = 0.0;
        for (Transaction transaction : history) {
            if(transaction.getType() == TransactionType.DEPOSIT){
                balance = balance + transaction.getAmount();
            }
            else{
                balance = balance - transaction.getAmount();
            }
        }
        setBalance(balance);
        return balance;
    }
}
