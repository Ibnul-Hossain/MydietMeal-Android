package com.mydietmeal.mydietmealupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Packages extends AppCompatActivity  {


    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);


    }

    public void goBack(View view) {
        try {
            Intent intent = new Intent(Packages.this, Dashboard.class);
        startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}