package edu.northeastern.team36.FinalProject;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.team36.R;

public class FinalProjectActivity extends AppCompatActivity {
    private RecyclerView postsRv;
    private RecyclerView postRecyclerView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_project);

        postArrayList = new ArrayList<>();
        Post testPost = new Post("postID", "Kaiwen", "description: balabala", "Test Post"
        , "Genshin Impact", "2022.11.22", 4);
        postArrayList.add(testPost);
        postRecyclerView = findViewById(R.id.recyclerViewPosts);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList, this);
        postRecyclerView.setAdapter(postAdapter);


    }



}
