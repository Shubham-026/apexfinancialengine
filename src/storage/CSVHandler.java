package storage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import models.Transaction;
import models.TransactionType;
import java.io.BufferedReader;
import java.io.FileReader;

    /**
     * The CSVHandler class manages database interactions for the application using CSV files.
     * It handles reading transactions, appending new records, resetting the dataset, 
     * and pre-populating mock sample data for system evaluation.
     */
    public class CSVHandler {
        
        /**
         * Reads transactions from a specified CSV source file.
         * Parses commas-separated values into active in-memory Transaction business models.
         * * @param fileName The path of the target CSV file to load.
         * @return An ArrayList containing all parsed Transaction records, or an empty list upon failure.
         */
        public ArrayList<Transaction> loadTransactions(String fileName){
            String line;
            ArrayList<Transaction> loadedTransactions = new ArrayList<>();
            
            try {
                // Initialize BufferedReader to read the CSV file sequentially
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                reader.readLine(); // discard header row
                
                // Parse every row and split data attributes by comma boundaries
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    // Reconstruct Transaction business object with parsed properties
                    loadedTransactions.add(new Transaction(parts[0], TransactionType.valueOf(parts[1]), Double.parseDouble(parts[2]), parts[3],parts[4],Long.parseLong(parts[5])));
                }
                reader.close();
                
                return loadedTransactions;
            } catch (IOException e) {
                // Handle system input/output exceptions gracefully
                System.out.println("Unable to Load the Transactions");
                System.out.println("Some error occured!!!");
                System.out.println("Error: " + e.getMessage());
                
                return new ArrayList<>();
            }
            
        }


        /**
         * Appends a newly created transaction record to the persistent local CSV file.
         * * @param transaction The Transaction model object containing details to persist.
         * @return true if write operation succeeds, false if an error occurs.
         */
        public boolean appendTransaction(Transaction transaction){
            try {
                // Open file writer in append mode (true) to add records to the end of the file
                FileWriter writer = new FileWriter("src/dataset/dataset.csv", true);
                String formatedTransaction = transaction.getId() +","+ transaction.getType() +","+ transaction.getAmount()+","+transaction.getCategory()+","+transaction.getDescription()+","+transaction.getTimestamp();
                writer.write(formatedTransaction + "\n" );
                writer.close();
                return true;
            } catch (IOException e) {
                // Notify user if storage append fails
                System.out.println("Something went wrong while trying to append transaction!!!");
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        }
        
        /**
         * Resets the active dataset CSV file by clearing its contents and writing a new header row.
         * * @return true if truncation and header write succeeds, false otherwise.
         */
        public boolean resetFile(){
            try {
                // Open file writer without append mode to clear existing content on open
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
        
        /**
         * Loads historical records from sampleData.csv and writes them into the primary dataset file.
         * * @return true if sample data is successfully loaded and written, false if resetting or loading fails.
         */
        public boolean loadSampleData(){
            ArrayList<Transaction> sampleTransactions = loadTransactions("src/dataset/sampleData.csv");
            boolean resetStatus = resetFile();
            if (resetStatus) {
                // Iterate through sample records and append each one to the clean file
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

        /**
         * Generates an empty shell transaction object containing default values.
         * * @return A new instance of a Transaction with null and zero value fields.
         */
        public Transaction getTransaction(){
            return new Transaction(null, null, 0, null, null, 0);
        }
    }