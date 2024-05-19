package org.project.DbContext.Interfaces;

import org.project.Entities.Book;
import org.project.Entities.Order;

import java.util.List;

public interface IOrderRepo {

    boolean addBookToOrder(int orderId,Book book);
    int createOrder(String email);
    String countOrders();
    List<Order> getOrders();
}
