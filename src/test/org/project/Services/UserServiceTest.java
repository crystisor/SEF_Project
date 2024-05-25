package org.project.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Entities.User;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest
{
    
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }
    
    @Test
    void isFirstNameValid()
    {
        User user = new User("aaaa", "aaaaaaa", "corina@yahoo.com", "strada voinicilor 7" ,"7878787878",
                "klkl", "klkl");
        
        assertEquals(false, userService.isFirstNameValid(user.getFirstName()));
    }

    @Test
    void isLastNameValid()
    {
        User user = new User("aaaa", "aa##aaaaa", "corina@yahoo.com", "strada voinicilor 7" ,"7878787878",
                "klkl", "klkl");
        
        assertEquals(false, userService.isLastNameValid(user.getLastName()));
    }

    @Test
    void isEmailValid()
    {
        User user = new User("aaaa", "aaaaaaa", "cor{{{{{[ina@yahoo.com", "strada voinicilor 7" ,"7878787878",
                "klkl", "klkl");
        
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

    @Test
    void testIsFirstNameValid() {
 
        assertTrue(userService.isFirstNameValid("JohnDoe"));
        assertTrue(userService.isFirstNameValid("john_doe123"));
        assertTrue(userService.isFirstNameValid("john-doe"));
        
        assertFalse(userService.isFirstNameValid("John Doe")); // Space not allowed
        assertFalse(userService.isFirstNameValid("john.doe")); // Period not allowed
        assertFalse(userService.isFirstNameValid("john@doe")); // Special character not allowed
    }

    @Test
    void testIsLastNameValid() {
        assertTrue(userService.isLastNameValid("Doe"));
        assertTrue(userService.isLastNameValid("doe123"));
        assertTrue(userService.isLastNameValid("doe-doe"));

  
        assertFalse(userService.isLastNameValid("Doe ")); // Space not allowed
        assertFalse(userService.isLastNameValid("doe.")); // Period not allowed
        assertFalse(userService.isLastNameValid("doe@doe")); // Special character not allowed
    }

    @Test
    void testIsEmailValid() {
        
        assertTrue(userService.isEmailValid("john.doe@example.com"));
        assertTrue(userService.isEmailValid("john_doe@example.com"));
        assertTrue(userService.isEmailValid("john.doe123@example.co.uk"));
        
        assertFalse(userService.isEmailValid("john.doe@example")); // No top-level domain
        assertFalse(userService.isEmailValid("john.doe@.com")); // No domain name
        assertFalse(userService.isEmailValid("john.doe@com")); // No period in domain
    }

    @Test
    void testIsPhoneValid() {
        // Valid cases
        assertTrue(userService.isPhoneValid("1234567890"));
        assertTrue(userService.isPhoneValid("123 456 7890"));

        // Invalid cases
        assertFalse(userService.isPhoneValid("12345")); // Too short
        assertFalse(userService.isPhoneValid("12345678901")); // Too long
        assertFalse(userService.isPhoneValid("123-456-7890")); // Hyphens not allowed
    }

}