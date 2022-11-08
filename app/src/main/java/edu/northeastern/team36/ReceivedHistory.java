package edu.northeastern.team36;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class ReceivedHistory extends AppCompatActivity {
    private static final String TAG = "ReceivedHistory";
    private RecyclerView receivedHistoryRecyclerView;
    private ReceivedAdapter receivedAdapter;
    private ArrayList<Received> receivedList;
    private ArrayList<String> stickers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_history);
        Bundle extras = getIntent().getExtras();
        receivedList = extras.getParcelableArrayList("receivedList");
        stickers = extras.getStringArrayList("stickers");

        receivedHistoryRecyclerView = findViewById(R.id.recyclerViewReceivedHistory);
        receivedHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        receivedAdapter = new ReceivedAdapter(receivedList, stickers, this);
        receivedHistoryRecyclerView.setAdapter(receivedAdapter);

    }

}