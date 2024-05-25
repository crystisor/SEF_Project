package org.project.Forms.Admin;

import org.junit.jupiter.api.Test;
import org.project.Entities.Book;
import org.project.Entities.Order;
import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewOrdersFormTest
{

    @Test
    void testListToArrayOfStrings()
    {
        // Create sample orders
        List<Order> orders = new ArrayList<>();

        // Create books
        List<Book> books1 = new ArrayList<>();
        books1.add(new Book("Book A1", "Author A1", "ISBN A1", "Price A1", "Quantity A1", "Image URL A1"));
        books1.add(new Book("Book A2", "Author A2", "ISBN A2", "Price A2", "Quantity A2", "Image URL A2"));

        List<Book> books2 = new ArrayList<>();
        books2.add(new Book("Book B1", "Author B1", "ISBN B1", "Price B1", "Quantity B1", "Image URL B1"));

        List<Book> books3 = new ArrayList<>();
        books3.add(new Book("Book C1", "Author C1", "ISBN C1", "Price C1", "Quantity C1", "Image URL C1"));
        books3.add(new Book("Book C2", "Author C2", "ISBN C2", "Price C2", "Quantity C2", "Image URL C2"));

        // Create orders with books and all fields set
        Order order1 = new Order();
        order1.setOrderID("10");
        order1.setFeedback("Pending");
        order1.setUserID("1");
        order1.setDate("2024-05-01");
        order1.setBooks(books1);

        Order order2 = new Order();
        order2.setOrderID("20");
        order2.setFeedback("Pending");
        order2.setUserID("2");
        order2.setDate("2024-05-02");
        order2.setBooks(books2);

        Order order3 = new Order();
        order3.setOrderID("30");
        order3.setFeedback("Pending");
        order3.setUserID("3");
        order3.setDate("2024-05-03");
        order3.setBooks(books3);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        // Test the method
        String[][] result = ViewOrdersForm.listToArrayOfStrings(orders);

        // Assertions
        assertNotNull(result);
        assertEquals(3, result.length);

        assertArrayEquals(new String[]{"10", "2024-05-01", "Book A1\nBook A2", "Image URL A1"}, result[0]);
        assertArrayEquals(new String[]{"20", "2024-05-02", "Book B1", "Image URL B1"}, result[1]);
        assertArrayEquals(new String[]{"30", "2024-05-03", "Book C1\nBook C2", "Image URL C1"}, result[2]);
    }

    @Test
    void testListToArrayOfStringsNoPending()
    {
        // Create sample orders
        List<Order> orders = new ArrayList<>();

        // Create books
        List<Book> books1 = new ArrayList<>();
        books1.add(new Book("Book A1", "Author A1", "ISBN A1", "Price A1", "Quantity A1", "Image URL A1"));
        books1.add(new Book("Book A2", "Author A2", "ISBN A2", "Price A2", "Quantity A2", "Image URL A2"));

        List<Book> books2 = new ArrayList<>();
        books2.add(new Book("Book B1", "Author B1", "ISBN B1", "Price B1", "Quantity B1", "Image URL B1"));

        List<Book> books3 = new ArrayList<>();
        books3.add(new Book("Book C1", "Author C1", "ISBN C1", "Price C1", "Quantity C1", "Image URL C1"));
        books3.add(new Book("Book C2", "Author C2", "ISBN C2", "Price C2", "Quantity C2", "Image URL C2"));

        // Create orders with books and all fields set
        Order order1 = new Order();
        order1.setOrderID("10");
        order1.setFeedback("Don't want to deliver");
        order1.setUserID("1");
        order1.setDate("2024-05-01");
        order1.setBooks(books1);

        Order order2 = new Order();
        order2.setOrderID("20");
        order2.setFeedback("In delivery");
        order2.setUserID("2");
        order2.setDate("2024-05-02");
        order2.setBooks(books2);

        Order order3 = new Order();
        order3.setOrderID("30");
        order3.setFeedback("Rejected");
        order3.setUserID("3");
        order3.setDate("2024-05-03");
        order3.setBooks(books3);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        // Test the method
        String[][] result = ViewOrdersForm.listToArrayOfStrings(orders);

        // Assertions
        assertNull(result);

    }
}