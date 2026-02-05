package com.revshop.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.revshop.exceptions.AuthenticationException;
import com.revshop.exceptions.DatabaseOperationException;
import com.revshop.exceptions.DuplicateUserException;
import com.revshop.model.User;
import com.revshop.util.DBConnection;
public class UserDAO {

    private static final Logger logger =
            Logger.getLogger(UserDAO.class);

    public boolean register(User user)
            throws DuplicateUserException, DatabaseOperationException {

        logger.info("Register request for email: " + user.getEmail());

        Connection con = null;
        PreparedStatement checkPs = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String checkSql =
                "SELECT 1 FROM USERS WHERE LOWER(email)=LOWER(?)";
            checkPs = con.prepareStatement(checkSql);
            checkPs.setString(1, user.getEmail());
            rs = checkPs.executeQuery();

            if (rs.next()) {
                logger.warn("Duplicate registration attempt: " + user.getEmail());
                throw new DuplicateUserException("Email already exists");
            }

            String insertSql =
                "INSERT INTO USERS (USER_ID, NAME, EMAIL, PASSWORD, ROLE, QUESTION, ANSWER) " +
                "VALUES (USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";

            ps = con.prepareStatement(insertSql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getQuestion());
            ps.setString(6, user.getAnswer());
            ps.executeUpdate();

            logger.info("User registered successfully: " + user.getEmail());
            return true;

        } catch (SQLException e) {
            logger.error("Database error during registration", e);
            throw new DatabaseOperationException("Database error", e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (checkPs != null) checkPs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
    public User login(String email, String password)
            throws AuthenticationException, DatabaseOperationException {

        logger.info("Login attempt for email: " + email);

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT USER_ID, NAME, EMAIL, ROLE, QUESTION, ANSWER " +
                "FROM USERS WHERE LOWER(email)=LOWER(?) AND password=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Login successful: " + email);
                User user = new User();
                user.setId(rs.getInt("USER_ID"));
                user.setName(rs.getString("NAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setRole(rs.getString("ROLE"));
                user.setQuestion(rs.getString("QUESTION"));
                user.setAnswer(rs.getString("ANSWER"));
                return user;
            }

            logger.warn("Invalid login attempt: " + email);
            throw new AuthenticationException("Invalid email or password");

        } catch (SQLException e) {
            logger.error("Database error during login", e);
            throw new DatabaseOperationException("Database error", e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
    public boolean forgotPassword(String email,
                                  String question,
                                  String answer,
                                  String newPassword)
            throws DatabaseOperationException {

        logger.info("Forgot password request for email: " + email);

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "UPDATE USERS SET password=? " +
                "WHERE LOWER(email)=LOWER(?) AND question=? AND answer=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, email);
            ps.setString(3, question);
            ps.setString(4, answer);

            boolean updated = ps.executeUpdate() > 0;

            if (updated) {
                logger.info("Password updated successfully for email: " + email);
            } else {
                logger.warn("Password update failed (details mismatch) for email: " + email);
            }

            return updated;

        } catch (SQLException e) {
            logger.error("Database error during password reset", e);
            throw new DatabaseOperationException("Database error", e);
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}