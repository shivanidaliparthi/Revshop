


package com.revshop.dao;

import java.util.ArrayList;
import java.util.List;
import com.revshop.model.Order;

public class OrderDAO {

    private static List<Order> orders = new ArrayList<Order>();
    private static int oid = 0;

    public void save(Order o) {
        orders.add(o);
    }

    public int nextOrderId() {
        return ++oid;
    }

    public List<Order> getAllOrders() {
        return orders;
    }
}