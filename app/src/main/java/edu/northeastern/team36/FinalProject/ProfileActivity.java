package edu.northeastern.team36.FinalProject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<Review> reviewsList;
    private RecyclerView recyclerView;
    private String username, userID;
    private static ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerView = findViewById(R.id.recyclerViewReviews);
        username = getIntent().getStringExtra("username");
        userID = getIntent().getStringExtra("userID");
        reviewsList = new ArrayList<>();
        setAdapter();
        getReviews();
        getAvgRate();



        TextView userName = (TextView) findViewById(R.id.usernameTv);
        //TextView ratings = (TextView) findViewById(R.id.ratingTv);

        userName.setText(username);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottomNav.getMenu().getItem(3).setChecked(true);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent0 = new Intent(ProfileActivity.this,FinalProjectActivity.class);
                    intent0.putExtra("username", username);
                    intent0.putExtra("userID", userID);
                    startActivity(intent0);
                    finish();
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
                    finish();
                    break;
                case R.id.nav_profile:
                    Intent intent3 = new Intent(ProfileActivity.this,ProfileActivity.class);
                    intent3.putExtra("username", username);
                    intent3.putExtra("userID", userID);
                    startActivity(intent3);
                    finish();
                    break;
            }

            return true;
        });

        }


    private void setAdapter(){
        reviewAdapter = new ReviewAdapter(reviewsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reviewAdapter);

    }

    private void getReviews() {
        JsonObject toObj = new JsonObject();
        JsonObject toId = new JsonObject();
        toId.addProperty("$oid",userID);
        toObj.add("to", toId);

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
//                System.out.println("the reviews " + message.toString());
                JsonArray reviewArray = message.getAsJsonArray("documents");

                for (int i = 0; i < reviewArray.size(); i++) {
                    JsonElement reviewJsonObject = reviewArray.get(i);
                    HashMap reviewMap = new Gson().fromJson(reviewJsonObject.toString(), HashMap.class);
                    Double doubleRating = (Double) reviewMap.get("rate");
                    Review review = new Review(reviewMap.get("_id").toString(), reviewMap.get("content").toString(),doubleRating.intValue());
                    reviewsList.add(review);
                    reviewAdapter.notifyItemChanged(i);
                }

//                System.out.println("Reviews" + reviewsList);

            }
        };

        new DataFunctions().getReviewByUser(handleMessage, toObj);
    };


    public void getAvgRate(){
        JsonObject toObj = new JsonObject();
        JsonObject toId = new JsonObject();
        toId.addProperty("$oid","637ce04eb5eb013ea20e7011");
        toObj.add("to", toId);

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
                JsonArray rateArray = message.getAsJsonArray("documents");
                if (rateArray.isEmpty()) {
                    return;
                }
                JsonElement rateJsonObject = rateArray.get(0);
                HashMap rateMap = new Gson().fromJson(rateJsonObject.toString(), HashMap.class);
                Double avgRate = (Double) rateMap.get("avgRate");
                String avgRateStr = "Average Rating: " + String.format("%.2f", avgRate);

                TextView avgRateTextView = findViewById(R.id.ratingTv);
                avgRateTextView.setText(avgRateStr);
//                System.out.println("the avg rate " + message.toString());
            }
        };

        new DataFunctions().getAvgRate(handleMessage, toObj);
    }
}