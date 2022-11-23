package edu.northeastern.team36.FinalProject;

public class Post {

    private String authorName, postID, description, title, time, game;
    private int seats;

    public Post(String postID, String authorName, String description, String title, String game,
                String time,  int seats) {
        this.postID = postID;
        this.authorName = authorName;
        this.title = title;
        this.description = description;
        this.time = time;
        this.game = game;
        this.seats = seats;

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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
