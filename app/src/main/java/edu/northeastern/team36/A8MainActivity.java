package edu.northeastern.team36;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class A8MainActivity extends AppCompatActivity {

    private DatabaseReference myDB;
    private TextView helloCurrentUser;
    String currentUser = "NONE";
    private TextView loginUsername;
    private Spinner imageID;
    private TextView toUser;
    private Button btnLogin;
    private Button btnSend;
    private TextView txtReceived;
    private TextView txtSent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a8_main);

        helloCurrentUser = (TextView) findViewById(R.id.textView3);
        loginUsername = (TextView) findViewById(R.id.userInput);
        imageID = (Spinner) findViewById(R.id.imageID);
        toUser = (TextView) findViewById(R.id.toUser);
        btnLogin = (Button) findViewById(R.id.button3);
        btnSend = (Button) findViewById(R.id.button4);
        txtReceived = (TextView) findViewById(R.id.textView4);
        txtSent = (TextView) findViewById(R.id.textView5);




        String[] currencyList = {"sticker0", "sticker1","sticker2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,currencyList);
        imageID.setAdapter(adapter);

        //Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helloCurrentUser.setText("Hello, " + loginUsername.getText().toString());
                currentUser = loginUsername.getText().toString();
            }
        });





        // Connect with firebase
        myDB = FirebaseDatabase.getInstance().getReference();

        // Insert 1 message object into DB

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser == "NONE"){
                    // Ask to login
                } else {
                    String messageID = "message_" + UUID.randomUUID().toString();
                    String fromUsername = currentUser;
                    String toUsername = toUser.getText().toString();
                    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
                    String stickerID = imageID.getSelectedItem().toString();

                    writeNewMessage(messageID, fromUsername, toUsername, timestamp, stickerID);
                }



            }
        });


    }

    public void writeNewMessage(String messageID, String fromUsername, String toUsername, String timestamp, String stickerID) {
        RTDBMessage message = new RTDBMessage(fromUsername, toUsername, timestamp, stickerID);

        myDB.child("messages").child(messageID).setValue(message);
    }
}