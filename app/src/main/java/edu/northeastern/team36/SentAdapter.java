package edu.northeastern.team36;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SentAdapter extends RecyclerView.Adapter<SentViewHolder> {
    private static final String TAG = "SentAdapter";
    private final List<String> stickers;
    private final List<Sent> sentList;
    private final Context context;

    public SentAdapter(List<Sent> sentList, List<String> stickers, Context context) {
        for (Sent sent : sentList) {
            Log.e(TAG, sent.getStickerID() + " " + sent.getSentCount());
        }
        this.sentList = sentList;
        this.stickers = stickers;
        this.context = context;
    }

    @NonNull
    @Override
    public SentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sent, null));
    }

    @Override
    public void onBindViewHolder(@NonNull SentViewHolder holder, int position) {
        Sent currSent = sentList.get(position);
        Log.e(TAG, "StickerID: " + currSent.getStickerID() +
                ", Count: " + currSent.getSentCount());
        holder.sentCount.setText("Count: " + currSent.getSentCount());
        String currStickerID = currSent.getStickerID();
        int currStickerResource = - 1;
        if (currStickerID.equals(stickers.get(0))) {
            currStickerResource = R.drawable.apple;
        } else if (currStickerID.equals(stickers.get(1))) {
            currStickerResource = R.drawable.orange;
        } else if (currStickerID.equals(stickers.get(2))) {
            currStickerResource = R.drawable.strawberry;
        } else if (currStickerID.equals(stickers.get(3))) {
            currStickerResource = R.drawable.watermelon;
        } else {
            Log.e(TAG, "Wrong StickerID: " + currStickerID +
                    ", Count: " + currSent.getSentCount());
            return;
        }
        holder.sticker.setImageResource(currStickerResource);

    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }
}
