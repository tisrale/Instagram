package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                login(username, password);


            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                try {
                    Signup(username, password);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });






    }

    private void Signup(String username, String password) throws ParseException {
        // Create the ParseUser
      final   ParseUser user = new ParseUser();
// Set core properties
        user.setUsername(username);
        user.setPassword(password);
        //user.setEmail("email@example.com");
// Set custom properties
       // user.put("phone", "650-253-0000");
// Invoke signUpInBackground
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        final int usernum = query.whereMatches("username", username).count(); // find adults
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (usernum == 0) {
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e!=null){
                                Log.e(TAG, "Issue with Sign up");
                                e.printStackTrace();
                                return;
                            }
                            // ToDo: navigate to new activity if the user has signed properly
                            goMainActivity();
                        }
                    });
                } else {
                    Log.d(TAG, "use different Username");
                    Toast.makeText(LoginActivity.this, "Use new username", Toast.LENGTH_LONG).show();
                    etUsername.setText("");
                    etPassword.setText("" +      "");
                    // Something went wrong.


                }
            }
        });


    }

    private void login(String username, String password){

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e!=null){
                    Log.e(TAG, "Issue with login");
                    e.printStackTrace();
                    return;
                }
                // ToDo: navigate to new activity if the user has signed properly
                goMainActivity();

            }
        });
    }

    private void goMainActivity() {
        Log.d(TAG, "Navigating to Main Acitivity");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
