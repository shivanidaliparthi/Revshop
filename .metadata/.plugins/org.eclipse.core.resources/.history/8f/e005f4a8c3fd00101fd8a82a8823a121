package com.revshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revshop.model.User;
import com.revshop.util.DBConnection;

public class UserDAO {

    public boolean register(User user) {

        Connection con = null;
        PreparedStatement checkPs = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String checkSql =
            "SELECT 1 FROM USERS WHERE LOWER(email) = LOWER(?)";

        String insertSql =
            "INSERT INTO USERS (USER_ID, NAME, EMAIL, PASSWORD, ROLE, QUESTION, ANSWER) " +
            "VALUES (USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try {
            con = DBConnection.getConnection();

            // check duplicate email
            checkPs = con.prepareStatement(checkSql);
            checkPs.setString(1, user.getEmail().trim());
            rs = checkPs.executeQuery();

            if (rs.next()) {
                return false;
            }

            // insert user
            ps = con.prepareStatement(insertSql);
            ps.setString(1, user.getName().trim());
            ps.setString(2, user.getEmail().trim());
            ps.setString(3, user.getPassword().trim());
            ps.setString(4, user.getRole().trim());
            ps.setString(5, user.getQuestion().trim());
            ps.setString(6, user.getAnswer().trim());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (checkPs != null) checkPs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    // ================= LOGIN (FIXED) =================
    public User login(String email, String password) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
            "SELECT USER_ID, NAME, EMAIL, ROLE, QUESTION, ANSWER " +
            "FROM USERS WHERE LOWER(email)=LOWER(?) AND password=?";

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, email.trim());
            ps.setString(2, password.trim());

            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("USER_ID"));      // ⭐ important
                user.setName(rs.getString("NAME"));   // ⭐ important
                user.setEmail(rs.getString("EMAIL"));
                user.setRole(rs.getString("ROLE"));   // ⭐ MOST important
                user.setQuestion(rs.getString("QUESTION"));
                user.setAnswer(rs.getString("ANSWER"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return null;
    }

    public boolean forgotPassword(String email,
                                  String question,
                                  String answer,
                                  String newPassword) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql =
            "UPDATE USERS SET password=? " +
            "WHERE LOWER(email)=LOWER(?) AND question=? AND answer=?";

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, newPassword.trim());
            ps.setString(2, email.trim());
            ps.setString(3, question.trim());
            ps.setString(4, answer.trim());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}