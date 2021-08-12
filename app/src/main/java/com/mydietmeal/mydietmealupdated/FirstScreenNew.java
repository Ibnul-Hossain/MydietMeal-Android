package com.mydietmeal.mydietmealupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class FirstScreenNew extends AppCompatActivity {
TextInputLayout txtHeight,txtWeight,txtAge;
    String weightString, heightString, ageString,genderString, opinion, bmrString;
    RadioButton radioMale, radioFemale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen_new);

        txtHeight = findViewById(R.id.height);
        txtWeight = findViewById(R.id.weight);
        txtAge = findViewById(R.id.age);

        radioMale = findViewById(R.id.male);
        radioFemale = findViewById(R.id.female);




    }

    public void seeDietChart(View view){
        heightString = txtHeight.getEditText().getText().toString();
        weightString = txtWeight.getEditText().getText().toString();
        ageString = txtAge.getEditText().getText().toString();

        float height = Float.parseFloat(heightString);
        float weight = Float.parseFloat(weightString);
        int age = Integer.parseInt(ageString);

        opinion = calBMI(height,weight,age, genderString);
        double bmrValue = calBMR(height,weight,age, genderString);
        bmrString = String.valueOf(bmrValue);

//        Toast.makeText(getApplicationContext(), txtHeight.getEditText().getText(),Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), genderString,Toast.LENGTH_LONG).show();
        DietValueHelperClass dietValueHelperClass = new DietValueHelperClass(heightString,weightString,ageString,genderString,opinion,bmrString);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String ID = user.getPhoneNumber();
        final String userphone = Objects.requireNonNull(ID);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UsersData");
        reference.child(userphone).child("dietValue").setValue(dietValueHelperClass);
        Intent intent = new Intent(FirstScreenNew.this, Dashboard.class);
        intent.putExtra("BMI", opinion);
        intent.putExtra("BMR", bmrString);
        startActivity(intent);
        finish();
    }// End of onCreate Method

    public String  calBMI(float height, float weight, int age, String gender){
        String opinion;
        ArrayList<String> bmiValues = new ArrayList<String>();
        double bmi = weight / (Math.pow(height,2));
        if(bmi < 15){
            opinion = "Very severely underweight";
            //bmiValues.add(bmi,opinion);
        }
        else if(bmi >= 15 && bmi <16){
            opinion = "Severely underweight";
        }
        else if(bmi >= 16 && bmi <=18.4){
            opinion = "Underweight";
        }
        else if(bmi >= 18.5 && bmi <=24.9){
            opinion = "Normal";
        }
        else if(bmi >= 25 && bmi <=29.9){
            opinion = "Overweight";
        }
        else if(bmi >=30 ){
            opinion = "Obesity";
        }

        else{
            opinion = "Please input valid age, height or weight";
        }
        return opinion;
    }//End of calBMI method

    public double calBMR(float height, float weight, int age, String gender){
        double basalMetabolicRate = 0;

        if (gender.equals("Male")){
            basalMetabolicRate = 66.47 + (13.75 * weight) +(5.003 * (height*100)) - (6.755 * age);
        }
        else if (gender.equals("Female")){
            basalMetabolicRate = 655.1 + (9.563 * weight) + (1.85 * (height* 100)) - (4.676 * age);
        }
        else {
            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
        }
        return basalMetabolicRate;
    }//End of calBMR method

    public void selectGender(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.male:
                if(isChecked){
                    radioFemale.setChecked(false);
                    genderString = "Male";
                }
                else{
                    radioMale.setChecked(true);
                }
                break;
            case R.id.regular:
                if(isChecked){
                    radioMale.setChecked(false);
                    genderString = "Female";
                }
                else{
                    radioFemale.setChecked(true);
                }
                break;
        }
    }//End of selectPackage method


}
