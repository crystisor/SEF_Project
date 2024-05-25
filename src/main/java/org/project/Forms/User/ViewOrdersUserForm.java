package org.project.Forms.User;

import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.Entities.Book;
import org.project.Entities.Feedback;
import org.project.Entities.Library;
import org.project.Entities.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewOrdersUserForm extends JDialog {

    private JPanel viewOrdersPanel;
    private JTable ordersTable;
    private JButton btnCancel;
    private static final String[] columnNames = {"Order ID", "Date", "Feedback"};
    private static String[][] rows;
    private static String userId;
    IOrderRepo _orderRepo;

    public ViewOrdersUserForm(JDialog parent, String userId, IOrderRepo orderRepo)
    {
        super(parent);
        ViewOrdersUserForm.userId = userId;
        setTitle("UserOrders");
        setContentPane(viewOrdersPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        _orderRepo = orderRepo;
       // List<Order> orders = _orderRepo.getOrdersByUserId(userId);
        ViewOrdersUserForm.rows = listOrderToArrayOfStrings(_orderRepo.getOrdersByUserId(userId));

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

    public String[][] listOrderToArrayOfStrings(List<Order> orders)
    {
        String[][] arr = new String[orders.size()][3];
        for (int i = 0; i < orders.size(); i++)
        {
            Order order = orders.get(i);
            arr[i][0] = order.getOrderID();
            arr[i][1] = order.getDate();
            if (order.getFeedback() != null)
                arr[i][2] = order.getFeedback();
            else
                arr[i][2] = "Pending";
            System.out.println("Order:" + order.getOrderID() + " Date:" + order.getDate());
        }
        return arr;
    }

}
