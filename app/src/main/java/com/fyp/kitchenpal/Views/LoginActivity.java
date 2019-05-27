package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.Repository.SharedPrefs;

public class LoginActivity extends AppCompatActivity {

    Button loginBt;
    Button signUpBT;
    Button adminLoginBT;
    EditText emailET;
    EditText passwordET;
    SharedPrefs sharedPrefs;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button newPasswordBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        newPasswordBT = findViewById(R.id.changePasswordBT);
        emailET = findViewById(R.id.adminLoginEmail);
        passwordET = findViewById(R.id.adminLoginPassword);
        loginBt = findViewById(R.id.adminLoginBT);
        signUpBT = findViewById(R.id.btnSignup);
        adminLoginBT = findViewById(R.id.loginAsAdmin);
        sharedPrefs = new SharedPrefs(this);

        if (sharedPrefs.isLoggedInAsUser()) {
            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
            return;
        }

        if (sharedPrefs.isLoggedInAsAdmin()) {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        adminLoginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("HELL", "onClick: ");
                Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        newPasswordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.matches(emailPattern)) {
                    Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() <4 ) {
                    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!sharedPrefs.getEmail().equals(email)) {
                    Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!sharedPrefs.getPassword().equals(password)) {
                    Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                sharedPrefs.loginAsUser(true);
                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
