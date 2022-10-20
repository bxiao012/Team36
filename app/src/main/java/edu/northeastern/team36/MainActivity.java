package edu.northeastern.team36;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // At Your Service button
    Button btnService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Redirect to the At Your Service activity
        btnService = findViewById(R.id.button);
        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toServiceActivity = new Intent(MainActivity.this, AtYourServiceActivity.class);
                startActivity(toServiceActivity);
            }
        });
    }
}