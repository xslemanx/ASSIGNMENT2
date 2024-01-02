package birzeit.slemanhghithan.a2edass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txt_username;
    private EditText txt_password1;
    private CheckBox chk_rememberme;
    private Button btn_login;
    private SharedPreferences sharedPreferences;
    private TextView txt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        txt_username = findViewById(R.id.txt_username);
        txt_password1 = findViewById(R.id.txt_password1);
        chk_rememberme = findViewById(R.id.chk_rememberme);
        txt_register = findViewById(R.id.txt_register);
        btn_login = findViewById(R.id.btn_register);

        txt_register.setOnClickListener(e -> {
            Intent registerIntent = new Intent(MainActivity.this, Register.class);
            startActivity(registerIntent);
            finish();
        });


        boolean rememberMe = sharedPreferences.getBoolean("remember_me", false);
        if (rememberMe) {
            String storedUsername = sharedPreferences.getString("username", "");
            String storedPassword = sharedPreferences.getString("password", "");

            txt_username.setText(storedUsername);
            txt_password1.setText(storedPassword);
            chk_rememberme.setChecked(true);
        }

        btn_login.setOnClickListener(e -> {
            String enteredUsername = txt_username.getText().toString();
            String enteredPassword = txt_password1.getText().toString();


            String storedUsername = sharedPreferences.getString("username", "");
            String storedPassword = sharedPreferences.getString("password", "");

            if (enteredUsername.equals(storedUsername) && enteredPassword.equals(storedPassword)) {
                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();


                boolean rememberMe1 = chk_rememberme.isChecked();


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("remember_me", rememberMe1);

                if (rememberMe1) {

                    editor.putString("username", enteredUsername);
                    editor.putString("password", enteredPassword);
                } else {

                    editor.remove("username");
                    editor.remove("password");
                }

                editor.apply();

                Intent countryIntent = new Intent(MainActivity.this, Country.class);
                startActivity(countryIntent);
                finish();

            } else {
                Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }
}