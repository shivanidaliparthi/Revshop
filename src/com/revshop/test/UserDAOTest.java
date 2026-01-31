package com.revshop.test;



import static org.junit.Assert.*;
import org.junit.Test;

import com.revshop.dao.UserDAO;
import com.revshop.model.User;

public class UserDAOTest {

    @Test
    public void testDuplicateEmailRegistration() {

        UserDAO dao = new UserDAO();

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@gmail.com");
        user.setPassword("test123");
        user.setRole("BUYER");
        user.setQuestion("pet name");
        user.setAnswer("tom");

        boolean first = dao.register(user);
        boolean second = dao.register(user);

        assertTrue(first);      // first insert works
        assertFalse(second);    // duplicate should fail
    }
}