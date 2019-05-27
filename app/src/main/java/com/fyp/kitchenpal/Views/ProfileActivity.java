package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.Repository.SharedPrefs;

public class ProfileActivity extends AppCompatActivity {

    SharedPrefs sharedPrefs;
    TextView nameTV;
    TextView emailTV;
    Button logoutBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPrefs = new SharedPrefs(this);
        nameTV = findViewById(R.id.textViewname);
        emailTV = findViewById(R.id.textViewemail);
        logoutBT = findViewById(R.id.btnlogout);
        nameTV.setText(sharedPrefs.getName());
        emailTV.setText(sharedPrefs.getEmail());

        logoutBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefs.loginAsUser(false);
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
