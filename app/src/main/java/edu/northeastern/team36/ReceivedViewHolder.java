package edu.northeastern.team36;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReceivedViewHolder extends RecyclerView.ViewHolder {
    public TextView fromUser;
    public TextView timestamp;
    public ImageView sticker;

    public ReceivedViewHolder(@NonNull View itemView) {
        super(itemView);
        this.fromUser = itemView.findViewById(R.id.textViewFromUser);
        this.timestamp = itemView.findViewById(R.id.textViewTimestamp);
        this.sticker = itemView.findViewById(R.id.imageViewReceivedSticker);
    }
}
