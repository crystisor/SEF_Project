package org.project.Services;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil
{
    public static String hashPassword(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword)
    {
        if (BCrypt.checkpw(password, hashedPassword))
            return true;
        return false;
    }

    public static void main(String[] args)
    {
        System.out.println(hashPassword("apple"));
        System.out.println(hashPassword("apple"));
    }
}
