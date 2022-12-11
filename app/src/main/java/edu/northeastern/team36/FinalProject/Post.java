package edu.northeastern.team36.FinalProject;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class Post {

    private String authorName, postID, description, title, time, game, imgStr, status;
    private int seats, selected;
    private ArrayList<String> haveReviewToArray;  // list of username

    public Post(String postID, String authorName, String description, String title, String game,
                String time, String imgStr, String status, Integer seats, Integer selected,
                ArrayList<String> haveReviewToArray) {
        this.postID = postID;
        this.authorName = authorName;
        this.title = title;
        this.description = description;
        this.time = time;
        this.game = game;
        this.seats = seats;
        this.imgStr = imgStr;
        this.selected = selected;
        this.status = status;
        this.haveReviewToArray = haveReviewToArray;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getHaveReviewToArray() {
        return haveReviewToArray;
    }

    public void setHaveReviewToArray(ArrayList<String> haveReviewToArray) {
        this.haveReviewToArray = haveReviewToArray;
    }
}
