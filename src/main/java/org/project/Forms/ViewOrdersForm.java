    package org.project.Forms;


    import org.project.Entities.Library;
    import org.project.Entities.Order;
    import org.project.Entities.Book;
    import javax.swing.*;
    import javax.swing.text.View;
    import java.awt.*;
    import java.util.List;
    import java.util.stream.Collectors;

    public class ViewOrdersForm extends JDialog
    {
        private JPanel viewOrdersPanel;
        private JTable ordersTable;
        private static final String[] columnNames = {"Order ID", "Date", "Books"};
        private static String[][] rows;
        private static Library root;

        public ViewOrdersForm(JDialog parent, List<Order> orders, Library root)
        {
            super(parent);
            setTitle("ViewOrders");
            setContentPane(viewOrdersPanel);
            setMinimumSize(new Dimension(500, 500));
            setModal(true);
            setLocationRelativeTo(parent);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            System.out.println(root.getName() + " " + root.getID());
            ViewOrdersForm.root = root;
            System.out.println(root.getID());
            System.out.println(ViewOrdersForm.root.getID());
            listToArrayOfStrings(orders);
            ViewOrdersForm.rows = listToArrayOfStrings(orders);

            ordersTable = new JTable(rows, columnNames);
            JScrollPane scrollPane = new JScrollPane(ordersTable);
            viewOrdersPanel.setLayout(new BorderLayout());
            viewOrdersPanel.add(scrollPane, BorderLayout.CENTER);

            setVisible(true);
        }
        public static String[][] listToArrayOfStrings(List<Order> orders) {
            int count = 0; // ok rows
            for (Order order : orders)
            {
                if (order.getLibraryID().equals(ViewOrdersForm.root.getID()))
                    count++;
            }
            String[][] arr = new String[100][3];
            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                System.out.println(orders.get(i).getLibraryID());
                System.out.println(ViewOrdersForm.root.getID());
                if (order.getLibraryID().equals(ViewOrdersForm.root.getID()))
                {
                    arr[i][0] = order.getOrderID();
                    arr[i][1] = order.getDate();
                    arr[i][2] = order.getBooks().stream()
                            .map(Book::getName)
                            .collect(Collectors.joining(", "));
                }
            }
            return arr;
        }
    }
