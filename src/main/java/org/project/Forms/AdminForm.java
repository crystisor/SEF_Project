package org.project.Forms;

import org.project.Entities.Book;
import org.project.Services.BookService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class AdminForm extends JDialog
{
    private static final String dbURL = "jdbc:mysql://127.0.0.1/sef_project";
    private static final String dbUser = "cristi";
    private static final String dbPassword ="qwertyuiop";

    private JButton btnOrderView;
    private JButton btnSearch;
    private JButton btnAdd;
    private JPanel RootPanel;
    private JButton btnOrders;
    private JLabel libraryLogo;
    private JLabel Orders;
    private JLabel Books;
    private JLabel Users;
    private JLabel orderCount;
    private JLabel userCountLabel;
    private JTextField tfSearch;
    private JTextArea tfaSearch;
    private JTextField tfAddBookName;
    private JTextField tfAddBookAuthor;
    private JTextField tfAddBookPrice;
    private JLabel imageLabel;
    private JLabel labelAddBookIsbn;
    private JLabel labelAddBookName;
    private JLabel labelAddBookAuthor;
    private JLabel labelAddBookPrice;
    private JLabel labelSearchBook;
    private JLabel labelAddBookQuantity;
    private JTextField tfAddBookQuantity;
    private JTextField tfAddBookIsbn;
    private JButton btnAddInAddPanel;

    public AdminForm(JDialog parent)
    {
        super(parent);
        setTitle("Root");
        setContentPane(RootPanel);
        setMinimumSize(new Dimension(500,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        countUsers();

        btnSearch.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                displayBrowser();
            }
        });
        btnAdd.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                displayAdder();
            }
        });
        btnOrders.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });
        setVisible(true);
    }

    private void countUsers(){
        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "SELECT COUNT(*) AS userCount FROM Users";

            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                int userCount = rs.getInt("userCount");
                System.out.println("Number of users: " + userCount);

                userCountLabel.setText("Active users: " + userCount);
            } else {
                System.out.println("No users found");
            }

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void displayBrowser()
    {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.Y_AXIS));

        labelSearchBook = new JLabel("Please enter the book's name: ");
        tfSearch = new JTextField();

        searchPanel.add(labelSearchBook);
        searchPanel.add(tfSearch);

        tfaSearch = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(tfaSearch);
        searchPanel.add(scrollPane);

        JOptionPane.showMessageDialog(this, searchPanel, "Search Books", JOptionPane.PLAIN_MESSAGE);
        ImageIcon icon = null;
        if (search(tfSearch.getText()))
        {
            System.out.println("not null tfSearch");
            icon = displayImage();
            if (icon != null)
            {
                imageLabel = new JLabel();
                imageLabel.setIcon(icon);
                searchPanel.add(imageLabel);
                JOptionPane.showMessageDialog(this, imageLabel, "Test Image Display", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private boolean search(String bookName) // for text field
    {
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "SELECT * FROM Books WHERE Name=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, bookName);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                System.out.println("Book found");
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private ImageIcon displayImage()
    {
        try
        {
            File imageFile = new File("res/AppImages/colt_alb.jpg");
            Image image = ImageIO.read(imageFile);

            return new ImageIcon(image);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load image.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void displayAdder()
    {
        JPanel addBookPanel = new JPanel();
        addBookPanel.setLayout(new BoxLayout(addBookPanel, BoxLayout.Y_AXIS));

        labelAddBookIsbn = new JLabel("Book ISBN: ");
        tfAddBookIsbn = new JTextField();

        labelAddBookName = new JLabel("Book Name: ");
        tfAddBookName = new JTextField();

        labelAddBookAuthor = new JLabel("Book Author: ");
        tfAddBookAuthor = new JTextField();

        labelAddBookPrice = new JLabel("Book Price: ");
        tfAddBookPrice = new JTextField();

        labelAddBookQuantity = new JLabel("Book Quantity: ");
        tfAddBookQuantity = new JTextField();

        btnAddInAddPanel = new JButton("Add book");
        btnAddInAddPanel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Book book = new Book(tfAddBookName.getText(), tfAddBookAuthor.getText(), tfAddBookIsbn.getText(), tfAddBookPrice.getText(), tfAddBookQuantity.getText());
                if (!BookService.isBookValid(book))
                {
                    System.out.println("Wrong");
                    return;
                }
                addBook(book);
            }
        });

        addBookPanel.add(labelAddBookIsbn);
        addBookPanel.add(tfAddBookIsbn);
        addBookPanel.add(labelAddBookName);
        addBookPanel.add(tfAddBookName);
        addBookPanel.add(labelAddBookAuthor);
        addBookPanel.add(tfAddBookAuthor);
        addBookPanel.add(labelAddBookPrice);
        addBookPanel.add(tfAddBookPrice);
        addBookPanel.add(labelAddBookQuantity);
        addBookPanel.add(tfAddBookQuantity);
        addBookPanel.add(btnAddInAddPanel);

        JOptionPane.showMessageDialog(this, addBookPanel, "Add books", JOptionPane.PLAIN_MESSAGE);

    }

    private void addBook(Book book)
    {
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "INSERT INTO Books (ISBN, Name, Author, Price, Quantity) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement insertBook = conn.prepareStatement(query);
            insertBook.setString(1, book.getIsbn());
            insertBook.setString(2, book.getName());
            insertBook.setString(3, book.getAuthor());
            insertBook.setString(4, book.getPrice());
            insertBook.setString(5, book.getQuantity());

            int rowsInserted = insertBook.executeUpdate();
            if (rowsInserted > 0)
            {
                System.out.println("Book added");
            } else
                System.out.println("Failed to add book");
            insertBook.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        AdminForm adminForm = new AdminForm(null);
    }
}
