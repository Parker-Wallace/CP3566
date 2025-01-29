package Assignments.A1;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private List<Book> authoredBooks;
    private String name;
    private String ID;


    public Author(String ID, String name) {
        this.ID = ID;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }
}
