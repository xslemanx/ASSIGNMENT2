package birzeit.slemanhghithan.a2edass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class detailes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes);

        TextView textViewDetails = findViewById(R.id.textView2);
        Button btnBack = findViewById(R.id.btn_back);
        String countryDetailsString = getIntent().getStringExtra("country_details");

        if (countryDetailsString != null) {
            try {
                JSONObject countryDetails = new JSONObject(countryDetailsString);
                displayCountryDetails(textViewDetails, countryDetails);
            } catch (JSONException e) {
                e.printStackTrace();
                textViewDetails.setText("Error parsing country details");
            }
        } else {
            textViewDetails.setText("No country details found");
        }

        btnBack.setOnClickListener(v -> finish());
    }

    private void displayCountryDetails(TextView textView, JSONObject countryDetails) {
        try {
            String name = countryDetails.getString("name");
            String capital = countryDetails.getString("capital");
            int population = countryDetails.getInt("population");
            String region = countryDetails.getString("region");
            String subregion = countryDetails.getString("subregion");

            String detailsText = "Name: " + name +
                    "\nCapital: " + capital +
                    "\nPopulation: " + population +
                    "\nRegion: " + region +
                    "\nSubregion: " + subregion;

            textView.setText(detailsText);
        } catch (JSONException e) {
            e.printStackTrace();
            textView.setText("Error parsing country details");
        }
    }
}