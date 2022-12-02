package edu.northeastern.team36.FinalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                Intent intent = new Intent();
                intent.putExtra("result", new String[]
                        {timeInput.getText().toString(),
                        gameInput.getText().toString(),
                        authorNameInput.getText().toString(),
                        timeInput.getText().toString(),
                        descriptionInput.getText().toString(),
                        seatsInput.getText().toString()});
                setResult(36, intent);

                // go back
                CreatePostActivity.super.onBackPressed();
            }
        });
    }
}