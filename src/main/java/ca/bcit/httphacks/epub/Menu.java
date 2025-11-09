package ca.bcit.httphacks.epub;

import java.util.Scanner;

public class Menu {

    /**
     * Displays the ASCII art intro and main menu.
     * @return a string path for the selected option, or null to quit.
     */
    public String show() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("""
             ___________________
             | _______________ |
             | |XXXXXXXXXXXXX| |
             | |XXXXXXXXXXXXX| |
             | |XXXXXXXXXXXXX| |
             | |XXXXXXXXXXXXX| |
             | |XXXXXXXXXXXXX| |
             |_________________|
                 _[_______]_
             ___[___________]___
            |         [_____] []|__
            |         [_____] []|  \\__
            L___________________J     \\ \\___\\/
             ___________________      /\\
            /###################\\    (__)
            """);

        System.out.println("  Welcome to E-Type Game!  ");
        System.out.println("===========================");
        System.out.println("Please select an option:");
        System.out.println("1. Select a Book");
        System.out.println("2. Continue from previous");
        System.out.println("3. Exit");
        System.out.print("> ");

        int choice;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                break;
            } else {
                System.out.print("Enter a valid number (1-3): ");
                scanner.next(); // discard bad input
            }
        }

        switch (choice) {
            case 1 -> {
                System.out.print("Enter file path: ");
                return scanner.nextLine();
            }
            case 2 -> {
                return "test.txt";

            }
            case 3 -> {
                System.out.println("Goodbye!");
                return null;
            }
            default -> {
                System.out.println("Invalid choice. Exiting...");
                return null;
            }
        }
    }
}
