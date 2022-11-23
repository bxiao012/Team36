package edu.northeastern.team36.FinalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.northeastern.team36.R;

public class LogInActivity extends AppCompatActivity {

    private Button signInBtn, signUpBtn;
    private EditText usernameEt, passwordEt;
    String username = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        usernameEt = findViewById(R.id.usernameEditText);
        passwordEt = findViewById(R.id.passwordEditText);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true) {
                    // update username
                    finalProjectActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Error Message",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true) {
                    // update username
                    finalProjectActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Duplicate username!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void finalProjectActivity() {
        Intent intent = new Intent(this, FinalProjectActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
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