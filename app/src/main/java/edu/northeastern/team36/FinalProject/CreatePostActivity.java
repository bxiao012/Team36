package edu.northeastern.team36.FinalProject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.northeastern.team36.FinalProject.DAO.DataFunctions;
import edu.northeastern.team36.FinalProject.DAO.MyRunnable;
import edu.northeastern.team36.R;

public class CreatePostActivity extends AppCompatActivity {
    EditText titleInput;
    EditText gameInput;
    EditText authorNameInput;
    EditText timeInput;
    EditText descriptionInput;
    EditText seatsInput;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        titleInput = (EditText) findViewById(R.id.editTextTitle);
        gameInput = (EditText) findViewById(R.id.editTextGame);
        authorNameInput = (EditText) findViewById(R.id.editTextAuthorName);
        timeInput = (EditText) findViewById(R.id.editTextTime);
        descriptionInput = (EditText) findViewById(R.id.editTextDescription);
        seatsInput = (EditText) findViewById(R.id.editTextSeats);

        // tap the button to submit the detail and go back
        submitBtn = (Button) findViewById(R.id.submitButton);

        // pass these info back to final project activity
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click submitted");
//                Intent intent = new Intent();
//                intent.putExtra("result", new String[]
//                        {titleInput.getText().toString(),
//                        gameInput.getText().toString(),
//                        authorNameInput.getText().toString(),
//                        timeInput.getText().toString(),
//                        descriptionInput.getText().toString(),
//                        seatsInput.getText().toString()});
//                setResult(36, intent);

                // create json object
                JsonObject post = new JsonObject();
                post.addProperty("title",titleInput.getText().toString());
                post.addProperty("content", descriptionInput.getText().toString());
                post.addProperty("gameName",gameInput.getText().toString());
                post.addProperty("seat", Integer.parseInt(seatsInput.getText().toString()) );
                post.addProperty("location", "somewhere");
                post.addProperty("gameTime", timeInput.getText().toString());
                post.addProperty("status", "Closed");
                String formatDate= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                post.addProperty("createTime", formatDate);
                JsonObject owner = new JsonObject();
                JsonObject ownerID = new JsonObject();
                ownerID.addProperty("$oid", "637ce04eb5eb013ea20e7010");
                owner.addProperty("name","user10");
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
                        Log.d(TAG, "post is successfully created!");
                    }
                };

                new DataFunctions().createPost(handleMessage, post, imageObj);
                // go back
                CreatePostActivity.super.onBackPressed();
            }
        });
    }
}