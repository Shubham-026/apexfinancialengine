package storage;
import models.UserDetails;
import java.io.FileReader;
// import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;


public class DetailsHandler {

    public UserDetails detailLoader(){

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/dataset/accountDetail.txt"));            
            String accountNum = reader.readLine();// line 5
            String name = reader.readLine();      // line 1
            String dobRaw = reader.readLine();    // line 2
            String genderRaw = reader.readLine(); // line 3
            String phoneRaw = reader.readLine();  // line 4
            reader.close();
            return new UserDetails(accountNum, name, Integer.parseInt(dobRaw), genderRaw.charAt(0), Long.parseLong(phoneRaw));

        } catch (IOException e) {
            System.out.println("Unable to Load the Transactions");
            System.out.println("Some error occured!!!");
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    
}                           
