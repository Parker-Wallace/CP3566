package Assignments.A1;

import org.example.JDBC.DBProperties;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BookDatabaseManager {
    public static final String DB_NAME = "/Books";
    public static List<Book> books;
    public static List<Author> authors;

    public BookDatabaseManager() throws SQLException {
        books = new ArrayList<>();
        authors = new ArrayList<>();
        ResultSet allBooks = selectAllBook();
        ResultSet Linking = selectIntermediary();
        ResultSet allAuthors = selectAllAuthors();

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
                    allAuthors.getString("Author")
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


    public void addBook(Book book) {}


    public void addAuthor(Author author) {}


    public ResultSet selectAll() {
    String Query = "SELECT t.isbn, t.title, t.editionNumber, t.copyright, CONCAT(a.FirstName, ' ', a.lastName) AS 'Author Name' FROM titles t JOIN authorisbn ai ON t.isbn = ai.isbn JOIN authors a ON ai.authorID = a.authorID;";
    return executeSelectQuery(Query);
    }

    public ResultSet selectAllAuthors() {
        String Query = "Select a.authorID, concat(a.FirstName, \" \", a.lastName) AS \"Author\" FROM authors a";

        return executeSelectQuery(Query);
    }
    public ResultSet selectAllBook() {
        String Query = "SELECT * from titles";
        return executeSelectQuery(Query);
    }

    private ResultSet selectIntermediary(){
        String Query = "SELECT * from authorisbn";
        return executeSelectQuery(Query);
    }

    public ResultSet selectIndividualRecord(String table, String comparator, String value ) {
        String Query = "SELECT * FROM" + table + " WHERE " + comparator + " = '" + value + "'";
        return executeSelectQuery(Query);
    }


    public ResultSet executeSelectQuery(String Query) {
        try{
            Connection conn = DriverManager.getConnection(
                    DBProperties.DATABASE_URL + DB_NAME, DBProperties.DATABASE_USER, DBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Query);
            return rs;
    }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

public void executeQuery(String Query) {
    try{
        Connection conn = DriverManager.getConnection(
                DBProperties.DATABASE_URL + DB_NAME, DBProperties.DATABASE_USER, DBProperties.DATABASE_PASSWORD);
        Statement stmt = conn.createStatement();
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
}


}