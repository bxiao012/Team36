package edu.northeastern.team36;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;

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
                title = (TextView) findViewById(R.id.textView);
                title.setText(message.toString());
                System.out.println("the posts " + message.toString());
            }
        };

        //JsonObject id = new JsonObject();
        //JsonObject oid = new JsonObject();
        //oid.addProperty("$oid", "637ce196b5eb013ea20e701a");
        //id.add("_id", oid);
        //new DataFunctions().getReviewByUser(handleMessage,id);
        new DataFunctions().getAllPosts(handleMessage);



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