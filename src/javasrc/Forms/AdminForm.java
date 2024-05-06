package javasrc.Forms;

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
    private JLabel bookCount;
    private JLabel userCount;
    private JTextField tfSearch;
    private JTextArea tfaSearch;
    private JTextField tfAddBookName;
    private JTextField tfAddBookAuthor;
    private JTextField tfAddBookPrice;
    private JLabel imageLabel;

    public AdminForm(JDialog parent)
    {
        super(parent);
        setTitle("Root");
        setContentPane(RootPanel);
        setMinimumSize(new Dimension(500,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
    private void displayBrowser()
    {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.Y_AXIS));

        tfSearch = new JTextField();
        searchPanel.add(tfSearch);
        tfaSearch = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(tfaSearch);
        searchPanel.add(scrollPane);

        JOptionPane.showMessageDialog(this, searchPanel, "Search Books", JOptionPane.PLAIN_MESSAGE);
        ImageIcon icon = null;
        if (search(tfSearch.getText()))
        {
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sef_project", "cristi", "qwertyuiop");

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
            // Load the image from the project directory
            File imageFile = new File("/home/cristi/Desktop/School/SEF/SEF_Project/src/res/AppImages/colt_alb.jpg");
            Image image = ImageIO.read(imageFile);
            ImageIcon icon = new ImageIcon(image);

            // Set the image to the JLabel
            return icon;
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
        addBookPanel.setLayout(new BoxLayout(addBookPanel,BoxLayout.Y_AXIS));

        tfAddBookName = new JTextField();
        tfAddBookAuthor = new JTextField();
        tfAddBookPrice = new JTextField();
        addBookPanel.add(tfAddBookName);
        addBookPanel.add(tfAddBookAuthor);
        addBookPanel.add(tfAddBookPrice);
        JOptionPane.showMessageDialog(this, addBookPanel, "Search Books", JOptionPane.PLAIN_MESSAGE);
        try
        {
            int price = Integer.parseInt(tfAddBookPrice.getText());
            addBook(33, tfAddBookName.getText(), tfAddBookAuthor.getText(), price, false);
        }
        catch (NumberFormatException e)
        {
            System.err.println("Input is not a valid integer: " + tfAddBookPrice.getText());
        }
    }
    private void addBook(long ISBN, String bookName, String bookAuthor, int price, boolean borrowed)
    {
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sef_project", "cristi", "qwertyuiop");

            Statement st = conn.createStatement();
            String query = "INSERT INTO Books (ISBN, Name, Author, Price, Borrowed) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, String.valueOf(ISBN));
            ps.setString(2, bookName);
            ps.setString(3, bookAuthor);
            ps.setInt(4, price);
            ps.setBoolean(5, borrowed);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0)
            {
                System.out.println("Book added");
            }
            else
                System.out.println("Failed to add book");
            ps.close();
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
