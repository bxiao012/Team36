package edu.northeastern.team36;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SentViewHolder extends RecyclerView.ViewHolder {
    public ImageView sticker;
    public TextView sentCount;

    public SentViewHolder(@NonNull View itemView) {
        super(itemView);
        sticker = itemView.findViewById(R.id.imageViewSentSticker);
        sentCount = itemView.findViewById(R.id.textViewSentCount);
    }
}
