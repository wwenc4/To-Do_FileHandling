import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class ToDoFileHandling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create a To-Do List \n2. Read a To-Do List \n3. List All To-Do Lists \n4. Delete a To-Do List \n5. Exit");
            System.out.print("\nOption: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1:
                    BufferedWriter writer = null;
                    try {
                        System.out.print("Enter the name of the To-Do List (e.g., MyList.txt): ");
                        String listName = sc.nextLine();
                        LocalDateTime timeNow = LocalDateTime.now();
                        DateTimeFormatter timeFrmtr = DateTimeFormatter.ofPattern("dd MMM yyyy @ HH:mm:ss");
                        String timestamp = timeNow.format(timeFrmtr);
                        writer = new BufferedWriter(new FileWriter(listName, true));
                        System.out.println("You are currently creating a To-Do List. Enter 'Exit' to quit: ");
                        writer.write("To-Do List created at " + timestamp + "\n");
                        while (true) {
                            String input = sc.nextLine();
                            if (input.equalsIgnoreCase("Exit")) {
                                break;
                            }
                            writer.write("> " + input + "\n");
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred, please try again.");
                        e.printStackTrace();
                    } finally {
                        if (writer != null) {
                            try {
                                writer.close();
                            } catch (IOException e) {
                                System.out.println("Error closing the writer.");
                                e.printStackTrace();
                            }
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter the file name to read (e.g., MyList.txt): ");
                    String readFileName = sc.nextLine();
                    File toRead = new File(readFileName);
                    if (!toRead.exists()) {
                        System.out.println("File does not exist.");
                        break;
                    }
                    try (BufferedReader reader = new BufferedReader(new FileReader(toRead))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred while reading the file.");
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    File directory = new File(".");
                    File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
                    if (files == null || files.length == 0) {
                        System.out.println("No To-Do List files found, please create one and they will be listed here.");
                    } else {
                        System.out.println("Available To-Do List files:");
                        for (File file : files) {
                            System.out.println("- " + file.getName());
                        }
                    }
                    break;

                case 4:
                    System.out.print("Enter the file name to delete (e.g., MyList.txt): ");
                    String deleteFileName = sc.nextLine();
                    File fileToDelete = new File(deleteFileName);
                    if (fileToDelete.exists()) {
                        if (fileToDelete.delete()) {
                            System.out.println("File deleted successfully.");
                        } else {
                            System.out.println("Failed to delete the file.");
                        }
                    } else {
                        System.out.println("File does not exist.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting the program.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}