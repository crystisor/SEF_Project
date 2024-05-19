package org.project.DbContext.Repos;

import org.project.DbContext.DbConfig;
import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.Entities.Book;

import java.sql.*;
import java.util.List;

public class OrderRepo extends DbConfig implements IOrderRepo {


    public int createOrder(int userId,List<Book> books) {

        int orderId = -1;
        String insertOrderSQL = "INSERT INTO `Orders` (User_id, Date) VALUES (?, ?)";

        try (

                Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                PreparedStatement orderStatement = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {

            orderStatement.setInt(1, userId);
            orderStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            int rowsAffected = orderStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = orderStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

         if ( !addBooksToOrder(orderId,books) )
             return -1;

        return orderId;
    }

    private boolean addBooksToOrder(int orderId, List<Book> books) {
        String insertOrderDetailsSQL = "INSERT INTO OrderDetails (OrderID, BookID) VALUES (?, ?)";
        boolean success = false;

        try (Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
             PreparedStatement orderDetailsStatement = connection.prepareStatement(insertOrderDetailsSQL)) {

            for (Book book : books) {
                orderDetailsStatement.setInt(1, orderId);
                orderDetailsStatement.setString(2, book.getIsbn());
                orderDetailsStatement.executeUpdate();
            }
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

}

