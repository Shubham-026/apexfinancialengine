package processing;
import models.*;
import java.util.ArrayList;

/**
 * SearchHandler coordinates the retrieval of financial records.
 * It implements a binary search utility to find specific transactions by their identifiers.
 */
public class SearchHandler {
    
    /**
     * Performs a binary search on the transaction history list to locate a transaction by ID.
     * Assumes the history list is pre-sorted by transaction ID.
     * * @param targetId The ID string of the transaction being searched for (e.g., "TXN0001").
     * @param history The list of transactions to search through.
     * @return The matched Transaction object, or null if no match is found.
     */
    public Transaction searchById(int targetId, ArrayList<Transaction> history){
        // Initialize boundaries for the binary search space
        int low = 0;
        int high =  history.size() - 1;
        int mid = high/2;
        int targetIndex = -1;

        // Loop to divide and conquer the search space
        while (low <= high) {
            // Calculate the midpoint index of the current range
            mid = (high + low)/2;
            
            // Compare the numeric portion of the midpoint ID with the target ID
            if (Integer.parseInt(history.get(mid).getId().substring(3)) == targetId) {
                targetIndex = mid;
                break; // Target identified, break out of search loop
            } 
            // If mid-point ID is smaller than target, shift search to upper half
            else if (Integer.parseInt(history.get(mid).getId().substring(3)) < targetId) {
                low = mid + 1;
            } 
            // If mid-point ID is larger than target, shift search to lower half
            else {
                high = mid - 1;
            }

        }
        
        // Evaluate search results and return the corresponding Transaction object
        if (targetIndex != -1) {
            return history.get(targetIndex);
        } else {
            return null;
        }
    }
}