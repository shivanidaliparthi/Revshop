package com.revshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.revshop.model.CartItem;
import com.revshop.model.Product;
import com.revshop.util.DBConnection;

public class OrderItemDAO {

    public static void addOrderItem(int orderId, CartItem item) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO order_items (order_id, product_id, quantity, price) " +
                "VALUES (?, ?, ?, ?)";

            ps = con.prepareStatement(sql);

            Product product = item.getProduct();

            ps.setInt(1, orderId);
            ps.setInt(2, product.getId());              // from Product
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, product.getSellingPrice()); // per-item price

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
}







































