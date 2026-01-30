
package com.revshop.model;

public class Product {

    private int id;
    private String name;
    private String category;
    private double mrp;
    private double discount;
    private int stock;

    public Product(int id, String name, String category,
                   double mrp, double discount, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.mrp = mrp;
        this.discount = discount;
        this.stock = stock;
    }

   
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getMrp() { return mrp; }
    public double getDiscount() { return discount; }
    public int getStock() { return stock; }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public double getSellingPrice() {
        return mrp - (mrp * discount / 100);
    }


    public double getPrice() {
        return getSellingPrice();
    }
}