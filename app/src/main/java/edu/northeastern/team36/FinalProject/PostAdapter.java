package edu.northeastern.team36.FinalProject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import edu.northeastern.team36.R;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private final Context context;
    private final ArrayList<Post> postArrayList;
    private final String username;

    public PostAdapter(ArrayList<Post> postArrayList, String username, Context context) {
        this.context = context;
        this.username = username;
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
        holder.gameTv.setText(currPost.getGame());
        holder.seatTv.setText("Seats: " + currPost.getSeats());
        holder.descriptionTv.setText(currPost.getDescription());
        holder.imageIv.setImageResource(R.drawable.apple);

        // pass details to postDetailActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = currPost.getTitle();
                String postAuthor = currPost.getAuthorName();
                String description = currPost.getDescription();
                int seat = currPost.getSeats();
                String game = currPost.getGame();
                Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("EXTRA_TITLE", title);
                intent.putExtra("AUTHOR_NAME", postAuthor);
                intent.putExtra("DESCRIPTION", description);
                intent.putExtra("SEAT", seat);
                intent.putExtra("GAME", game);

                // pass image
                Bitmap bmp = BitmapFactory.decodeResource(PostAdapter.this.context.getResources(), R.drawable.apple);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("IMAGE", byteArray);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

}
