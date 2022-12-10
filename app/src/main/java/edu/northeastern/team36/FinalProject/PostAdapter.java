package edu.northeastern.team36.FinalProject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import edu.northeastern.team36.R;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private static final String TAG = "PostAdapter";
    private final Context context;
    private final ArrayList<Post> postArrayList;
    private final String username, userID;

    public PostAdapter(Context context, ArrayList<Post> postArrayList, String username, String userID) {
        this.context = context;
        this.username = username;
        this.userID = userID;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post currPost = postArrayList.get(position);
        holder.titleTv.setText(currPost.getTitle());
        holder.postAuthorTv.setText(currPost.getAuthorName());
        holder.postTimeTv.setText(currPost.getTime());
        holder.gameTv.setText(currPost.getGame());
        holder.descriptionTv.setText(currPost.getDescription());
        String seatsRemaining = "Seats: " + currPost.getSelected().toString() + " / "
                + currPost.getSeats().toString();
        holder.seatTv.setText(seatsRemaining);

        // set bitmap to imageView
        try {
//            Log.e(TAG, "post imgStr: " + currPost.getImgStr());
            byte[] imgByte = Base64.decode(currPost.getImgStr(), Base64.NO_WRAP);
//            Log.e(TAG, "imgByte :" + imgByte.toString());
            Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            holder.imageIv.setImageBitmap(imgBitmap);
        } catch (Exception e) {
            Log.e(TAG, "Error message: " + e.getMessage());
        }

        // pass details to postDetailActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("userID", userID);
                intent.putExtra("postID", currPost.getPostID());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

}
