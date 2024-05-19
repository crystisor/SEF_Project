package org.project.DbContext.Repos;

import org.project.DbContext.DbConfig;
import org.project.DbContext.Interfaces.IBookRepo;
import org.project.Entities.Book;
import org.project.Entities.Library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepo extends DbConfig implements IBookRepo {


    public Book getBookByName(String bookName) {

        Book book = null;
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Books WHERE Name = '" + bookName + "'");

            while (resultSet.next()) {
                book = new Book( resultSet.getString("Name"), resultSet.getString("Author"), resultSet.getString("ISBN"),
                        resultSet.getString("Price"), resultSet.getString("Quantity"), resultSet.getString("Image_url"));
            }
            System.out.println(book + "\n");

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    public List<Book> getBooksByLibraryName(String libraryName) {

        System.out.println("libraryName: " + libraryName);
        List<Book> books = new ArrayList<>();
       // DefaultListModel<Book> bookListModel = new DefaultListModel<>();
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Books " +
                    "INNER JOIN Libraries ON Books.library_id = Libraries.id " +
                    "WHERE Libraries.name = '" + libraryName + "'");

            while (rs.next()) {
                Book book = new Book(rs.getString("Name"), rs.getString("Author"), rs.getString("ISBN"),
                        rs.getString("Price"), rs.getString("Quantity"), rs.getString("Image_url"));
                books.add(book);
               // bookListModel.addElement(book);
            }

            //bookList.setModel(bookListModel);
            rs.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    public void editBook(Book book, String quantity, String price)
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

    public void deleteBook(Book book)
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

    public void addBook(Book book, Library root)
    {
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "INSERT INTO Books (ISBN, Name, Author, Price, Quantity, Library_id, Image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement insertBook = conn.prepareStatement(query);
            insertBook.setString(1, book.getIsbn());
            insertBook.setString(2, book.getName());
            insertBook.setString(3, book.getAuthor());
            insertBook.setString(4, book.getPrice());
            insertBook.setString(5, book.getQuantity());
            insertBook.setString(6, root.getID());
            insertBook.setString(7, book.getImage_url());

            int rowsInserted = insertBook.executeUpdate();
            if (rowsInserted > 0)
            {
                System.out.println("Book added");
            } else
                System.out.println("Failed to add book");
            insertBook.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
