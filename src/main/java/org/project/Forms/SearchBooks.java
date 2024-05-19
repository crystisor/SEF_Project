package org.project.Forms;

import org.project.Entities.Book;
import org.project.Services.BookListCellRenderer;
import org.project.Entities.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SearchBooks extends  JDialog
{
    private static final String dbURL = "jdbc:mysql://127.0.0.1/sef_project";
    private static final String dbUser = "cristi";
    private static final String dbPassword ="qwertyuiop";

    private static Library root;
    private JPanel searchBooksPanel;
    private JTextField tfSearchBook;
    private JList<Book> bookList;
    private JButton searchButton;
    private JButton cancelButton;

    public SearchBooks(JDialog parent, Library root)
    {
        super(parent);
        SearchBooks.root = root;
        setTitle("Search Books");
        setContentPane(searchBooksPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        bookList.setCellRenderer(new BookListCellRenderer());

        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Book book = search(tfSearchBook.getText());
                if (book != null)
                {
                    EditBook editBook = new EditBook(SearchBooks.this, book);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Book not found!");
                }
            }
        });
        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        displayBooks();
        setVisible(true);
    }

    private Book search(String bookName) // for text field
    {
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "SELECT * FROM Books WHERE Name = ? AND library_id = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, bookName);
            ps.setString(2, root.getID());

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

    private void displayBooks()
    {
        DefaultListModel<Book> listModel = new DefaultListModel<>();
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "SELECT * FROM Books WHERE library_id=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, root.getID());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Book book = new Book(rs.getString("Name"), rs.getString("Author"), rs.getString("ISBN"),
                        rs.getString("Price"), rs.getString("Quantity"), rs.getString("Image_url"));
                listModel.addElement(book);
            }
            bookList.setModel(listModel);

            rs.close();
            ps.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Library lib = new Library("1","aaa","aaaaa","aaaa","aaaa");
        SearchBooks searchBooks = new SearchBooks(null,lib);
    }
}