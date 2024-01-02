package birzeit.slemanhghithan.a2edass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private EditText txt_username;
    private EditText txt_password1;
    private EditText txt_password2;
    private CheckBox chk_rememberme;
    private Button btn_register;
    private SharedPreferences sharedPreferences;
    private TextView txt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        setContentView(R.layout.activity_register);

        txt_username = findViewById(R.id.txt_username);
        txt_password1 = findViewById(R.id.txt_password1);
        txt_password2 = findViewById(R.id.txt_password2);
        chk_rememberme = findViewById(R.id.chk_rememberme);
        txt_login = findViewById(R.id.txt_register);
        btn_register = findViewById(R.id.btn_register);

        txt_login.setOnClickListener(e -> {
            Intent loginIntent = new Intent(Register.this, MainActivity.class);
            startActivity(loginIntent);
            finish();
        });

        btn_register.setOnClickListener(e -> {
            String username = txt_username.getText().toString();
            String password1 = txt_password1.getText().toString();
            String password2 = txt_password2.getText().toString();

            if (username.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                Toast.makeText(Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!password1.equals(password2)) {
                Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else if (userExists(username)) {
                Toast.makeText(Register.this, "Username already exists, please choose a different one", Toast.LENGTH_SHORT).show();
            } else {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("password", password1);
                editor.putBoolean("remember_me", chk_rememberme.isChecked());
                editor.apply();

                Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        retrieveData();
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", txt_username.getText().toString());
        editor.putString("password1", txt_password1.getText().toString());
        editor.putString("password2", txt_password2.getText().toString());
        editor.putBoolean("remember_me", chk_rememberme.isChecked());
        editor.apply();
    }

    private void retrieveData() {
        String storedUsername = sharedPreferences.getString("username", "");
        String storedPassword1 = sharedPreferences.getString("password1", "");
        String storedPassword2 = sharedPreferences.getString("password2", "");
        boolean rememberMe = sharedPreferences.getBoolean("remember_me", false);

        txt_username.setText(storedUsername);
        txt_password1.setText(storedPassword1);
        txt_password2.setText(storedPassword2);
        chk_rememberme.setChecked(rememberMe);
    }


    private boolean userExists(String username) {

        String existingUsername = sharedPreferences.getString("username", "");
        return existingUsername.equals(username);
    }
}
