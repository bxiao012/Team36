package edu.northeastern.team36.FinalProject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_project);

        username = getIntent().getStringExtra("username");
        postArrayList = new ArrayList<>();
        Post testPost = new Post("postID", "Kaiwen Zhou", "description: balabala", "Test Post1"
        , "Genshin Impact", "2022.11.22", 4);
        Post testPost1 = new Post("postID", "Lu Wang", "description: balabala", "Test Post2"
                , "Genshin Impact", "2022.11.23", 4);
        postArrayList.add(testPost);
        postArrayList.add(testPost1);
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

