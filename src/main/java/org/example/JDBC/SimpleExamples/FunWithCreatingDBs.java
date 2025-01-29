package org.example.JDBC.SimpleExamples;

import org.example.JDBC.DBProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple example that creates a Database called "STUDENTS".
 */
public class FunWithCreatingDBs {

    public static void main(String[] args) {
        // Open a connection
        try(Connection conn = DriverManager.getConnection(
                DBProperties.DATABASE_URL, DBProperties.DATABASE_USER, DBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "CREATE DATABASE if not exists STUDENTS";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}