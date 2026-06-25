package storage;
import models.UserDetails;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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

    /**
     * Edits a specific field in the profile text file based on the field indicator.
     * 1 -> Name
     * 2 -> Date Of Birth
     * 3 -> Phone Number
     * * @param field The field index to be modified.
     * @param newValue The new value to set for the chosen field.
     * @return true if the modification succeeded, false otherwise.
     */
    public boolean editProfile(int field, String newValue) {
        String accountNum = null;
        String name = null;
        String dobRaw = null;
        String genderRaw = null;
        String phoneRaw = null;

        // 1. Read and temporarily store all existing information from the text file
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dataset/accountDetail.txt"))) {
            accountNum = reader.readLine();
            name = reader.readLine();
            dobRaw = reader.readLine();
            genderRaw = reader.readLine();
            phoneRaw = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading the account details file: " + e.getMessage());
            return false;
        }

        // Basic verification to make sure we didn't read an empty or incomplete file
        if (accountNum == null || name == null || dobRaw == null || genderRaw == null || phoneRaw == null) {
            System.out.println("Error: The account file structure seems incomplete.");
            return false;
        }

        // 2. Select and update the targeted field based on the input index
        switch (field) {
            case 1:
                name = newValue;
                break;
            case 2:
                dobRaw = newValue;
                break;
            case 3:
                phoneRaw = newValue;
                break;
            default:
                System.out.println("Error: Invalid field option chosen. Choose 1 (Name), 2 (DOB), or 3 (Phone).");
                return false;
        }

        // 3. Overwrite the file with the updated records sequentially
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/dataset/accountDetail.txt"))) {
            writer.println(accountNum);
            writer.println(name);
            writer.println(dobRaw);
            writer.println(genderRaw);
            writer.println(phoneRaw);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing the updated profile to file: " + e.getMessage());
            return false;
        }
    }
}