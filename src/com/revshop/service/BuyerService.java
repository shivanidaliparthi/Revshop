package com.revshop.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import com.revshop.dao.*;
import com.revshop.model.*;
public class BuyerService {

    private static final Logger logger = Logger.getLogger(BuyerService.class);

    private ProductDAO productDAO = new ProductDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();
    private NotificationService notify = new NotificationService();
    private List<CartItem> cart = new ArrayList<CartItem>();

    public void buyerMenu(User buyer, Scanner sc) {

        logger.info("Buyer menu opened for userId: " + buyer.getId());

        int ch;
        do {
            System.out.println("1.View Products 2.Add Cart 3.Place Order 4.Review 5.Exit");
            ch = sc.nextInt();

            try {
                switch (ch) {

                    case 1:
                        logger.info("Viewing products");
                        for (Product p : productDAO.getAll()) {
                            System.out.println(p.getName() + " Price:" + p.getSellingPrice());
                        }
                        break;

                    case 2:
                        int id = sc.nextInt();
                        int q = sc.nextInt();
                        cart.add(new CartItem(productDAO.getById(id), q));
                        logger.info("Product added to cart. ProductId=" + id);
                        break;

                    case 3:
                        logger.info("Placing order");
                        OrderDAO orderDAO = new OrderDAO();
                        double total = 0;
                        for (CartItem item : cart) {
                            total += item.getTotalPrice() * item.getQuantity();
                        }
                        int orderId = orderDAO.createOrder(buyer.getId(), total);
                        for (CartItem item : cart) {
                            OrderItemDAO.addOrderItem(orderId, item);
                        }
                        notify.notifyBuyer("Order placed successfully");
                        cart.clear();
                        logger.info("Order placed. OrderId=" + orderId);
                        break;

                    case 4:
                        Review review = new Review(0, sc.nextInt(), buyer.getId(),
                                sc.nextInt(), sc.nextLine());
                        reviewDAO.addReview(review);
                        logger.info("Review added by userId=" + buyer.getId());
                        break;
                }
            } catch (Exception e) {
                logger.error("Error in buyer menu", e);
            }

        } while (ch != 5);
    }

    public int getCartSize() {
        return cart.size();
    }
}