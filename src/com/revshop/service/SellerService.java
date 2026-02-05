package com.revshop.service;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import com.revshop.dao.ProductDAO;
import com.revshop.dao.ReviewDAO;
import com.revshop.model.Product;
import com.revshop.model.Review;
public class SellerService {

    private static final Logger logger = Logger.getLogger(SellerService.class);

    private ProductDAO productDAO = new ProductDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();
    private NotificationService notify = new NotificationService();
    private static final int STOCK_THRESHOLD = 5;

    public void sellerMenu(Scanner sc) {

        logger.info("Seller menu opened");

        int ch;
        do {
            ch = sc.nextInt();
            try {
                switch (ch) {
                    case 1:
                        addProduct(sc);
                        break;
                    case 2:
                        viewProducts();
                        break;
                    case 3:
                        viewReviews();
                        break;
                    case 4:
                        checkStockThreshold();
                        break;
                }
            } catch (Exception e) {
                logger.error("Error in seller menu", e);
            }
        } while (ch != 5);
    }

    private void addProduct(Scanner sc) {
        Product p = new Product(
            productDAO.nextId(),
            sc.next(),
            sc.next(),
            sc.nextDouble(),
            sc.nextDouble(),
            sc.nextInt()
        );
        productDAO.addProduct(p);
        logger.info("Product added: " + p.getName());

        if (p.getStock() <= STOCK_THRESHOLD) {
            logger.warn("Low stock for product: " + p.getName());
            notify.notifySeller("LOW STOCK ALERT");
        }
    }

    private void viewProducts() {
        List<Product> products = productDAO.getAll();
        logger.info("Viewing products count=" + products.size());
    }

    private void viewReviews() {
        List<Review> reviews = reviewDAO.getAll();
        logger.info("Viewing reviews count=" + reviews.size());
    }

    private void checkStockThreshold() {
        for (Product p : productDAO.getAll()) {
            if (p.getStock() <= STOCK_THRESHOLD) {
                logger.warn("Low stock: " + p.getName());
            }
        }
    }
}