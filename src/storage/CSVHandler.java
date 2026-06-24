package storage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import models.Transaction;
import models.TransactionType;
import java.io.BufferedReader;
import java.io.FileReader;

    public class CSVHandler {
        
        public ArrayList<Transaction> loadTransactions(String fileName){
            String line;
            ArrayList<Transaction> loadedTransactions = new ArrayList<>();
            
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                reader.readLine(); // discard header row
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    loadedTransactions.add(new Transaction(parts[0], TransactionType.valueOf(parts[1]), Double.parseDouble(parts[2]), parts[3],parts[4],Long.parseLong(parts[5])));
                }
                reader.close();
                
                return loadedTransactions;
            } catch (IOException e) {
                System.out.println("Unable to Load the Transactions");
                System.out.println("Some error occured!!!");
                System.out.println("Error: " + e.getMessage());
                
                return new ArrayList<>();
            }
            
        }


        public boolean appendTransaction(Transaction transaction){
            try {
                FileWriter writer = new FileWriter("src/dataset/dataset.csv", true);
                String formatedTransaction = transaction.getId() +","+ transaction.getType() +","+ transaction.getAmount()+","+transaction.getCategory()+","+transaction.getDescription()+","+transaction.getTimestamp();
                writer.write(formatedTransaction + "\n" );
                writer.close();
                return true;
            } catch (IOException e) {
                System.out.println("Something went wrong while trying to append transaction!!!");
                System.out.println("Error: " + e.getMessage());
                return false;
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
        
        public boolean loadSampleData(){
            ArrayList<Transaction> sampleTransactions = loadTransactions("src/dataset/sampleData.csv");
            boolean resetStatus = resetFile();
            if (resetStatus) {
                for (Transaction t : sampleTransactions) {
                    appendTransaction(t);
                }
                return true;
            } else {
                System.out.println("some problem occured trying to reset file!!!");
                // System.out.println(e.getMessage());
                return false;   
            } 
        }

        public Transaction getTransaction(){
            return new Transaction(null, null, 0, null, null, 0);
        }
    }
        
