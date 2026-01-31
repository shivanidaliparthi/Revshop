

package com.revshop.model;

import java.util.Date;

public class Order {

    private int orderId;
    private int buyerId;
    private double totalAmount;
    private Date orderDate;

    public Order(int orderId, int buyerId, double totalAmount, Date orderDate) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

    public int getOrderId() { return orderId; }
    public int getBuyerId() { return buyerId; }
    public double getTotalAmount() { return totalAmount; }
    public Date getOrderDate() { return orderDate; }
}