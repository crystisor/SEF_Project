package org.project.Services;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.project.Entities.Book;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderServiceTest {

    private OrderService orderService;
    @BeforeEach
    public void setUp() {
        orderService = new OrderService();
    }

    @Test
    public void testGetPriceWithValidOrder() {

        List<Book> order = new ArrayList<>();
        order.add(new Book("Book 1", "Author One", "1", "10.0", "2",""));
        order.add(new Book("Book 2", "Author Two", "2", "15.5", "1",""));

        double expectedPrice = 25.5;
        double actualPrice = orderService.getPrice(order);

        assertEquals(expectedPrice, actualPrice, 0.001);
    }

    @Test
    public void testGetPriceWithInvalidQuantity() {

        List<Book> order = new ArrayList<>();
        order.add(new Book("Book 1", "Book One", "1", "10.0", "-1",""));

        double expectedPrice = -1;
        double actualPrice = orderService.getPrice(order);

        assertEquals(expectedPrice, actualPrice, 0.001);
    }

    @Test
    public void testGetPriceWithEmptyOrder() {
        List<Book> order = new ArrayList<>();

        double expectedPrice = 0.0;
        double actualPrice = orderService.getPrice(order);

        assertEquals(expectedPrice, actualPrice, 0.001);
    }

}
