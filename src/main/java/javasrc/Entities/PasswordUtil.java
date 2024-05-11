package javasrc.Entities;

import org.mindrot.jbcrypt.BCrypt;

import javafx.util.Pair;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

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
