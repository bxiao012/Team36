package edu.northeastern.team36.FinalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class PostDetailActivity extends AppCompatActivity {
    private static final String TAG = "PostDetailActivity";
    private String postID, username, userID;
    private String passUsername, passUserID;
    private String title, authorName, description, seatRemaining, gameName, gameTime, status, createTime;
    private List<Map> appliedUsers, selectedUsers;
    private TextView titleTv, authorTv, descriptionTv, seatTv, gameTv, gameTimeTv;
    private ImageView imageView;
    Spinner appliedSpinner;
    Spinner selectedSpinner;
    Button enterApplied;
    Button enterSelected;
    Button enterOwner;
    Map<String, String> appliedMap = new HashMap<>();
    Map<String, String> selectedMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        // get info from previous intent
        postID = getIntent().getStringExtra("postID");
        username = getIntent().getStringExtra("username");
        userID = getIntent().getStringExtra("userID");

        // get view by id
        titleTv = (TextView) findViewById(R.id.titleTv2);
        authorTv = (TextView) findViewById(R.id.postAuthorTv2);
        descriptionTv = (TextView) findViewById(R.id.descriptionTv2);
        seatTv = (TextView) findViewById(R.id.seatTv2);
        gameTv = (TextView) findViewById(R.id.gameTv2);
        gameTimeTv = (TextView) findViewById(R.id.game_time_tv);
        imageView = (ImageView) findViewById(R.id.imageTv2);
        appliedSpinner = (Spinner) findViewById(R.id.applied_spinner);
        selectedSpinner = (Spinner) findViewById(R.id.selected_spinner);
        enterApplied = (Button) findViewById(R.id.applied_button);
        enterSelected = (Button) findViewById(R.id.selected_button);
        enterOwner = (Button) findViewById(R.id.enter_owner);

        // get postDetail data from database
        findPostWithImage();

        // enter profile page
        enterApplied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appliedSpinner.getSelectedItem() == null) {
                    Toast.makeText(PostDetailActivity.this, "No applied user selected!", Toast.LENGTH_SHORT).show();
                } else {
                    passUsername = appliedSpinner.getSelectedItem().toString();
                    passUserID = appliedMap.get(passUsername);
                    Intent intent = new Intent(PostDetailActivity.this,ProfileActivity.class);
                    intent.putExtra("username", passUsername);
                    intent.putExtra("userID", passUserID);
                    startActivity(intent);
                }
            }
        });

        enterSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedSpinner.getSelectedItem() == null) {
                    Toast.makeText(PostDetailActivity.this, "No selected user selected!", Toast.LENGTH_SHORT).show();
                } else {
                    passUsername = selectedSpinner.getSelectedItem().toString();
                    passUserID = selectedMap.get(passUsername);
                    Intent intent = new Intent(PostDetailActivity.this,ProfileActivity.class);
                    intent.putExtra("username", passUsername);
                    intent.putExtra("userID", passUserID);
                    startActivity(intent);
                }
            }
        });

        enterOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(PostDetailActivity.this,ProfileActivity.class);
                    intent.putExtra("username", passUsername);
                    intent.putExtra("userID", passUserID);
                    startActivity(intent);
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

                    // update appliedUsers and selectedUsers
                    appliedUsers = (List<Map>) postMap.get("applied");
                    selectedUsers = (List<Map>) postMap.get("selected");

                    List<String> appliedUsersList = new ArrayList<>();
                    List<String> selectedUsersList = new ArrayList<>();

                    // set applied users spinner
                    if (!appliedUsers.isEmpty()) {
                        for(Map appliedUser : appliedUsers) {
                            appliedUsersList.add(appliedUser.get("name").toString());
                            appliedMap.put(appliedUser.get("name").toString(), appliedUser.get("id").toString());
                        }
                        String[] appliedArray = new String[appliedUsersList.size()];
                        appliedArray = appliedUsersList.toArray(appliedArray);

                        ArrayAdapter<String> appliedAdapter = new ArrayAdapter<String>
                                (PostDetailActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,appliedArray);
                        appliedSpinner.setAdapter(appliedAdapter);
                    }


                    // set selected users spinner
                    if (!selectedUsers.isEmpty()) {
                        for(Map selectedUser : selectedUsers) {
                            selectedUsersList.add(selectedUser.get("name").toString());
                            selectedMap.put(selectedUser.get("name").toString(), selectedUser.get("id").toString());
                        }

                        String[] selectedArray = new String[appliedUsersList.size()];
                        selectedArray = appliedUsersList.toArray(selectedArray);

                        ArrayAdapter<String> selectedAdapter = new ArrayAdapter<String>
                                (PostDetailActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,selectedArray);
                        selectedSpinner.setAdapter(selectedAdapter);
                    }

//                    Log.e(TAG, " applied: " + appliedUsers.toString() + " selected: " + selectedUsers.toString());

                    // get other useful data
                    Map ownerMap = (Map) postMap.get("owner");
                    passUserID = ownerMap.get("id").toString();
                    passUsername = ownerMap.get("name").toString();
                    title = postMap.get("title").toString();
                    authorName = ownerMap.get("name").toString();
                    description = postMap.get("content").toString();
                    gameName = postMap.get("gameName").toString();
                    status = postMap.get("status").toString();
                    createTime = postMap.get("createTime").toString();
                    gameTime = postMap.get("gameTime").toString();
                    Double doubleSeat = (Double) postMap.get("seat");
                    seatRemaining = "Seats: " + selectedUsers.size() + "/" + doubleSeat.intValue();

                    // set data to xml
                    titleTv.setText(title);
                    authorTv.setText(authorName);
                    descriptionTv.setText(description);
                    seatTv.setText(seatRemaining);
                    gameTv.setText(gameName);
                    gameTimeTv.setText(gameTime);

                    // process image and set image to xml
                    List<Map> imgArray = (List<Map>) postMap.get("imageDetail");
//                    Log.e(TAG, imgArray.toString());
                    Map imgMap = (Map) imgArray.get(0);
                    String imgStr = imgMap.get("img").toString();
                    String[] imgStrList = imgStr.split(",");
                    imgStr = imgStrList[imgStrList.length - 1];
                    byte[] imgByte = Base64.decode(imgStr, Base64.NO_WRAP);
                    Bitmap imgBitMap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                    imageView.setImageBitmap(imgBitMap);

                    // TODO: write the logics about appliedUsers and selectedUsers
                }

//                Log.e(TAG, "the posts with image" + message.toString());
            }
        };

        new DataFunctions().findPostsWithImage(handleMessage, ownerObj);
    }
}