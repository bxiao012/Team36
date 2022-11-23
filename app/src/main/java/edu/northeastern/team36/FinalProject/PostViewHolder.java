package edu.northeastern.team36.FinalProject;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.team36.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public ImageButton detailBtn, applyBtn;
    public TextView titleTv, postTimeTv, postAuthorTv, descriptionTv;
    public ImageView imageIv;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.detailBtn = itemView.findViewById(R.id.detailBtn);
        this.applyBtn = itemView.findViewById(R.id.applyBtn);

        this.titleTv = itemView.findViewById(R.id.titleTv);
        this.postTimeTv = itemView.findViewById(R.id.postTimeTv);
        this.postAuthorTv = itemView.findViewById(R.id.postAuthorTv);
        this.descriptionTv = itemView.findViewById(R.id.descriptionTv);
        this.imageIv = itemView.findViewById(R.id.imageIv);
    }
}