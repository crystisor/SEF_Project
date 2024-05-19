package org.project.DbContext.Interfaces;

import org.project.Entities.Book;
import org.project.Entities.Library;

import java.util.List;

public interface IBookRepo {

    Book getBookByName(String name);
    List<Book> getBooksByLibraryName(String libraryName);
    void editBook(Book book, String editQuantity, String editPrice);
    void deleteBook(Book book);
    void addBook(Book book, Library library);
}
