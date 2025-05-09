import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class ToDoFileHandling {
    private static final String TODO_DIR = "to-do lists";
    private static final Scanner sc = new Scanner(System.in);

    //colours for texts in the cons
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";

    private static void createToDoList() {                                                  // creating a list
    BufferedWriter pen = null;                                                              // created on initial                
        try {
            System.out.print("List name: ");
            String listName = sc.nextLine();
        
            if (!listName.toLowerCase().endsWith(".txt")) {
                listName += ".txt";
            }
            String starlitPath = TODO_DIR + File.separator + listName;
                            
            // Adds time to every entry.                       
            LocalDateTime spacetimecontinuum = LocalDateTime.now();
            DateTimeFormatter whatgivesForm = DateTimeFormatter.ofPattern("dd MMM yyyy @ HH:mm:ss");
            String time = spacetimecontinuum.format(whatgivesForm);
    
            // When writing on a created file...
            pen = new BufferedWriter(new FileWriter(starlitPath, true));
            System.out.println( GREEN + "\nYou are currently writing on " + listName + ". Enter '~' to quit." + RESET);
            pen.write("To-Do List created at " + time + "\n");
                            
            while (true) {
                String entry = sc.nextLine();
                if (entry.equalsIgnoreCase("~")) {
                    break; // Exit the loop when '~' is entered
                }
                pen.write("> " + entry + "\n");
            }
        } catch (IOException e) {
            System.out.println(RED + "Error!" + RESET);
            e.printStackTrace();
        } finally {
            if (pen != null) {
                try {
                    pen.close();
                } catch (IOException e) {
                    System.out.println(RED + "Error!" + RESET);
                    e.printStackTrace();
                }
            }
        }
    }

    private static void readToDoList(String fileName) {  
        listAll();
        
        System.out.println(GREEN + "\nEnter the name of the file to read " + RESET + RED + "Or enter '~' to return to menu." + RESET);
        System.out.print("\n> ");
        String input = sc.nextLine();

        while (true) {
            if (input.equalsIgnoreCase("~")) {
                frontFrame();
            }
                                               // reading a list
                                                                                            // created on beta 2
        System.out.println(BLUE + "\nNow reading list " + input + "." + RESET);

        
        if (!fileName.toLowerCase().endsWith(".txt")) {
            input += ".txt";
        }
        File toRead = new File(TODO_DIR + File.separator + input);
        if (!toRead.exists()) {
            System.out.println(RED + "...File does not exist.\n" + RESET);
            return;
        }
        try (BufferedReader seer = new BufferedReader(new FileReader(toRead))) {
            String content;
            while ((content = seer.readLine()) != null) {
                System.out.println(content);
            }
        } catch (IOException e) {
            System.out.println(RED + "Error!" + RESET);
            e.printStackTrace();
        }
        menRetOExit();
    }
}
    private static void viewList(){                                                         // viewing list of todos
        File libr = new File(TODO_DIR);                                                     // created on beta 2
        File[] entries = libr.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (entries == null || entries.length == 0) {
            System.out.println(RED + "No To-Do Lists found. Please create one, they they will shown here." + RESET);
            System.out.print("\nCreate a new To-Do List? [Y/N]");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                createToDoList();
            } else if (choice.equalsIgnoreCase("n")) {
                frontFrame();
            } else {
                System.out.println("Invalid choice. Returning to menu.");
                frontFrame();
            }
        } else {
            System.out.println("\nAvailable To-Do Lists: ");
            for (File file : entries) {
                System.out.println("> " + file.getName());
            }
        }
        menRetOExit();
    }

    private static void mdifList() {
        listAll();
    
        System.out.print(GREEN + "\nEnter the name of the file to modify: " + RESET);
        String mdifList = sc.nextLine().trim();
        if (!mdifList.toLowerCase().endsWith(".txt")) {
            mdifList += ".txt";
        }
        File fileToModify = new File(TODO_DIR + File.separator + mdifList);
        if (!fileToModify.exists()) {
            System.out.println(RED + "File '" + mdifList + "' does not exist." + RESET);
            return;
        }
    
        BufferedWriter writer = null;
        try {
            LocalDateTime timeNow = LocalDateTime.now();
            DateTimeFormatter timeFrmtr = DateTimeFormatter.ofPattern("dd MMM yyyy @ HH:mm:ss");
            String timestamp = timeNow.format(timeFrmtr);
            writer = new BufferedWriter(new FileWriter(fileToModify, true));
            System.out.print(GREEN + "\nYou are modifying the To-Do List." + RESET + RED + "Enter '~' to quit." + RESET + "\n> ");

            writer.write("\nUpdated at " + timestamp + "\n");

            while (true) {
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("~")) {
                    break;
                }
                writer.write(BLUE + "> " + RESET + input + "\n");
            }
        } catch (IOException e) {
            System.out.println(RED + "An error occurred while modifying '" + mdifList + "': " + e.getMessage() + RESET);
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println(RED + "Error closing the writer for '" + mdifList + "': " + e.getMessage() + RESET);
                    e.printStackTrace();
                }
            }
        }
    }   
    private static void delList(String fileName){
        listAll();
        
        System.out.print(RED + "\nEnter the name of file to delete: " + RESET);
        String delFileName = sc.nextLine();                                                // deleting a list

        if (!fileName.toLowerCase().endsWith(delFileName)){
            delFileName += ".txt";
        }
        File toDel = new File(TODO_DIR + File.separator + delFileName);
        if (!toDel.exists()) {
            System.out.println(RED + "...File does not exist.\n" + RESET);
        }
        if (toDel.delete()) {
            System.out.println(GREEN+ "File '" + delFileName + "' deleted successfully." + RESET);
        } else {
            System.out.println(RED + "Failed to delete the file '" + delFileName + "'." + RESET);
        }
        System.out.print("\nDo you want to delete another file? [Y/N]: ");
        String call = sc.nextLine();

        if(call.equalsIgnoreCase("y")){
            delList(fileName);
            System.out.print(PURPLE + "\nDo you want to delete another file? (y/n): " + RESET);
            call = sc.nextLine();
        } else if (call.equalsIgnoreCase("n")) {
            frontFrame();
        }
    }
    public static void listAll(){
        // List all to-do lists
        System.out.println(GREEN + "\n[Available To-Do Lists]:\n" + RESET);
        File todoDir = new File(TODO_DIR);
        if (todoDir.exists() && todoDir.isDirectory()) {
            for (File file : todoDir.listFiles()) {
                if (file.isFile()) {
                    System.out.println("> " + file.getName());
                }
            }
        } else {
            System.out.println(RED + "To-Do directory does not exist or is not a directory." + RESET);
            return;
        }
    }
    public static void systExit(){
        System.out.println("\nProgram Exited.");
        System.exit(0);
    }
    public static void frontFrame(){
        
        File todoDir = new File(TODO_DIR);                                                  // this one creates a new directory if it doesn't exist.
        if (!todoDir.exists()) {                                                            // created on initial
            todoDir.mkdir();
        }
        while (true) {
            System.out.println("\n=== To-Do List Manager ===");
            System.out.println("\n1. Create a To-Do List \n2. Read a To-Do List \n3. View To-Do Lists \n4. Modify a To-Do List \n5. Delete a To-Do List \n6. Exit");
            System.out.print("\nOption: ");
            int opt = sc.nextInt();
            System.out.println("*---===-===-===-===-===-===-===-===---*");
            sc.nextLine();
    
            switch (opt) {
                case 1:                                                                     // creation of a list
                    createToDoList();
                    break;

                case 2:                                                                     // reading a list
                    readToDoList(TODO_DIR);
                    break;

                case 3:                                                                     // viewing a list
                    viewList();
                    break;
                
                case 4:                                                                     // modifying a list
                    mdifList();
                    break;

                case 5:                                                                     // deleting a list
                    delList(TODO_DIR);
                    break;

                case 6:                                                                     // exiting the program
                    systExit();

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
                }
            }
        }

    public static void menRetOExit(){

        System.out.println("\nReturn to menu? [Y/N]");
        String choice = sc.nextLine();

        if(choice.equalsIgnoreCase("y")){
            frontFrame();
        } else if (choice.equalsIgnoreCase("n")){
            systExit();
        } else {
            System.out.println(RED + "Invalid choice.\n Returning to menu." + RESET);
            frontFrame();
        }
    }

    public static void main(String[] args) {
       frontFrame(); 
    }
}