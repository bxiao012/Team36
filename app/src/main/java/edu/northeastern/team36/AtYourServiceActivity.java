package edu.northeastern.team36;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.gson.JsonObject;
import edu.northeastern.team36.Retrofit.RetrofitBuilder;
import edu.northeastern.team36.Retrofit.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtYourServiceActivity extends AppCompatActivity {
    EditText currencyToText;
    EditText currencyFromText;
    Spinner convertToDropdown;
    Spinner convertFromDropdown;
    Button btnCalculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        currencyToText = (EditText) findViewById(R.id.toValue);
        currencyFromText = (EditText) findViewById(R.id.fromValue);
        convertToDropdown = (Spinner) findViewById(R.id.toCurrency);
        convertFromDropdown = (Spinner) findViewById(R.id.fromCurrency);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);

        final LoadingAlertDialog loadingAlertDialog = new LoadingAlertDialog(AtYourServiceActivity.this);

        String[] currencyList = {"USD", "AOA","ARS","AUD","CAD","CNY","EUR","GBP","INR","LAK","LBP","MOP","NZD","VUV","WST","ZAR","ZWL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,currencyList);
        convertFromDropdown.setAdapter(adapter);
        convertToDropdown.setAdapter(adapter);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAlertDialog.startLoadingAlertDialog();

                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
                Call<JsonObject> call = retrofitInterface.getExchangeCurrency(convertFromDropdown.getSelectedItem().toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        Log.d("response", String.valueOf(response.body()));
                        JsonObject rates = res.getAsJsonObject("rates");
                        double currency = Double.valueOf(currencyFromText.getText().toString());
                        double multiplier = Double.valueOf(rates.get(convertToDropdown.getSelectedItem().toString()).toString());
                        double result = currency * multiplier;
                        currencyToText.setText(String.valueOf(result));

                        loadingAlertDialog.stopLoadingAlertDialog();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                    }
                });
            }
        });



    }
}