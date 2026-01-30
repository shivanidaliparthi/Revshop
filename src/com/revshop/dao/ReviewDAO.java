package com.revshop.dao;

import java.util.ArrayList;
import java.util.List;
import com.revshop.model.Review;

public class ReviewDAO {

    private static List<Review> reviews = new ArrayList<Review>();

    public void addReview(Review r) {
        reviews.add(r);
    }

    public List<Review> getAll() {
        return reviews;
    }
}