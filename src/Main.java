import java.util.*;
import storage.*;


public class Main {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        CSVHandler csvHandler = new CSVHandler();
        while (true) {
            clearScreen();
            System.out.println("Working directory: " + System.getProperty("user.dir"));
            System.out.println("+=============================================================+");
            System.out.println("|                 APEX FINANCIAL ENGINE                       |");
            System.out.println("+=============================================================+");
            System.out.println("|   1. View Balance and History                               |");
            System.out.println("|   2. New Transaction (Deposit/Expense)                      |");
            System.out.println("|   3. Search Transaction                                     |");
            System.out.println("|   4. Run Spend Analytics                                    |");
            System.out.println("|   5. View Category Spending Breakdown                       |");
            System.out.println("|   6. More                                                   |");
            System.out.println("|   7. EXIT                                                   |");
            System.out.println("+=============================================================+");
            System.out.print("   Choose an option: ");
            
            int choiceMain = sc.nextInt();
            sc.nextLine();
            System.out.println();
            if(choiceMain == 1){
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choiceMain == 2) {
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choiceMain == 3) {
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choiceMain == 4) {
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choiceMain == 5) {
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choiceMain == 6) {
                System.out.println("+=============================================================+");
                System.out.println("|                 APEX FINANCIAL ENGINE                       |");
                System.out.println("+=============================================================+");
                System.out.println("|   1. Reset all data                                         |");
                System.out.println("|   2. Load sample data                                       |");
                System.out.println("|   3. Back to main menu                                      |");
                System.out.println("+=============================================================+");
                System.out.print("   Choose an option: ");
                int choiceMore = sc.nextInt();
                sc.nextLine();
                if(choiceMore == 1){

                    System.out.println("This process will delete all data");
                    System.out.println("Are you sure you want to continue? y/n :");
                    char choiceReset = sc.nextLine().charAt(0);

                    if (choiceReset == 'y') {
                        System.out.println("To continue retype this sentense as it is:");
                        System.out.println(" -->   DELETE ALL DATA:");
                        String response = sc.nextLine();

                        if (response.equals("DELETE ALL DATA"))  {
                            System.out.println("Deleting the data...");
                            boolean resetStatus = csvHandler.resetFile();

                            if (resetStatus == true) {
                                System.out.println("Deletition Success!!!");
                                System.out.println("Press Enter to go back to Main Menu");
                                sc.nextLine();
                                continue;
                            }
                            else{
                                System.out.println("Press Enter to go back to Main Menu");
                                sc.nextLine();
                                continue;
                            }
                          
                        }
                        else {
                            System.out.println("Confirmation Failed, Try Again.");
                            System.out.println("Press Enter to go back to Main Menu");
                            sc.nextLine();
                            continue;
                        }
                    }
                    else{
                        System.out.println("Press Enter to go back to Main Menu");
                        sc.nextLine();
                        continue;

                    }
                }
                else if(choiceMore == 2){
                    System.out.println("Feature not available yet");
                    System.out.println("Press \"ENTER\" to continue:");
                    sc.nextLine();
                    continue;
                }
                else if (choiceMore == 3) {
                    System.out.println("press enter to go back to main menu:");
                    sc.nextLine();
                    continue;
                }
                else{
                    continue;

                }
            }
            else if (choiceMain == 7) {
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                System.out.println("Exiting...");
                sc.close();
                System.exit(0);
            }
            else{
                continue;
            }

            
        }
    }

    public static void clearScreen(){           //A method to clear terminal screen
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
        try {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println();
        }
    }
}
