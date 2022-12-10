package edu.northeastern.team36.FinalProject;

public class Review {
    private String userName;
    private String reviewDetail;

    public Review(String userName, String reviewDetail) {
        this.userName = userName;
        this.reviewDetail = reviewDetail;


    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReviewDetail() {
        return reviewDetail;
    }

    public void setReviewDetail(String reviewDetail) {
        this.reviewDetail = reviewDetail;
    }
}
