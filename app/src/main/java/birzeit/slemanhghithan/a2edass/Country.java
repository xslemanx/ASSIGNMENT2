package birzeit.slemanhghithan.a2edass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Country extends AppCompatActivity {

    private EditText edttxtCountry;
    private Button button;
    private TextView txtCapital;
    private ImageView flagImageView;
    private RequestQueue requestQueue;
    private Button btnLogout;
    private Button btnShowDetails;

    private JSONObject countryDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        edttxtCountry = findViewById(R.id.edttxt_country);
        button = findViewById(R.id.button);
        txtCapital = findViewById(R.id.txt_capital);
        flagImageView = findViewById(R.id.img_flag);
        requestQueue = Volley.newRequestQueue(this);
        btnLogout = findViewById(R.id.btn_logout);
        btnShowDetails = findViewById(R.id.btn_show_detailes);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryName = edttxtCountry.getText().toString().trim();
                if (!countryName.isEmpty()) {
                    makeApiCall(countryName);
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent countryIntent = new Intent(Country.this, MainActivity.class);
                startActivity(countryIntent);
                finish();
            }
        });

        btnShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailsActivity();
            }
        });
    }

    private void makeApiCall(String countryName) {
        String apiUrl = "https://restcountries.com/v2/name/" + countryName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {
                                countryDetails = response.getJSONObject(0);
                                String capital = countryDetails.getString("capital");
                                String countryCode = countryDetails.getString("alpha2Code");
                                txtCapital.setText("Capital: " + capital);


                                loadFlagImage(countryCode);
                            } else {
                                txtCapital.setText("No data found");
                                flagImageView.setImageResource(android.R.color.transparent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            txtCapital.setText("Error parsing response");
                            flagImageView.setImageResource(android.R.color.transparent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtCapital.setText("API call failed");
                        flagImageView.setImageResource(android.R.color.transparent);
                    }
                });

        requestQueue.add(request);
    }

    private void loadFlagImage(String countryCode) {
        String flagUrl = "https://flagcdn.com/w320/" + countryCode.toLowerCase() + ".png";

        ImageRequest imageRequest = new ImageRequest(
                flagUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        flagImageView.setImageBitmap(response);
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER_INSIDE,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        flagImageView.setImageResource(android.R.color.darker_gray);

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            String errorMsg = new String(error.networkResponse.data);
                            Log.e("FlagError", "Status Code: " + statusCode + ", Error: " + errorMsg);
                        } else {
                            Log.e("FlagError", "Unknown error occurred");
                        }
                    }
                });

        requestQueue.add(imageRequest);
    }

    private void showDetailsActivity() {
        if (countryDetails != null) {
            Intent detailsIntent = new Intent(Country.this, detailes.class);
            detailsIntent.putExtra("country_details", countryDetails.toString());
            startActivity(detailsIntent);
        }
    }
}
