package edu.northeastern.team36.FinalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import edu.northeastern.team36.R;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("EXTRA_TITLE");
        String authorName = intent.getStringExtra("AUTHOR_NAME");
        String description = intent.getStringExtra("DESCRIPTION");
        int seat = intent.getIntExtra("SEAT", 3);
        String game = intent.getStringExtra("GAME");

        TextView titleTv = (TextView) findViewById(R.id.titleTv2);
        TextView authorTv = (TextView) findViewById(R.id.postAuthorTv2);
        TextView descriptionTv = (TextView) findViewById(R.id.descriptionTv2);
        TextView seatTv = (TextView) findViewById(R.id.seatTv2);
        TextView gameTv = (TextView) findViewById(R.id.gameTv2);

        titleTv.setText(title);
        authorTv.setText(authorName);
        descriptionTv.setText(description);
        seatTv.setText("seat: " + seat);
        gameTv.setText(game);

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("IMAGE");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView image = (ImageView) findViewById(R.id.imageTv2);

        image.setImageBitmap(bmp);
    }
}