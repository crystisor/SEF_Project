package org.project.Forms;

import org.project.DbContext.Interfaces.IBookRepo;
import org.project.Entities.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EditBook extends JDialog {

    private static Book book;
    private JButton editButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JPanel editPanel;
    private JLabel Icon;
    private IBookRepo _bookRepo;

    public EditBook(JDialog parent, Book book, IBookRepo bookRepo) {
        super(parent);
        EditBook.book = book;
        setTitle("Edit books");
        setContentPane(editPanel);
        setMinimumSize(new Dimension(300, 300));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        _bookRepo = bookRepo;

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel editPanel = new JPanel();
                editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));

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
                    _bookRepo.editBook(book, editQuantity.getText(), editPrice.getText());
                System.out.print("a");
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _bookRepo.deleteBook(book);
                dispose();
            }
        });
        setVisible(true);
    }
}