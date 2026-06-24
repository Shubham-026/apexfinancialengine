package storage;
import models.UserDetails;
import java.io.FileReader;
// import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Handles the retrieval of user profile and account details from the local file system.
 */
public class DetailsHandler {

    /**
     * Reads user details from a flat text file and returns a structured UserDetails object.
     * * @return a fully populated UserDetails object, or null if the file cannot be read.
     */
    public UserDetails detailLoader(){

        try {
            // Open a buffered reader stream to read the local account details text file sequentially
            BufferedReader reader = new BufferedReader(new FileReader("src/dataset/accountDetail.txt"));            
            String accountNum = reader.readLine();
            String name = reader.readLine();      
            String dobRaw = reader.readLine();    
            String genderRaw = reader.readLine(); 
            String phoneRaw = reader.readLine();  
            
            // Close the resource stream to prevent memory leaks
            reader.close();
            
            // Map the parsed raw strings into a new UserDetails business object
            return new UserDetails(accountNum, name, dobRaw, genderRaw.charAt(0), Long.parseLong(phoneRaw));

        } catch (IOException e) {
            // Inform the user of any operational system exceptions during storage access
            System.out.println("Unable to Load the Transactions");
            System.out.println("Some error occured!!!");
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    
}