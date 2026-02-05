package com.revshop.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.revshop.dao.UserDAO;
import com.revshop.model.User;

public class UserDAOTest {

    @Test
    public void testUserObjectCreation() {

        UserDAO dao = new UserDAO();
        User user = new User();

        user.setName("Test User");
        user.setEmail("test@test.com");
        user.setPassword("1234");
        user.setRole("BUYER");

        assertNotNull(user);
        assertNotNull(dao);
    }
}
