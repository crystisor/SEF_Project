package javasrc.Forms;

import com.mysql.cj.log.Log;
import javasrc.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog{
    private JPanel LoginPanel;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JButton btnCancel;

    public LoginForm(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(650,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String email = tfEmail.getText();
                    String password = String.valueOf(pfPassword.getPassword());

                    boolean canLogin = getUser(email,password);
                    boolean isRoot = isRoot(email, password);
                    if(isRoot){
                        System.out.println("Logged as root");
                        dispose();
                        //AdminForm adminForm = new AdminForm(LoginForm.this);
                    }
                    else if (canLogin){
                        System.out.println("Logged as user");
                        dispose();
                    }
                    else {
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
                dispose();
                RegistrationForm registrationForm = new RegistrationForm(LoginForm.this);
            }
        });

        setVisible(true);
    }

    private boolean isRoot(String email, String password){
        return email.equals("root") && password.equals("root");
    }
    private boolean getUser(String email, String password) {

        boolean canLogin = false;

        try {
            Connection conn = DriverManager.getConnection
                    ("jdbc:mysql://127.0.0.1:3306/sef_project", "cristi", "qwertyuiop");

            Statement st = conn.createStatement();
            String query = "SELECT * FROM Users WHERE email=? AND password=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,email);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                User user = new User(rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getString("password"));
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