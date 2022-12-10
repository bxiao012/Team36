package edu.northeastern.team36.FinalProject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import edu.northeastern.team36.R;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<Review> reviewsList;
    private RecyclerView recyclerView;
    private String username, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerView = findViewById(R.id.recyclerViewReviews);
        username = getIntent().getStringExtra("username");
        userID = getIntent().getStringExtra("userID");
        reviewsList = new ArrayList<>();
        setReviewInfo();
        setAdapter();



        TextView userName = (TextView) findViewById(R.id.usernameTv);
        TextView rating = (TextView) findViewById(R.id.ratingTv);

        userName.setText(username);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent0 = new Intent(ProfileActivity.this,FinalProjectActivity.class);
                    intent0.putExtra("username", username);
                    intent0.putExtra("userID", userID);
                    startActivity(intent0);
                    break;
                case R.id.nav_my_posts:
                    Intent intent1 = new Intent(ProfileActivity.this, MyPostsActivity.class);
                    intent1.putExtra("username", username);
                    intent1.putExtra("userID", userID);
                    startActivity(intent1);
                    break;
                case R.id.nav_applied_posts:
                    Intent intent2 = new Intent(ProfileActivity.this, AppliedPostsActivity.class);
                    intent2.putExtra("username", username);
                    intent2.putExtra("userID", userID);
                    startActivity(intent2);
                    break;
                case R.id.nav_profile:
                    Intent intent3 = new Intent(ProfileActivity.this,ProfileActivity.class);
                    intent3.putExtra("username", username);
                    intent3.putExtra("userID", userID);
                    startActivity(intent3);
                    break;
            }

            return true;
        });



        }

    private void setReviewInfo(){
        reviewsList.add(new Review(username, "very good team player"));
        reviewsList.add(new Review(username, "good teammate"));
        reviewsList.add(new Review(username, "smooth collaboration"));


    }

    private void setAdapter(){
        ReviewAdapter adapter = new ReviewAdapter(reviewsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }


}