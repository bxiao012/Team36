package edu.northeastern.team36.FinalProject;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.team36.R;

public class FinalProjectActivity extends AppCompatActivity {
    private RecyclerView postsRv;
    private Button loadMoreBtn;
    private ArrayList<ModelPost> postArrayList;
    private AdapterPost adapterPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_project);

        postArrayList = new ArrayList<>();
    }



}
