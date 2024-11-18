import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    private static List<String> itemList = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFileName = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char choice;

        do {
            displayMenu();
            choice = scanner.nextLine().toUpperCase().charAt(0);
            switch (choice) {
                case 'A':
                    addItem(scanner);
                    break;
                case 'D':
                    deleteItem(scanner);
                    break;
                case 'I':
                    insertItem(scanner);
                    break;
                case 'V':
                    viewList();
                    break;
                case 'M':
                    moveItem(scanner);
                    break;
                case 'O':
                    openList(scanner);
                    break;
                case 'S':
                    saveList(scanner);
                    break;
                case 'C':
                    clearList();
                    break;
                case 'Q':
                    quitProgram(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 'Q');
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("V - View the list");
        System.out.println("M - Move an item");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear all items from the current list");
        System.out.println("Q - Quit the program");
        System.out.print("Choose an option: ");
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter the item to add: ");
        itemList.add(scanner.nextLine());
        needsToBeSaved = true;
    }

    private static void deleteItem(Scanner scanner) {
        System.out.print("Enter the index of the item to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < itemList.size()) {
            itemList.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void insertItem(Scanner scanner) {
        System.out.print("Enter the item to insert: ");
        String item = scanner.nextLine();
        System.out.print("Enter the index to insert the item at: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index <= itemList.size()) {
            itemList.add(index, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void viewList() {
        System.out.println("\nList:");
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(i + ": " + itemList.get(i));
        }
    }

    private static void moveItem(Scanner scanner) {
        System.out.print("Enter the index of the item to move: ");
        int fromIndex = Integer.parseInt(scanner.nextLine());
        if (fromIndex >= 0 && fromIndex < itemList.size()) {
            System.out.print("Enter the new index for the item: ");
            int toIndex = Integer.parseInt(scanner.nextLine());
            if (toIndex >= 0 && toIndex <= itemList.size()) {
                String item = itemList.remove(fromIndex);
                itemList.add(toIndex, item);
                needsToBeSaved = true;
            } else {
                System.out.println("Invalid index.");
            }
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void openList(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save them before opening a new list? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                saveList(scanner);
            }
        }
        System.out.print("Enter the filename to open: ");
        String filename = scanner.nextLine();
        try {
            itemList = Files.readAllLines(Paths.get(filename));
            currentFileName = filename;
            needsToBeSaved = false;
            System.out.println("List loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static void saveList(Scanner scanner) {
        if (currentFileName == null) {
            System.out.print("Enter the filename to save as: ");
            currentFileName = scanner.nextLine();
        }
        try {
            Files.write(Paths.get(currentFileName), itemList);
            needsToBeSaved = false;
            System.out.println("List saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void clearList() {
        itemList.clear();
        needsToBeSaved = true;
    }

    private static void quitProgram(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save them before quitting? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                saveList(scanner);
            }
        }
        System.out.println("Goodbye!");
    }
}
