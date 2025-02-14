package Assignments.A1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Assignments.DBProperties;

/**
 * Backend management class for the {@link BookApplication} Application class
 * <p>
 * facilitates sql queries and database connection
 */
public class BookDatabaseManager {
    public static final String DB_NAME = "/library";

    /**
     * sends one parameterized query to the library database
     * <p>
     * only updates one value set at a time
     * 
     * @param table                        the table where the updated value exists
     *                                     in the database
     * @param column                       the column where the updated values will
     *                                     be changed
     * @param updatedValue                 the new value for the dataset
     * @param oldValuePrimaryKeyColunmName the primary key column name for the table
     *                                     for the old dataset
     * @param oldValuePrimaryKey           the primary key value of the old dataset
     * @throws SQLException
     */
    public void updateDatabase(String table,
            String column,
            String updatedValue,
            String oldValuePrimaryKeyColunmName,
            String oldValuePrimaryKey) throws SQLException {
        String Query = String.format("Update %s set %s = '%s' where %s = '%s'",
                table, column, updatedValue, oldValuePrimaryKeyColunmName, oldValuePrimaryKey);
        executeQuery(Query);
    }

    /**
     * Adds a single {@link Book} to the database using an <code>INSERT</code> query
     * 
     * @param book a Book to add to the backend database
     */
    public void addBook(Book book) {
        String insertBookQueryString = String.format("""
                INSERT INTO titles
                VALUES ('%s', '%s', '%s', '%s');
                """,
                book.getIsbn(),
                book.getTitle(),
                book.getCopyright(),
                book.getEditionumber());
        executeQuery(insertBookQueryString);
        for (Author author : book.getAuthorList()) {
            String insertBookAuthorRelationshipStringQuery = String.format("""
                    INSERT INTO authorISBN
                    VALUES (%s, %s);
                    """,
                    author.getID(),
                    book.getIsbn());
            executeQuery(insertBookAuthorRelationshipStringQuery);
        }
    }

    /**
     * adds a single {@link Author} to the backend database using an
     * <codeINSERT</code> query
     * 
     * @param author an Author to add to the backend database
     * @throws SQLException
     */
    public void addAuthor(Author author) {
        String insertAuthorQueryString = String.format("INSERT INTO authors (firstName, lastName) values ('%s','%s')",
                author.getFirstName(), author.getLastName());
        executeQuery(insertAuthorQueryString);
    }

    /**
     * uses a <code>SELECT</code> query to retrieve all values in the library
     * database
     * 
     * @return a Resultset of the returned values from the SQL query
     */
    public ResultSet selectAll() {
        String Query = "SELECT t.isbn, t.title, t.editionNumber, t.copyright, a.FirstName,  a.lastName FROM titles t JOIN authorISBN ai ON t.isbn = ai.isbn JOIN authors a ON ai.authorID = a.authorID;";
        return executeSelectQuery(Query);
    }

    /**
     * uses a <code>SELECT</code> query to retrieve all values in the authors table
     * of the library database
     * 
     * @return a Resultset of the returned values from the SQL query
     */
    public ResultSet selectAllAuthors() {
        String Query = "Select * FROM authors a";
        return executeSelectQuery(Query);
    }

    /**
     * uses a <code>SELECT</code> query to retrieve all values in the titles table
     * of the library database
     * 
     * @return a Resultset of the returned values from the SQL query
     */
    public ResultSet selectAllBook() {
        String Query = "SELECT * from titles";
        return executeSelectQuery(Query);
    }

    /**
     * uses a <code>SELECT</code> query to retrieve all values in the authorisbn
     * table of the library database
     * 
     * @return a Resultset of the returned values from the SQL query
     */
    public ResultSet selectIntermediary() {
        String Query = "SELECT * from authorISBN";
        return executeSelectQuery(Query);
    }

    /**
     * uses a <code>SELECT</code> query to retrieve a single parameterized row from
     * the library database
     * 
     * @param table      the table from which the record will be retrieved
     * @param comparator the column for which the selected record(s) will be checked
     *                   against the value parameter
     * @param value      the value for which the parameter <code>WHERE</code> will
     *                   be checked against
     * @return a Resultset of the returned values from the SQL query
     */
    public ResultSet selectIndividualRecord(String table, String comparator, String value) {
        String Query = "SELECT * FROM" + table + " WHERE " + comparator + " = '" + value + "'";
        return executeSelectQuery(Query);
    }

    /**
     * helper function which executes a <code>SELECT</code> query on the databse
     * 
     * @param Query a String representing the query to be sent to the database
     * @return a Resultset from the returned values from the SQL query
     */
    private ResultSet executeSelectQuery(String Query) {
        try {
            Connection conn = DriverManager.getConnection(
                    DBProperties.DATABASE_URL + DB_NAME, DBProperties.DATABASE_USER, DBProperties.DATABASE_PASSWORD);
            PreparedStatement ps = conn.prepareStatement(Query);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * helper function which executed a non <code>SELECT</code> query on the
     * database
     * 
     * @param Query a String representation of the query to be sent to the database
     */
    public void executeQuery(String Query) {
        try {
            Connection conn = DriverManager.getConnection(
                    DBProperties.DATABASE_URL + DB_NAME, DBProperties.DATABASE_USER, DBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
            stmt.executeQuery(Query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets the database using a predetermined set of tables and data
     */
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
        Queries.add(
                """
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

}