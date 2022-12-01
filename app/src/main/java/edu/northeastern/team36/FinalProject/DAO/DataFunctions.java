package edu.northeastern.team36.FinalProject.DAO;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import edu.northeastern.team36.Retrofit.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataFunctions {





    class getAllPostsRunnable implements Runnable {
        private MyRunnable runnable;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);
            JsonObject req = new JsonObject();


            req.addProperty("dataSource", "Cluster0");
            req.addProperty("database", "team36_db");
            req.addProperty("collection", "posts");
            //JsonObject filter = new JsonObject();
            //filter.addProperty("gameName", "DOTA");
            //req.add("filter", filter);



            Call<JsonObject> call = retrofitInterface.getAllPosts(req);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject res = response.body();
                    Log.d("response", String.valueOf(response.body()));
                    try {
                        handler.post(runnable.setParam(res));


                    } catch (NumberFormatException e) {
                        Log.d("catch", String.valueOf(response.body()));

                    } finally {
                        Log.d("finally", String.valueOf(response.body()));

                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("onFailure", "failure");
                }
            });



        }

    }
    public void getAllPosts(MyRunnable runnable) {

        getAllPostsRunnable getAllPostsRunnable = new getAllPostsRunnable();
        getAllPostsRunnable.setRunnable(runnable);
        new Thread(getAllPostsRunnable).start();

    }


    class getReviewByUserRunnable implements Runnable {
        private MyRunnable runnable;
        private JsonObject paramObj;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }
        public void setParamObj(JsonObject obj) {
            this.paramObj = obj;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);
            JsonObject req = new JsonObject();


            req.addProperty("dataSource", "Cluster0");
            req.addProperty("database", "team36_db");
            req.addProperty("collection", "reviews");
            //JsonObject filter = new JsonObject();
            //filter.addProperty("", "DOTA");
            req.add("filter", paramObj);



            Call<JsonObject> call = retrofitInterface.getAllPosts(req);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject res = response.body();
                    Log.d("response", String.valueOf(response.body()));
                    try {
                        handler.post(runnable.setParam(res));


                    } catch (NumberFormatException e) {
                        Log.d("catch", String.valueOf(response.body()));

                    } finally {
                        Log.d("finally", String.valueOf(response.body()));

                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("onFailure", "failure");
                }
            });



        }

    }
    public void getReviewByUser(MyRunnable runnable, JsonObject paramObj) {

        getReviewByUserRunnable getReviewByUserRunnable = new getReviewByUserRunnable();
        getReviewByUserRunnable.setParamObj(paramObj);
        getReviewByUserRunnable.setRunnable(runnable);
        new Thread(getReviewByUserRunnable).start();

    }



}

