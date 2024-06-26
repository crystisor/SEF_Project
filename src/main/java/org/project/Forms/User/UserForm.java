package org.project.Forms.User;

import org.project.DbContext.Interfaces.IBookRepo;
import org.project.DbContext.Interfaces.ILibraryRepo;
import org.project.DbContext.Repos.BookRepo;
import org.project.DbContext.Repos.LibraryRepo;
import org.project.DbContext.Repos.OrderRepo;
import org.project.DbContext.Repos.UserRepo;
import org.project.Entities.Book;
import org.project.Forms.LoginForm;
import org.project.Services.BookListCellRenderer;
import org.project.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserForm extends JDialog {

    private JPanel userPanel;
    private JPanel bookPanel;
    private JButton orderDetails;
    private JButton addBooktoOrder;
    private JButton addFunds;
    private JList<String> libList;
    private JList<Book> bookList;
    private JLabel Icon;
    private JButton btnOrders;
    private JPanel accPanel;
    private JLabel balLabel;
    private JButton btnLogout;
    IBookRepo _bookRepo;
    ILibraryRepo _libraryRepo;
    //IOrderRepo _orderRepo;

    private List<Book> order = new ArrayList<>();

    public UserForm(JDialog parent, User user, BookRepo bookRepo, LibraryRepo libraryRepo) {
        super(parent);
        setTitle("User");
        setMinimumSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        _bookRepo = bookRepo;
        _libraryRepo = libraryRepo;
        //_orderRepo = orderRepo;

        bookList.setCellRenderer(new BookListCellRenderer());

        // Initialize components
       userPanel.setLayout(new BorderLayout());

        List<String> libNames = _libraryRepo.getLibraryNames();
        libList = new JList<>(libNames.toArray(new String[0]));

        JScrollPane libScrollPane = new JScrollPane(libList);
        libScrollPane.setPreferredSize(new Dimension(150, 300));

        JScrollPane bookScrollPane = new JScrollPane(bookList);

        // Add components to the panel
        bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.X_AXIS));
        bookPanel.add(Box.createHorizontalStrut(10));
        bookPanel.add(addBooktoOrder);
        bookPanel.add(Box.createHorizontalStrut(10)); // Add space between buttons
        bookPanel.add(orderDetails);
        bookPanel.add(Box.createHorizontalStrut(200));
        bookPanel.add(btnOrders, BorderLayout.EAST);
        bookPanel.add(Box.createHorizontalStrut(10));
        bookPanel.add(addFunds, BorderLayout.EAST);

        userPanel.add(libScrollPane, BorderLayout.WEST);
        userPanel.add(bookScrollPane, BorderLayout.CENTER);
        userPanel.add(bookPanel, BorderLayout.SOUTH);

        accPanel.setLayout(new BoxLayout(accPanel, BoxLayout.Y_AXIS));
        accPanel.add(Icon, BorderLayout.WEST);
        accPanel.add(balLabel,BorderLayout.EAST);
        accPanel.add(btnLogout);

        Icon.setText("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");
        balLabel.setText("Current balance: " + user.getBalance() + "$");
        userPanel.add(accPanel, BorderLayout.NORTH);

        setContentPane(userPanel);

        // Add action listener for library selection
        libList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedLibrary = libList.getSelectedValue();
                if (selectedLibrary != null) {

                    List<Book> books = _bookRepo.getBooksByLibraryName(selectedLibrary);
                    updateBookList(books);
                }
            }
        });

        addBooktoOrder.addActionListener(e -> {
            Book selectedBook = bookList.getSelectedValue();
            if (selectedBook != null) {

                Book book = _bookRepo.getBookByName(selectedBook.getName());
                if (book != null) {
                     order.add(book);
                    JOptionPane.showMessageDialog(UserForm.this, "Book '" + selectedBook + "' added to order.");
                } else {
                    JOptionPane.showMessageDialog(UserForm.this, "Failed to retrieve book information.");
                }
            } else {
                JOptionPane.showMessageDialog(UserForm.this, "Please select a book to add to the order.");
            }
        });

        orderDetails.addActionListener(e -> {
            if (!order.isEmpty()) {
                UserOrderForm userOrder = new UserOrderForm(this, user, order, new OrderRepo(), new UserRepo(), new BookRepo());
                balLabel.setText("Current balance: " + user.getBalance() + "$");
            } else {
                JOptionPane.showMessageDialog(UserForm.this, "Please add at least one book to the order.");
            }
        });

        btnOrders.addActionListener(e -> {
                ViewOrdersUserForm userOrdersForm = new ViewOrdersUserForm(this, user.getUserId(), new OrderRepo());
        });

        addFunds.addActionListener( e -> {
                CardForm cardForm = new CardForm(this, user, new UserRepo());
                balLabel.setText("Current balance: " + user.getBalance() + "$");
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm(null, new UserRepo());
            }
        });
        setVisible(true);
    }

    public void updateBookList(List<Book> books) {

        DefaultListModel<Book> bookListModel = new DefaultListModel<>();
        for (Book book : books) {
            bookListModel.addElement(book);
        }
        bookList.setModel(bookListModel);
    }

    public static void main(String[] args) {

        User u = new User("uvuvuevuevueonyetnieuenvu","ubuemubuem osas","iov@gmail.com","cuc","0123012444","as","1000000000000.0");
        u.setUserId("39");
        System.out.println(Double.parseDouble(u.getBalance()));
        SwingUtilities.invokeLater(() -> new UserForm(null,u,new BookRepo(), new LibraryRepo()));
    }
}
