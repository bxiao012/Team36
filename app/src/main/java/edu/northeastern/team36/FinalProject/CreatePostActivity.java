package edu.northeastern.team36.FinalProject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.northeastern.team36.FinalProject.DAO.DataFunctions;
import edu.northeastern.team36.FinalProject.DAO.MyRunnable;
import edu.northeastern.team36.R;

public class CreatePostActivity extends AppCompatActivity {
    int LOCATION_REQUEST_CODE = 10001;
    EditText titleInput;
    EditText gameInput;
    EditText authorNameInput;
    EditText timeInput;
    EditText descriptionInput;
    EditText seatsInput;
    Button submitBtn;
    Button locationBtn;
    Button imageBtn;
    private String location;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            if (locationResult == null) {
                Log.d(TAG, "onLocationResult: somewhere");
                // default location
                location = "SomeWhere";
            }
            for (Location loc: locationResult.getLocations()) {
                Log.d(TAG, "onLocationResult: " + loc.toString());
                location = loc.toString();
                System.out.println(location);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CreatePostActivity.this);
        // location request
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setNumUpdates(1);

        // get location
        locationBtn = (Button) findViewById(R.id.locationButton);

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
                Log.d(TAG, "onClick: click submitted");

                // create json object
                JsonObject post = new JsonObject();
                post.addProperty("title",titleInput.getText().toString());
                post.addProperty("content", descriptionInput.getText().toString());
                post.addProperty("gameName",gameInput.getText().toString());
                post.addProperty("seat", Integer.parseInt(seatsInput.getText().toString()) );
                Log.d(TAG, "onClick: " + location);
                post.addProperty("location", location);
                post.addProperty("gameTime", timeInput.getText().toString());
                post.addProperty("status", "Closed");
                String formatDate= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                post.addProperty("createTime", formatDate);
                JsonObject owner = new JsonObject();
                JsonObject ownerID = new JsonObject();
                ownerID.addProperty("$oid", "637ce04eb5eb013ea20e7010");
                owner.addProperty("name","user10");
                owner.add("id", ownerID);
                post.add("owner", owner);
                post.add("applied", new JsonArray());
                post.add("selected", new JsonArray());
                JsonObject imageObj = new JsonObject();
                imageObj.addProperty("img","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMAAAACoCAYAAAC2e+");
                imageObj.addProperty("uploadTime", formatDate);
                MyRunnable handleMessage = new MyRunnable() {
                    JsonObject message;
                    @Override
                    public MyRunnable setParam(JsonObject param) {
                        message = param;
                        return this;
                    }

                    @Override
                    public void run() {
                        handleMessage(message);
                    }

                    private void handleMessage(JsonObject message) {
                        Log.d(TAG, "post is successfully created!");
                    }
                };

                new DataFunctions().createPost(handleMessage, post, imageObj);
                // go back
                CreatePostActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            getLastLocation();
            checkSettingsAndStartLocationUpdates();
        } else { // ask for the permission
            askLocationPermission();
        }
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // settings of device are satisfied and we can start location updates
                startLocationUpdates();
            }
        });

        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(CreatePostActivity.this, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "askLocationPermission: you should show an alert dialog...");
                ActivityCompat.requestPermissions
                        (this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions
                        (this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }

}