
package com.revshop.model;

public class Review {

    private int reviewId;
    private int productId;
    private int userId;
    private int rating;
    private String reviewComment;

    public Review(int reviewId,
    		int productId,
    		int userId,
            int rating, 
            String reviewComment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.reviewComment = reviewComment;
    }

    public int getReviewId() { return reviewId; }
    public int getProductId() { return productId; }
    public int getUserId() { return userId; }
    public int getRating() { return rating; }
    public String getReviewComment() { return reviewComment; }
}