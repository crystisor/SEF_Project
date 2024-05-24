package org.project.DbContext.Interfaces;

import org.project.Entities.Book;
import org.project.Entities.Order;

import java.util.List;

public interface IOrderRepo {

    int createOrder(int userId, List<Book> books);

    String countOrders();

    List<Order> getOrders(String libraryID);

    void deleteOrder(int orderId);

    void sendUserFeedback(int userID, String feedback);
}
