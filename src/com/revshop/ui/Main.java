package com.revshop.ui;
import java.util.Scanner;
import org.apache.log4j.Logger;
import com.revshop.dao.UserDAO;
import com.revshop.exceptions.AuthenticationException;
import com.revshop.exceptions.DatabaseOperationException;
import com.revshop.exceptions.DuplicateUserException;
import com.revshop.model.User;
import com.revshop.service.BuyerService;
import com.revshop.service.SellerService;
public class Main {

    private static final Logger logger =
            Logger.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("=== RevShop Application Started ===");

        Scanner sc = new Scanner(System.in);
        UserDAO dao = new UserDAO();
        BuyerService buyer = new BuyerService();
        SellerService seller = new SellerService();

        int ch;
        do {
            System.out.println("1.Register 2.Login 3.Forgot 4.Exit");
            ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    try {
                        logger.info("User selected REGISTER option");

                        User u = new User();

                        System.out.println("Name:");
                        u.setName(sc.nextLine());

                        System.out.println("Email:");
                        u.setEmail(sc.nextLine());

                        System.out.println("Password:");
                        u.setPassword(sc.nextLine());

                        System.out.println("Role:");
                        u.setRole(sc.nextLine());

                        System.out.println("Question:");
                        u.setQuestion(sc.nextLine());

                        System.out.println("Answer:");
                        u.setAnswer(sc.nextLine());

                        boolean registered = dao.register(u);

                        if (registered) {
                            logger.info("User registered successfully: " + u.getEmail());
                            System.out.println("✅ Registration successful");
                        }

                    } catch (DuplicateUserException e) {
                        logger.warn("Duplicate registration attempt", e);
                        System.out.println("❌ " + e.getMessage());

                    } catch (DatabaseOperationException e) {
                        logger.error("Database error during registration", e);
                        System.out.println("❌ Database error. Please try again later.");
                    }
                    break;
                case 2:
                    try {
                        logger.info("User selected LOGIN option");

                        System.out.println("Email:");
                        String e = sc.nextLine();

                        System.out.println("Password:");
                        String p = sc.nextLine();

                        User user = dao.login(e, p);

                        logger.info("Login successful for user: " + e);
                        System.out.println("✅ Login successful. Welcome " + user.getName());

                        if ("BUYER".equalsIgnoreCase(user.getRole())) {
                            logger.info("Redirecting to BuyerService for userId: " + user.getId());
                            buyer.buyerMenu(user, sc);
                        } else {
                            logger.info("Redirecting to SellerService");
                            seller.sellerMenu(sc);
                        }

                    } catch (AuthenticationException e) {
                        logger.warn("Invalid login attempt", e);
                        System.out.println("❌ " + e.getMessage());

                    } catch (DatabaseOperationException e) {
                        logger.error("Database error during login", e);
                        System.out.println("❌ Database error. Please try again later.");
                    }
                    break;
                case 3:
                    try {
                        logger.info("User selected FORGOT PASSWORD option");

                        System.out.println("Email:");
                        String em = sc.nextLine();

                        System.out.println("Question:");
                        String q = sc.nextLine();

                        System.out.println("Answer:");
                        String a = sc.nextLine();

                        System.out.println("New Password:");
                        String np = sc.nextLine();

                        boolean reset = dao.forgotPassword(em, q, a, np);

                        if (reset) {
                            logger.info("Password reset successful for email: " + em);
                            System.out.println("✅ Password updated successfully. Please login.");
                        } else {
                            logger.warn("Password reset failed due to mismatch for email: " + em);
                            System.out.println("❌ Details mismatch. Password not updated.");
                        }

                    } catch (DatabaseOperationException e) {
                        logger.error("Database error during password reset", e);
                        System.out.println("❌ Database error. Please try again later.");
                    }
                    break;
            }

        } while (ch != 4);

        logger.info("=== RevShop Application Closed ===");
        sc.close();
    }
}