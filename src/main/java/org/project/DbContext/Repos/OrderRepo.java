package org.project.DbContext.Repos;

import org.project.DbContext.DbConfig;
import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.Entities.Book;
import org.project.Entities.Order;

import java.sql.*;
import java.util.ArrayList;
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



    public String countOrders()
    {
        int orderCount = -1;
        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "SELECT COUNT(*) AS orderCount FROM Orders";

            ResultSet rs = st.executeQuery(query);
            if (rs.next())
            {
                orderCount = rs.getInt("orderCount");
                System.out.println("Number of orders: " + orderCount);
            } else
            {
                System.out.println("No orders found");
            }

            rs.close();
            st.close();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return "Orders: " + orderCount;
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = conn.createStatement();
            String query = "SELECT * FROM Orders";
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                Order order = new Order();
                order.setOrderID(rs.getString("ID_order"));
                order.setDate(rs.getString("Date"));
                order.setUserID(rs.getString("User_id"));
                order.setLibraryID(rs.getString("Library_id"));

                String orderID = rs.getString("ID_order");
                String queryGetOrderDetails = "SELECT BookID FROM OrderDetails WHERE OrderID=" + orderID;
                Statement stBooks = conn.createStatement();
                ResultSet rsBooksID = stBooks.executeQuery(queryGetOrderDetails);

                List<Book> books = new ArrayList<>();
                while (rsBooksID.next())
                {
                    String bookID = rsBooksID.getString("BookID");
                    String queryGetBookDetails = "SELECT * FROM Books WHERE ISBN=" + bookID;
                    Statement stBook = conn.createStatement();
                    ResultSet rsBook = stBook.executeQuery(queryGetBookDetails);
                    while (rsBook.next())
                    {
                        Book book = new Book(rsBook.getString("Name"), rsBook.getString("Author"), rsBook.getString("ISBN"),
                                rsBook.getString("Price"), rsBook.getString("Quantity"), rsBook.getString("Image_url"));
                        books.add(book);
                    }
                }
                order.setBooks(books);

                rsBooksID.close();
                stBooks.close();

                orders.add(order);
            }
            rs.close();
            st.close();
            conn.close();
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
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement selectUser = conn.createStatement();
            String selectUserQuery = "SELECT User_id from Orders WHERE Orders.ID_order =" + orderID;
            ResultSet rs = selectUser.executeQuery(selectUserQuery);

            while (rs.next())
            {
                String updateFeedbackQuery = "INSERT INTO Feedbacks (Text, User_id) VALUES (?, ?)";

                PreparedStatement updateUser = conn.prepareStatement(updateFeedbackQuery);
                updateUser.setString(1, feedback);
                updateUser.setString(2, rs.getString(1));
                updateUser.executeUpdate();

            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

