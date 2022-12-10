package edu.northeastern.team36;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.northeastern.team36.FinalProject.DAO.MyRunnable;
import edu.northeastern.team36.FinalProject.FinalProjectActivity;
import edu.northeastern.team36.FinalProject.LogInActivity;
import edu.northeastern.team36.FinalProject.DAO.DataFunctions;

public class MainActivity extends AppCompatActivity {
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonA7 = (Button) findViewById(R.id.button);
        Button buttonA8 = (Button) findViewById(R.id.button2);
        Button buttonFinal = (Button) findViewById(R.id.button3);

        buttonA7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtYourServiceActivity();
            }
        });
        buttonA8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A8MainActivity();
            }
        });

        buttonFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinalProjectActivity();
            }
        });

        //test data functions
        //TestCreatePost();
        //TestCreatePost();
        //TestUpdatePost();
        //TestFindUser();
        //TestFindPosts();
        //TestCreateReview();
        //TestFindReviews();
        //TestFindImage();
        //TestFindPostsWithImage();




    }




    public void TestCreatePost(){
        JsonObject post = new JsonObject();
        post.addProperty("title", "The title !");
        post.addProperty("content", "The content!");
        post.addProperty("gameName", "Dota2");
        post.addProperty("seat", 2);
        post.addProperty("location", "Somewhere");
        post.addProperty("gameTime", "2022-11-20 20:00:00");
        post.addProperty("status", "In progress");
        String formatDate= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        post.addProperty("createTime", formatDate);
        JsonObject owner = new JsonObject();
        JsonObject ownerID = new JsonObject();
        ownerID.addProperty("$oid", "637ce04eb5eb013ea20e7010");
        owner.addProperty("name","user1");
        owner.add("id", ownerID);
        post.add("owner", owner);
        post.add("applied", new JsonArray());
        post.add("selected", new JsonArray());
        JsonObject imageObj = new JsonObject();
        imageObj.addProperty("img","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMAAAACoCAYAAAC2e+");
        imageObj.addProperty("uploadTime", formatDate);
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
                System.out.println("the post created " + message.toString());
                title = (TextView) findViewById(R.id.textView);
                title.setText(message.toString());
            }
        };

        new DataFunctions().createPost(handleMessage, post, imageObj);
    }
    public void TestUpdatePost(){
        /*
        Input data:
        {
            "applied": [
                            {
                                "name": "user2"
                                "id": {
                                        "$oid": "637ce04eb5eb013ea20e7010"
                                        }
                             }
                       ]
        }

         */
        JsonObject postId = new JsonObject();
        JsonObject id = new JsonObject();
        // Post ID
        id.addProperty("$oid","638fdbd6ded805e0ad540143");
        postId.add("_id", id);
        JsonObject applied = new JsonObject();
        JsonObject appliedOne = new JsonObject();
        appliedOne.addProperty("name","user2");
        JsonObject oid = new JsonObject();
        oid.addProperty("$oid", "637ce04eb5eb013ea20e7011");
        appliedOne.add("id",oid);
        JsonArray appliedArr = new JsonArray();
        appliedArr.add(appliedOne);
        // "selected" for inserting into selected and "applied" for inserting into applied
        applied.add("selected", appliedArr);

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
                System.out.println("the post UPDATED " + message.toString());
            }
        };

        new DataFunctions().updatePost(handleMessage, postId, applied);
    }
    public void TestFindUser(){
        // Returns user document or {"documents":[]}
        JsonObject userObj = new JsonObject();
        userObj.addProperty("name","user1");
        userObj.addProperty("password", "123456");

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
                System.out.println("the user " + message.toString());
            }
        };

        new DataFunctions().findUser(handleMessage, userObj);
    }
    public void TestFindPosts(){
        // Returns Posts by owner id
        JsonObject ownerObj = new JsonObject();
        JsonObject ownerId = new JsonObject();
        ownerId.addProperty("$oid","637ce04eb5eb013ea20e7010");
        ownerObj.add("owner.id", ownerId);

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
                System.out.println("the posts " + message.toString());
            }
        };

        new DataFunctions().findPosts(handleMessage, ownerObj);
    }
    public void TestCreateReview(){
        /* Input data format (reviewObj):
{
  "from": {
    "$oid": "637ce04eb5eb013ea20e7010"
  },
  "to": {
    "$oid": "637ce04eb5eb013ea20e7011"
  },
  "content": "GREATE PLAYER!",
  "rate": 5,
  "createTime": "2022-11-21 09:00:00",
  "postId": {
    "$oid": "637ce23db5eb013ea20e702b"
  }
}



         */
        JsonObject reviewObj = new JsonObject();
        JsonObject toId = new JsonObject();
        toId.addProperty("$oid","637ce04eb5eb013ea20e7011");
        JsonObject fromId = new JsonObject();
        fromId.addProperty("$oid", "637ce04eb5eb013ea20e7010");
        JsonObject postId = new JsonObject();
        postId.addProperty("$oid","637ce23db5eb013ea20e702b");
        reviewObj.add("from", fromId);
        reviewObj.add("to", toId);
        reviewObj.add("postId", postId);
        reviewObj.addProperty("content", "good...");
        reviewObj.addProperty("rate", 5);
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
                System.out.println("the review created " + message.toString());
            }
        };

        new DataFunctions().createReview(handleMessage, reviewObj);
    }
    public void TestFindReviews(){
        // Find reviews "to" somebody
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
                System.out.println("the reviews " + message.toString());
            }
        };

        new DataFunctions().findReviews(handleMessage, toObj);
    }
    public void TestFindImage(){
        JsonObject imageObj = new JsonObject();
        JsonObject imageId = new JsonObject();
        imageId.addProperty("$oid","637ce375b5eb013ea20e7042");
        imageObj.add("_id", imageId);

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
                System.out.println("the image " + message.toString());
            }
        };

        new DataFunctions().findImage(handleMessage, imageObj);
    }
    public void TestFindPostsWithImage(){
        // Find Posts by owner id - the aggregate query with the base64 image string.
        JsonObject ownerObj = new JsonObject();
        JsonObject ownerId = new JsonObject();
        ownerId.addProperty("$oid","637ce04eb5eb013ea20e7010");
        // Use "_id" as the replacement of "owner.id" -> FindPostByPostId
        ownerObj.add("owner.id", ownerId);

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
                System.out.println("the posts with image" + message.toString());
            }
        };

        new DataFunctions().findPostsWithImage(handleMessage, ownerObj);
    }






    public void AtYourServiceActivity(){
        Intent intent = new Intent(this,AtYourServiceActivity.class) ;
        startActivity(intent);
    }

    public void A8MainActivity(){
        Intent intent = new Intent(this,A8MainActivity.class) ;
        startActivity(intent);
    }

    public void FinalProjectActivity(){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}