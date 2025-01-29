package Assignments.A1;

import org.example.JDBC.DBProperties;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

public class BookApplication {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        BookDatabaseManager db = new BookDatabaseManager();
            while (true) {
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
                        editAttributes();
                        break;
                    case 4:
                        addBook();
                        break;
                    case 5:
                        // Ensured by our object relationships, nothing needed here.
                        break;
                    case 6:
                        System.out.println("Exiting program...");
                        return;
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
            System.out.println("5. Make sure the relationships are maintained");
            System.out.println("6. Quit");
        }

        private static void printAllBooks(BookDatabaseManager db) throws SQLException {
            for (Book book: db.books) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author(s):");
                for (Author author: book.getAuthorList()) {
                    System.out.println("\t" + author.getName());
                }

            }
    }
    private static void printAllAuthors(BookDatabaseManager db) throws SQLException {
        for (Author author : db.authors) {
            System.out.println("Name: " + author.getName());
            System.out.println("Authored Titles:");
            for (Book book: author.getAuthoredBooks()) {
                System.out.println("\t" + book.getTitle());
            }
        }
    }
    private static void editAttributes(BookDatabaseManager db) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an attribute:");
        System.out.println("[1] Title");
        System.out.println("[2] Author");
        int choice = scanner.nextInt();
        if (choice == 1) {}
        else if (choice == 2) {}
        else {}
        scanner.close();
    }
    private static void addBook() throws SQLException {}

    public static void editBookAttributes(BookDatabaseManager db) throws SQLException {
        scanner = new Scanner(System.in);

        // User Selects a book to edit
        System.out.println("Select a book to edit:");
        int i = 0;
        for (Book book: db.books) {
            System.out.printf("[%d] %s%n", i++, book.getTitle());
        }

        // Lists the current attributes from the db
        Book choice = db.books.get(scanner.nextInt());
        System.out.printf("Attributes for %s:%n", choice.getTitle() );
        System.out.println("Title: " + choice.getTitle());
        System.out.println("Edition number:" + choice.getEditionumber());
        System.out.println("Copyright:" + choice.getCopyright());
        System.out.println("<------------>");
        System.out.println("enter new title: enter to skip");
        System.out.println("enter new Edition Number: enter to skip");
        System.out.println("enter new Cpoyright: enter to skip");


        // update book attributes
        // syncronize the book db
    }
    public static void editAuthorAttributes(BookDatabaseManager db) throws SQLException {}

}


