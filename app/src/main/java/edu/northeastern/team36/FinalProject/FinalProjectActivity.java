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

import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Map;

import edu.northeastern.team36.FinalProject.DAO.DataFunctions;
import edu.northeastern.team36.FinalProject.DAO.MyRunnable;
import edu.northeastern.team36.R;

public class FinalProjectActivity extends AppCompatActivity {
    private static final String TAG = "AppCompatActivity";
    private RecyclerView postsRv;
    private RecyclerView postRecyclerView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postArrayList;
    private String username, userID;
    private String title, game, authorName, time, description;
    private int seats;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_project);

        username = getIntent().getStringExtra("username");
        userID = getIntent().getStringExtra("userID");
        postArrayList = new ArrayList<>();

        // call back the post details from the createPostActivity
        ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d(TAG, "onActivityResult: ");
                        if (result.getResultCode() == 36) {
                            Intent intent = result.getData();

                            if(intent != null) {
//                                 Extract data
                                String[] data = intent.getStringArrayExtra("result");
                                title = data[0];
                                game = data[1];
                                authorName = data[2];
                                time = data[3];
                                description = data[4];
                                seats = Integer.parseInt(data[5]);
                                postArrayList.add(new Post("postId", authorName, description, title, game, time, seats));
                            }
                        }
                    }
                }
        );

        // add a new post by clicking the fab
        fab = (FloatingActionButton) findViewById(R.id.addNewPostFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalProjectActivity.this, CreatePostActivity.class);
                activityLauncher.launch(intent);
            }
        });


        // fill the postRecyclerView
        postRecyclerView = findViewById(R.id.recyclerViewPosts);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList, username, userID, this);
        postRecyclerView.setAdapter(postAdapter);

        getAllPosts();


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent0 = new Intent(FinalProjectActivity.this, FinalProjectActivity.class);
                    intent0.putExtra("username", username);
                    intent0.putExtra("userID", userID);
                    startActivity(intent0);
                    break;
                case R.id.nav_my_posts:
                    Intent intent1 = new Intent(FinalProjectActivity.this, MyPostsActivity.class);
                    intent1.putExtra("username", username);
                    intent1.putExtra("userID", userID);
                    startActivity(intent1);
                    break;
                case R.id.nav_applied_posts:
                    Intent intent2 = new Intent(FinalProjectActivity.this,AppliedPostsActivity.class);
                    intent2.putExtra("username", username);
                    intent2.putExtra("userID", userID);
                    startActivity(intent2);
                    break;
                case R.id.nav_profile:
                    Intent intent3 = new Intent(FinalProjectActivity.this,ProfileActivity.class);
                    intent3.putExtra("username", username);
                    intent3.putExtra("userID", userID);
                    startActivity(intent3);
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
                JsonArray postArray = message.getAsJsonArray("documents");

                for (int i = 0; i < postArray.size(); i++) {
                    JsonElement postJsonObject = postArray.get(i);
//                    System.out.println(postJsonObject);
                    HashMap postMap = new Gson().fromJson(postJsonObject.toString(), HashMap.class);

                    Double doubleSeat = (Double) postMap.get("seat");
                    Map ownerMap = (Map) postMap.get("owner");
                    String ownerName = (String) ownerMap.get("name");
//                    System.out.println(ownerName);
                    Post post = new Post(postMap.get("_id").toString(), ownerName,
                            postMap.get("content").toString(), postMap.get("title").toString(), postMap.get("gameName").toString(),
                            postMap.get("createTime").toString(), doubleSeat.intValue());
                    postArrayList.add(post);
//                    postAdapter.notifyItemChanged(i);
                }
                postAdapter.notifyDataSetChanged();
            }

        };

        new DataFunctions().getAllPosts(handleMessageAllPosts);
    }


    @SuppressLint("RestrictedApi")
    public static void removeNavigationShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        menuView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        menuView.buildMenuView();
    }
    }

