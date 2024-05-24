package org.project.DbContext.Repos;

import org.project.DbContext.DbConfig;
import org.project.DbContext.Interfaces.IUserRepo;
import org.project.Entities.Library;
import org.project.Entities.User;
import org.project.Services.PasswordUtil;

import java.sql.*;

public class UserRepo extends DbConfig implements IUserRepo {

    public UserRepo()  {
    }

    public boolean addNewUser(User user, String encryptedPassword) {

        boolean  hasRegisteredUser = false;
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

    public User getUser(String email, String password) {

        User user = null;

        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "SELECT * FROM Users WHERE email=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();;

            if (rs.next())
            {
                if (PasswordUtil.checkPassword(password, rs.getString("password"))) {
                    user = new User(rs.getString("first_name"), rs.getString("last_name"),
                            rs.getString("email"), rs.getString("address"),rs.getString("phone_number"),
                            rs.getString("password"));
                    user.setUserId(rs.getString("id"));
                }
            }

            st.close();
            conn.close();

        }catch ( Exception e ) {
            e.printStackTrace();
        }

        return user;
    }

    public Library isLibrary(String email, String password)
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

    public String countUsers(){

        int userCount = -1;
        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            Statement st = conn.createStatement();
            String query = "SELECT COUNT(*) AS userCount FROM Users";

            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                userCount = rs.getInt("userCount");
                System.out.println("Number of users: " + userCount);
            } else {
                System.out.println("No users found");
            }

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ("Active users: " + userCount);
    }
}
