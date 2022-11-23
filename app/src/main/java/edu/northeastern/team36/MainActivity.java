package edu.northeastern.team36;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.northeastern.team36.FinalProject.FinalProjectActivity;

public class MainActivity extends AppCompatActivity {

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
        Intent intent = new Intent(this, FinalProjectActivity.class);
        startActivity(intent);
    }
}