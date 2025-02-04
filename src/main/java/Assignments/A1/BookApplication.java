package Assignments.A1;

import org.example.JDBC.DBProperties;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookDatabaseManager db = new BookDatabaseManager();


    public static void main(String[] args) throws SQLException {
        Library library = new Library(db);

            while (true) {

                printMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                switch (choice) {
                    case 1:
                        printAllBooks(library);
                        break;
                    case 2:
                        printAllAuthors(library);
                        break;
                    case 3:
                        editAttributes(library);
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

    private static void printAllBooks(Library library) throws SQLException {
            for (Book book: library.getBooks()) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author(s):");
                for (Author author: book.getAuthorList()) {
                    System.out.println("\t" + author.getFullName());
                }

            }
    }

    private static void printAllAuthors(Library library) throws SQLException {
        for (Author author : library.getAuthors()) {
            System.out.println("Name: " + author.getFullName());
            System.out.println("Authored Titles:");
            for (Book book: author.getAuthoredBooks()) {
                System.out.println("\t" + book.getTitle());
            }
        }
    }

    private static void editAttributes(Library library) throws SQLException {

        System.out.println("Select an attribute:");
        System.out.println("[1] Title");
        System.out.println("[2] Author");
        int choice = scanner.nextInt();
        if (choice == 1) {
            editBookAttributes(library);
        }
        else if (choice == 2) {
            editAuthorAttributes(library);
        }
        else {}
    }

    public static void editBookAttributes(Library library) throws SQLException {
        // User Selects a book to edit
        System.out.println("Select a book to edit:");
        int i = 0;
        for (Book book: library.getBooks()) {
            System.out.printf("[%d] %s%n", i++, book.getTitle());
        }

        // Lists the current attributes from the db
        int selectedBookIndex = scanner.nextInt();
        scanner.nextLine(); // consume newline character
        Book choice = library.getBooks().get(selectedBookIndex);
        System.out.printf("Attributes for %s:%n", choice.getTitle() );
        System.out.println("Title: " + choice.getTitle());
        System.out.println("Edition number:" + choice.getEditionumber());
        System.out.println("Copyright:" + choice.getCopyright());
        
        // Get updated information
        System.out.println("<------------>");
        System.out.println("enter new title: ");
        String newTitle = scanner.nextLine();
        System.out.println("enter new Edition Number: ");
        String newEditionNumber = scanner.nextLine();
        System.out.println("enter new Copyright: ");
        String newCopyright = scanner.nextLine();
        db.updateDatabase("titles","title", newTitle, "isbn", choice.getIsbn());
        db.updateDatabase("titles","editionNumber", newEditionNumber, "isbn", choice.getIsbn());
        db.updateDatabase("titles","copyright", newCopyright, "isbn", choice.getIsbn());

    }

    public static void editAuthorAttributes(Library library) throws SQLException {

        // User Selects an author to edit
        System.out.println("Select an author to edit:");
        int i = 0;
        for (Author author: library.getAuthors()) {
            System.out.printf("[%d] %s%n", i++, author.getFullName());
        }

        // Lists the current attributes from the db
        int selectedAuthorIndex = scanner.nextInt();
        scanner.nextLine(); // consume newline character
        Author choice = library.getAuthors().get(selectedAuthorIndex);
        System.out.printf("Attributes for %s:%n", choice.getFirstName());
        System.out.println("Full name: " + choice.getFullName());

        // Get updated information
        System.out.println("<------------>");
        System.out.println("enter new firstName: ");
        String newFirstName = scanner.nextLine();
        System.out.println("enter new lastName: ");
        String newLastName = scanner.nextLine();
        library.getAuthors().get(library.getAuthors().indexOf(choice)).setFirstName(newFirstName);
        library.getAuthors().get(library.getAuthors().indexOf(choice)).setLastName(newLastName);

        db.updateDatabase("authors", "firstName", newFirstName, "authorID", choice.getID());
        db.updateDatabase("authors", "lastName", newLastName, "authorID", choice.getID());


    }

    public static void addAuthor(Library library) throws SQLException {
        System.out.println("input author first name:");
        String firstName = scanner.nextLine();
        System.out.println("input author last name:");
        String lastName = scanner.nextLine();

        List<Book> authoredBooks = new ArrayList<>();
        while (true){
            System.out.println("Enter Authored book ID:");
            System.out.println("[0] Finish Selection");
        for (int i = 0; i < library.getBooks().size() - 1; i++) {
            if (authoredBooks.contains(library.getBooks().get(i))) {
             break;
            }
            else {
                System.out.println("[" + (i + 1) + "] " + library.getBooks().get(i).getTitle());
            }
        }
        int choice = scanner.nextInt();
        if (choice == 0) {
            return;
        }
        else if (choice >= library.getBooks().size() && choice > 1 ) {
            // add the book tp the list
            authoredBooks.add(library.getBooks().get(choice - 1));
        }
        else {break;}
        }
    }

    public static void addBook() throws SQLException {}
}


