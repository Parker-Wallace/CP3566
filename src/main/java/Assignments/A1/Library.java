package Assignments.A1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Book> books;
    private final List<Author> authors;

    public Library(List<Book> books, List<Author> authors) {
        this.books = books;
        this.authors = authors;
    }

    public Library(BookDatabaseManager db) throws SQLException {
        books = new ArrayList<>();
        authors = new ArrayList<>();
        ResultSet allBooks = db.selectAllBook();
        ResultSet Linking = db.selectIntermediary();
        ResultSet allAuthors = db.selectAllAuthors();

        // Creates a new book object for each entry in the Db
        while (allBooks.next()) {
            Book book = new Book(
                    allBooks.getString("title"),
                    allBooks.getString("isbn"),
                    allBooks.getString("editionNumber"),
                    allBooks.getString("copyright"));
            books.add(book);
        }

        // Creates a new Author object for each entry in the Db
        while (allAuthors.next()) {
            Author author = new Author(
                    allAuthors.getString("authorID"),
                    allAuthors.getString("firstName"),
                    allAuthors.getString("lastName")
            );
            authors.add(author);
        }

        // Creates relationships between authors and books using an foreign key relationship present in the Db
        while (Linking.next()) {
            String ISBN = Linking.getString("isbn");
            String authorID = Linking.getString("authorID");

            // using Array.stream() to find the book object with the matching Isbn
            Book book = books.stream()
                    .filter(n -> ISBN.equals(n.getIsbn()))
                    .findFirst()
                    .orElse(null);

            // using Array.stream() to find the author object with the matching authorID
            Author author = authors.stream()
                    .filter(n -> authorID.equals(n.getID()))
                    .findFirst()
                    .orElse(null);

            assert book != null;
            book.addBookAuthor(author);
            assert author != null;
            author.addAuthoredBook(book);

        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Author> getAuthors() {
        return authors;
    }


}
