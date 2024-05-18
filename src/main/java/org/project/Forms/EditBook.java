package org.project.Forms;

import org.project.Entities.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EditBook extends JDialog
{
    private static final String dbURL = "jdbc:mysql://127.0.0.1/sef_project";
    private static final String dbUser = "cristi";
    private static final String dbPassword ="qwertyuiop";

    private static Book book;
    private JButton editButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JPanel editPanel;

    public EditBook(JDialog parent, Book book)
    {
        super(parent);
        EditBook.book = book;
        setTitle("Edit books");
        setContentPane(editPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editButton.addActionListener(new ActionListener()
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
                    //editBook(book, editQuantity.getText(), editPrice.getText());
                    System.out.print("a");
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
        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                deleteBook(book);
                dispose();
            }
        });
        setVisible(true);
    }

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
}
