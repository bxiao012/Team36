package edu.northeastern.team36.FinalProject;

import android.content.Intent;
import android.os.Bundle;
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

public class AppliedPostsActivity extends AppCompatActivity {
    private static PostAdapter postAdapter;
    private static ArrayList<Post> postArrayList;
    private RecyclerView postRecyclerView;
    private String username, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_posts);
        username = getIntent().getStringExtra("username");
        userID = getIntent().getStringExtra("userID");
        postArrayList = new ArrayList<>();


        // fill the postRecyclerView
        postRecyclerView = findViewById(R.id.recyclerViewAppliedPosts);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList, username, userID, this);
        postRecyclerView.setAdapter(postAdapter);

        getAppliedPosts();

        // navbar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent0 = new Intent(AppliedPostsActivity.this, FinalProjectActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.nav_my_posts:
                    Intent intent1 = new Intent(AppliedPostsActivity.this, MyPostsActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_applied_posts:
                    Intent intent2 = new Intent(AppliedPostsActivity.this, AppliedPostsActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.nav_profile:
                    Intent intent3 = new Intent(AppliedPostsActivity.this, ProfileActivity.class);
                    startActivity(intent3);
                    break;
            }

            return true;
        });
    }



    /*
        Helper Function to add all applied posts to postArrayList
     */
    private void getAppliedPosts() {
        // create handleMessage
        MyRunnable handleMessageAllPosts = new MyRunnable() {
            JsonObject message;
            @Override
            public MyRunnable setParam(JsonObject param) {
                message = param;
                return this;
            }

            @Override
            public void run() {
                handleMessageAllPosts(message);
            }

            private void handleMessageAllPosts(JsonObject message) {
                JsonArray postArray = message.getAsJsonArray("documents");

                for (int i = 0; i < postArray.size(); i++) {
                    JsonElement postJsonObject = postArray.get(i);
//                    System.out.println(postJsonObject);
                    HashMap postMap = new Gson().fromJson(postJsonObject.toString(), HashMap.class);

                    Double doubleSeat = (Double) postMap.get("seat");
                    Map ownerMap = (Map) postMap.get("owner");
                    String ownerName = (String) ownerMap.get("name");
//                    System.out.println(ownerName);

                    // check if applied or not
                    List<Map> appliedUsers = (List<Map>) postMap.get("applied");
                    System.out.println(userID);
                    for (int j = 0; j < appliedUsers.size(); j++) {
//                        System.out.println(appliedUsers.get(j).get("id").toString());
                        String appliedUserID = (String) appliedUsers.get(j).get("id");
                        if (appliedUserID != null && appliedUserID.equals(userID)) {
                            Post post = new Post(postMap.get("_id").toString(), ownerName,
                                    postMap.get("content").toString(), postMap.get("title").toString(), postMap.get("gameName").toString(),
                                    postMap.get("createTime").toString(), doubleSeat.intValue());
                            postArrayList.add(post);
                        }
                    }


//                    postAdapter.notifyItemChanged(i);
                }
                postAdapter.notifyDataSetChanged();
            }

        };

        // get all posts by using the handleMessage
        new DataFunctions().getAllPosts(handleMessageAllPosts);
    };
}
