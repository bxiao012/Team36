package edu.northeastern.team36.FinalProject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import edu.northeastern.team36.R;

public class ReviewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        TextView title = (TextView) findViewById(R.id.reviewActivityTitle);
        title.setText("This is review activity");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent0 = new Intent(ReviewsActivity.this,FinalProjectActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.nav_reviews:
                    Intent intent1 = new Intent(ReviewsActivity.this, ReviewsActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_profile:
                    Intent intent2 = new Intent(ReviewsActivity.this,ProfileActivity.class);
                    startActivity(intent2);
                    break;
            }

            return true;
        });
    }


}
