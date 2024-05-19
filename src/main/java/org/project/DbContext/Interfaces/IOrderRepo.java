package org.project.DbContext.Interfaces;

import org.project.Entities.Book;

public interface IOrderRepo {

    boolean addBookToOrder(int orderId,Book book);
    int createOrder(String email);
}
