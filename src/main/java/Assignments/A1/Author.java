package Assignments.A1;

import java.util.ArrayList;
import java.util.List;

/**
 * An author relating to the library database.
 * can be an author of one or many books.
 */
public class Author {
    private List<Book> authoredBooks;
    private String firstName;
    private String lastName;
    private int ID;

    /**
     * constructor method for the author class.
     * 
     * @param ID        The author id as it will exist in the database.
     * @param firstName The first name of this author.
     * @param lastName  The last name of this author.
     */
    public Author(int ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authoredBooks = new ArrayList<Book>();
    }

    /**
     * Adds a book to the list of this Authors authored books.
     * 
     * @param book The book object to add to this authors authored books list.
     */
    public void addAuthoredBook(Book book) {
        if (!this.authoredBooks.contains(book)) {
            this.authoredBooks.add(book);
        }
    }

    /**
     * Getter method for this authors authored books.
     * 
     * @return A list of book objects this author has authored.
     */
    public List<Book> getAuthoredBooks() {
        return authoredBooks;
    }

    /**
     * Getter method for this authors first name.
     * 
     * @return This authors first name property.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter method for this authors last name.
     * 
     * @return This authors last name property.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter method for this authors full name.
     * 
     * @return This authors full name as first name + last name.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Getter method for this authors ID.
     * 
     * @return This authors ID.
     */
    public int getID() {
        return ID;
    }

    /**
     * Setter method for this authors first name.
     * 
     * @param firstName The new first name for this author.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter method for this authors first name.
     * 
     * @param lastName The new last name for this author.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
