package org.project.Forms;

import org.project.Entities.PasswordUtil;
import org.project.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationForm extends JDialog{
    private JPasswordField pfPassword;
    private JTextField tfPhone;
    private JTextField tfEmail;
    private JTextField tfLastName;
    private JTextField tfFirstName;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel RegisterPanel;
    private JTextField tfAddress;
    private static final String dbURL = "jdbc:mysql://127.0.0.1/sef_project";
    private static final String dbUser = "cristi";
    private static final String dbPassword ="qwertyuiop";

    public RegistrationForm(JDialog parent) {
        super(parent);
        setTitle("Registration Form");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(500, 550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
                dispose();
                //LoginForm loginFormForm = new LoginForm(LoginForm.this);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void registerUser()
    {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String email = tfEmail.getText();
        String address = tfAddress.getText();
        String phone = tfPhone.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());
        String encryptedPassword;

        try
        {
            encryptedPassword = PasswordUtil.hashPassword(password);

            User user = new User(firstName, lastName, email, address, phone, password);

            if (user.isValidUser() != 0)
            {
                JOptionPane.showMessageDialog(this, "Please enter valid data " + user.isValidUser(), "Try again", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword))
            {
                JOptionPane.showMessageDialog(this,
                        "Passwords do not match",
                        "Try again", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean registered = addUserToDatabase(user, encryptedPassword);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    boolean hasRegisteredUser;
    private boolean addUserToDatabase(User user, String encryptedPassword) {

        hasRegisteredUser = false;
        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            Statement st = conn.createStatement();
            String query = "INSERT INTO Users (first_name,last_name,email,address,phone_number,password)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement insertUserStatement = conn.prepareStatement(query);
            insertUserStatement.setString(1,user.getFirstName());
            insertUserStatement.setString(2,user.getLastName());
            insertUserStatement.setString(3,user.getEmail());
            insertUserStatement.setString(4,user.getAddress());
            insertUserStatement.setString(5,user.getPhone());
            insertUserStatement.setString(6,encryptedPassword);
            int addedRowsUser = insertUserStatement.executeUpdate();

            if (addedRowsUser > 0)
            {
                hasRegisteredUser = true;
            }
            st.close();
            conn.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return hasRegisteredUser;
    }
    public static void main(String[] args) {
        RegistrationForm frm = new RegistrationForm(null);
        boolean registered = frm.hasRegisteredUser;

        if( registered ) {
            System.out.println("Successfully registered");
        }
        else {
            System.out.println("Failed to register");
        }
    }
}