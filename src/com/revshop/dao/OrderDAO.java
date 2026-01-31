
package com.revshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.util.Date;

import com.revshop.model.Order;
import com.revshop.util.DBConnection;

public class OrderDAO {

  
    public int createOrder(int buyerId, double totalAmount) {

        Connection con = null;
        PreparedStatement ps = null;
        int orderId = nextId();

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO orders (order_id, buyer_id, total_amount) " +
                "VALUES (?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, buyerId);
            ps.setDouble(3, totalAmount);

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
        return orderId;
    }


    public Order getOrderById(int id) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order order = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(
                "SELECT * FROM orders WHERE order_id = ?"
            );
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                order = new Order(
                    rs.getInt("order_id"),
                    rs.getInt("buyer_id"),
                    rs.getDouble("total_amount"),
                    rs.getDate("order_date")
                );
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
        return order;
    }


    private int nextId() {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 1;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(
                "SELECT NVL(MAX(order_id),0) + 1 FROM orders"
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