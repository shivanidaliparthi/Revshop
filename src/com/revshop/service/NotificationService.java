

package com.revshop.service;

public class NotificationService {

    
    public void notifyBuyer(String message) {
        System.out.println("[BUYER NOTIFICATION] " + message);
    }

  
    public void notifySeller(String message) {
        System.out.println("[SELLER NOTIFICATION] " + message);
    }
}