package edu.northeastern.team36;

public class RTDBMessage {


    public String fromUsername;
    public String toUsername;
    public String timestamp;
    public String stickerID;


    public RTDBMessage() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public RTDBMessage(String fromUsername, String toUsername, String timestamp, String stickerID) {
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.timestamp = timestamp;
        this.stickerID = stickerID;
    }
}
