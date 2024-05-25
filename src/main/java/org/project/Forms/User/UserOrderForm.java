package org.project.Forms.User;

import org.project.DbContext.Interfaces.IBookRepo;
import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.DbContext.Interfaces.IUserRepo;
import org.project.DbContext.Repos.BookRepo;
import org.project.DbContext.Repos.OrderRepo;
import org.project.DbContext.Repos.UserRepo;
import org.project.Entities.Book;
import org.project.Entities.User;
import org.project.Services.BookListCellRenderer;
import org.project.Services.OrderService;

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
    private IOrderRepo _orderRepo;
    private IUserRepo _userRepo;
    private IBookRepo _bookRepo;

    public UserOrderForm(JDialog parent, User user, List<Book> selectedBooks, IOrderRepo orderRepo, IUserRepo userRepo,IBookRepo bookRepo) {
        super(parent);
        setTitle("Order");
        setMinimumSize(new Dimension(400, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        _orderRepo = orderRepo;
        _userRepo = userRepo;
        _bookRepo = bookRepo;

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

                OrderService srv = new OrderService();
                double orderPrice = srv.getPrice(selectedBooks);
                System.out.println(orderPrice);
                double crtBalance = Double.parseDouble(user.getBalance());

                if( orderPrice == -1)
                    JOptionPane.showMessageDialog(UserOrderForm.this, "One or more books are not in stock!");
                else if( crtBalance < orderPrice )
                    JOptionPane.showMessageDialog(UserOrderForm.this, "Insufficient balance!");
                else {

                    crtBalance -= orderPrice;
                    user.setBalance(crtBalance + "");

                    _orderRepo.createOrder(user.getUserId(), selectedBooks);
                    _userRepo.updateUserFunds(user.getUserId(),"-"+orderPrice);

                    for( Book b : selectedBooks ) {
                        b.setQuantity( Integer.parseInt(b.getQuantity())-1 + "" );
                        _bookRepo.editBook(b,b.getQuantity(),b.getPrice());
                    }

                    JOptionPane.showMessageDialog(UserOrderForm.this, "Order confirmed!");
                    selectedBooks.clear();
                    dispose();
                }
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = orderList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Book removedBook = bookListModel.remove(selectedIndex);
                    selectedBooks.remove(removedBook); // Remove from selectedBooks list
                    updateOrderList(selectedBooks);

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

        new UserOrderForm(null, null, books, new OrderRepo(), new UserRepo(), new BookRepo());
    }
}
