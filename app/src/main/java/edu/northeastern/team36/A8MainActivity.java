package edu.northeastern.team36;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class A8MainActivity extends AppCompatActivity {

    private static final String TAG = "A8MainActivity";

    private DatabaseReference myDB;
    private TextView helloCurrentUser;
    String currentUser = "NONE";
    private TextView loginUsername;
    private TextView toUser;
    private Button btnLogin;
    private Button btnSend;
    private TextView txtReceived;
    private TextView txtSent;
    private RadioButton btn1, btn2, btn3, btn4;
    private RadioGroup radioGroup;
    private ArrayList<RadioButton> radioButtons = new ArrayList<>();
    private Map<RadioButton, String> btnImageMap = new HashMap<>();
    private String selected;


    private Map<String, Integer> sentDict = new HashMap();


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a8_main);
        helloCurrentUser = (TextView) findViewById(R.id.textView3);
        loginUsername = (TextView) findViewById(R.id.userInput);
        toUser = (TextView) findViewById(R.id.toUser);
        btnLogin = (Button) findViewById(R.id.button3);
        btnSend = (Button) findViewById(R.id.button4);
        txtReceived = (TextView) findViewById(R.id.textView4);
        txtSent = (TextView) findViewById(R.id.textView5);
        ImageView image1 = findViewById(R.id.imageView1);
        ImageView image2 = findViewById(R.id.imageView2);
        ImageView image3 = findViewById(R.id.imageView3);
        ImageView image4 = findViewById(R.id.imageView4);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupID);
        btn1 = findViewById(R.id.radioButton1);
        btn2 = findViewById(R.id.radioButton2);
        btn3 = findViewById(R.id.radioButton3);
        btn4 = findViewById(R.id.radioButton4);
        radioButtons.add(btn1);
        radioButtons.add(btn2);
        radioButtons.add(btn3);
        radioButtons.add(btn4);
        btnImageMap.put(btn1, "1");
        btnImageMap.put(btn2, "2");
        btnImageMap.put(btn3, "3");
        btnImageMap.put(btn4, "4");
//        initImage();

        //Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helloCurrentUser.setText("Hello, " + loginUsername.getText().toString());
                currentUser = loginUsername.getText().toString();


                // Connect with firebase
                myDB = FirebaseDatabase.getInstance().getReference();

                // Add DB updates listener
                myDB.child("messages").addChildEventListener(
                        new ChildEventListener() {

                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                showMessage(dataSnapshot);
                                Log.e(TAG, "onChildAdded: dataSnapshot = " + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                showMessage(dataSnapshot);
                                Log.v(TAG, "onChildChanged: " + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e(TAG, "onCancelled:" + databaseError);
                                Toast.makeText(getApplicationContext()
                                        , "DBError: " + databaseError, Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }
        });

        // Send message - insert 1 message object into DB
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser == "NONE"){
                    Toast.makeText(getApplicationContext(), "Please login at first!", Toast.LENGTH_LONG).show();
                } else {
                    String messageID = "message_" + UUID.randomUUID().toString();
                    String fromUsername = currentUser;
                    String toUsername = toUser.getText().toString();
                    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
                    String stickerID = selected;
                    writeNewMessage(messageID, fromUsername, toUsername, timestamp, stickerID);
                }
            }
        });


    }


    public void selectImage(View view){
        for (RadioButton radioButton : radioButtons){
            radioButton.setChecked(false);
        }
        RadioButton thisRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        thisRadioButton.setChecked(true);
        selected = btnImageMap.get(thisRadioButton);
    }

    public void writeNewMessage(String messageID, String fromUsername, String toUsername, String timestamp, String stickerID) {
        RTDBMessage message = new RTDBMessage(fromUsername, toUsername, timestamp, stickerID);

        myDB.child("messages").child(messageID).setValue(message);
    }


    private void showMessage(DataSnapshot dataSnapshot) {
        RTDBMessage message = dataSnapshot.getValue(RTDBMessage.class);
        if (message.toUsername.equals(currentUser)){
            txtReceived.append("\n\nfrom: " + message.fromUsername + "\nstickerID: " + message.stickerID + "\ntime: " + message.timestamp);
        }
        if (message.fromUsername.equals(currentUser)){
            String currSticker = message.stickerID;
            if (sentDict.containsKey(currSticker)) {
                sentDict.put(currSticker, sentDict.get(currSticker) + 1);
                txtSent.setText(sentString(sentDict));
            }
        }



    }

    private String sentString(Map<String, Integer> sentMap) {
        StringBuilder sentBuilder = new StringBuilder("Sent:\n");
        for (String sticker : sentMap.keySet()){
            sentBuilder.append(sticker + " " + sentMap.get(sticker).toString() + "\n");
        }

        return sentBuilder.toString();
    }

}