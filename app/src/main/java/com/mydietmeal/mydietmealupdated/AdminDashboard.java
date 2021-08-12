package com.mydietmeal.mydietmealupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
    }

    public void cardViewClick(View view) {
        switch (view.getId()) {
            case R.id.cardTotalMeal:
                startActivity(new Intent(getApplicationContext(),TotalMealCalculation.class));
                finish();
                break;
            case R.id.cardApproveConsumer:
                startActivity(new Intent(getApplicationContext(),ApproveConsumer.class));
                break;
            case R.id.cardApproveNutritionist:
                startActivity(new Intent(getApplicationContext(),ApproveNutritionist.class));
                break;
            case R.id.cardPayment:
//                startActivity(new Intent(getApplicationContext(),AdminPayment.class));
                finish();
                break;
        }
    }
}