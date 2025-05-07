import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class ToDoFileHandling {
    private static final String TODO_DIR = "to-do lists";
    private static final Scanner sc = new Scanner(System.in);

    private static void createToDoList() {                                                  // creating a list
    BufferedWriter pen = null;                                                              // created on initial                
        try {
            System.out.print("Enter the Name of the To-Do List: ");
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
            System.out.println("\nYou are currently writing on " + listName + ". Enter '~' to quit.");
            pen.write("To-Do List created at " + time + "\n");
                            
            while (true) {
                String entry = sc.nextLine();
                if (entry.equalsIgnoreCase("~")) {
                    break; // Exit the loop when '~' is entered
                }
                pen.write("> " + entry + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        } finally {
            if (pen != null) {
                try {
                    pen.close();
                } catch (IOException e) {
                    System.out.println("Error!");
                    e.printStackTrace();
                }
            }
        }
    }

    private static void readToDoList(String fileName) {  
        System.out.print("Enter the name of the file to read.");
        System.out.print("For ease of use, show all to do lists first for a list, then enter the file name.");
        String readfileName = sc.nextLine();                                                // reading a list
                                                                                            // created on beta 2
        System.out.println("\nNow reading list " + readfileName + ":");
        
        if (!fileName.toLowerCase().endsWith(".txt")) {
            readfileName += ".txt";
        }
        File toRead = new File(TODO_DIR + File.separator + readfileName);
        if (!toRead.exists()) {
            System.out.println("...File does not exist.\n");
            return;
        }
        try (BufferedReader seer = new BufferedReader(new FileReader(toRead))) {
            String content;
            while ((content = seer.readLine()) != null) {
                System.out.println(content);
            }
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
    private static void viewList(){                                                         // viewing list of todos
        File libr = new File(TODO_DIR);                                                     // created on beta 2
        File[] entries = libr.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (entries == null || entries.length == 0) {
            System.out.println("No To-Do Lists found. Please create one, they they will shown here.");
        } else {
            System.out.println("\nAvailable To-Do Lists: ");
            for (File file : entries) {
                System.out.println("> " + file.getName());
            }
        }
    }

    private static void mdifList(Scanner sc) {
        // List all to-do lists
        System.out.println("Available To-Do Lists:");
        File todoDir = new File(TODO_DIR);
        if (todoDir.exists() && todoDir.isDirectory()) {
            for (File file : todoDir.listFiles()) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }
        } else {
            System.out.println("To-Do directory does not exist or is not a directory.");
            return;
        }
    
        System.out.print("Enter the name of the file to modify: ");
        String mdifList = sc.nextLine().trim();
        // Append .txt if not already present
        if (!mdifList.toLowerCase().endsWith(".txt")) {
            mdifList += ".txt";
        }
        File fileToModify = new File(TODO_DIR + File.separator + mdifList);
        if (!fileToModify.exists()) {
            System.out.println("File '" + mdifList + "' does not exist.");
            return;
        }
    
        BufferedWriter writer = null;
        try {
            LocalDateTime timeNow = LocalDateTime.now();
            DateTimeFormatter timeFrmtr = DateTimeFormatter.ofPattern("dd MMM yyyy @ HH:mm:ss");
            String timestamp = timeNow.format(timeFrmtr);
            writer = new BufferedWriter(new FileWriter(fileToModify, true));
            System.out.println("You are modifying the To-Do List. Enter '~' to quit: ");
            writer.write("Updated at " + timestamp + "\n");
            while (true) {
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("~")) {
                    break;
                }
                writer.write("> " + input + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while modifying '" + mdifList + "': " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error closing the writer for '" + mdifList + "': " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }   
    private static void delList(String fileName){
        System.out.print("Enter the name of file to delete.");
        System.out.print("For ease of use, show all to do lists first for a list, then enter the file name.");
        String delFileName = sc.nextLine();                                                // deleting a list

        if (!fileName.toLowerCase().endsWith(delFileName)){
            delFileName += ".txt";
        }
        File toDel = new File(TODO_DIR + File.separator + delFileName);
        if (!toDel.exists()) {
            System.out.println("...File does not exist.\n");
        }
        if (toDel.delete()) {
            System.out.println("File '" + delFileName + "' deleted successfully.");
        } else {
            System.out.println("Failed to delete the file '" + delFileName + "'.");
        }
    }
    public static void main(String[] args) {
        File todoDir = new File(TODO_DIR);                                                  // this one creates a new directory if it doesn't exist.
        if (!todoDir.exists()) {                                                            // created on initial
            todoDir.mkdir();
        }
        while (true) {
            System.out.println("\n1. Create a To-Do List \n2. Read a To-Do List \n3. View To-Do Lists \n4. Modify a To-Do List \n5. Delete a To-Do List \n6. Exit");
            System.out.print("\nOption: ");
            int opt = sc.nextInt();
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
                    mdifList(sc);
                    break;

                case 5:                                                                     // deleting a list
                    delList(TODO_DIR);
                    break;

                case 6:                                                                     // exiting the program
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
