package edu.northeastern.team36.FinalProject;


import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.annotation.SuppressLint;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.core.Tag;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.team36.FinalProject.DAO.DataFunctions;
import edu.northeastern.team36.FinalProject.DAO.MyRunnable;
import edu.northeastern.team36.R;

public class FinalProjectActivity extends AppCompatActivity {
    private static final String TAG = "AppCompatActivity";
    private static final String POST_TYPE = "HomePagePosts";
    private RecyclerView postsRv;
    private RecyclerView postRecyclerView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postArrayList;
    private String username, userID;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_project);

        username = getIntent().getStringExtra("username");
        userID = getIntent().getStringExtra("userID");
        postArrayList = new ArrayList<>();

        // fill the postRecyclerView
        postRecyclerView = findViewById(R.id.recyclerViewPosts);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(this, postArrayList, username, userID, POST_TYPE);
        postRecyclerView.setAdapter(postAdapter);

        getAllPosts();

        // add a new post by clicking the fab
        fab = (FloatingActionButton) findViewById(R.id.addNewPostFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalProjectActivity.this, CreatePostActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent0 = new Intent(FinalProjectActivity.this, FinalProjectActivity.class);
                    intent0.putExtra("username", username);
                    intent0.putExtra("userID", userID);
                    startActivity(intent0);
                    finish();
                    break;
                case R.id.nav_my_posts:
                    Intent intent1 = new Intent(FinalProjectActivity.this, MyPostsActivity.class);
                    intent1.putExtra("username", username);
                    intent1.putExtra("userID", userID);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.nav_applied_posts:
                    Intent intent3 = new Intent(FinalProjectActivity.this,AppliedPostsActivity.class);
                    intent3.putExtra("username", username);
                    intent3.putExtra("userID", userID);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.nav_profile:
                    Intent intent4 = new Intent(FinalProjectActivity.this,ProfileActivity.class);
                    intent4.putExtra("username", username);
                    intent4.putExtra("userID", userID);
                    startActivity(intent4);
                    finish();
                    break;
            }

            return true;
        });

    }

    private void getAllPosts() {

        // get all posts
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
                // empty the postArrayList
//                postArrayList = new ArrayList<>();
                JsonArray postArray = message.getAsJsonArray("documents");

                for (int i = 0; i < postArray.size(); i++) {
                    JsonElement postJsonObject = postArray.get(i);
//                    System.out.println(postJsonObject);
                    HashMap postMap = new Gson().fromJson(postJsonObject.toString(), HashMap.class);

                    Double doubleSeat = (Double) postMap.get("seat");
                    Map ownerMap = (Map) postMap.get("owner");
                    List selectedUsers = (List) postMap.get("selected");
//                    Log.e(TAG, "In getAllPost: " + postMap);

//                    Log.e(TAG, "In getAllPost: " + postMap.get("image").toString());

                    Post post = new Post(postMap.get("_id").toString(), ownerMap.get("name").toString(),
                            postMap.get("content").toString(), postMap.get("title").toString(),
                            postMap.get("gameName").toString(), postMap.get("createTime").toString(),
                            postMap.get("image").toString(), postMap.get("status").toString(),
                            doubleSeat.intValue(), selectedUsers.size());
                    postArrayList.add(post);
                }

                // update imgStr in posts
                for (int i = 0; i < postArrayList.size(); i++) {
                    findImageByImageId(i);
                }
            }

        };

        new DataFunctions().getAllPosts(handleMessageAllPosts);
    }

    private void findImageByImageId(int i){
        JsonObject imageObj = new JsonObject();
        JsonObject imageId = new JsonObject();
        Post currPost = postArrayList.get(i);
        Log.i(TAG, currPost.getImgStr());
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
//                    Log.e(TAG, "In handleMessage: imgStr is " + imgStr);
                    currPost.setImgStr(imgList[imgList.length - 1]);
//                    Log.e(TAG, "In handleMessage: " + imgMap.get("img").toString());
                }
                postAdapter.notifyItemChanged(i);
            }

        };

        new DataFunctions().findImage(handleMessage, imageObj);
    }

    @SuppressLint("RestrictedApi")
    public static void removeNavigationShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        menuView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        menuView.buildMenuView();
    }
}

