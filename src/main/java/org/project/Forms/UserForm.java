package org.project.Forms;

import javax.swing.*;
import java.awt.*;

public class UserForm extends JDialog
{
    private JPanel userPanel;
    private JButton searchBook;
    private JButton librarySelect;
    private JButton placeOrder;

    public UserForm(JDialog parent)
    {
        super(parent);
        setTitle("Root");
        setContentPane(userPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static  void main(String[] args)
    {
        UserForm userForm = new UserForm(null);
    }
}