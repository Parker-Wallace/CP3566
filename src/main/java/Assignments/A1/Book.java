package Assignments.A1;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Book to be contained in a library
 */
public class Book {
    private String title;
    private String isbn;
    private String editionumber;
    private String copyright;
    private List<Author> authorList;

    /**
     * Constructor for the Book object.
     * 
     * @param title        the title of the book
     * @param isbn         the ISBN number of the book represented as a 10-digit
     *                     number
     * @param editionumber the edition number of the book
     * @param copyright    the copyright year of the book
     */
    public Book(String title, String isbn, String editionumber, String copyright) {
        this.title = title;
        this.isbn = isbn;
        this.editionumber = editionumber;
        this.copyright = copyright;
        this.authorList = new ArrayList<>();
    }

    /**
     * Adds an Author to this books author list.
     * 
     * @param author the Author object to be added to this books authorList property
     */
    public void addBookAuthor(Author author) {
        if (!this.authorList.contains(author)) {
            this.authorList.add(author);
        }
    }

    /**
     * Getter method for this book's title
     * 
     * @return the String representation of this book's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter method for this book's isbn
     * 
     * @return the String representation of this book's isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * getter method for this book's list of authors
     * 
     * @return the List<Author> of Authors of this book
     */
    public List<Author> getAuthorList() {
        return authorList;
    }

    /**
     * getter method for this book's edition number
     * 
     * @return the String representation of this book's edition number
     */
    public String getEditionumber() {
        return editionumber;
    }

    /**
     * getter method for this book's copyright year
     * 
     * @return the String representation of this book's copyright year
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * setter method for this book's title property
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * setter method for this books edition number property
     * 
     * @param editionumber the new edition number for this book
     */
    public void setEditionumber(String editionumber) {
        this.editionumber = editionumber;
    }

    /**
     * setter method for this books copyright year property
     * 
     * @param copyright the new copyright year for this book
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * setter method for this books author list
     * 
     * @param authorList the new lsit of this books authors
     */
    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }
}
