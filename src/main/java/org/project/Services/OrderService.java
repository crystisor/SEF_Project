package org.project.Services;

import org.project.DbContext.Interfaces.IBookRepo;
import org.project.Entities.Book;

import java.util.List;

public class OrderService {

    public double getPrice(List<Book> order) {

        double orderPrice = 0.0;

        for( Book book : order ) {

            if( Integer.parseInt(book.getQuantity()) >= 1 )
                orderPrice += Double.parseDouble(book.getPrice());
            else
                return -1;
        }
        return orderPrice;
    }
}
