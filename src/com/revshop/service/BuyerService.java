package com.revshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revshop.dao.*;
import com.revshop.model.*;

public class BuyerService {

    private ProductDAO productDAO = new ProductDAO();
    private OrderDAO orderDAO = new OrderDAO();
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
                    Order order = new Order(
                        orderDAO.nextOrderId(), buyer.getId(), cart);
                    orderDAO.save(order);
                    notify.notifyBuyer("Order placed successfully!");
                    notify.notifySeller("New order received!");
                    cart.clear();
                    break;

                case 4:
                    System.out.print("Product Id: ");
                    int pid = sc.nextInt();
                    System.out.print("Rating: ");
                    int r = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Comment: ");
                    String c = sc.nextLine();
                    reviewDAO.addReview(
                        new Review(pid, buyer.getId(), r, c));
                    break;
            }
        } while (ch != 5);
    }

    // ✅ ADD THIS METHOD HERE (outside buyerMenu)
    public int getCartSize() {
        return cart.size();
    }
}







