package edu.northeastern.team36.FinalProject;

public class Review {
    private String userID;
    private String reviewDetail;
    private double rating;

    public Review(String userID, String reviewDetail, Double rating) {
        this.userID = userID;
        this.reviewDetail = reviewDetail;
        this.rating = rating;


    }


    public String getReviewDetail() {
        return reviewDetail;
    }

    public void setReviewDetail(String reviewDetail) {
        this.reviewDetail = reviewDetail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
