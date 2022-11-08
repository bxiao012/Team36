package edu.northeastern.team36;

public class Sent {
    public final String stickerID;
    public final Integer sentCount;

    public Sent(String stickerID, Integer sentCount) {
        this.stickerID = stickerID;
        this.sentCount = sentCount;
    }

    public String getStickerID() {
        return stickerID;
    }

    public Integer getSentCount() {
        return sentCount;
    }
}
