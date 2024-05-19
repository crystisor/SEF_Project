package org.project.DbContext.Repos;

import org.project.DbContext.DbConfig;
import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.Entities.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderRepo extends DbConfig implements IOrderRepo {

    public int createOrder(String userEmail) {

        int orderId = -1;
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement statement = connection.createStatement();

            // Execute an SQL INSERT query to create a new order record for the user
            String query = "INSERT INTO Order (user_email) VALUES ('" + userEmail + "')";
            int rowsAffected = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                }
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId;
    }

    public boolean addBookToOrder(int orderId, Book book) {

        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement statement = connection.createStatement();

            // Execute an SQL INSERT query to add the book to the order in the database
            String query = "INSERT INTO OrderDetails (OrderDetail_ID, book_id) VALUES (" +
                    orderId + ", " + book.getIsbn() + ")";
            int rowsAffected = statement.executeUpdate(query);

            statement.close();
            connection.close();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
