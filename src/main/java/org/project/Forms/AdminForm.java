package org.project.Forms;

import org.project.DbContext.Interfaces.IBookRepo;
import org.project.DbContext.Interfaces.IUserRepo;
import org.project.Entities.Book;
import org.project.Entities.Library;
import org.project.Services.BookService;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class AdminForm extends JDialog
{

    private static Library root;
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
    private JLabel labelImageURL;
    private JTextField tfImageURl;
    private JTextField tfAddBookQuantity;
    private JTextField tfAddBookIsbn;
    private JButton btnAddInAddPanel;
    IUserRepo userRepo;
    IBookRepo bookRepo;

    public AdminForm(JDialog parent, Library root)
    {
        super(parent);
        setTitle("Root");
        setContentPane(RootPanel);
        setMinimumSize(new Dimension(500,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        AdminForm.root = root;
        userCountLabel.setText(userRepo.countUsers());

        btnSearch.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SearchBooks searchBooks = new SearchBooks(AdminForm.this, root);
                //displayBrowser();
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


    /*

    private void displayBrowser()
    {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.Y_AXIS));

        labelSearchBook = new JLabel("Enter the book's name: ");
        tfSearch = new JTextField();

        searchPanel.add(labelSearchBook);
        searchPanel.add(tfSearch);

        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BoxLayout(booksPanel, BoxLayout.Y_AXIS));
        displayBooks(booksPanel, root);

        JScrollPane scrollPane = new JScrollPane(booksPanel);
        searchPanel.add(scrollPane);

        JOptionPane.showMessageDialog(this, searchPanel, "Search Books", JOptionPane.PLAIN_MESSAGE);

        ImageIcon icon = null;
        Book book = search(tfSearch.getText());
        if (book != null)
        {
            icon = displayImage();
            if (icon != null)
            {
                imageLabel = new JLabel();
                imageLabel.setIcon(icon);
                searchPanel.add(imageLabel);

                JButton editBtn = new JButton("Edit Book");
                JButton deleteBtn = new JButton("Delete Book");
                searchPanel.add(editBtn);
                searchPanel.add(deleteBtn);

                editBtn.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        JPanel editPanel = new JPanel();
                        editPanel.setLayout(new BoxLayout(editPanel,BoxLayout.Y_AXIS));

                        JLabel quantityLabel = new JLabel("Edit Quantity:");
                        JLabel priceLabel = new JLabel("Edit Price:");
                        JTextField editQuantity = new JTextField();
                        JTextField editPrice = new JTextField();
                        editPanel.add(quantityLabel);
                        editPanel.add(editQuantity);
                        editPanel.add(priceLabel);
                        editPanel.add(editPrice);

                        int result = JOptionPane.showConfirmDialog(null, editPanel,
                                "Edit Book Details", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION)
                            editBook(book, editQuantity.getText(), editPrice.getText());
                    }
                });

                deleteBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int result = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete this book?",
                                "Delete Book", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            deleteBook(book);
                        }
                    }
                });
                JOptionPane.showMessageDialog(this, searchPanel, "Test Image Display", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
    */

    /*
    private void editBook(Book book, String quantity, String price)
    {
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "UPDATE Books SET Price = ?, Quantity = ? WHERE Name = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, price);
            ps.setString(2, quantity);
            ps.setString(3, book.getName());

            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("Book Edited");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void deleteBook(Book book)
    {
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "DELETE FROM Books WHERE Name = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, book.getName());

            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("Book deleted");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    */

    /*
    private Book search(String bookName) // for text field
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
                return new Book(rs.getString("name"), rs.getString("author"), rs.getString("isbn"),
                        rs.getString("price"), rs.getString("quantity"), rs.getString("Image_url"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

     */
    /*
    private ImageIcon displayImage()
    {
        try
        {
            File imageFile = new File("src/main/resources/res/AppImages/colt_alb.jpg");
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
    */

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

        labelImageURL = new JLabel("Image URL: ");
        tfImageURl = new JTextField();

        btnAddInAddPanel = new JButton("Add book");
        btnAddInAddPanel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Book book = new Book(tfAddBookName.getText(), tfAddBookAuthor.getText(), tfAddBookIsbn.getText(), tfAddBookPrice.getText(), tfAddBookQuantity.getText(), tfImageURl.getText());
                if (!BookService.isBookValid(book))
                {
                    System.out.println("Wrong");
                    return;
                }
                bookRepo.addBook(book,root);
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
        addBookPanel.add(labelImageURL);
        addBookPanel.add(tfImageURl);
        addBookPanel.add(btnAddInAddPanel);

        JOptionPane.showMessageDialog(this, addBookPanel, "Add books", JOptionPane.PLAIN_MESSAGE);

    }

    /*
    private void addBook(Book book)
    {
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "INSERT INTO Books (ISBN, Name, Author, Price, Quantity, Library_id, Image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement insertBook = conn.prepareStatement(query);
            insertBook.setString(1, book.getIsbn());
            insertBook.setString(2, book.getName());
            insertBook.setString(3, book.getAuthor());
            insertBook.setString(4, book.getPrice());
            insertBook.setString(5, book.getQuantity());
            insertBook.setString(6, root.getID());
            insertBook.setString(7, book.getImage_url());

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
*/

    /*
    private void displayBooks(JPanel booksPanel, Library root)
    {
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "SELECT * FROM Books WHERE library_id=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, root.getID());
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                System.out.println(rs.getString("name"));
                Book book = new Book(rs.getString("Name"), rs.getString("Author"), rs.getString("ISBN"),
                       rs.getString("Price"), rs.getString("Quantity"), rs.getString("Image_url"));
                JLabel bookLabel = new JLabel(book.getName());

                ImageIcon bookIcon = new ImageIcon(rs.getString("Image_url"));
                JLabel imageLabel = new JLabel(bookIcon);

                JPanel bookPanel = new JPanel();
                bookPanel.setLayout(new BorderLayout());
                bookPanel.add(bookLabel, BorderLayout.NORTH);
                bookPanel.add(imageLabel, BorderLayout.CENTER);

                booksPanel.add(bookPanel);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
*/

    public static void main(String[] args)
    {
        Library lib = new Library("1","aaa","aaaaa","aaaa","aaaa");
        AdminForm adminForm = new AdminForm(null, lib);
    }
}
