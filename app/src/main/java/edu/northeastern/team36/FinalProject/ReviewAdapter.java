package edu.northeastern.team36.FinalProject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.team36.R;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    private ArrayList<Review> reviewsList;

    public ReviewAdapter(ArrayList<Review> reviewsList){
        this.reviewsList = reviewsList;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private TextView reviewTxt;

        public MyViewHolder(final View view){
            super(view);
            reviewTxt = view.findViewById(R.id.reviewDetailTv);
        }
    }



    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder holder, int position) {
        String review = reviewsList.get(position).getReviewDetail();
        holder.reviewTxt.setText(review);

    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }
}
