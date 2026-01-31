
package com.revshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revshop.model.Review;
import com.revshop.util.DBConnection;

public class ReviewDAO {

    // ADD REVIEW
    public void addReview(Review r) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            String sql =
                "INSERT INTO reviews " +
                "(review_id, product_id, user_id, rating, review_comment) " +
                "VALUES (?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, nextId());
            ps.setInt(2, r.getProductId());
            ps.setInt(3, r.getUserId());
            ps.setInt(4, r.getRating());
            ps.setString(5, r.getReviewComment());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // GET ALL REVIEWS
    public List<Review> getAll() {

        List<Review> list = new ArrayList<Review>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM reviews");
            rs = ps.executeQuery();

            while (rs.next()) {
                Review r = new Review(
                    rs.getInt("review_id"),
                    rs.getInt("product_id"),
                    rs.getInt("user_id"),
                    rs.getInt("rating"),
                    rs.getString("review_comment")
                );
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // GET REVIEWS BY PRODUCT ID
    public List<Review> getByProductId(int productId) {

        List<Review> list = new ArrayList<Review>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(
                "SELECT * FROM reviews WHERE product_id = ?"
            );
            ps.setInt(1, productId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Review r = new Review(
                    rs.getInt("review_id"),
                    rs.getInt("product_id"),
                    rs.getInt("user_id"),
                    rs.getInt("rating"),
                    rs.getString("review_comment")
                );
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // NEXT REVIEW ID
    private int nextId() {

        int id = 1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(
                "SELECT NVL(MAX(review_id),0) + 1 FROM reviews"
            );
            rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return id;
    }
}