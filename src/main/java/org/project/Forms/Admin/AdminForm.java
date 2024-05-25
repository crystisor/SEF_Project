package org.project.Forms.Admin;

import org.project.DbContext.Interfaces.IBookRepo;
import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.DbContext.Interfaces.IUserRepo;
import org.project.DbContext.Repos.BookRepo;
import org.project.DbContext.Repos.OrderRepo;
import org.project.DbContext.Repos.UserRepo;
import org.project.Entities.Book;
import org.project.Entities.Library;
import org.project.Entities.Order;
import org.project.Services.BookService;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
    private JLabel libLabel;
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
    IUserRepo _userRepo;
    IBookRepo _bookRepo;
    IOrderRepo _orderRepo;

    public AdminForm(JDialog parent, Library root, IUserRepo userRepo, IBookRepo bookRepo, IOrderRepo orderRepo)
    {
        super(parent);
        setTitle("Root");
        setContentPane(RootPanel);
        setMinimumSize(new Dimension(500,600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        AdminForm.root = root;
        _userRepo = userRepo;
        _bookRepo = bookRepo;
        _orderRepo = orderRepo;
        userCountLabel.setText(_userRepo.countUsers());
        orderCount.setText(_orderRepo.countOrdersPerLibrary(root.getID()));
        btnSearch.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SearchBooks searchBooks = new SearchBooks(AdminForm.this, AdminForm.root, new BookRepo());
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
                List<Order> orders = _orderRepo.getOrdersByLibraryId(root.getID());
                for (Order order : orders)
                {
                    System.out.println("Order: " + order.getOrderID());
                    for (Book book : order.getBooks())
                    {
                        System.out.print(book.getName() + " ");
                    }
                    System.out.println();
                }
                ViewOrdersForm viewOrdersForm = new ViewOrdersForm(AdminForm.this, _orderRepo.getOrdersByLibraryId(root.getID()), AdminForm.root, _orderRepo);
                orderCount.setText(_orderRepo.countOrdersPerLibrary(root.getID()));
            }
        });
        this._userRepo = new UserRepo();
        libLabel.setText("Welcome, " + root.getEmail() + "!");
        setVisible(true);
    }

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
                _bookRepo.addBook(book,root);
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

    /*public static void main(String[] args)
    {
        Library lib = new Library("1","corina","aaaaa","aaaa","aaaa");
        AdminForm adminForm = new AdminForm(null, lib, new UserRepo(), new BookRepo(), new OrderRepo());
    }
    */

}
