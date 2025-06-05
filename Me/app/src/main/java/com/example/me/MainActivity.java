package com.example.me;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView currencyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyTextView = findViewById(R.id.currencyTextView);
        fetchExchangeRate();
    }

    private void fetchExchangeRate() {
        String url = "https://api.exchangerate-api.com/v4/latest/USD";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            double rate = response.getJSONObject("rates").getDouble("CNY");
                            String currency = String.format(Locale.getDefault(), "%.4f", rate);
                            currencyTextView.setText(currency);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            currencyTextView.setText("解析错误");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        currencyTextView.setText("网络错误");
                    }
                });

        queue.add(jsonObjectRequest);
    }
}