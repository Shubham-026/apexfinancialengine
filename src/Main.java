import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.*;
import storage.*;
// import processing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        CSVHandler csvHandler = new CSVHandler();
        DetailsHandler detailsHandler = new DetailsHandler();
        UserDetails userDetails = detailsHandler.detailLoader();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss").withZone(ZoneId.systemDefault());
        Account account = new Account();
        account.setHistory(csvHandler.loadTransactions("src/dataset/dataset.csv"));
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
            System.out.print("      Choose an option: ");

            String choiceMain = sc.nextLine();
            System.out.println();
            if ("1".equals(choiceMain)) {
                clearScreen();
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
                for (Transaction transact : account.getHistory()) {
                    System.out.println("|" + transact.getId() + "  " + transact.getType() + "  "
                            + String.format("%-15s", transact.getCategory()) + "  "
                            + String.format("%-30.30s", transact.getDescription()) + "  "
                            + formatter.format(Instant.ofEpochMilli(transact.getTimestamp())) + "  "
                            + String.format("%-13.2f", transact.getAmount()) + "|");
                    System.out.println(
                            "+---------------------------------------------------------------------------------------------------+");
                }
                System.out.println("|        CURRENT BALANCE : " + String.format("%-70.2f", account.calculateBalance()) + "   |");
                System.out.println("+===================================================================================================+");
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            } else if ("2".equals(choiceMain)) {
                clearScreen();
                printBanner("New Transaction (Deposit/Expense)");
                System.out.println("    DEPOSIT OR EXPENSE :   d/e?");
                char transactionType = sc.nextLine().charAt(0);
                clearScreen();
                TransactionType type;
                if ('d' == transactionType) {
                    printBanner("Transaction Type \"DEPOSIT\" ");
                    type = TransactionType.DEPOSIT;
                } else if ('e' == transactionType) {
                    printBanner("Transaction Type \"EXPENSE\" ");
                    type = TransactionType.EXPENSE;

                } else {
                    System.out.println("    Wrong Choice");
                    System.out.println("    Press \"ENTER\" to go back to main menu:");
                    sc.nextLine();
                    continue;

                }
                System.out.print("Enter Amount: ");
                double transactionAmount = sc.nextDouble();
                sc.nextLine();
                System.out.print("Enter Category: ");
                String transactionCategory = sc.nextLine();
                System.out.print("Enter Short Description: ");
                String transactionDescription = sc.nextLine();
                String newTransactionID;
                if (account.getHistory().isEmpty()) {
                    newTransactionID = "TXN0001";
                } else {

                    int nextNum = Integer.parseInt(account.getHistory().get(account.getHistory().size()-1).getId().substring(3)) + 1;
                    newTransactionID = "TXN" + String.format("%04d", nextNum);
                }
                Transaction newTransaction = new Transaction(newTransactionID,
                        type,
                        transactionAmount,
                        transactionCategory,
                        transactionDescription,
                        System.currentTimeMillis());
                boolean appendStatus = csvHandler.appendTransaction(newTransaction); 

                        if (appendStatus) {
                            account.getHistory().add(newTransaction);
                            System.out.println("    Transaction added successfully!!!");
                            System.out.println("    Press \"ENTER\" to continue:");
                        } else {
                            System.out.println("    Press \"ENTER\" to continue:");
                        }
                sc.nextLine();
                continue;
            } else if ("3".equals(choiceMain)) {
                clearScreen();
                printBanner("Search Transaction ");
                System.err.println("    Enter the Transaction no, eg. TXN0000");
                
                System.out.println("    Feature not available yet");
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            } else if ("4".equals(choiceMain)) {
                clearScreen();
                printBanner("Run Spend Analytics");
                System.out.println("    Feature not available yet");
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            } else if ("5".equals(choiceMain)) {
                clearScreen();
                printBanner("View Category Spending Breakdown");
                System.out.println("    Feature not available yet");
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            } else if ("6".equals(choiceMain)) {
                clearScreen();
                printBanner("MORE");
                System.out.println("|   1. Reset all data                                         |");
                System.out.println("|   2. Load sample data                                       |");
                System.out.println("|   3. Back to main menu                                      |");
                System.out.println("+=============================================================+");
                System.out.print("   Choose an option: ");
                String choiceMore = sc.nextLine();
                if ("1".equals(choiceMore)) {
                    clearScreen();
                    printBanner("MORE > RESET ALL DATA");
                    System.out.println("    This process will delete all data");
                    System.out.println("    Are you sure you want to continue? y/n :");
                    char choiceReset = sc.nextLine().charAt(0);

                    if (choiceReset == 'y') {
                        System.out.println("    To continue retype this sentense as it is:");
                        System.out.println("    -->   DELETE ALL DATA");
                        String response = sc.nextLine();

                        if (response.equals("DELETE ALL DATA")) {
                            System.out.println("    Deleting the data...");
                            boolean resetStatus = csvHandler.resetFile();

                            if (resetStatus == true) {
                                account.setHistory(csvHandler.loadTransactions("src/dataset/dataset.csv"));
                                System.out.println("    Deletition Success!!!");
                                System.out.println("    Press Enter to go back to Main Menu");
                                sc.nextLine();
                                continue;
                            }

                        } else {
                            System.out.println("    Confirmation Failed, Try Again.");
                        }
                    }
                } else if ("2".equals(choiceMore)) {
                    clearScreen();
                    printBanner("MORE  > LOAD SAMPLE DATA");
                    if (account.getHistory().size() == 0) {
                        if (csvHandler.loadSampleData()) {
                            account.setHistory(csvHandler.loadTransactions("src/dataset/dataset.csv"));
                            System.out.println("    Sample data loaded successfully!!!");
                        } else {
                            System.out.println("    Sample data loading failed!!!");
                        }
                        
                    } else {
                        System.out.println("    Sample load not possible.");
                        System.out.println("    File already has "+ account.getHistory().size()+" entries");
                    }
                }
                System.out.println("    press enter to go back to main menu:");
                sc.nextLine();
                continue;
            } else if ("7".equals(choiceMain)) {
                System.out.println("    Press \"ENTER\" to continue:");
                sc.nextLine();
                System.out.println("    Exiting...");
                sc.close();
                System.exit(0);
            } else {
                continue;
            }

        }
    }

    public static void clearScreen() { // A method to clear terminal screen
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println();
        }
    }

    public static void printBanner(String heading) {
        DetailsHandler detailsHandler = new DetailsHandler();
        UserDetails userDetails = detailsHandler.detailLoader();
        System.out.println("+=============================================================+"); // 61 char space
        System.out.println("|                 APEX FINANCIAL ENGINE                       |");
        System.out.println("+=============================================================+"); // 61 char space
        System.out.println("|  Name: " + String.format("%-15s", userDetails.getUserName()) + "     Account Number: "
                + String.format("%-10s", userDetails.getAccountNumber()) + "       |");
        System.out.println("| Gender: " + userDetails.getGender() + "     DOB: "
                + String.format("%-10s", userDetails.getDateOfBirth()) + "  Phone Number: "
                + String.format("%-10s", userDetails.getPhoneNumber()) + "     |");
        System.out.println("+=============================================================+");
        System.out.println("|  --> " + String.format("%-50S", heading) + "     |");
    }
}
