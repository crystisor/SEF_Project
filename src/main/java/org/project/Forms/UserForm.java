package org.project.Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton searchBook;
    private JButton librarySelect;
    private JButton placeOrder;
    private JList<String> libList;
    private JList<String> bookList;

    
    public UserForm(JDialog parent) {
        super(parent);
        setTitle("User");
        setMinimumSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());

        searchBook = new JButton("Search Book");
        librarySelect = new JButton("Library Select");
        placeOrder = new JButton("Place Order");

        List<String> libNames = getLibraryNames();
        libList = new JList<>(libNames.toArray(new String[0]));

        JScrollPane libScrollPane = new JScrollPane(libList);
        libScrollPane.setPreferredSize(new Dimension(150, 300));

        bookList = new JList<>();
        JScrollPane bookScrollPane = new JScrollPane(bookList);

        // Add components to the panel
        bookPanel = new JPanel();
        bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.X_AXIS));
        bookPanel.add(searchBook);
        bookPanel.add(Box.createHorizontalStrut(10)); // Add space between buttons
        bookPanel.add(librarySelect);
        bookPanel.add(Box.createHorizontalStrut(10)); // Add space between buttons
        bookPanel.add(placeOrder);

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

        setVisible(true);
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
        SwingUtilities.invokeLater(() -> new UserForm(null));
    }
}
