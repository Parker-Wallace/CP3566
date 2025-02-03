package Assignments.A1;

import com.sun.jna.WString;
import org.example.JDBC.DBProperties;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BookDatabaseManager {
    public static final String DB_NAME = "/Books";
    private final List<Book> books;
    private final List<Author> authors;

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

    public void updateDatabase(String table,
                               String column,
                               String updatedValue,
                               String oldValuePrimaryKeyColunmName,
                               String oldValuePrimaryKey) throws SQLException {
        String Query = String.format("Update %s set %s = '%s' where %s = '%s'",
                table, column, updatedValue, oldValuePrimaryKeyColunmName, oldValuePrimaryKey);
        executeQuery(Query);
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
            PreparedStatement ps = conn.prepareStatement(Query);
            return ps.executeQuery();
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
        stmt.executeQuery(Query);
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
}

    public void resetDatabase() {
    List<String> Queries = new ArrayList<>();
    Queries.add("DROP TABLE IF EXISTS authorISBN");
    Queries.add("DROP TABLE IF EXISTS titles");
    Queries.add("DROP TABLE IF EXISTS authors");
    Queries.add("""
            CREATE TABLE authors (
               authorID INT NOT NULL AUTO_INCREMENT,
               firstName varchar (20) NOT NULL,
               lastName varchar (30) NOT NULL,
               PRIMARY KEY (authorID)
            );""");
    Queries.add("""
            CREATE TABLE titles (
               isbn varchar (20) NOT NULL,
               title varchar (100) NOT NULL,
               editionNumber INT NOT NULL,
               copyright varchar (4) NOT NULL,
               PRIMARY KEY (isbn));""");
    Queries.add("""
            CREATE TABLE authorISBN (
               authorID INT NOT NULL,
               isbn varchar (20) NOT NULL,
               FOREIGN KEY (authorID) REFERENCES authors (authorID),\s
               FOREIGN KEY (isbn) REFERENCES titles (isbn));""");
    Queries.add("""
            INSERT INTO authors (firstName, lastName)
            VALUES\s
               ('Paul','Deitel'),\s
               ('Harvey','Deitel'),
               ('Abbey','Deitel'),\s
               ('Dan','Quirk'),
               ('Michael','Morgano');""");
    Queries.add("""
            INSERT INTO titles (isbn,title,editionNumber,copyright)
            VALUES
               ('0132151006','Internet & World Wide Web How to Program',5,'2012'),
               ('0133807800','Java How to Program',10,'2015'),
               ('0132575655','Java How to Program, Late Objects Version',10,'2015'),
               ('013299044X','C How to Program',7,'2013'),\s
               ('0132990601','Simply Visual Basic 2010',4,'2013'),
               ('0133406954','Visual Basic 2012 How to Program',6,'2014'),
               ('0133379337','Visual C# 2012 How to Program',5,'2014'),
               ('0136151574','Visual C++ How to Program',2,'2008'),
               ('0133378713','C++ How to Program',9,'2014'),
               ('0133764036','Android How to Program',2,'2015'),
               ('0133570924','Android for Programmers: An App-Driven Approach, Volume 1',2,'2014'),
               ('0132121360','Android for Programmers: An App-Driven Approach',1,'2012');""");
    Queries.add("""
            INSERT INTO authorISBN (authorID,isbn)
            VALUES
               (1,'0132151006'),(2,'0132151006'),(3,'0132151006'),(1,'0133807800'),(2,'0133807800'),(1,'0132575655'),
               (2,'0132575655'),(1,'013299044X'),(2,'013299044X'),(1,'0132990601'),(2,'0132990601'),(3,'0132990601'),
               (1,'0133406954'),(2,'0133406954'),(3,'0133406954'),(1,'0133379337'),(2,'0133379337'),(1,'0136151574'),
               (2,'0136151574'),(4,'0136151574'),(1,'0133378713'),(2,'0133378713'),(1,'0133764036'),(2,'0133764036'),
               (3,'0133764036'),(1,'0133570924'),(2,'0133570924'),(3,'0133570924'),(1,'0132121360'),(2,'0132121360'),
               (3,'0132121360'),(5,'0132121360');""");

    for (String query : Queries) {
        executeQuery(query);
    }
}

    public List<Book> getBooks() {
        return books;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}