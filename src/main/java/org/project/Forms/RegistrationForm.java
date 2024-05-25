package org.project.Forms;

import org.project.DbContext.Interfaces.IUserRepo;
import org.project.DbContext.Repos.UserRepo;
import org.project.Services.PasswordUtil;
import org.project.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private IUserRepo _userRepo;

    public RegistrationForm(JDialog parent, IUserRepo userRepo) {

        super(parent);
        setTitle("Registration Form");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(500, 550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        _userRepo = userRepo;

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

    boolean registered;
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

            User user = new User(firstName, lastName, email, address, phone, password, "0");

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

           registered = _userRepo.addNewUser(user,encryptedPassword);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RegistrationForm frm = new RegistrationForm(null, new UserRepo());;
        if( frm.registered ) {
            System.out.println("Successfully registered");
        }
        else {
            System.out.println("Failed to register");
        }
    }
}