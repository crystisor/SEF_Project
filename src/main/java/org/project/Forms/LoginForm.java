package org.project.Forms;

import org.project.DbContext.Interfaces.IUserRepo;
import org.project.DbContext.Repos.BookRepo;
import org.project.DbContext.Repos.LibraryRepo;
import org.project.DbContext.Repos.OrderRepo;
import org.project.DbContext.Repos.UserRepo;
import org.project.Entities.Library;
import org.project.Entities.User;
import org.project.Forms.Admin.AdminForm;
import org.project.Forms.User.UserForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoginForm extends JDialog{

    private JPanel LoginPanel;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JButton btnCancel;
    private JCheckBox libraryCheckBox;
    private static int checkBoxState = 0;
    IUserRepo _userRepo;

    public LoginForm(JFrame parent, IUserRepo userRepo){
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(650,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        _userRepo = userRepo;

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

                User user = _userRepo.getUser(email,password);

                if (checkBoxState == 1)
                {
                    Library root =  _userRepo.isLibrary(email, password);
                    if (root != null)
                    {
                        System.out.println("Logged as root");
                        dispose();
                        AdminForm adminForm = new AdminForm(LoginForm.this, root, new UserRepo(), new BookRepo(), new OrderRepo());
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(LoginForm.this,
                                "Invalid email or password",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if ( user != null )
                {
                    System.out.println("Logged as user");
                    dispose();
                    UserForm userForm = new UserForm(LoginForm.this,user, new BookRepo(), new LibraryRepo() );
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
                RegistrationForm registrationForm = new RegistrationForm(LoginForm.this, new UserRepo());
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {

        LoginForm loginForm = new LoginForm(null, new UserRepo());
    }
}
