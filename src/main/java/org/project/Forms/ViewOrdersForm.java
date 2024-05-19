package org.project.Forms;


import org.project.Entities.Order;
import org.project.Entities.Book;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ViewOrdersForm extends JDialog
{
    private JPanel viewOrdersPanel;
    private JTable ordersTable;
    private static final String[] columnNames = {"Order ID", "Date", "Books"};
    private static String[][] rows;

    public ViewOrdersForm(JDialog parent, List<Order> orders)
    {
        super(parent);
        setTitle("ViewOrders");
        setContentPane(viewOrdersPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listToArrayOfStrings(orders);
        ViewOrdersForm.rows = listToArrayOfStrings(orders);

        ordersTable = new JTable(rows, columnNames);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        viewOrdersPanel.setLayout(new BorderLayout());
        viewOrdersPanel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
    public static String[][] listToArrayOfStrings(List<Order> orders) {
        String[][] arr = new String[orders.size()][3];

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            arr[i][0] = order.getOrderID();
            arr[i][1] = order.getDate();
            arr[i][2] = order.getBooks().stream()
                    .map(Book::getName)
                    .collect(Collectors.joining(", "));
            System.out.println(arr[i][0] + ": " + arr[i][1] + ": " + arr[i][2]);
        }
        return arr;
    }
}
