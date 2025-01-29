package Assignments.A1;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private String isbn;
    private String editionumber;
    private String copyright;
    private List<Author> authorList;

        public Book(String title, String isbn, String editionumber, String copyright) {
            this.title = title;
            this.isbn = isbn;
            this.authorList = new ArrayList<>();
        }

    public void addBookAuthor(Author author) {
        if (!this.authorList.contains(author)) {
            this.authorList.add(author);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public String getEditionumber() {
        return editionumber;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEditionumber(String editionumber) {
        this.editionumber = editionumber;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }
}
