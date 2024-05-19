package org.project.Forms;

import org.project.DbContext.Interfaces.IBookRepo;
import org.project.DbContext.Interfaces.ILibraryRepo;
import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.DbContext.Repos.BookRepo;
import org.project.DbContext.Repos.LibraryRepo;
import org.project.DbContext.Repos.OrderRepo;
import org.project.Entities.Book;
import org.project.Services.BookListCellRenderer;
import org.project.Entities.User;

import javax.swing.*;
import java.awt.*;
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
        bookPanel.add(orderDetails);
        bookPanel.add(Box.createHorizontalStrut(10)); // Add space between buttons
        bookPanel.add(addBooktoOrder);
        bookPanel.add(Box.createHorizontalStrut(10)); // Add space between buttons
        bookPanel.add(addFunds);

        userPanel.add(libScrollPane, BorderLayout.WEST);
        userPanel.add(bookScrollPane, BorderLayout.CENTER);
        userPanel.add(bookPanel, BorderLayout.SOUTH);
        userPanel.add(Icon, BorderLayout.NORTH);

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
                UserOrderForm userOrder = new UserOrderForm(this, user, order, new OrderRepo());
            } else {
                JOptionPane.showMessageDialog(UserForm.this, "Please add at least one book to the order.");
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

        User u = new User("gigi","gogu","iov@gmail.com","cuc","0123012444","as");
        u.setUserId(38);
        SwingUtilities.invokeLater(() -> new UserForm(null,u,new BookRepo(), new LibraryRepo()));
    }
}
