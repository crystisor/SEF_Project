package org.project.Forms;

import org.project.Entities.Book;
import org.project.Entities.User;
import org.project.Services.BookListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserOrderForm extends JDialog {

    private JList<Book> orderList;
    private JPanel orderPanel = new JPanel();
    private JButton btnCancel;
    private JButton btnConfirm;
    private JButton btnRemove;
    private DefaultListModel<Book> bookListModel;

    public UserOrderForm(JDialog parent, User user, List<Book> selectedBooks) {
        super(parent);
        setTitle("Order");
        setMinimumSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        orderList.setCellRenderer(new BookListCellRenderer());
        orderPanel.setLayout(new BorderLayout());

        orderList = new JList<>();
        JScrollPane libScrollPane = new JScrollPane(orderList);
        libScrollPane.setPreferredSize(new Dimension(150, 300));
        orderPanel.add(libScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnCancel);
        orderPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(orderPanel);
        updateOrderList(selectedBooks);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for confirming the order here
                // For example, save the order details to the database
                JOptionPane.showMessageDialog(UserOrderForm.this, "Order confirmed!");
                dispose();
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = orderList.getSelectedIndex();
                if (selectedIndex != -1) {
                    bookListModel.remove(selectedIndex);
                    if (bookListModel.isEmpty()) {
                        JOptionPane.showMessageDialog(UserOrderForm.this, "No more items in the order. Closing the form.");
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(UserOrderForm.this, "No book selected to remove.");
                }
            }
        });

        setVisible(true);
    }

    public void updateOrderList(List<Book> books) {
        bookListModel = new DefaultListModel<>();
        for (Book book : books) {
            bookListModel.addElement(book);
        }
        orderList.setModel(bookListModel);
    }

    public static void main(String[] args) {

        List<Book> books = List.of(
                new Book("A", "A", "A", "A", "A", "A"),
                new Book("B", "B", "B", "B", "B", "B"),
                new Book("C", "C", "C", "C", "C", "C")
        );

        new UserOrderForm(null, null, books);
    }
}
