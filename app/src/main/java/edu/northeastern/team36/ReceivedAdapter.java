package edu.northeastern.team36;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReceivedAdapter extends RecyclerView.Adapter<ReceivedViewHolder> {
    private static final String TAG = "ReceivedAdapter";
    private final List<Received> receivedList;
    private final List<String> stickers;
    private final Context context;

    public ReceivedAdapter(List<Received> receivedList, List<String> stickers, Context context) {
        this.receivedList = receivedList;
        this.context = context;
        this.stickers = stickers;
    }

    @NonNull
    @Override
    public ReceivedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceivedViewHolder(LayoutInflater.from(context).inflate(R.layout.item_received, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedViewHolder holder, int position) {
        Received currReceived = receivedList.get(position);
        holder.fromUser.setText("From: " + currReceived.getFromUser());
        holder.timestamp.setText("Timestamp: " + currReceived.getTimestamp());
        String currStickerID = currReceived.getStickerID();
        int currStickerResource = - 1;
        if (currStickerID == null) {
            return;
        } else if (currStickerID.equals(stickers.get(0))) {
            currStickerResource = R.drawable.apple;
        } else if (currStickerID.equals(stickers.get(1))) {
            currStickerResource = R.drawable.orange;
        } else if (currStickerID.equals(stickers.get(2))) {
            currStickerResource = R.drawable.strawberry;
        } else if (currStickerID.equals(stickers.get(3))) {
            currStickerResource = R.drawable.watermelon;
        } else {
            Log.e(TAG, "Wrong StickerID: " + currStickerID +
                    ", From" + currReceived.getFromUser() + ", Timestamp: " + currReceived.getTimestamp());
            return;
        }
        holder.sticker.setImageResource(currStickerResource);
    }

    @Override
    public int getItemCount() {
        return receivedList.size();
    }
}
