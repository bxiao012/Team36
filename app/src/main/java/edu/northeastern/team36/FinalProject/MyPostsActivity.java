package edu.northeastern.team36.FinalProject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.team36.FinalProject.DAO.DataFunctions;
import edu.northeastern.team36.FinalProject.DAO.MyRunnable;
import edu.northeastern.team36.R;

public class MyPostsActivity extends AppCompatActivity {
    private static final String TAG = "MyPostsActivity";
    private static PostAdapter postAdapter;
    private static ArrayList<Post> postArrayList;
    private RecyclerView postRecyclerView;
    private String username, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        username = getIntent().getStringExtra("username");
        userID = getIntent().getStringExtra("userID");
        postArrayList = new ArrayList<>();

        // fill the postRecyclerView
        postRecyclerView = findViewById(R.id.recyclerViewMyPosts);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(this, postArrayList, username, userID);
        postRecyclerView.setAdapter(postAdapter);

        getMyPosts();


        // navbar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent0 = new Intent(MyPostsActivity.this,FinalProjectActivity.class);
                    intent0.putExtra("username", username);
                    intent0.putExtra("userID", userID);
                    startActivity(intent0);
                    finish();
                    break;
                case R.id.nav_my_posts:
                    Intent intent1 = new Intent(MyPostsActivity.this, MyPostsActivity.class);
                    intent1.putExtra("username", username);
                    intent1.putExtra("userID", userID);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.nav_applied_posts:
                    Intent intent2 = new Intent(MyPostsActivity.this, AppliedPostsActivity.class);
                    intent2.putExtra("username", username);
                    intent2.putExtra("userID", userID);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.nav_profile:
                    Intent intent3 = new Intent(MyPostsActivity.this,ProfileActivity.class);
                    intent3.putExtra("username", username);
                    intent3.putExtra("userID", userID);
                    startActivity(intent3);
                    finish();
                    break;
            }

            return true;
        });
    }

    /*
        Helper Function to add all posts by the current user to postArrayList
     */
    private void getMyPosts() {
        // Returns Posts by owner id
        JsonObject ownerObj = new JsonObject();
        JsonObject ownerId = new JsonObject();
        ownerId.addProperty("$oid",userID);
        ownerObj.add("owner.id", ownerId);

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
                System.out.println("the posts " + message.toString());
                JsonArray postArray = message.getAsJsonArray("documents");

                for (int i = 0; i < postArray.size(); i++) {
                    JsonElement postJsonObject = postArray.get(i);
//                    System.out.println(postJsonObject);
                    HashMap postMap = new Gson().fromJson(postJsonObject.toString(), HashMap.class);

                    Double doubleSeat = (Double) postMap.get("seat");
                    Map ownerMap = (Map) postMap.get("owner");
                    String ownerName = (String) ownerMap.get("name");
                    List selectedUsers = (List) postMap.get("selected");

                    Post post = new Post(postMap.get("_id").toString(), ownerMap.get("name").toString(),
                            postMap.get("content").toString(), postMap.get("title").toString(),
                            postMap.get("gameName").toString(), postMap.get("createTime").toString(),
                            postMap.get("image").toString(), doubleSeat.intValue(), selectedUsers.size());
                    postArrayList.add(post);
                }
                // update imgStr in posts
                for (int i = 0; i < postArrayList.size(); i++) {
                    findImageByImageId(i);
                }

            }
        };

        new DataFunctions().findPosts(handleMessage, ownerObj);
    };
    private void findImageByImageId(int i){
        JsonObject imageObj = new JsonObject();
        JsonObject imageId = new JsonObject();
        Post currPost = postArrayList.get(i);
//        Log.e(TAG, currPost.getImgStr());
        imageId.addProperty("$oid", currPost.getImgStr());
        imageObj.add("_id", imageId);

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
                if (message != null) {
                    JsonArray imgArray = message.getAsJsonArray("documents");

                    HashMap imgMap = new Gson().fromJson(imgArray.get(0).toString(), HashMap.class);
                    String imgStr = imgMap.get("img").toString();
                    // delete the prefix("data:image/.*;base64,")
                    String[] imgList = imgStr.split(",");
                    Log.e(TAG, "In handleMessage: imgStr is " + imgStr);
                    currPost.setimgStr(imgList[1]);
//                    Log.e(TAG, "In handleMessage: " + imgMap.get("img").toString());
                }
                postAdapter.notifyItemChanged(i);
            }
        };

        new DataFunctions().findImage(handleMessage, imageObj);
    }

}
