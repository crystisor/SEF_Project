package org.project.Forms;

import org.project.DbContext.Interfaces.IOrderRepo;
import org.project.Entities.Library;
import org.project.Entities.Order;
import org.project.Entities.Book;
import org.project.Services.ImageRenderer;
import org.project.Services.MultilineCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class ViewOrdersForm extends JDialog {
    private JPanel viewOrdersPanel;
    private JTable ordersTable;
    private JButton btnCancel;
    private JButton btnReject;
    private JButton btnAccept;
    private static final String[] columnNames = {"Order ID", "Date", "Books", "Image"};
    private static String[][] rows;
    private static Library root;
    private IOrderRepo _orderRepo;

    public ViewOrdersForm(JDialog parent, List<Order> orders, Library root, IOrderRepo orderRepo) {
        super(parent);
        setTitle("ViewOrders");
        setContentPane(viewOrdersPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ViewOrdersForm.root = root;
        ViewOrdersForm.rows = listToArrayOfStrings(orders);
        _orderRepo = orderRepo;
        DefaultTableModel tableModel = new DefaultTableModel(rows, columnNames);

        ordersTable = new JTable(tableModel);
        ordersTable.setRowHeight(60);
        ordersTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        ordersTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        ordersTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        ordersTable.getColumnModel().getColumn(3).setCellRenderer(new ImageRenderer());
        ordersTable.getColumnModel().getColumn(2).setCellRenderer(new MultilineCellRenderer());

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        viewOrdersPanel.setLayout(new BorderLayout());
        viewOrdersPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        btnAccept = new JButton("Accept");
        btnReject = new JButton("Reject");

        btnAccept.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int row = ordersTable.getSelectedRow();   // get index
                if (row != -1)
                {
                    TableModel model = ordersTable.getModel(); // get record
                    if (model instanceof DefaultTableModel)
                    {
                        DefaultTableModel defaultTableModel = (DefaultTableModel) model;

                        if (defaultTableModel.getValueAt(row, 0) != null)
                        {
                            int orderID = Integer.parseInt(defaultTableModel.getValueAt(row, 0).toString());
                            _orderRepo.sendUserFeedback(orderID, "In delivery");
                            //_orderRepo.deleteOrder(orderID);
                            defaultTableModel.removeRow(row);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(ViewOrdersForm.this,
                                    "Selected record is null",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        System.out.println("The table model is not a DefaultTableModel");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(ViewOrdersForm.this,
                            "Select a record",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonsPanel.add(btnAccept);

        btnReject.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int row = ordersTable.getSelectedRow();   // get index
                if (row == -1)
                    JOptionPane.showMessageDialog(ViewOrdersForm.this,
                            "Select a record",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                else
                {
                    TableModel model = ordersTable.getModel(); // get record
                    if (model instanceof DefaultTableModel)
                    {
                        DefaultTableModel defaultTableModel = (DefaultTableModel) model;

                        if (defaultTableModel.getValueAt(row, 0) == null)
                            JOptionPane.showMessageDialog(ViewOrdersForm.this,
                                    "Selected record is null",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                        else
                        {
                            JPanel feedbackPanel = new JPanel();
                            feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));

                            JLabel feedbackLabel = new JLabel("Type a feedback:");
                            JTextField feedbackTf = new JTextField();
                            feedbackPanel.add(feedbackLabel);
                            feedbackPanel.add(feedbackTf);
                            int result = JOptionPane.showConfirmDialog(null, feedbackPanel,
                                    "Feedback", JOptionPane.OK_CANCEL_OPTION);

                            if (result == JOptionPane.OK_OPTION)
                            {
                                int orderID = Integer.parseInt(defaultTableModel.getValueAt(row, 0).toString());
                                if (!feedbackTf.getText().isEmpty())
                                    _orderRepo.sendUserFeedback(orderID, feedbackTf.getText());
                                else
                                    _orderRepo.sendUserFeedback(orderID, "Rejected");
                                //_orderRepo.deleteOrder(orderID);
                                defaultTableModel.removeRow(row);
                            }
                            else
                                dispose();
                        }
                    }
                    else
                        System.out.println("The table model is not a DefaultTableModel");
                }
            }
        });
        buttonsPanel.add(btnReject);

        viewOrdersPanel.add(buttonsPanel, BorderLayout.NORTH);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());
        viewOrdersPanel.add(btnCancel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static String[][] listToArrayOfStrings(List<Order> orders)
    {
        String[][] arr = new String[orders.size()][4];
        for (int i = 0; i < orders.size(); i++)
        {
            Order order = orders.get(i);
            {
                if (order.getFeedback() == "Pending")
                {
                    arr[i][0] = order.getOrderID();
                    arr[i][1] = order.getDate();
                    arr[i][2] = order.getBooks().stream()
                            .map(Book::getName)
                            .collect(Collectors.joining("\n"));
                    arr[i][3] = order.getBooks().get(0).getImage_url();
                }
            }
        }
        return arr;
    }

}
