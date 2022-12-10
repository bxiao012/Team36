package edu.northeastern.team36.FinalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.team36.FinalProject.DAO.DataFunctions;
import edu.northeastern.team36.FinalProject.DAO.MyRunnable;
import edu.northeastern.team36.R;

public class CreateReviewActivity extends AppCompatActivity {
    private String postID, username, userID, title, ownerUserID, ownerUsername;
    List<String> userArray = new ArrayList<>();
    private List<Map> selectedUsers;
    HashMap<String, String> userMap = new HashMap<>();
    HashMap<String, Boolean> reviewMap = new HashMap<>();
    boolean addUserToReviewMap = true;
    Spinner userSpinner;
    Button submit;
    TextView titleTv;
    EditText rate;
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

        // get current user information
        username = getIntent().getStringExtra("username");
        userID = getIntent().getStringExtra("userID");
        postID = getIntent().getStringExtra("postID");

        userSpinner = (Spinner) findViewById(R.id.user_spinner);
        submit = (Button) findViewById(R.id.submit_review_btn);
        titleTv = (TextView) findViewById(R.id.title_tv);
        rate = (EditText) findViewById(R.id.editTv_rate);
        content = (EditText) findViewById(R.id.editTv_content);

        findPostWithImage();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userSpinner.getSelectedItem() == null || content.getText().toString().equals("") || rate.getText().toString().equals("")) {
                    Toast.makeText(CreateReviewActivity.this, "No User selected, No Rate or No Content", Toast.LENGTH_SHORT).show();
                } else {
                    // if the user is reviewed, can not be reviewed again
                    if (reviewMap.get(userSpinner.getSelectedItem().toString())) {
                        Toast.makeText(CreateReviewActivity.this, "This User Has Been Reviewed!", Toast.LENGTH_SHORT).show();
                    } else {
                        reviewMap.put(userSpinner.getSelectedItem().toString(), true);
                        JsonObject reviewObj = new JsonObject();
                        JsonObject toId = new JsonObject();
                        toId.addProperty("$oid",userID);
                        JsonObject fromId = new JsonObject();
                        fromId.addProperty("$oid", userMap.get(userSpinner.getSelectedItem()));
                        JsonObject postId = new JsonObject();
                        postId.addProperty("$oid",postID);
                        reviewObj.add("from", fromId);
                        reviewObj.add("to", toId);
                        reviewObj.add("postId", postId);
                        reviewObj.addProperty("content", content.getText().toString());
                        reviewObj.addProperty("rate", Double.parseDouble(rate.getText().toString()));
                        String formatDate= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        reviewObj.addProperty("createTime", formatDate);

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
                                userArray.clear();
                                findPostWithImage();
                                System.out.println("the review created " + message.toString());
                            }
                        };

                        new DataFunctions().createReview(handleMessage, reviewObj);
                    }
                }
            }

        });


    }

    public void findPostWithImage() {
        // Find Post by post id - the aggregate query with the base64 image string.
        JsonObject ownerObj = new JsonObject();
        JsonObject ownerId = new JsonObject();
        ownerId.addProperty("$oid", postID);
        ownerObj.add("_id", ownerId);

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
                JsonArray postArray = message.getAsJsonArray("documents");
                if (postArray.size() > 0) {
                    JsonElement postJsonObject = postArray.get(0);
                    HashMap postMap = new Gson().fromJson(postJsonObject.toString(), HashMap.class);

                    // get selectedUsers
                    selectedUsers = (List<Map>) postMap.get("selected");


                    // set selected users spinner
                    if (!selectedUsers.isEmpty()) {
                        for(Map selectedUser : selectedUsers) {
                            if (selectedUser.get("name").toString().equals(username))
                                continue;
                            userArray.add(selectedUser.get("name").toString());
                            userMap.put(selectedUser.get("name").toString(), selectedUser.get("id").toString());
                            if (addUserToReviewMap) {
                                reviewMap.put(selectedUser.get("name").toString(), false);
                            }
                        }
                    }

//                    Log.e(TAG, " applied: " + appliedUsers.toString() + " selected: " + selectedUsers.toString());

                    // get other useful data
                    Map ownerMap = (Map) postMap.get("owner");
                    ownerUserID = ownerMap.get("id").toString();
                    ownerUsername = ownerMap.get("name").toString();
                    title = postMap.get("title").toString();

                    // set data to xml
                    titleTv.setText(title);

                    // set spinner containing owner
                    // if current user is not owner
                    if (!username.equals(ownerUsername)) {
                        userArray.add(ownerUsername);
                        userMap.put(ownerUsername, ownerUserID);
                        if(addUserToReviewMap) {
                            reviewMap.put(ownerUsername, false);
                        }
                    }
                    String[] userArr = new String[userArray.size()];
                    userArr = userArray.toArray(userArr);
                    ArrayAdapter<String> selectedAdapter = new ArrayAdapter<String>
                            (CreateReviewActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,userArr);
                    userSpinner.setAdapter(selectedAdapter);

                    // stop adding user to the review map
                    addUserToReviewMap = false;
                }

            }
        };

        new DataFunctions().findPostsWithImage(handleMessage, ownerObj);
    }

}