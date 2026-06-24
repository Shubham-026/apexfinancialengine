package models;

/**
 * Defines the classification of financial transactions.
 * This enumeration categorizes transactions as either a deposit or an expense,
 * ensuring consistent type safety throughout the financial application.
 */
public enum TransactionType {
    /**
     * Represents incoming funds added to an account.
     */
    DEPOSIT,
    /**
     * Represents outgoing funds spent from an account.
     */
    EXPENSE
}