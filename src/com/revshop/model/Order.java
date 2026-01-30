


package com.revshop.model;

import java.util.List;

public class Order {

    private int orderId;
    private int buyerId;
    private List<CartItem> items;

    public Order(int orderId, int buyerId, List<CartItem> items) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.items = items;
    }

    public int getOrderId() { return orderId; }
    public int getBuyerId() { return buyerId; }
    public List<CartItem> getItems() { return items; }
}