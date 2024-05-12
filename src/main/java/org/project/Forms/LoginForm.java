package org.project.Forms;

import org.project.Entities.Library;
import org.project.Entities.PasswordUtil;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class LoginForm extends JDialog{

    private static final String dbURL = "jdbc:mysql://25.19.87.249/sef_project";
    private static final String dbUser = "sx3";
    private static final String dbPassword ="Q2@@wertyuiop";

    private JPanel LoginPanel;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JButton btnCancel;
    private JCheckBox libraryCheckBox;
    private static final String dbURL = "jdbc:mysql://25.19.87.249/sef_project";
    private static final String dbUser = "sx3";
    private static final String dbPassword ="Q2@@wertyuiop";
    private static int checkBoxState = 0;

    public LoginForm(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(650,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        libraryCheckBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    checkBoxState = 1;
                else
                    checkBoxState = 0;
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                boolean canLogin = getUser(email,password);
                if (checkBoxState == 1)
                {
                    Library root =  isRoot(email, password);
                    if (root != null)
                    {
                        System.out.println("Logged as root");
                        dispose();
                        AdminForm adminForm = new AdminForm(LoginForm.this, root);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(LoginForm.this,
                                "Invalid email or password",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (canLogin)
                {
                    System.out.println("Logged as user");
                    dispose();
                    UserForm userForm = new UserForm(LoginForm.this);
                }
                else
                {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Invalid email or password",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm = new RegistrationForm(LoginForm.this);
            }
        });

        setVisible(true);
    }

    private Library isRoot(String email, String password)
    {
        try
        {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "SELECT * FROM Libraries WHERE email=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && password.equals("root"))
            {
                return new Library(rs.getString("id"), rs.getString("name"), rs.getString("address"),
                        rs.getString("phone"), rs.getString("email"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private boolean getUser(String email, String password) {

        boolean canLogin = false;

        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "SELECT email, password FROM Users WHERE email=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();;

            if (rs.next())
            {
                if (PasswordUtil.checkPassword(password, rs.getString("password")))
                    canLogin = true;
            }

            st.close();
            conn.close();

        }catch ( Exception e ) {
            e.printStackTrace();
        }

        return canLogin;
    }

    public static void main(String[] args) {

        LoginForm loginForm = new LoginForm(null);
    }
}
