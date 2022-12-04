package edu.northeastern.team36.FinalProject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import edu.northeastern.team36.R;

public class MyPostsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        TextView title = (TextView) findViewById(R.id.MyPostsActivityTitle);
        title.setText("These are my posts");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent0 = new Intent(MyPostsActivity.this,FinalProjectActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.nav_my_posts:
                    Intent intent1 = new Intent(MyPostsActivity.this, MyPostsActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_applied_posts:
                    Intent intent2 = new Intent(MyPostsActivity.this, AppliedPostsActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.nav_profile:
                    Intent intent3 = new Intent(MyPostsActivity.this,ProfileActivity.class);
                    startActivity(intent3);
                    break;
            }

            return true;
        });
    }


}
