package processing;
import models.*;
import java.util.ArrayList;;

public class SearchHandler {
    
    public Transaction searchById(String targetId, ArrayList<Transaction> history){
        int low = 0;
        int high =  history.size() - 1;
        int mid = high/2;
        int targetIndex = -1;

        while (low < high) {
            mid = (high + low)/2;
            if (Integer.parseInt(history.get(mid).getId().substring(3)) == Integer.parseInt(targetId.substring(3))) {
                System.out.println("Transaction found");
                targetIndex = mid;
                break;
            } else if (Integer.parseInt(history.get(mid).getId().substring(3)) < Integer.parseInt(targetId.substring(3))) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }

        }
        if (targetIndex != -1) {
            return history.get(targetIndex);
        } else {
            return null;
        }
    }
}
