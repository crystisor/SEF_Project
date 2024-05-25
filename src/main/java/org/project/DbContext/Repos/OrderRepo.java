package org.project.DbContext.Repos;

import org.project.DbContext.DbConfig;
import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.Entities.Book;
import org.project.Entities.Feedback;
import org.project.Entities.Library;
import org.project.Entities.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepo extends DbConfig implements IOrderRepo {


    public int createOrder(String userId,List<Book> books) {

        int orderId = -1;
        String insertOrderSQL = "INSERT INTO `Orders` (User_id, Date, Feedback) VALUES (?, ?, ?)";

        try (

                Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                PreparedStatement orderStatement = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {

            orderStatement.setString(1, userId);
            orderStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            orderStatement.setString(3, "Pending");
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
        String insertOrderDetailsSQL = "INSERT INTO OrderDetails (OrderID, BookID, Library_id) VALUES (?, ?, ?)";
        String selectLibraryIdSQL = "SELECT library_id FROM Books WHERE Name = ?";
        boolean success = false;

        try (Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
             PreparedStatement orderDetailsStatement = connection.prepareStatement(insertOrderDetailsSQL);
             PreparedStatement libraryIdStatement = connection.prepareStatement(selectLibraryIdSQL)) {

            for (Book book : books) {
                libraryIdStatement.setString(1, book.getName());
                try (ResultSet resultSet = libraryIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String libraryId = resultSet.getString("library_id");

                        orderDetailsStatement.setInt(1, orderId);
                        orderDetailsStatement.setString(2, book.getIsbn());
                        orderDetailsStatement.setString(3, libraryId);
                        orderDetailsStatement.executeUpdate();
                    } else {
                        System.out.println("Library ID not found for book: " + book.getName());
                    }
                }
            }
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public String countOrdersPerLibrary(String libraryId) {
        int orderCount = 0;
        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            // Query to count distinct OrderIDs from OrderDetails and check matching orders in Order table with Feedback='Pending'
            String query = "SELECT COUNT(DISTINCT od.OrderID) AS orderCount " +
                    "FROM OrderDetails od " +
                    "JOIN `Orders` o ON od.OrderID = o.ID_order " +
                    "WHERE od.Library_id = ? AND o.Feedback = 'Pending'";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, libraryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                orderCount = rs.getInt("orderCount");
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Orders: " + orderCount;
    }


    public List<Order> getOrdersByLibraryId(String libraryID)
    {
        List<Order> orders = new ArrayList<>();
        try
        {
            Connection conn =DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement getOrdersStatement = conn.createStatement();
            String getOrdersQuery = "SELECT ID_order, User_id, Date, Feedback FROM Orders";
            ResultSet rs = getOrdersStatement.executeQuery(getOrdersQuery);

            while (rs.next())
            {
                Order order = new Order();
                order.setOrderID(rs.getString("ID_order"));
                order.setUserID(rs.getString("User_id"));
                order.setDate(rs.getString("Date"));
                order.setFeedback(rs.getString("Feedback"));

                Statement getOrderDetailsStatement = conn.createStatement();
                String getOrderDetailsQuery = "SELECT BookID FROM OrderDetails WHERE OrderDetails.Library_id = '" + libraryID + "' AND OrderDetails.OrderID = '" + order.getOrderID() + "'" ;
                ResultSet resultSet = getOrderDetailsStatement.executeQuery(getOrderDetailsQuery);

                List<Book> books = new ArrayList<>();
                while (resultSet.next())
                {
                    Statement getBookDetailsStatement = conn.createStatement();
                    String getBookDetailsQuery = "SELECT * FROM Books WHERE Books.ISBN = '" + resultSet.getString("BookID") + "'";
                    ResultSet resultSetBook = getBookDetailsStatement.executeQuery(getBookDetailsQuery);

                    if (resultSetBook.next())
                    {
                        System.out.println(resultSetBook.getString("ISBN"));
                        Book book = new Book( resultSetBook.getString("Name"), resultSetBook.getString("Author"), resultSetBook.getString("ISBN"),
                                resultSetBook.getString("Price"), resultSetBook.getString("Quantity"), resultSetBook.getString("Image_url"));
                        books.add(book);
                    }
                    getBookDetailsStatement.close();
                }
                order.setBooks(books);
                getOrderDetailsStatement.close();

                if( !order.getBooks().isEmpty())
                    orders.add(order);
            }
            getOrdersStatement.close();
            conn.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        List<Order> orders = new ArrayList<>();

        String getOrdersQuery = "SELECT ID_order, User_id, Date, Feedback FROM Orders WHERE User_id = ?";

        try (
                Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                PreparedStatement getOrdersStatement = conn.prepareStatement(getOrdersQuery)
        ) {
            getOrdersStatement.setString(1, userId);

            try (ResultSet rs = getOrdersStatement.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderID(rs.getString("ID_order"));
                    order.setUserID(rs.getString("User_id"));
                    order.setDate(rs.getString("Date"));
                    order.setFeedback(rs.getString("Feedback"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public void deleteOrder(int orderID)
    {
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement stOrdersDetails = conn.createStatement();
            String deleteOrderDetails = "DELETE FROM OrderDetails WHERE OrderID=" + orderID;

            int rowsDeleted = stOrdersDetails.executeUpdate(deleteOrderDetails);
            if (rowsDeleted > 0)
                System.out.println("Order details deleted successfully");
            else
                System.out.println("Error deleting order details");

            Statement stOrders = conn.createStatement();
            String deleteOrders = "DELETE FROM Orders WHERE ID_order=" + orderID;

            rowsDeleted = stOrders.executeUpdate(deleteOrders);
            if (rowsDeleted > 0)
                System.out.println("Order deleted successfully");
            else
                System.out.println("Error deleteing orders");

            stOrders.close();
            stOrdersDetails.close();
            conn.close();
        }
         catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void sendUserFeedback(int orderID, String feedback)
    {
        try {

            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String insertFeedbackQuery = "UPDATE Orders SET Feedback = ? WHERE ID_order = ?";

            PreparedStatement updateStmt = conn.prepareStatement(insertFeedbackQuery);
            updateStmt.setString(1, feedback);
            updateStmt.setInt(2, orderID);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("Feedback inserted successfully.");
            }

            updateStmt.close();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<Feedback> getFeedbacks(String userId)
    {
        List<Feedback> feedbacks = new ArrayList<>();
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement selectFeedback = conn.createStatement();
            String selectFeedbackQuery = "SELECT * FROM Feedbacks WHERE User_id = '" + userId + "'";
            ResultSet rs = selectFeedback.executeQuery(selectFeedbackQuery);
            while (rs.next())
            {
                Feedback feedback = new Feedback(rs.getString("User_id"), rs.getString("Order_id"), rs.getString("Date"), rs.getString("Text"));
                feedbacks.add(feedback);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return feedbacks;
    }
}

