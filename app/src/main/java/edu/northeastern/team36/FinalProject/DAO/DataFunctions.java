package edu.northeastern.team36.FinalProject.DAO;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
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

    class createPostRunnable implements Runnable {
        private JsonObject post;
        private JsonObject imageObj;
        private MyRunnable runnable;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }
        public void setPost(JsonObject post) {
            this.post = post;
        }
        public void setImageObj(JsonObject imageObj) {
            this.imageObj = imageObj;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);

            JsonObject req1 = new JsonObject();
            req1.addProperty("dataSource", "Cluster0");
            req1.addProperty("database", "team36_db");
            req1.addProperty("collection", "images");
            req1.add("document", imageObj);

            JsonObject req2 = new JsonObject();
            req2.addProperty("dataSource", "Cluster0");
            req2.addProperty("database", "team36_db");
            req2.addProperty("collection", "posts");


            Call<JsonObject> call1 = retrofitInterface.uploadImage(req1);
            call1.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call1, Response<JsonObject> response) {
                    JsonObject res = response.body();
                    String imgId = res.get("insertedId").getAsString();
                    JsonObject imgIdObj = new JsonObject();
                    imgIdObj.addProperty("$oid", imgId);
                    post.add("image",imgIdObj);
                    //post.addProperty("image",imgId);
                    req2.add("document", post);
                    Call<JsonObject> call2 = retrofitInterface.createPost(req2);
                    call2.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call2, Response<JsonObject> response) {
                            JsonObject res = response.body();
                            Log.d("response2", String.valueOf(response.body()));
                            try {
                                handler.post(runnable.setParam(res));
                            } catch (NumberFormatException e) {
                                Log.d("catch2", String.valueOf(response.body()));

                            } finally {
                                Log.d("finally2", String.valueOf(response.body()));

                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call2, Throwable t) {
                            Log.d("onFailure2", "failure");
                        }
                    });
                    Log.d("response1", String.valueOf(response.body()));
                    try {
                        //handler.post(runnable.setParam(res));
                    } catch (NumberFormatException e) {
                        Log.d("catch1", String.valueOf(response.body()));

                    } finally {
                        Log.d("finally1", String.valueOf(response.body()));

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call1, Throwable t) {
                    Log.d("onFailure1", "failure");
                }
            });

        }
    }
    public void createPost(MyRunnable runnable, JsonObject post, JsonObject imageObj) {
        createPostRunnable createPostRunnable = new createPostRunnable();
        createPostRunnable.setPost(post);
        createPostRunnable.setImageObj(imageObj);
        createPostRunnable.setRunnable(runnable);
        new Thread(createPostRunnable).start();
    }

    class updatePostRunnable implements Runnable {
        private JsonObject postId;
        private JsonObject updateObj;
        private MyRunnable runnable;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }
        public void setPostId(JsonObject postId) {
            this.postId = postId;
        }
        public void setUpdateObj(JsonObject updateObj) {
            this.updateObj = updateObj;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);

            JsonObject req = new JsonObject();

            req.addProperty("dataSource", "Cluster0");
            req.addProperty("database", "team36_db");
            req.addProperty("collection", "posts");
            JsonObject filter = new JsonObject();
            req.add("filter", postId);
            JsonObject update = new JsonObject();
            update.add("$set", updateObj);
            req.add("update", update);

            Call<JsonObject> call = retrofitInterface.updatePost(req);
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
    public void updatePost(MyRunnable runnable, JsonObject postId, JsonObject updateObj) {

        updatePostRunnable updatePostRunnable = new updatePostRunnable();
        updatePostRunnable.setPostId(postId);
        updatePostRunnable.setUpdateObj(updateObj);
        updatePostRunnable.setRunnable(runnable);
        new Thread(updatePostRunnable).start();

    }

    class findUserRunnable implements Runnable {
        private JsonObject userObj;
        private MyRunnable runnable;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }
        public void setUserObj(JsonObject userObj) {
            this.userObj = userObj;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);
            JsonObject req = new JsonObject();


            req.addProperty("dataSource", "Cluster0");
            req.addProperty("database", "team36_db");
            req.addProperty("collection", "users");
            req.add("filter", userObj);



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
    public void findUser(MyRunnable runnable, JsonObject userObj) {

        findUserRunnable findUserRunnable = new findUserRunnable();
        findUserRunnable.setUserObj(userObj);
        findUserRunnable.setRunnable(runnable);
        new Thread(findUserRunnable).start();

    }

    class findPostsRunnable implements Runnable {
        private JsonObject paramObj;
        private MyRunnable runnable;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }
        public void setParamObj(JsonObject paramObj) {
            this.paramObj = paramObj;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);
            JsonObject req = new JsonObject();


            req.addProperty("dataSource", "Cluster0");
            req.addProperty("database", "team36_db");
            req.addProperty("collection", "posts");
            req.add("filter", paramObj);



            Call<JsonObject> call = retrofitInterface.findPosts(req);
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
    public void findPosts(MyRunnable runnable, JsonObject paramObj) {

        findPostsRunnable findPostsRunnable = new findPostsRunnable();
        findPostsRunnable.setParamObj(paramObj);
        findPostsRunnable.setRunnable(runnable);
        new Thread(findPostsRunnable).start();

    }

    class createReviewRunnable implements Runnable {
        private JsonObject reviewObj;
        private MyRunnable runnable;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }
        public void setReviewObj(JsonObject reviewObj) {
            this.reviewObj = reviewObj;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);
            JsonObject req = new JsonObject();


            req.addProperty("dataSource", "Cluster0");
            req.addProperty("database", "team36_db");
            req.addProperty("collection", "reviews");
            req.add("document", reviewObj);



            Call<JsonObject> call = retrofitInterface.createReview(req);
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
    public void createReview(MyRunnable runnable, JsonObject reviewObj) {

        createReviewRunnable createReviewRunnable = new createReviewRunnable();
        createReviewRunnable.setReviewObj(reviewObj);
        createReviewRunnable.setRunnable(runnable);
        new Thread(createReviewRunnable).start();

    }

    class findReviewsRunnable implements Runnable {
        private JsonObject paramObj;
        private MyRunnable runnable;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }
        public void setParamObj(JsonObject paramObj) {
            this.paramObj = paramObj;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);
            JsonObject req = new JsonObject();


            req.addProperty("dataSource", "Cluster0");
            req.addProperty("database", "team36_db");
            req.addProperty("collection", "reviews");
            req.add("filter", paramObj);



            Call<JsonObject> call = retrofitInterface.findReviews(req);
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
    public void findReviews(MyRunnable runnable, JsonObject paramObj) {

        findReviewsRunnable findReviewsRunnable = new findReviewsRunnable();
        findReviewsRunnable.setParamObj(paramObj);
        findReviewsRunnable.setRunnable(runnable);
        new Thread(findReviewsRunnable).start();

    }

    class getImageRunnable implements Runnable {
        private JsonObject paramObj;
        private MyRunnable runnable;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }
        public void setParamObj(JsonObject paramObj) {
            this.paramObj = paramObj;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);
            JsonObject req = new JsonObject();


            req.addProperty("dataSource", "Cluster0");
            req.addProperty("database", "team36_db");
            req.addProperty("collection", "images");
            req.add("filter", paramObj);



            Call<JsonObject> call = retrofitInterface.getImage(req);
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
    public void findImage(MyRunnable runnable, JsonObject paramObj) {

        getImageRunnable getImageRunnable = new getImageRunnable();
        getImageRunnable.setParamObj(paramObj);
        getImageRunnable.setRunnable(runnable);
        new Thread(getImageRunnable).start();

    }

    class findPostsWithImageRunnable implements Runnable {
        private JsonObject paramObj;
        private MyRunnable runnable;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }
        public void setParamObj(JsonObject paramObj) {
            this.paramObj = paramObj;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);
            JsonObject req = new JsonObject();


            req.addProperty("dataSource", "Cluster0");
            req.addProperty("database", "team36_db");
            req.addProperty("collection", "posts");
            JsonArray arr = new JsonArray();
            JsonObject match = new JsonObject();
            match.add("$match",paramObj);
            arr.add(match);
            JsonObject lookup = new JsonObject();
            JsonObject lookupObj = new JsonObject();
            lookupObj.addProperty("from","images");
            lookupObj.addProperty("localField","image");
            lookupObj.addProperty("foreignField","_id");
            lookupObj.addProperty("as","imageDetail");
            lookup.add("$lookup", lookupObj);
            arr.add(lookup);
            req.add("pipeline", arr);

            Call<JsonObject> call = retrofitInterface.findPostsWithImage(req);
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
    public void findPostsWithImage(MyRunnable runnable, JsonObject paramObj) {

        findPostsWithImageRunnable findPostsWithImageRunnable = new findPostsWithImageRunnable();
        findPostsWithImageRunnable.setParamObj(paramObj);
        findPostsWithImageRunnable.setRunnable(runnable);
        new Thread(findPostsWithImageRunnable).start();

    }

    class getAvgRateRunnable implements Runnable {
        private JsonObject paramObj;
        private MyRunnable runnable;
        private Handler handler = new Handler();
        public void setRunnable(MyRunnable runnable) {
            this.runnable = runnable;
        }
        public void setParamObj(JsonObject paramObj) {
            this.paramObj = paramObj;
        }

        @Override
        public void run() {
            Looper.prepare();

            FinalRetrofitInterface retrofitInterface = (FinalRetrofitInterface) FinalRetrofitBuilder.getRetrofitInstance().create(FinalRetrofitInterface.class);
            JsonObject req = new JsonObject();


            req.addProperty("dataSource", "Cluster0");
            req.addProperty("database", "team36_db");
            req.addProperty("collection", "reviews");
            JsonArray arr = new JsonArray();
            JsonObject match = new JsonObject();
            match.add("$match",paramObj);
            arr.add(match);
            JsonObject group = new JsonObject();
            JsonObject groupObj = new JsonObject();
            groupObj.addProperty("_id","");
            JsonObject avgObj = new JsonObject();
            avgObj.addProperty("$avg", "$rate");
            groupObj.add("avgRate",avgObj);
            group.add("$group", groupObj);
            arr.add(group);
            req.add("pipeline", arr);

            Call<JsonObject> call = retrofitInterface.getAvgRate(req);
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
    public void getAvgRate(MyRunnable runnable, JsonObject paramObj) {

        getAvgRateRunnable getAvgRateRunnable = new getAvgRateRunnable();
        getAvgRateRunnable.setParamObj(paramObj);
        getAvgRateRunnable.setRunnable(runnable);
        new Thread(getAvgRateRunnable).start();

    }







}

