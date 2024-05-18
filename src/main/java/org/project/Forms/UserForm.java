package org.project.Forms;

import org.project.Entities.Book;
import org.project.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserForm extends JDialog {
    private static final String dbURL = "jdbc:mysql://25.19.87.249/sef_project";
    private static final String dbUser = "sx3";
    private static final String dbPassword = "Q2@@wertyuiop";

    private JPanel userPanel;
    private JPanel bookPanel;
    private JButton orderDetails;
    private JButton addBooktoOrder;
    private JButton addFunds;
    private JList<String> libList;
    private JList<String> bookList;

    private List<Book> order = new ArrayList<>();

    public UserForm(JDialog parent, User user) {
        super(parent);
        setTitle("User");
        setMinimumSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());

        List<String> libNames = getLibraryNames();
        libList = new JList<>(libNames.toArray(new String[0]));

        JScrollPane libScrollPane = new JScrollPane(libList);
        libScrollPane.setPreferredSize(new Dimension(150, 300));

        bookList = new JList<>();
        JScrollPane bookScrollPane = new JScrollPane(bookList);

        // Add components to the panel
        bookPanel = new JPanel();
        bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.X_AXIS));
        bookPanel.add(orderDetails);
        bookPanel.add(Box.createHorizontalStrut(10)); // Add space between buttons
        bookPanel.add(addBooktoOrder);
        bookPanel.add(Box.createHorizontalStrut(10)); // Add space between buttons
        bookPanel.add(addFunds);

        userPanel.add(libScrollPane, BorderLayout.WEST);
        userPanel.add(bookScrollPane, BorderLayout.CENTER);
        userPanel.add(bookPanel, BorderLayout.SOUTH);

        setContentPane(userPanel);

        // Add action listener for library selection
        libList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedLibrary = libList.getSelectedValue();
                if (selectedLibrary != null) {
                    List<String> books = getBooksForLibrary(selectedLibrary);
                    updateBookList(books);
                }
            }
        });

        addBooktoOrder.addActionListener(e -> {
            String selectedBookName = bookList.getSelectedValue();
            if (selectedBookName != null) {
                Book book = retrieveBook(selectedBookName);
                if (book != null) {
                     order.add(book);
                    JOptionPane.showMessageDialog(UserForm.this, "Book '" + selectedBookName + "' added to order.");
                } else {
                    JOptionPane.showMessageDialog(UserForm.this, "Failed to retrieve book information.");
                }
            } else {
                JOptionPane.showMessageDialog(UserForm.this, "Please select a book to add to the order.");
            }
        });

        orderDetails.addActionListener(e -> {
            if (!order.isEmpty()) {
                int orderId = createNewOrder(user.getEmail());
                if (orderId != -1) {
                    for (Book book : order) {
                        addToOrder(orderId, book);
                    }
                    JOptionPane.showMessageDialog(UserForm.this, "Order placed successfully.");
                    order.clear(); // Clear selected books after placing order
                } else {
                    JOptionPane.showMessageDialog(UserForm.this, "Failed to place order.");
                }
            } else {
                JOptionPane.showMessageDialog(UserForm.this, "Please add at least one book to the order.");
            }
        });

        setVisible(true);
    }

    private int createNewOrder(String userEmail) {
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

    private boolean addToOrder(int orderId, Book book) {
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement statement = connection.createStatement();

            // Execute an SQL INSERT query to add the book to the order in the database
            String query = "INSERT INTO OrderBook (order_id, book_id) VALUES (" +
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

    private Book retrieveBook(String selectedBook) {

        Book book = null;
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Books WHERE Name = '" + selectedBook + "'");

            while (resultSet.next()) {
               book = new Book( resultSet.getString("Name"), resultSet.getString("Author"), resultSet.getString("ISBN"),
                       resultSet.getString("Price"), resultSet.getString("Quantity"), resultSet.getString("Image_url"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    public static List<String> getLibraryNames() {
        List<String> libraryNames = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM Libraries");

            while (resultSet.next()) {
                libraryNames.add(resultSet.getString("name"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return libraryNames;
    }

    public static List<String> getBooksForLibrary(String libraryName) {
        List<String> books = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Books.Name FROM Books " +
                    "INNER JOIN Libraries ON Books.library_id = Libraries.id " +
                    "WHERE Libraries.name = '" + libraryName + "'");

            while (resultSet.next()) {
                books.add(resultSet.getString("Name"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    public void updateBookList(List<String> books) {

        DefaultListModel<String> bookListModel = new DefaultListModel<>();
        for (String book : books) {
            bookListModel.addElement(book);
        }
        bookList.setModel(bookListModel);
    }

    public static void main(String[] args) {

        User u = new User("gigi","gogu","iov@gmail.com","cuc","0123012444","as");
        SwingUtilities.invokeLater(() -> new UserForm(null,u));
    }
}
