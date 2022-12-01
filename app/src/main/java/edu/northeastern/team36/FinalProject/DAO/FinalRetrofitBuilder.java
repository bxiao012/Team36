package edu.northeastern.team36.FinalProject.DAO;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FinalRetrofitBuilder {
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://data.mongodb-api.com/app/data-cnyjt/endpoint/data/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
