package storage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import models.Transaction;
import models.TransactionType;
import java.io.BufferedReader;
import java.io.FileReader;

    public class CSVHandler {
        
        public ArrayList<Transaction> loadTransactions(){
            String line;
            ArrayList<Transaction> loadedTransactions = new ArrayList<>();
            
            try {
                BufferedReader reader = new BufferedReader(new FileReader("src/dataset/dataset.csv"));
                reader.readLine(); // discard header row
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    loadedTransactions.add(new Transaction(parts[0], TransactionType.valueOf(parts[1]), Double.parseDouble(parts[2]), parts[3],parts[4],Long.parseLong(parts[5])));
                }
                reader.close();

                return loadedTransactions;
            } catch (IOException e) {
                System.out.println("Unable to Load the Treansactions");
                System.out.println("Some error occured!!!");
                System.out.println("Error: " + e.getMessage());
                
                return new ArrayList<>();
            }
            
        }





        public boolean resetFile(){
            try {
                FileWriter writer = new FileWriter("src/dataset/dataset.csv");
                writer.write("id,type,amount,category,description,timestamp\n");
                writer.close();
                return true;
            } catch (IOException e) {   
                System.out.println("Unable to Clear the file");
                System.out.println("Some error occured!!!");
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        }
    }
        
