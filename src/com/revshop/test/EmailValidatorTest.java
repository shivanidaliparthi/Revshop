package com.revshop.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.revshop.util.EmailValidator;
public class EmailValidatorTest {

    @Test
    public void testValidEmails() {
        assertTrue(EmailValidator.isValid("test@gmail.com"));
        assertTrue(EmailValidator.isValid("abc_123@yahoo.in"));
    }
    @Test
    public void testInvalidEmails() {
        assertFalse(EmailValidator.isValid("testgmail.com"));
        assertFalse(EmailValidator.isValid("@gmail.com"));
        assertFalse(EmailValidator.isValid("test@"));
        assertFalse(EmailValidator.isValid(null));
    }
}
