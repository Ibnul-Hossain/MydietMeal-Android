package com.mydietmeal.mydietmealupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NutritionistDashboard extends AppCompatActivity {
    TextView txtName, txtPhone;
    String name, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_dashboard);
        txtName = findViewById(R.id.nutriDashboardName);
        txtPhone = findViewById(R.id.nutriDashboardPhone);

        name = getIntent().getStringExtra("name").toString();
        phone = getIntent().getStringExtra("phone").toString();

        txtName.setText(name);
        txtPhone.setText(phone);

    }//End of onCreate method

    public void nutriCardViewClick(View view) {
        switch (view.getId()) {
            case R.id.cardProfileStatus:
                startActivity(new Intent(getApplicationContext(),NutriProfile.class));
                finish();
                break;
            case R.id.cardConsultConsumer:
                startActivity(new Intent(getApplicationContext(),ConsultConsumer.class));
                finish();
                break;
            case R.id.inital:
                Toast.makeText(this, "Under development", Toast.LENGTH_SHORT).show();
                break;
            case R.id.inital2:
                Toast.makeText(this, "Under development", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }// End of nutriCardViewClick method
}