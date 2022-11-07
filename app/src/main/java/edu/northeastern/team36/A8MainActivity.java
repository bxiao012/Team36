package edu.northeastern.team36;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class A8MainActivity extends AppCompatActivity {

    private static final String TAG = "A8MainActivity";
    private static final int STICKER_NUMBER = 4;

    private DatabaseReference myDB;
    private TextView helloCurrentUser;
    String currentUser = "NONE";
    private TextView loginUsername;
    private TextView toUser;
    private Button btnLogin;
    private Button btnSend;
    private Button btnShowReceived;
    private Button btnShowSent;
    private TextView txtReceived;
    private TextView txtSent;
    private RadioButton btn1, btn2, btn3, btn4;
    private RadioGroup radioGroup;
    private ArrayList<RadioButton> radioButtons = new ArrayList<>();
    private Map<RadioButton, String> btnImageMap = new HashMap<>();
    private String selected;


    private Map<String, Integer> sentDict = new HashMap<>();
    private List<String> stickers = new ArrayList<>();


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
        btnShowReceived = (Button) findViewById(R.id.buttonShowReceived);
        btnShowSent = (Button) findViewById(R.id.buttonShowSent);

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
        for (int i=0; i < STICKER_NUMBER; i++) {
            String currStickerId = "sticker" + i;
            stickers.add(currStickerId);
            sentDict.put(currStickerId, 0);
        }
        btnImageMap.put(btn1, stickers.get(0));
        btnImageMap.put(btn2, stickers.get(1));
        btnImageMap.put(btn3, stickers.get(2));
        btnImageMap.put(btn4, stickers.get(3));
//        initImage();

        //Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helloCurrentUser.setText("Hello, " + loginUsername.getText().toString());
                currentUser = loginUsername.getText().toString();
                // init received and sent history
                txtReceived.setText("Received");
                txtSent.setText("Sent:\n\n");
                for (int i = 0; i < STICKER_NUMBER; i++) {
                    String currStickerId = "sticker" + i;
                    sentDict.put(currStickerId, 0);
                }
                // Connect database and add listener
                connectDatabaseAddListener();
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

        // show received history
        btnShowReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int zeroLine = 0;
                if (txtReceived.getMaxLines() == zeroLine) {
                    txtReceived.setMaxLines(Integer.MAX_VALUE);
                    btnShowReceived.setText("Close Received");
                } else {
                    txtReceived.setMaxLines(zeroLine);
                    btnShowReceived.setText("Show Received");

                }
            }
        });

        // show sent history
        btnShowSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int zeroLine = 0;
                if (txtSent.getMaxLines() == zeroLine) {
                    txtSent.setMaxLines(Integer.MAX_VALUE);
                    btnShowSent.setText("Close Sent");
                } else {
                    txtSent.setMaxLines(zeroLine);
                    btnShowSent.setText("Show Sent");

                }
            }
        });

    }

    // Connect database and add listener
    private void connectDatabaseAddListener() {
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

    public void selectImage(View view){
//        for (RadioButton radioButton : radioButtons){
//            radioButton.setChecked(false);
//        }
        RadioButton thisRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
//        thisRadioButton.setChecked(true);
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
            createNotificationChannel();
            sendNotification();
        }
        if (message.fromUsername.equals(currentUser)){
            String currSticker = message.stickerID;
            if (sentDict.containsKey(currSticker)) {
                sentDict.put(currSticker, sentDict.get(currSticker) + 1);
                txtSent.setText(sentString(sentDict));
            }
        }



    }

    // add to the sent history
    private String sentString(Map<String, Integer> sentMap) {
        StringBuilder sentBuilder = new StringBuilder("Sent:\n\n");
        for (String sticker : sentMap.keySet()){
            sentBuilder.append(sticker + " " + sentMap.get(sticker).toString() + "\n");
        }

        return sentBuilder.toString();
    }

    // create notification
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "New Sticker";
            String description = "A new sticker is sent from someone else!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("STICKER_NOTIFICATION", name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification() {
        // no action, just display the image and text
        // build notification
        String channelId = "STICKER_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("New Sticker!")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_team36_foreground);

//        // show image
        Bitmap bitmap = null;
        if (selected == null) {
            return;
        } else if (selected.equals(stickers.get(0))) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
        } else if (selected.equals(stickers.get(1))) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
        } else if (selected.equals(stickers.get(2))) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.strawberry);
        } else if (selected.equals(stickers.get(3))) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.watermelon);
        }
        builder.setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification noti = builder.build();
        notificationManager.notify(1, noti);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("helloCurrentUser", helloCurrentUser.getText().toString());
        outState.putString("currentUser", currentUser);
        outState.putString("currentToUser", toUser.getText().toString());
        outState.putInt("selectedButton", radioGroup.getCheckedRadioButtonId());
        outState.putString("selected", selected);

        outState.putString("txtReceived", txtReceived.getText().toString());
        outState.putString("txtSent", txtSent.getText().toString());
        outState.putInt("txtReceivedMaxLine", txtReceived.getMaxLines());
        outState.putInt("txtSentMaxLine", txtSent.getMaxLines());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        helloCurrentUser.setText(savedInstanceState.getString("helloCurrentUser"));
        currentUser = savedInstanceState.getString("currentUser"); // 可能出问题，把setOnclick改成函数这里也call一下
        connectDatabaseAddListener();

        radioGroup.check(savedInstanceState.getInt("selectedButton"));
        selected = savedInstanceState.getString("selected");

        toUser.setText(savedInstanceState.getString("currentToUser"));
        txtReceived.setText(savedInstanceState.getString("txtReceived"));
        txtSent.setText(savedInstanceState.getString("txtSent"));

        int txtReceivedMaxLine, txtSentMaxLine, zeroLine = 0;
        txtReceivedMaxLine = savedInstanceState.getInt("txtReceivedMaxLine");
        txtSentMaxLine = savedInstanceState.getInt("txtSentMaxLine");
        txtReceived.setMaxLines(txtReceivedMaxLine);
        txtSent.setMaxLines(txtSentMaxLine);
        btnShowReceived.setText(txtReceivedMaxLine == zeroLine ? "Show Received" : "Close Received");
        btnShowSent.setText(txtSentMaxLine == zeroLine ? "Show Sent" : "Close Sent");


    }
}