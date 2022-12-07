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
        //TestUpdatePost();





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