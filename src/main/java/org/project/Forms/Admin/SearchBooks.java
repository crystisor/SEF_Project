package org.project.Forms.Admin;

import org.project.DbContext.Interfaces.IBookRepo;
import org.project.DbContext.Repos.BookRepo;
import org.project.Entities.Book;
import org.project.Services.BookListCellRenderer;
import org.project.Entities.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchBooks extends  JDialog
{
    private static Library root;
    private JPanel searchBooksPanel;
    private JTextField tfSearchBook;
    private JList<Book> bookList;
    private JButton searchButton;
    private JButton cancelButton;
    private JLabel Icon;
    private IBookRepo _bookRepo;

    public SearchBooks(JDialog parent, Library root, IBookRepo bookRepo)
    {
        super(parent);
        SearchBooks.root = root;
        setTitle("Search Books");
        setContentPane(searchBooksPanel);
        setMinimumSize(new Dimension(400, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        _bookRepo = bookRepo;

        bookList.setCellRenderer(new BookListCellRenderer());

        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Book book = _bookRepo.getBookByName(tfSearchBook.getText());
                if (book != null)
                {
                    EditBook editBook = new EditBook(SearchBooks.this, book, new BookRepo());
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
        //displayBooks();
        updateBookList(_bookRepo.getBooksByLibraryName(root.getName()));
        setVisible(true);
    }

    public void updateBookList(List<Book> books) {

        DefaultListModel<Book> bookListModel = new DefaultListModel<>();
        for (Book book : books) {
            System.out.println("a");
            System.out.println("Book: " + book.getImage_url());
            bookListModel.addElement(book);
        }
        bookList.setModel(bookListModel);
    }

    public static void main(String[] args)
    {
        Library lib = new Library("1","aaa","aaaaa","aaaa","aaaa");
        SearchBooks searchBooks = new SearchBooks(null,lib, new BookRepo());
    }
}
