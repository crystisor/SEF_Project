package org.project.DbContext.Interfaces;

import org.project.Entities.Book;
import org.project.Entities.Feedback;
import org.project.Entities.Order;

import java.util.List;

public interface IOrderRepo {

    int createOrder(String userId, List<Book> books);
    String countOrdersPerLibrary(String LibraryID);

    List<Order> getOrdersByLibraryId(String libraryID);

    List<Order> getOrdersByUserId(String userId);

    void deleteOrder(int orderId);

    void sendUserFeedback(int userID, String feedback);

    List<Feedback> getFeedbacks(String userID);
}
