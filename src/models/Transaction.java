package models;

public class Transaction {
    private double amount;
    private String id;
    private long timestamp;
    private String category;
    private String description;
    private TransactionType type;
    
    public Transaction(String id,TransactionType type, double amount, String category, String description, long timestamp){
        this.id = id;
        this.timestamp = timestamp;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.type = type;
    }


    public TransactionType getType() {
        return type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
