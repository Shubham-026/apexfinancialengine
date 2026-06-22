import java.util.*;
public class Main {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            clearScreen();
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
            
            int choice = sc.nextInt();
            sc.nextLine();
            System.out.println();
            if(choice == 1){
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choice == 2) {
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choice == 3) {
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choice == 4) {
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choice == 5) {
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choice == 6) {
                System.out.println("Feature not available yet");
                System.out.println("Press \"ENTER\" to continue:");
                sc.nextLine();
                continue;
            }
            else if (choice == 7) {
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
