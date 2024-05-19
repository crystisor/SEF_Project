package org.project.DbContext.Interfaces;

import org.project.Entities.Book;

import java.util.List;

public interface IBookRepo {

    Book getBookByName(String name);
    public List<Book> getBooksByLibraryName(String libraryName);
}
