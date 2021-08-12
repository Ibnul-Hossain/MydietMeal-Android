package com.mydietmeal.mydietmealupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Payment extends AppCompatActivity {
    protected TextView textViewPackageName, textViewPackagePrice, textViewTotalPrice;
    TextInputLayout txtTransactionID;
    protected RadioButton radioButton1, radioButton2;
    protected String costPerDayString,packageName, totalPayable, nameFromMakeSubscription,
            phoneFromMakeSubscription, lunchPlace, lunchTime, transactionID;
    protected double costPerDay;
    private int subscriptionPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Hooks
        textViewPackageName = findViewById(R.id.textViewPackageName);
        textViewPackagePrice = findViewById(R.id.textViewCostPerDay);
        textViewTotalPrice = findViewById(R.id.textViewTotalCost);
        radioButton1 = findViewById(R.id.oneWeek);
        radioButton2 = findViewById(R.id.fifteenDays);
        txtTransactionID = findViewById(R.id.transactionID);


        //Getting previous activities data
        nameFromMakeSubscription = getIntent().getStringExtra("name");
        phoneFromMakeSubscription = getIntent().getStringExtra("phone");
        packageName = getIntent().getStringExtra("PackageName");
        lunchPlace = getIntent().getStringExtra("LunchPlace");
        lunchTime = getIntent().getStringExtra("LunchTime");
        costPerDayString = getIntent().getStringExtra("Price");
        assert costPerDayString != null;
        costPerDay = Double.parseDouble(costPerDayString);

        textViewPackageName.setText(packageName);
        textViewPackagePrice.setText(costPerDayString);

    }//End of onCreate method

    public void daySelection(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.oneWeek:
                if (isChecked) {
                    radioButton2.setChecked(false);
                    subscriptionPlan = 7;
                } else {
                    radioButton1.setChecked(true);
                    subscriptionPlan = 7;
                }
                totalPayable = Double.toString(costPerDay * subscriptionPlan);
                textViewTotalPrice.setText(totalPayable);
                break;
            case R.id.fifteenDays:
                if (isChecked) {
                    radioButton1.setChecked(false);
                    subscriptionPlan = 15;
                } else {
                    radioButton1.setChecked(true);
                    subscriptionPlan = 15;
                }
                totalPayable = Double.toString(costPerDay * subscriptionPlan);
                textViewTotalPrice.setText(totalPayable);
                break;
        }

    }//End of daySelection method

    public void confirmSubscription(View view){
        transactionID = txtTransactionID.getEditText().getText().toString();
        confirmSubscriptionHelperClass subscriptionHelperClass = new
                confirmSubscriptionHelperClass(packageName,costPerDayString, lunchPlace,
                lunchTime,totalPayable,transactionID, "Pending" );
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UsersData");
        Toast.makeText(getApplicationContext(), transactionID, Toast.LENGTH_LONG).show();
        reference.child(phoneFromMakeSubscription).child("subscriptionInfo").setValue(subscriptionHelperClass);
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent);
        finish();
    }//End of confirmSubscription method



}//End of Payment class