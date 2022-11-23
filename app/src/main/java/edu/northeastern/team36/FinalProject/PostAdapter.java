package edu.northeastern.team36.FinalProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.team36.R;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private final Context context;
    private final ArrayList<Post> postArrayList;

    public PostAdapter(ArrayList<Post> postArrayList, Context context) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post, null));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post currPost = postArrayList.get(position);
        holder.titleTv.setText(currPost.getTitle());
        holder.postAuthorTv.setText(currPost.getAuthorName());
        holder.postTimeTv.setText(currPost.getTime());
        holder.descriptionTv.setText(currPost.getDescription());
        holder.imageIv.setImageResource(R.drawable.apple);
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

}
