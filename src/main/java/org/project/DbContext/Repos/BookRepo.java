package org.project.DbContext.Repos;

import org.project.DbContext.DbConfig;
import org.project.DbContext.Interfaces.IBookRepo;
import org.project.Entities.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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


            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    public List<Book> getBooksByLibraryName(String libraryName) {

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

}
