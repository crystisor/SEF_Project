package org.project.Forms;

import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.Entities.Book;
import org.project.Entities.Library;
import org.project.Entities.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ViewOrdersUserForm extends JDialog {

    private JPanel viewOrdersPanel;
    private JTable ordersTable;
    private JButton btnCancel;
    private static final String[] columnNames = {"Order ID", "Date", "Feedback"};
    private static String[][] rows;
    IOrderRepo _orderRepo;

    public ViewOrdersUserForm(JDialog parent, String userId, IOrderRepo orderRepo)
    {
        super(parent);
        setTitle("UserOrders");
        setContentPane(viewOrdersPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        _orderRepo = orderRepo;
        ViewOrdersUserForm.rows = listToArrayOfStrings(_orderRepo.getOrdersByUserId(userId));

        DefaultTableModel tableModel = new DefaultTableModel(rows, columnNames);

        ordersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        viewOrdersPanel.setLayout(new BorderLayout());
        viewOrdersPanel.add(scrollPane, BorderLayout.CENTER);


        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());
        viewOrdersPanel.add(btnCancel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static String[][] listToArrayOfStrings(List<Order> orders)
    {
        String[][] arr = new String[100][3];
        for (int i = 0; i < orders.size(); i++)
        {
            Order order = orders.get(i);
            {
                arr[i][0] = order.getOrderID();
                arr[i][1] = order.getDate();
                arr[i][2] = "feedback";
            }
        }
        return arr;
    }
}
