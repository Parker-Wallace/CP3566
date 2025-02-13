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
                    Integer.parseInt(allAuthors.getString("authorID")),
                    allAuthors.getString("firstName"),
                    allAuthors.getString("lastName")
            );
            authors.add(author);
        }

        // Creates relationships between authors and books using an foreign key relationship present in the Db
        while (Linking.next()) {
            String ISBN = Linking.getString("isbn");
            int authorID = Integer.parseInt(Linking.getString("authorID"));

            // using Array.stream() to find the book object with the matching Isbn
            Book book = books.stream()
                    .filter(n -> ISBN.equals(n.getIsbn()))
                    .findFirst()
                    .orElse(null);

            // using Array.stream() to find the author object with the matching authorID
            Author author = authors.stream()
                    .filter(n -> authorID == (n.getID()))
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

    public void editAuthor(BookDatabaseManager db, int indexOfAuthor, String newFirstName, String newLastName) throws SQLException {
        Author author = authors.get(indexOfAuthor);
        author.setFirstName(newFirstName);
        author.setLastName(newLastName);
        db.updateDatabase("authors", "firstName", newFirstName, "authorID", String.valueOf(authors.get(indexOfAuthor).getID()));
        db.updateDatabase("authors", "lastName", newLastName, "authorID", String.valueOf(authors.get(indexOfAuthor).getID()));
    }

    public void editBook(BookDatabaseManager db, int indexOfBook, String newTitle, String newEditionNumber, String newCopyright) throws SQLException {
        Book book = books.get(indexOfBook);
        book.setTitle(newTitle);
        book.setCopyright(newCopyright);
        book.setEditionumber(newEditionNumber);
        db.updateDatabase("titles", "title", newTitle, "isbn", book.getIsbn());
        db.updateDatabase("titles", "editionNumber", newEditionNumber, "isbn", book.getIsbn());
        db.updateDatabase("titles", "copyright", newCopyright, "isbn", book.getIsbn());

    }

    public void addBook (BookDatabaseManager db,Book book) throws SQLException {
// isbn needs to be 10 chars long
        this.books.add(book);
        db.addBook(book);
    }

    /**
     * Adds an author to this library and sends the author to the BookDatabaseManager param to add to the database.
     * @param db The BookDatabaseManager object for handling database syncronization.
     * @param author The Author object to be added.
     * @throws SQLException
     */
    public void addAuthor (BookDatabaseManager db, 
                            Author author
                        ) throws SQLException {
        this.authors.add(author);
        db.addAuthor(author);
}
}

