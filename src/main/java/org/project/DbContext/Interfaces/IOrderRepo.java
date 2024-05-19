package org.project.DbContext.Interfaces;

import org.project.Entities.Book;

import java.util.List;

public interface IOrderRepo {

    int createOrder(int userId, List<Book> books);
}
