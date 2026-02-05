package com.revshop.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.revshop.model.Product;
import com.revshop.util.DBConnection;
public class ProductDAO {

    public boolean addProduct(Product p) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
        
            String sql =
                "INSERT INTO PRODUCTS " +
                "(PRODUCT_ID, NAME, CATEGORY, MRP, DISCOUNT_PRICE, DISCOUNT_PERCENT, STOCK) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
         
            double discountPercent = p.getDiscount();
            double discountPrice = p.getMrp() * discountPercent / 100;

            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getCategory());
            ps.setDouble(4, p.getMrp());
            ps.setDouble(5, discountPrice);     // ✅ DISCOUNT_PRICE
            ps.setDouble(6, discountPercent);   // ✅ DISCOUNT_PERCENT
            ps.setInt(7, p.getStock());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // VIEW ALL PRODUCTS
    public List<Product> getAll() {

        List<Product> list = new ArrayList<Product>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM PRODUCTS");
            rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("PRODUCT_ID"),
                    rs.getString("NAME"),
                    rs.getString("CATEGORY"),
                    rs.getDouble("MRP"),
                    rs.getDouble("DISCOUNT_PERCENT"), // 🔧 FIX
                    rs.getInt("STOCK")
                );
                list.add(p);
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

    // NEXT PRODUCT ID
    public int nextId() {

        int id = 1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(
                "SELECT NVL(MAX(PRODUCT_ID),0) + 1 FROM PRODUCTS"
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

    // GET PRODUCT BY ID
    public Product getById(int id) {

        Product p = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(
                "SELECT * FROM PRODUCTS WHERE PRODUCT_ID = ?"
            );
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                p = new Product(
                    rs.getInt("PRODUCT_ID"),
                    rs.getString("NAME"),
                    rs.getString("CATEGORY"),
                    rs.getDouble("MRP"),
                    rs.getDouble("DISCOUNT_PERCENT"), // 🔧 FIX
                    rs.getInt("STOCK")
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
        return p;
    }
}