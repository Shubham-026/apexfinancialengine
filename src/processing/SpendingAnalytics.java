package processing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.*;

/**
 * Provides analytical computations for the financial engine.
 * This class calculates category spending breakdowns, grand totals, averages, 
 * and maximum transaction limits from a collection of historical transactions.
 */
public class SpendingAnalytics {
    
    /**
     * Iterates through a user's transaction history to compute complete financial metrics.
     * Computes spending totals per category, overall deposits/expenses, averages, and peak values.
     * * @param history The list of Transaction records to analyze.
     * @return A populated SpendAnalysisResult carrying the calculated data sets.
     */
    public SpendAnalysisResult runSpendAnalytics(ArrayList<Transaction> history){
        // Map to store running transaction totals for each spending category
        Map<String, Double> categoryTotals = new HashMap<>();
        // Map to store calculated summary statistics (averages and extremes)
        Map<String, Double> averageAndHighest = new HashMap<>();
        // Map to store separate running sums of all DEPOSIT and EXPENSE operations
        Map<String, Double> typeTotals = new HashMap<>();
        
        // Initialize the result container
        SpendAnalysisResult result = new SpendAnalysisResult();
        typeTotals.put("DEPOSIT", 0.0);
        typeTotals.put("EXPENSE", 0.0);
        
        // Counter variables for determining averages safely
        int depositCount = 0;
        int expenseCount = 0;
        double averageDeposit = 0;
        double averageExpense = 0;
        double highestDeposit = 0;
        double highestExpense = 0;
        
        // Process each individual transaction dynamically
        for (Transaction transaction : history) {
            
            // Standardize categories to upper case to prevent duplicate keys
            String category = transaction.getCategory().toUpperCase();
            double amount = transaction.getAmount();
            // Aggregate totals based on spending categories
            if (categoryTotals.containsKey(category)) {
                categoryTotals.put(category, categoryTotals.get(category) + amount);
            } else {
                categoryTotals.put(category, amount);
                
            }


            // Branch analytical calculation based on the Transaction type (DEPOSIT vs EXPENSE)
            if (transaction.getType() == TransactionType.DEPOSIT) {
                typeTotals.put("DEPOSIT", amount + typeTotals.get("DEPOSIT"));
                averageDeposit = averageDeposit + amount;
                highestDeposit = Math.max(highestDeposit , amount);
                depositCount++;
            } else {
                typeTotals.put("EXPENSE",  amount + typeTotals.get("EXPENSE"));
                averageExpense = averageExpense + amount;
                highestExpense = Math.max(amount, highestExpense);
                expenseCount++;
            }
            
        }
        // Calculate the arithmetic mean values for each classification
        averageDeposit = averageDeposit / depositCount;
        averageExpense = averageExpense / expenseCount;
        // Map calculated findings into standard naming structures for presentation
        averageAndHighest.put("Average Deposit", averageDeposit);
        averageAndHighest.put("Average Expense", averageExpense);
        averageAndHighest.put("Highest Deposit", highestDeposit);
        averageAndHighest.put("Highest Expense", highestExpense);  
        
        // Build and return final structured analytics data object
        result.categoryTotals = categoryTotals;
        result.typeTotals = typeTotals;
        result.averagesAndHighest = averageAndHighest;
        return result;
    }    

}