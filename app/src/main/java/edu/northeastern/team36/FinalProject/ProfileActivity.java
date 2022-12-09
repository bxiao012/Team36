package edu.northeastern.team36.FinalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import edu.northeastern.team36.R;

public class ProfileActivity extends AppCompatActivity {
    private String username, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = getIntent().getStringExtra("username");
        userID = getIntent().getStringExtra("userID");

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


}