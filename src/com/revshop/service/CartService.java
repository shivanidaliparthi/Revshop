package com.revshop.service;

import com.revshop.model.CartItem;
import com.revshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    private List<CartItem> cart = new ArrayList<CartItem>();

    public void addToCart(Product product, int qty) {
        for (CartItem item : cart) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + qty);
                return;
            }
        }
        cart.add(new CartItem(product, qty));
    }

    public List<CartItem> getCartItems() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }

    public double getTotal() {
        double total = 0;
        for (CartItem c : cart)
            total += c.getTotalPrice();
        return total;
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }
}