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
    private String selectedUsername, selectedUserID;
    private String title, authorName, description, seatRemaining, gameName, gameTime, status, createTime, location;
    private List<Map> appliedUsers, selectedUsers;
    private TextView titleTv, authorTv, descriptionTv, seatTv, gameTv, gameTimeTv;
    private ImageView imageView;
    private List<String> appliedUserArray = new ArrayList<>();
    private List<String> selectedUserArray = new ArrayList<>();
    Spinner appliedSpinner;
    Spinner selectedSpinner;
    Button selectBtn;
    Button addBtn;
    Button enterApplied;
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
        enterOwner = (Button) findViewById(R.id.enter_owner);
        selectBtn = (Button) findViewById(R.id.select_button);
        addBtn = (Button) findViewById(R.id.add_button);

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

        // add selected users from applied users
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appliedSpinner.getSelectedItem() == null || selectedUserArray.contains(appliedSpinner.getSelectedItem().toString())) {
                    Toast.makeText(PostDetailActivity.this, "Invalid! Select A Valid User", Toast.LENGTH_SHORT).show();
                } else {
                    selectedUserArray.add(appliedSpinner.getSelectedItem().toString());
                    for(int i = 0; i < selectedUserArray.size(); i++) {
                        System.out.println(selectedUserArray.get(i));
                    }
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUserArray.isEmpty() || appliedSpinner.getSelectedItem() == null || selectedUserArray.contains(appliedSpinner.getSelectedItem().toString())) {
                    Toast.makeText(PostDetailActivity.this, "No Item selected!", Toast.LENGTH_SHORT).show();
                } else {
                    JsonObject postId = new JsonObject();
                    JsonObject id = new JsonObject();
                    // Post ID
                    id.addProperty("$oid",postID);
                    postId.add("_id", id);
                    JsonArray selectedArr = new JsonArray();
                    JsonObject selected = new JsonObject();
                    for (int i = 0; i < selectedUserArray.size(); i++) {
                        JsonObject selectedOne = new JsonObject();
                        selectedOne.addProperty("name",selectedUserArray.get(i));
                        JsonObject oid = new JsonObject();
                        oid.addProperty("$oid", selectedMap.get(selectedUserArray.get(i)));
                        selectedOne.add("id",oid);
                        selectedArr.add(selectedOne);
                    }

                    // "selected" for inserting into selected and "applied" for inserting into applied
                    selected.add("selected", selectedArr);

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
                            appliedUserArray.clear();
                            selectedUserArray.clear();
                            findPostWithImage();
                            System.out.println("the post UPDATED " + message.toString());
                        }
                    };

                    new DataFunctions().updatePost(handleMessage, postId, selected);

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
//
//                    Map currUserMap = appliedUsers.get(0);
//                    String currName = currUserMap.get("name").toString();
//                    String currID = currUserMap.get("id").toString();

                    // set applied users spinner
                    if (!appliedUsers.isEmpty()) {
                        for(Map appliedUser : appliedUsers) {
                            appliedUserArray.add(appliedUser.get("name").toString());
                            appliedMap.put(appliedUser.get("name").toString(), appliedUser.get("id").toString());
                        }
                        String[] appliedArray = new String[appliedUserArray.size()];
                        appliedArray = appliedUserArray.toArray(appliedArray);

                        ArrayAdapter<String> appliedAdapter = new ArrayAdapter<String>
                                (PostDetailActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,appliedArray);
                        appliedSpinner.setAdapter(appliedAdapter);
                    }


                    // set selected users spinner
                    if (!selectedUsers.isEmpty()) {
                        for(Map selectedUser : selectedUsers) {
                            selectedUserArray.add(selectedUser.get("name").toString());
                            selectedMap.put(selectedUser.get("name").toString(), selectedUser.get("id").toString());
                        }

                        String[] selectedArray = new String[selectedUserArray.size()];
                        selectedArray = selectedUserArray.toArray(selectedArray);

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
                    location = postMap.get("location").toString();
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

                }

//                Log.e(TAG, "the posts with image" + message.toString());
            }
        };

        new DataFunctions().findPostsWithImage(handleMessage, ownerObj);
    }
}