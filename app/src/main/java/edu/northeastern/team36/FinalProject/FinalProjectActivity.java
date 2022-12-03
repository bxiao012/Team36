package edu.northeastern.team36.FinalProject;


import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import edu.northeastern.team36.MainActivity;
import edu.northeastern.team36.R;

public class FinalProjectActivity extends AppCompatActivity {
    private RecyclerView postsRv;
    private RecyclerView postRecyclerView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postArrayList;
    private String username;
    private String title, game, authorName, time, description;
    private int seats;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_project);

        username = getIntent().getStringExtra("username");
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


//        Post testPost = new Post("postID", "Kaiwen Zhou", "description: balabala", "Test Post1"
//        , "Genshin Impact", "2022.11.22", 4);
//        Post testPost1 = new Post("postID", "Lu Wang", "description: balabala", "Test Post2"
//                , "Genshin Impact", "2022.11.23", 4);
//        postArrayList.add(testPost);
//        postArrayList.add(testPost1);

        postRecyclerView = findViewById(R.id.recyclerViewPosts);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList, username, this);
        postRecyclerView.setAdapter(postAdapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent0 = new Intent(FinalProjectActivity.this, FinalProjectActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.nav_reviews:
                    Intent intent1 = new Intent(FinalProjectActivity.this, ReviewsActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_profile:
                    Intent intent2 = new Intent(FinalProjectActivity.this,ProfileActivity.class);
                    startActivity(intent2);
                    break;
            }

            return true;
        });

        }


    @SuppressLint("RestrictedApi")
    public static void removeNavigationShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        menuView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        menuView.buildMenuView();
    }
    }

