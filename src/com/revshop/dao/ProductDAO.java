

package com.revshop.dao;

import java.util.ArrayList;
import java.util.List;
import com.revshop.model.Product;

public class ProductDAO {

    private static List<Product> products = new ArrayList<Product>();
    private static int pid = 0;

    public void addProduct(Product p) {
        products.add(p);
    }

    public List<Product> getAll() {
        return products;
    }

    public Product getById(int id) {
        for (Product p : products)
            if (p.getId() == id)
                return p;
        return null;
    }

    public int nextId() {
        return ++pid;
    }
}


