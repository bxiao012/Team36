package edu.northeastern.team36;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Received implements Parcelable {
    public final String fromUser;
    public final String timestamp;
    public final String stickerID;

    public String getFromUser() {
        return fromUser;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getStickerID() {
        return stickerID;
    }

    public Received(String fromUser, String timestamp, String stickerID) {
        this.fromUser = fromUser;
        this.timestamp = timestamp;
        this.stickerID = stickerID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(getFromUser());
        out.writeString(getTimestamp());
        out.writeString(getStickerID());
    }

    public static final Parcelable.Creator<Received> CREATOR = new Creator<Received>() {
        @Override
        public Received createFromParcel(Parcel source) {
            String fromUser = source.readString();
            String timestamp = source.readString();
            String stickerID = source.readString();

            return new Received(fromUser, timestamp, stickerID);
        }

        @Override
        public Received[] newArray(int i) {
            return new Received[0];
        }
    };
}
