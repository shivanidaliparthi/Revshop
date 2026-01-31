package com.revshop.test;

import static org.junit.Assert.*;
import org.junit.Test;

import com.revshop.dao.UserDAO;
import com.revshop.model.User;

public class UserDAOForgotPasswordTest {

    @Test
    public void testForgotPasswordSuccess() {

        UserDAO dao = new UserDAO();

        
        User user = new User();
        user.setName("Forgot User");
        user.setEmail("forgot@gmail.com");
        user.setPassword("oldpass");
        user.setRole("BUYER");
        user.setQuestion("pet name");
        user.setAnswer("tom");

        dao.register(user);

      
        boolean result = dao.forgotPassword(
                "forgot@gmail.com",
                "pet name",
                "tom",
                "newpass123"
        );

    
        assertTrue(result);
    }
}