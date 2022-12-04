package edu.northeastern.team36.FinalProject.DAO;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FinalRetrofitInterface {

    @Headers({"Content-Type: application/json",
            "api-key: 0zwKEn5FdEABALtu6HaOT3HRgoAgGWzPkGwRIq18CjMvHWH9W66yusDCmQGC7O8w"
    })
    @POST("action/find")
    Call<JsonObject> getAllPosts(@Body JsonObject data);



}