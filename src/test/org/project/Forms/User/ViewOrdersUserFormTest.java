package org.project.Forms.User;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.DbContext.Repos.OrderRepo;
import org.project.Entities.Order;
import org.project.Forms.User.ViewOrdersUserForm;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ViewOrdersUserFormTest {

    @Test
    public void testListOrderToArrayOfStrings() {
        ViewOrdersUserForm form = new ViewOrdersUserForm(null, "testUser", new OrderRepo());

        List<Order> orders = new ArrayList<>();
        orders.add(new Order("1", "testUser", "In Delivery", "2023-05-25"));
        orders.add(new Order("2", "testUser", null, "2023-05-26"));

        String[][] expected = {
                {"1", "2023-05-25", "In Delivery"},
                {"2", "2023-05-26", "Pending"}
        };

        String[][] actual = form.listOrderToArrayOfStrings(orders);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testListOrderToArrayOfStringsWithEmptyList() {
        ViewOrdersUserForm form = new ViewOrdersUserForm(null, "testUser", new OrderRepo());

        List<Order> orders = new ArrayList<>();

        String[][] expected = new String[0][3];
        String[][] actual = form.listOrderToArrayOfStrings(orders);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testListOrderToArrayOfStringsWithNullFeedback() {
        ViewOrdersUserForm form = new ViewOrdersUserForm(null, "testUser", new OrderRepo());

        List<Order> orders = new ArrayList<>();
        orders.add(new Order("1", "testUser", null, "2023-05-25"));

        String[][] expected = {
                {"1", "2023-05-25", "Pending"}
        };

        String[][] actual = form.listOrderToArrayOfStrings(orders);

        assertArrayEquals(expected, actual);
    }
}