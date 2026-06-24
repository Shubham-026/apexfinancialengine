package models;

/**
 * Represents an individual financial transaction within the system.
 * This model captures crucial details such as the transaction type (deposit/expense),
 * the monetary value, categorization, a brief description, and a timestamp.
 */
public class Transaction {
    // Private member variables representing transaction attributes
    private double amount;
    private String id;
    private long timestamp;
    private String category;
    private String description;
    private TransactionType type;
    
    /**
     * Constructs a new Transaction instance with all necessary details.
     * * @param id          The unique transaction identifier (e.g., "TXN0001").
     * @param type        The type of transaction (DEPOSIT or EXPENSE).
     * @param amount      The monetary value of the transaction.
     * @param category    The category of the transaction (e.g., "Groceries", "Salary").
     * @param description A short summary or note about the transaction.
     * @param timestamp   The time the transaction occurred, represented in Unix epoch milliseconds.
     */
    public Transaction(String id,TransactionType type, double amount, String category, String description, long timestamp){
        this.id = id;
        this.timestamp = timestamp;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.type = type;
    }

    /**
     * Retrieves the classification of this transaction (DEPOSIT or EXPENSE).
     * @return The transaction type.
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Updates or sets the category of this transaction.
     * @param category The new category name.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Updates or sets the short description of this transaction.
     * @param description The new descriptive text.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the monetary amount of this transaction.
     * @return The transaction amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Retrieves the unique identifier of this transaction.
     * @return The unique transaction ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the Unix epoch millisecond timestamp representing when the transaction occurred.
     * @return The transaction timestamp.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Retrieves the categorized label assigned to this transaction.
     * @return The transaction category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Retrieves the descriptive text or note associated with this transaction.
     * @return The transaction description.
     */
    public String getDescription() {
        return description;
    }
}