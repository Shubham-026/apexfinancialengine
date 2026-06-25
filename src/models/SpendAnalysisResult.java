package models;
import java.util.Map;

/**
 * Data transfer object that holds the compiled results of spending analytics.
 * This class aggregates financial data across various categories and transaction types,
 * providing a structured layout for rendering metrics, summaries, and reports.
 */
public class SpendAnalysisResult {
    /**
     * A map storing category names (e.g., "Groceries", "Education") paired with their calculated transaction sums.
     */
    public Map<String, Double> categoryTotals;
    
    /**
     * A map storing the grand totals grouped by transaction type, such as "DEPOSIT" and "EXPENSE".
     */
    public Map<String, Double> typeTotals;
    
    /**
     * A map storing computed statistical metrics, including average and highest values for both deposits and expenses.
     */
    public Map<String, Double> averagesAndHighest;
}