package edu.northeastern.team36.FinalProject;

public class Review {
    private String userID;
    private String reviewDetail;

    public Review(String userID, String reviewDetail) {
        this.userID = userID;
        this.reviewDetail = reviewDetail;


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
}
