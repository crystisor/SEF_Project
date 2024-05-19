package org.project.Forms;

import org.project.Entities.Book;
import org.project.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserOrderForm extends JDialog {

    private JList orderList;
    private JPanel orderPanel = new JPanel();
    private JButton btnCancel;
    private JButton btnConfirm;

    public UserOrderForm(JDialog parent, User user, List<Book> selectedBooks) {

        super(parent);
        setTitle("Order");
        setMinimumSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        orderPanel.setLayout(new BorderLayout());

        JScrollPane libScrollPane = new JScrollPane(orderList);
        libScrollPane.setPreferredSize(new Dimension(150, 300));
        orderPanel.add(orderList, BorderLayout.NORTH);
        orderPanel.add(btnCancel, BorderLayout.SOUTH);
        setContentPane(orderPanel);
        updateOrderList(selectedBooks);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public void updateOrderList(List<Book> books) {

        DefaultListModel<Book> bookListModel = new DefaultListModel<>();
        for (Book book : books) {
            System.out.println("Book: " + book.getImage_url());
            bookListModel.addElement(book);
        }
        orderList.setModel(bookListModel);
    }

    public static void main(String[] args) {

        List<Book> books = new ArrayList<>();
        books.add(new Book("A","A","A","A","A","A"));
        books.add(new Book("B","B","B","B","B","B"));
        books.add(new Book("C","C","C","C","C","C"));
        UserOrderForm frm = new UserOrderForm(null, null, books);
    }
}
