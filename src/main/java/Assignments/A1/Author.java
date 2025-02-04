package Assignments.A1;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private List<Book> authoredBooks;
    private String firstName;
    private String lastName;
    private String ID;


    public Author(String ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authoredBooks = new ArrayList<Book>();
    }

    public void addAuthoredBook(Book book) {
        if (!this.authoredBooks.contains(book)) {
            this.authoredBooks.add(book);
        }
    }

    public List<Book> getAuthoredBooks() {
        return authoredBooks;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getID() {
        return ID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
