package edu.northeastern.team36.FinalProject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import edu.northeastern.team36.FinalProject.DAO.DataFunctions;
import edu.northeastern.team36.FinalProject.DAO.MyRunnable;
import edu.northeastern.team36.R;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private static final String TAG = "PostAdapter";
    private final Context context;
    private final ArrayList<Post> postArrayList;
    private final String username, userID, postsType;

    public PostAdapter(Context context, ArrayList<Post> postArrayList, String username, String userID, String postsType) {
        this.context = context;
        this.username = username;
        this.userID = userID;
        this.postArrayList = postArrayList;
        this.postsType = postsType;
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
        holder.reviewImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateReviewActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("userID", userID);
                intent.putExtra("postID", currPost.getPostID());
                intent.putStringArrayListExtra("haveReviewToArray",  currPost.getHaveReviewToArray());

                context.startActivity(intent);
            }
        });

        // set visibility
//        Log.e(TAG, currPost.getTitle() + " " + currPost.getHaveReviewToArray().toString() +
//                " " + currPost.getSelected() + " " + currPost.getHaveReviewToArray().size());
        if (this.postsType.equals("AppliedPosts")
                && currPost.getSelected() - 1 > currPost.getHaveReviewToArray().size()) {
            holder.reviewImgBtn.setVisibility(View.VISIBLE);
        } else if (this.postsType.equals("MyPosts") && currPost.getStatus().equals("In progress")
                && currPost.getSeats().equals(currPost.getSelected())) {
//            Log.e(TAG, currPost.getPostID() + " status: " + currPost.getStatus() + " seats: "
//            + currPost.getSeats() + " selected: " +currPost.getSelected());
            holder.endImgBtn.setVisibility(View.VISIBLE);
            holder.endImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePostClosed(currPost, holder);
                }
            });
        }

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


    private void updatePostClosed(Post post, PostViewHolder holder){

        JsonObject postId = new JsonObject();
        JsonObject id = new JsonObject();
        // Post ID
        id.addProperty("$oid", post.getPostID());
        postId.add("_id", id);
        // closed status
        JsonObject status = new JsonObject();
        status.addProperty("status", "Closed");

        MyRunnable handleMessage = new MyRunnable() {
            JsonObject message;
            @Override
            public MyRunnable setParam(JsonObject param) {
                message = param;
                return this;
            }

            @Override
            public void run() {
                handleMessage(message);
            }

            private void handleMessage(JsonObject message) {
//                System.out.println("the post UPDATED " + message.toString());
                holder.endImgBtn.setVisibility(View.INVISIBLE);
            }
        };

        new DataFunctions().updatePost(handleMessage, postId, status);
    }
}
