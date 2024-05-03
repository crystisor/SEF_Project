package javasrc.Forms;

import javasrc.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

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

    private void registerUser() {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String email = tfEmail.getText();
        String address = tfAddress.getText();
        String phone = tfPhone.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());

        User user = new User(firstName,lastName,email,address,phone,password);

        if( user.isValidUser() != 0 ) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid data " + user.isValidUser(),
                    "Try again",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if( !password.equals(confirmPassword) ) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match",
                    "Try again",JOptionPane.ERROR_MESSAGE);
            return;
        }

       boolean registered = addUserToDatabase(user);
    }

    boolean hasRegisteredUser;
    private boolean addUserToDatabase(User user) {

        hasRegisteredUser = false;
        try {
            Connection conn = DriverManager.getConnection
                    ("jdbc:mysql://25.19.87.249:3306/sef_project", "sx3", "Q2@@wertyuiop");
            Statement st = conn.createStatement();
            String query = "INSERT INTO Users (first_name,last_name,email,address,phone_number,password)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setString(4,user.getAddress());
            preparedStatement.setString(5,user.getPhone());
            preparedStatement.setString(6,user.getPassword());

            int addedRows = preparedStatement.executeUpdate();
            if( addedRows > 0 ) {
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
