package edu.northeastern.team36.FinalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.core.Tag;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

import edu.northeastern.team36.FinalProject.DAO.DataFunctions;
import edu.northeastern.team36.FinalProject.DAO.MyRunnable;
import edu.northeastern.team36.R;

public class LogInActivity extends AppCompatActivity {

    private Button signInBtn;
    private EditText usernameEt, passwordEt;
    String username = null;
    String userID = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        signInBtn = findViewById(R.id.signInBtn);
        usernameEt = findViewById(R.id.usernameEditText);
        passwordEt = findViewById(R.id.passwordEditText);
        usernameEt.setText((CharSequence) "Kaiwen");
        passwordEt.setText((CharSequence) "123456");

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findUser();
            }
        });

    }


    public void findUser(){
        // Returns user document or {"documents":[]}
        JsonObject userObj = new JsonObject();
//        System.out.println(usernameEt.getText());
//        System.out.println(passwordEt.getText());
        userObj.addProperty("name", usernameEt.getText().toString());
        userObj.addProperty("password", passwordEt.getText().toString());

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
//                System.out.println("the user " + message.toString());

                JsonArray userArray = message.getAsJsonArray("documents");
//                System.out.println(userArray.toString());
                if (userArray.isEmpty()) {
                    // cannot find the legal user
//                    System.out.println("userArray is Empty!");
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                } else {
                    // process the legal user
                    JsonElement userJsonObject = userArray.get(0);
                    HashMap userMap = new Gson().fromJson(userJsonObject.toString(), HashMap.class);

                    username = userMap.get("name").toString();
                    userID = userMap.get("_id").toString();
                    finalProjectActivity();
                }
            }
        };

        new DataFunctions().findUser(handleMessage, userObj);
    }


    public void finalProjectActivity() {
        Intent intent = new Intent(this, FinalProjectActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("userID", userID);
        startActivity(intent);
        // finish the LogInActivity to avoid returning to the LogInActivity
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", usernameEt.getText().toString());
        outState.putString("password", passwordEt.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        usernameEt.setText(savedInstanceState.getString("username"));
        passwordEt.setText(savedInstanceState.getString("password"));
    }
}