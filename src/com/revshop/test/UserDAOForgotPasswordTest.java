package com.revshop.test;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.revshop.dao.UserDAO;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOForgotPasswordTest {

    @Mock
    private UserDAO userDAO;

    @Test
    public void testForgotPasswordSuccess() throws Exception {

        when(userDAO.forgotPassword(
                "forgot@gmail.com",
                "pet name",
                "tom",
                "newpass123"
        )).thenReturn(true);

        boolean result = userDAO.forgotPassword(
                "forgot@gmail.com",
                "pet name",
                "tom",
                "newpass123"
        );

        assertTrue(result);
    }
}