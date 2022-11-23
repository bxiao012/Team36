package edu.northeastern.team36.FinalProject;

public class ModelPost {

    private String authorName, postID, description, title, time;

    public ModelPost(String authorName, String postID, String description, String title) {
        this.authorName = authorName;
        this.postID = postID;
        this.title = title;
        this.description = description;
        this.time = time;
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

    public void setDescription(String published) {
        this.description = published;
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

}
