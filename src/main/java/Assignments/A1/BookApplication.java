package Assignments.A1;

import org.example.JDBC.DBProperties;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

public class BookApplication {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        
            while (true) {
                // makes more sense to do continuous reads to the db in case of concurrent access updates
                BookDatabaseManager db = new BookDatabaseManager();
                printMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        printAllBooks(db);
                        break;
                    case 2:
                        printAllAuthors(db);
                        break;
                    case 3:
                        editAttributes(db);
                        break;
                    case 4:
                        addBook();
                        break;
                    case 5:
                        System.out.println("Exiting program...");
                        return;
                    case 6:
                        db.resetDatabase();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }

        private static void printMenu() {

            System.out.println("Select an option:");
            System.out.println("1. Print all the books from the database (showing the authors)");
            System.out.println("2. Print all the authors from the database (showing the books)");
            System.out.println("3. Edit a book’s or an author’s attributes");
            System.out.println("4. Add a book to the database for existing or new author(s)");
            System.out.println("5. Quit");
            System.out.println("6. Reset the database");
        }

        private static void printAllBooks(BookDatabaseManager db) throws SQLException {
            for (Book book: db.getBooks()) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author(s):");
                for (Author author: book.getAuthorList()) {
                    System.out.println("\t" + author.getName());
                }

            }
    }
    private static void printAllAuthors(BookDatabaseManager db) throws SQLException {
        for (Author author : db.getAuthors()) {
            System.out.println("Name: " + author.getName());
            System.out.println("Authored Titles:");
            for (Book book: author.getAuthoredBooks()) {
                System.out.println("\t" + book.getTitle());
            }
        }
    }
    private static void editAttributes(BookDatabaseManager db) throws SQLException {

        System.out.println("Select an attribute:");
        System.out.println("[1] Title");
        System.out.println("[2] Author");
        int choice = scanner.nextInt();
        if (choice == 1) {
            editBookAttributes(db);
        }
        else if (choice == 2) {
            editAuthorAttributes(db);
        }
        else {}
    }
    private static void addBook() throws SQLException {}

    public static void editBookAttributes(BookDatabaseManager db) throws SQLException {
        // User Selects a book to edit
        System.out.println("Select a book to edit:");
        int i = 0;
        for (Book book: db.getBooks()) {
            System.out.printf("[%d] %s%n", i++, book.getTitle());
        }

        // Lists the current attributes from the db
        int selectedBookIndex = scanner.nextInt();
        Book choice = db.getBooks().get(selectedBookIndex);
        System.out.printf("Attributes for %s:%n", choice.getTitle() );
        System.out.println("Title: " + choice.getTitle());
        System.out.println("Edition number:" + choice.getEditionumber());
        System.out.println("Copyright:" + choice.getCopyright());
        
        // Get updated information
        System.out.println("<------------>");
        System.out.println("enter new title: ");
        String newTitle = scanner.next();
        System.out.println("enter new Edition Number: ");
        String newEditionNumber = scanner.next();
        System.out.println("enter new Copyright: ");
        String newCopyright = scanner.next();
        db.updateDatabase("titles","title", newTitle, "isbn", choice.getIsbn());
        db.updateDatabase("titles","editionNumber", newEditionNumber, "isbn", choice.getIsbn());
        db.updateDatabase("titles","copyright", newCopyright, "isbn", choice.getIsbn());

    }
    public static void editAuthorAttributes(BookDatabaseManager db) throws SQLException {

        // User Selects an author to edit
        System.out.println("Select an author to edit:");
        int i = 0;
        for (Author author: db.getAuthors()) {
            System.out.printf("[%d] %s%n", i++, author.getName());
        }

        // Lists the current attributes from the db
        int selectedAuthorIndex = scanner.nextInt();
        scanner.nextLine(); // consume newline character
        Author choice = db.getAuthors().get(selectedAuthorIndex);
        System.out.printf("Attributes for %s:%n", choice.getName() );
        System.out.println("Full name: " + choice.getName());

        // Get updated information
        System.out.println("<------------>");
        System.out.println("enter new firstName: ");
        String newFirstName = scanner.nextLine();
        System.out.println("enter new lastName: ");
        String newLastName = scanner.nextLine();
        // upd
        db.updateDatabase("authors", "firstName", newFirstName, "authorID", choice.getID());
        db.updateDatabase("authors", "lastName", newLastName, "authorID", choice.getID());

    }

}


