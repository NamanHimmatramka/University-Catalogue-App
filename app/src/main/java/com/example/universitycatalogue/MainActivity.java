package com.example.universitycatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button searchButtonDefault;
    EditText countryName;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButtonDefault = findViewById(R.id.searchButtonDefault);
        countryName=findViewById(R.id.countryName);
        searchButton=findViewById(R.id.searchButton);

        Locale localCountry = Locale.getDefault();
        String countryCodeValue=localCountry.getDisplayCountry();

        searchButtonDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollegeList.class);
                intent.putExtra("countryName", countryCodeValue);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CollegeList.class);
                String toPass = null;
                if(!countryName.getText().toString().isEmpty()) {
                    toPass = countryName.getText().toString().substring(0, 1).toUpperCase() + countryName.getText().toString().substring(1);
                    intent.putExtra("countryName", toPass);
                    startActivity(intent);
                }
            }
        });
    }
}