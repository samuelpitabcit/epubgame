package ca.bcit.httphacks.epub;

import java.util.Scanner;
import java.io.File;

public class Menu
{

    /**
     * Displays the ASCII art intro and main menu.
     *
     * @return a string path for the selected option, or null to quit.
     */
    public String show()
    {

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
        while (true)
        {
            if (scanner.hasNextInt())
            {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                break;
            }
            else
            {
                System.out.print("Enter a valid number (1-3): ");
                scanner.next(); // discard bad input
            }
        }

        switch (choice)
        {
            case 1 ->
            {
                return selectFileFromRes(scanner);
            }
            case 2 ->
            {
                return "test.txt";

            }
            case 3 ->
            {
                System.out.println("Goodbye!");
                return null;
            }
            default ->
            {
                System.out.println("Invalid choice. Exiting...");
                return null;
            }
        }
    }


    /*
     * Helper method to list files from res
     */
    private String selectFileFromRes(Scanner scanner)
    {
        File resDir;
        resDir = new File("src/main/res");

        // Check if folder exists!
        if (!resDir.exists() ||
            !resDir.isDirectory())
        {

            System.out.println("\nError: no res folder found.");
            System.out.println("Make a folder in root!");

            return show();
        }

        // Getting all files from dir
        File[] files;
        files = resDir.listFiles((dir, name) ->
                                 {

                                     String lower;
                                     lower = name.toLowerCase();

                                     return lower.endsWith(".txt") ||
                                            lower.endsWith(".epub");
                                 });

        if (files == null ||
            files.length == 0)
        {
            System.out.println("No .txt nor .epub were found.");
            return show();
        }


        System.out.println("\n=== Lists of Books ===");

        for (int i = 0; i < files.length; i++)
        {
            System.out.printf("%d. %s%n",
                              (i + 1),
                              files[i].getName());
        }

        System.out.printf("%d. [Back to Main Menu]%n",
                          files.length + 1);
        System.out.print("Select book number: ");

        int selection;
        selection = -1;

        while (true)
        {
            if (scanner.hasNextInt())
            {
                selection = scanner.nextInt();
                scanner.nextLine();

                if (selection >= 1 &&
                    selection <= files.length + 1)
                {
                    break;
                }
            }
            else
            {
                scanner.next(); // discard bad input
            }
            System.out.print("Invalid selection. Try again: ");
        }

        // 'Back' option
        if (selection == files.length + 1)
        {
            return show();
        }
        return files[selection - 1].getPath();
    }
}
