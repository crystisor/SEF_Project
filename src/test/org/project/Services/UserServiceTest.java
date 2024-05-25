package org.project.Services;

import org.junit.jupiter.api.Test;
import org.project.Entities.User;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest
{

    @Test
    void isFirstNameValid()
    {
        User user = new User("aaaa", "aaaaaaa", "corina@yahoo.com", "strada voinicilor 7" ,"7878787878",
                "klkl", "klkl");

        UserService userService = new UserService();
        assertEquals(false, userService.isFirstNameValid(user.getFirstName()));
    }

    @Test
    void isLastNameValid()
    {
        User user = new User("aaaa", "aa##aaaaa", "corina@yahoo.com", "strada voinicilor 7" ,"7878787878",
                "klkl", "klkl");

        UserService userService = new UserService();
        assertEquals(false, userService.isLastNameValid(user.getLastName()));
    }

    @Test
    void isEmailValid()
    {
        User user = new User("aaaa", "aaaaaaa", "cor{{{{{[ina@yahoo.com", "strada voinicilor 7" ,"7878787878",
                "klkl", "klkl");

        UserService userService = new UserService();
        assertEquals(false, userService.isEmailValid(user.getEmail()));
    }

    @Test
    void isPhoneValid()
    {
        User user = new User("aaaa", "aaaaaaa", "cor{{{{{[ina@yahoo.com", "strada voinicilor 7" ,"787.8787878",
                "klkl", "klkl");

        UserService userService = new UserService();
        assertEquals(false, userService.isPhoneValid(user.getPhone()));
    }
}