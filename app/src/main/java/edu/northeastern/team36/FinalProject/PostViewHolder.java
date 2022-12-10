package edu.northeastern.team36.FinalProject;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.team36.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTv, postTimeTv, postAuthorTv, descriptionTv, gameTv, seatTv;
    public ImageView imageIv;
    public ImageButton endImgBtn, reviewImgBtn;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
//        this.applyBtn = itemView.findViewById(R.id.applyBtn);

        this.titleTv = itemView.findViewById(R.id.titleTv);
        this.postTimeTv = itemView.findViewById(R.id.postTimeTv);
        this.postAuthorTv = itemView.findViewById(R.id.postAuthorTv);
        this.gameTv = itemView.findViewById(R.id.gameTv);
        this.seatTv = itemView.findViewById(R.id.seatTv);
        this.descriptionTv = itemView.findViewById(R.id.descriptionTv);
        this.imageIv = itemView.findViewById(R.id.imageIv);
        this.endImgBtn = itemView.findViewById(R.id.endImgBtn);
        this.reviewImgBtn = itemView.findViewById(R.id.reviewImgBtn);
    }
}
