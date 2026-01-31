package com.revshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revshop.dao.*;
import com.revshop.model.*;

public class BuyerService {

    private ProductDAO productDAO = new ProductDAO();
    
    private ReviewDAO reviewDAO = new ReviewDAO();
    private NotificationService notify = new NotificationService();

    private List<CartItem> cart = new ArrayList<CartItem>();

    public void buyerMenu(User buyer, Scanner sc) {

        int ch;
        do {
            System.out.println("1.View Products 2.Add Cart 3.Place Order 4.Review 5.Exit");
            ch = sc.nextInt();

            switch (ch) {
                case 1:
                    for (Product p : productDAO.getAll()) {
                        System.out.println(
                            p.getName() + " MRP:" + p.getMrp()
                            + " Discount:" + p.getDiscount()
                            + " Price:" + p.getSellingPrice());
                    }
                    break;

                case 2:
                    System.out.print("Product Id: ");
                    int id = sc.nextInt();
                    System.out.print("Qty: ");
                    int q = sc.nextInt();
                    cart.add(new CartItem(productDAO.getById(id), q));
                    break;

                case 3:
                	       
                	OrderDAO orderDAO = new OrderDAO();

                	// 1️⃣ calculate total
                	double totalAmount = 0;
                	for (CartItem item : cart) {
                	    totalAmount += item.getTotalPrice() * item.getQuantity();
                	}

                	// 2️⃣ save order (orders table)
                	int orderId = orderDAO.createOrder(buyer.getId(), totalAmount);

                	System.out.println("Order placed successfully. Order ID: " + orderId);

                	// 3️⃣ save order items (order_items table)
                	for (CartItem item : cart) {
                	    OrderItemDAO.addOrderItem(orderId, item);
                	}

                	// 4️⃣ notify + clear
                	notify.notifyBuyer("Order placed successfully!");
                	cart.clear();
                	break;
              
//                    		OrderDAO orderDAO = new OrderDAO();
//
//                    		// calculate total amount from cart
//                    		double totalAmount = cart.getTotalAmount(); // or your own logic
//
//                    		int orderId = orderDAO.createOrder(buyer.getId(), totalAmount);
//
//                    		System.out.println("Order placed successfully. Order ID: " + orderId);
//                    		for (CartItem item : cart) {
//                    	        // assuming you already have OrderItemDAO
//                    	        OrderDAO.addOrderItem(orderId, item);
//                    	    }
//                    notify.notifyBuyer("Order placed successfully!");
//                  
//                    cart.clear();
                 

                case 4:
                    System.out.print("Product Id: ");
                    int pid = sc.nextInt();
                    System.out.print("Rating: ");
                    int r = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Comment: ");
                    String c = sc.nextLine();
                    Review review = new Review(
                            0,                  // reviewId (dummy, DAO generates real ID)
                            pid,
                            buyer.getId(),      // userId
                            r,
                            c
                        );
                    reviewDAO.addReview(review);
                    break;
            }
        } while (ch != 5);
    }

    public int getCartSize() {
        return cart.size();
    }
}

