import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.*;
import storage.*;
import processing.*;

/**
 * Main entry point for the Apex Financial Engine CLI Application.
 * This class coordinates the user interface, database file operations, 
 * user profile details, and transaction history interactions.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // Initialize input reader for interactive CLI menus
        Scanner sc = new Scanner(System.in);
        
        // Handlers to manage transaction CSV records and user account details
        CSVHandler csvHandler = new CSVHandler();
        DetailsHandler detailsHandler = new DetailsHandler();
        UserDetails userDetails = detailsHandler.detailLoader();
        
        // Formatter for timestamp presentation using the user's local timezone
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss").withZone(ZoneId.systemDefault());
        
        // Initialize the user's Account and populate historical transactions from dataset
        Account account = new Account();
        account.setHistory(csvHandler.loadTransactions("src/dataset/dataset.csv"));

        // Initialise Search Handler for searching through records
        SearchHandler search = new SearchHandler();
        
        // Infinite application loop for terminal-based user interaction
        while (true) {
            clearScreen();
            // System.out.println("Working directory: " + System.getProperty("user.dir"));
            printBanner("MAIN MENU");
            System.out.println("|   1. View Balance and History                               |");
            System.out.println("|   2. New Transaction (Deposit/Expense)                      |");
            System.out.println("|   3. Search Transaction                                     |");
            System.out.println("|   4. Run Spend Analytics                                    |");
            System.out.println("|   5. View Category Spending Breakdown                       |");
            System.out.println("|   6. More                                                   |");
            System.out.println("|   7. EXIT                                                   |");
            System.out.println("+=============================================================+");
            System.out.print("      Choose an option:   ");

            String choiceMain = sc.nextLine();
            System.out.println();


            if ("1".equals(choiceMain)) {       // View Balance and History 

                clearScreen();
                
                // Print user dashboard header along with personalized user details
                System.out.println("+===================================================================================================+");
                System.out.println("|                                       APEX FINANCIAL ENGINE                                       |");
                System.out.println("+===================================================================================================+");
                System.out.print("| Name: " + String.format("%-15s", userDetails.getUserName()) + " Account Number: "
                        + userDetails.getAccountNumber() + " ");
                System.out.println("Gender: " + userDetails.getGender() + " DOB: "
                        + String.format("%-10s", userDetails.getDateOfBirth()) + " Phone Number: "
                        + String.format("%-10s", userDetails.getPhoneNumber()) + " |");
                System.out.println("+===================================================================================================+");
                System.out.println("|  --> VIEW BALANCE AND HISTORY                                                                     |");
                System.out.println("+===================================================================================================+");
                
                // Iterate and cleanly print out each transaction record
                for (Transaction transact : account.getHistory()) {
                    System.out.println("|" + transact.getId() + "  " + transact.getType() + "  "
                            + String.format("%-15s", transact.getCategory()) + "  "
                            + String.format("%-30.30s", transact.getDescription()) + "  "
                            + formatter.format(Instant.ofEpochMilli(transact.getTimestamp())) + "  "
                            + String.format("%-13.2f", transact.getAmount()) + "|");
                    System.out.println(
                            "+---------------------------------------------------------------------------------------------------+");
                }
                
                // Calculate and print the overall remaining/current account balance
                System.out.println("|        CURRENT BALANCE : " + String.format("%-70.2f", account.calculateBalance()) + "   |");
                System.out.println("+===================================================================================================+");
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            } 
            
            else if ("2".equals(choiceMain)) {      // New Transaction (Deposit/Expense)

                clearScreen();

                printBanner("New Transaction (Deposit/Expense)");
                System.out.print("    DEPOSIT OR EXPENSE :   d/e?   ");
                char transactionType = sc.nextLine().charAt(0);

                clearScreen();

                TransactionType type;

                // Set transaction properties depending on selected type
                if ('d' == transactionType) {
                    printBanner("Transaction Type \"DEPOSIT\" ");
                    type = TransactionType.DEPOSIT;
                } 
                
                else if ('e' == transactionType) {
                    printBanner("Transaction Type \"EXPENSE\" ");
                    type = TransactionType.EXPENSE;

                } 
                
                else {
                    // Fallback to menu if choice is invalid
                    System.out.println("    Wrong Choice");
                    System.out.println("    Press \"ENTER\" to go back to main menu:");
                    sc.nextLine();
                    continue;

                }

                // Prompt user for financial details of transaction
                System.out.print("Enter Amount:     ");
                double transactionAmount = sc.nextDouble();
                sc.nextLine();

                System.out.print("Enter Category:   ");
                String transactionCategory = sc.nextLine();

                System.out.print("Enter Short Description:  ");
                String transactionDescription = sc.nextLine();

                String newTransactionID;

                // Auto-generate a sequential 4-digit transaction ID
                if (account.getHistory().isEmpty()) {
                    newTransactionID = "TXN0001";
                } 
                
                else {

                    int nextNum = Integer.parseInt(account.getHistory().get(account.getHistory().size()-1).getId().substring(3)) + 1;
                    newTransactionID = "TXN" + String.format("%04d", nextNum);
                }

                // Construct a new Transaction record with current timestamp
                Transaction newTransaction = new Transaction(newTransactionID,
                        type,
                        transactionAmount,
                        transactionCategory,
                        transactionDescription,
                        System.currentTimeMillis());

                // Append the newly created transaction to CSV and update the in-memory log
                boolean appendStatus = csvHandler.appendTransaction(newTransaction); 

                        if (appendStatus) {
                            account.getHistory().add(newTransaction);
                            System.out.println("    Transaction added successfully!!!");
                        } 
                        
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            } 
            
            
            else if ("3".equals(choiceMain)) {      // Search Transaction
                clearScreen();
                printBanner("Search Transaction ");
                System.out.print("    Enter the Transaction no, eg. TXN0000:    ");
                Transaction searchResult;
                if ((searchResult = search.searchById(sc.nextLine(), account.getHistory())) != null) {
                    System.out.println("    Search Result: Found!!! \n");
                    System.out.println("    Transaction ID:     "+ searchResult.getId());
                    System.out.println("    Transaction Type:   "+ searchResult.getType());
                    System.out.println("    Amount:             "+ searchResult.getAmount());
                    System.out.println("    Category:           "+ searchResult.getCategory());
                    System.out.println("    Description:        "+ searchResult.getDescription());
                    System.out.println("    Date & Time:        "+ formatter.format(Instant.ofEpochMilli(searchResult.getTimestamp())));
                }

                else{
                    System.out.println("NO Transaction found for the given Transaction ID!!! \n");
                }
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            } 
            
            
            else if ("4".equals(choiceMain)) {      // Run Spend Analytics
                clearScreen();
                printBanner("Run Spend Analytics");
                
                // Placeholder - spend analytical models are not yet built
                System.out.println("    Feature not available yet");
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            } 
            

            else if ("5".equals(choiceMain)) {      // VIEW SPENDING CATEGORY BREAKDOWN
                clearScreen();
                printBanner("View Category Spending Breakdown");
                
                // Placeholder - breakdown models are not yet built
                System.out.println("    Feature not available yet");
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            } 
            

            else if ("6".equals(choiceMain)) {      // MORE
                clearScreen();
                printBanner("MORE");    
                System.out.println("|   1. Reset all data                                         |");
                System.out.println("|   2. Load sample data                                       |");
                System.out.println("|   3. Back to main menu                                      |");
                System.out.println("+=============================================================+");
                System.out.print("   Choose an option:      ");
                String choiceMore = sc.nextLine();      // RESET ALL DATA
                if ("1".equals(choiceMore)) {                                         
                    clearScreen();
                    printBanner("MORE > RESET ALL DATA");
                    System.out.println("    This process will delete all data");
                    System.out.print("    Are you sure you want to continue? y/n :      ");
                    char choiceReset = sc.nextLine().charAt(0);

                    // Multi-step safety confirmation checks before erasing CSV database
                    if (choiceReset == 'y') {
                        System.out.println("    To continue retype this sentense as it is:");
                        System.out.print("    -->   DELETE ALL DATA     :   ");
                        String response = sc.nextLine();

                        if (response.equals("DELETE ALL DATA")) {
                            System.out.println("    Deleting the data...");
                            boolean resetStatus = csvHandler.resetFile();

                            if (resetStatus == true) {
                                // Reload database (which is now empty) back into memory
                                account.setHistory(csvHandler.loadTransactions("src/dataset/dataset.csv"));
                                System.out.println("    Deletition Success!!!");
                                System.out.println("    Press Enter to go back to Main Menu");
                                sc.nextLine();
                                continue;
                            }

                        } 
                        
                        else {
                            System.out.println("    Confirmation Failed, Try Again.");
                        }
                    }
                } 
                
                else if ("2".equals(choiceMore)) {      //LOAD SAMPLE DATA
                    
                    clearScreen();
                    printBanner("MORE  > LOAD SAMPLE DATA");

                    // Only permit sample data initialization when local storage has no active records
                    if (account.getHistory().size() == 0) {

                        if (csvHandler.loadSampleData()) {
                            // Synchronize memory with newly created sample dataset file contents
                            account.setHistory(csvHandler.loadTransactions("src/dataset/dataset.csv"));
                            System.out.println("    Sample data loaded successfully!!!");
                        } 
                        
                        else {
                            System.out.println("    Sample data loading failed!!!");
                        }
                        
                    } 
                    
                    else {
                        System.out.println("    Sample load not possible.");
                        System.out.println("    File already has "+ account.getHistory().size()+" entries");
                    }
                }

                System.out.println("    press enter to go back to main menu:");
                sc.nextLine();
                continue;
            } 
            

            else if ("7".equals(choiceMain)) {      //EXIT
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                System.out.println("    Exiting...");
                sc.close();
                System.exit(0);
            } 
            
            else {
                continue;
            }

        }
    }


    /**
     * Clears the terminal screen.
     * Uses OS-specific terminal processes to invoke the 'cls' command on Windows platforms,
     * and prints dynamic empty feeds as fallback measures.
     */
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println();
        }
    }


    /**
     * Renders a beautifully structured and aligned terminal header banner.
     * Integrates user details retrieved dynamically from UserDetails model instances.
     * * @param heading The descriptive title corresponding to the user's active console action/view.
     */
    public static void printBanner(String heading) {
        DetailsHandler detailsHandler = new DetailsHandler();
        UserDetails userDetails = detailsHandler.detailLoader();
        System.out.println("+=============================================================+");
        System.out.println("|                 APEX FINANCIAL ENGINE                       |");
        System.out.println("+=============================================================+"); 
        System.out.println("|  Name: " + String.format("%-15s", userDetails.getUserName()) + "     Account Number: "
                + String.format("%-10s", userDetails.getAccountNumber()) + "       |");
        System.out.println("| Gender: " + userDetails.getGender() + "     DOB: "
                + String.format("%-10s", userDetails.getDateOfBirth()) + "  Phone Number: "
                + String.format("%-10s", userDetails.getPhoneNumber()) + "     |");
        System.out.println("+=============================================================+");
        System.out.println("|  --> " + String.format("%-50S", heading) + "     |");
    }
}