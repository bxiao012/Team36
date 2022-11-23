package edu.northeastern.team36.FinalProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.team36.R;

public class AdapterPost extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<edu.northeastern.team36.FinalProject.ModelPost> postArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.posts,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelPost model = postArrayList.get(position);
        String authorName = model.getAuthorName();
        String postID = model.getPostID();
        String description = model.getDescription();
        String title = model.getTitle();
        String time = model.getTime();

    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    class HolderPost extends RecyclerView.ViewHolder{

        //UI Views of posts.xml
        ImageButton detailBtn;
        TextView titleTv,postInfoTv,detailTv;

        public HolderPost(@NonNull View itemView){
            super(itemView);

            //Init UI View
            detailBtn = itemView.findViewById(R.id.detailBtn);
            titleTv = itemView.findViewById(R.id.titleTv);
            postInfoTv = itemView.findViewById(R.id.postInfoTv);
            detailTv = itemView.findViewById(R.id.detailTv);
        }
    }
}
