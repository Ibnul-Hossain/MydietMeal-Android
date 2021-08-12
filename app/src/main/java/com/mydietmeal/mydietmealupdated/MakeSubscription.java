package com.mydietmeal.mydietmealupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MakeSubscription extends AppCompatActivity {
    private RadioButton radioButton1, radioButton2, radioButton3, checkBox1, checkBox2;
    private String packageName = "premium", nameFromDashboard, phoneFromMakeDashboard;
    private String mealNumber = "1";
    protected double subscriptionPrice;
    protected TextInputLayout textViewBreakfast, getTextViewBreakfastTime, textViewLunch,
            textViewLunchTime, textViewDinner, textViewDinnerTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_make_subscription);

        //Hooks
        checkBox1 = findViewById(R.id.premium);
        checkBox2 = findViewById(R.id.regular);
        radioButton1 = findViewById(R.id.oneWeek);
        radioButton2 = findViewById(R.id.fifteenDays);
        radioButton3 = findViewById(R.id.threeTimes);
        textViewBreakfast = findViewById(R.id.breakfast);
        getTextViewBreakfastTime = findViewById(R.id.breakfastTime);
        textViewLunch = findViewById(R.id.lunch);
        textViewLunchTime = findViewById(R.id.lunchTime);
        textViewDinner = findViewById(R.id.dinner);
        textViewDinnerTime = findViewById(R.id.dinnerTime);

        nameFromDashboard = getIntent().getStringExtra("name");
        phoneFromMakeDashboard = getIntent().getStringExtra("phone");



    }//End of onCreate method

    public void seePackage(View view) {
        Intent intent = new Intent(this, Packages.class);
        startActivity(intent);
    }//End of seePackage method

    public void selectPackage(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.premium:
                if(isChecked){
                    checkBox2.setChecked(false);
                    packageName = "premium";
                }
                else{
                    checkBox1.setChecked(true);
                }
                break;
            case R.id.regular:
                if(isChecked){
                    checkBox1.setChecked(false);
                    packageName = "regular";
                }
                else{
                    checkBox2.setChecked(true);
                }
                break;
        }
    }//End of selectPackage method

    public void selectMealNumber(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.oneWeek:
                if(isChecked){
                    radioButton2.setChecked(false);
                    radioButton3.setChecked(false);
                    mealNumber = "1";
                }
                else {
                    radioButton1.setChecked(true);
                }
                break;
            case R.id.fifteenDays:
                if(isChecked){
                    radioButton1.setChecked(false);
                    radioButton3.setChecked(false);
                    mealNumber = "2";
                }
                else{
                    radioButton1.setChecked(true);
                }
                break;
            case R.id.threeTimes:
                if(isChecked){
                    radioButton1.setChecked(false);
                    radioButton2.setChecked(false);
                    mealNumber = "3";
                }
                else{
                    radioButton1.setChecked(true);
                }
                break;

        }
    }//End of selectMealNumber method

    public double packagePrice(){

        if(packageName.equals("premium")){
            switch (mealNumber) {
                case "1":
                    subscriptionPrice = 150;
                    break;
                case "2":
                    subscriptionPrice = 150 * 2;
                    break;
                case "3":
                    subscriptionPrice = 150 * 3;
                    break;
            }
        }
        else if(packageName.equals("regular")){

            switch (mealNumber) {
                case "1":
                    subscriptionPrice = 120;
                    break;
                case "2":
                    subscriptionPrice = 120 * 2;
                    break;
                case "3":
                    subscriptionPrice = 120 * 3;
                    break;
            }
        }

        return subscriptionPrice;
    }//End of packagePrice method

    public void payForTheSubscription(View view){
        Intent intent = new Intent(this, Payment.class);
        intent.putExtra("name", nameFromDashboard);
        intent.putExtra("phone", phoneFromMakeDashboard);
        intent.putExtra("PackageName", packageName);
        intent.putExtra("MealNumber", mealNumber);
        intent.putExtra("LunchPlace", Objects.requireNonNull(textViewLunch.getEditText()).getText().toString());
        intent.putExtra("LunchTime", Objects.requireNonNull(textViewLunchTime.getEditText()).getText().toString());
        double price = packagePrice();
        String packPrice = Double.toString(price);
        intent.putExtra("Price", packPrice);
        startActivity(intent);
    }//End of payForTheSubscription method

}//End of MakeSubscription class