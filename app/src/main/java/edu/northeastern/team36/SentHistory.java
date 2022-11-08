package edu.northeastern.team36;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentHistory extends AppCompatActivity {
    private static final String TAG = "SentHistory";
    private Map<String, Integer> sentDict;
    private RecyclerView sentHistoryRecyclerView;
    private SentAdapter sentAdapter;
    private ArrayList<String> stickers;
    private List<Sent> sentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_history);
        sentDict = (HashMap<String, Integer>) getIntent().getSerializableExtra("sentDict");
        stickers = getIntent().getStringArrayListExtra("stickers");

        sentHistoryRecyclerView = findViewById(R.id.recyclerViewSentHistory);
        sentHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sentDict.forEach((stickerID, stickerCount) -> {
            sentList.add(new Sent(stickerID, stickerCount));
        });
        sentAdapter = new SentAdapter(sentList, stickers, this);
        sentHistoryRecyclerView.setAdapter(sentAdapter);
    }
}