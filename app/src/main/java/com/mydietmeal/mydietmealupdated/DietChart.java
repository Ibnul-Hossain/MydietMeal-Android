package com.mydietmeal.mydietmealupdated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DietChart extends AppCompatActivity {

    TextView showBMI, showBMR, showCarbo, showProtein, showFat, showBreakfast, showLunch, showDinner;
    String bmiFromDB = "",bmrFromDB = "1.0", genderFromDB = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_chart);
        showBMI = findViewById(R.id.showBMI);
        showBMR = findViewById(R.id.showBMR);
        showCarbo = findViewById(R.id.showCarbohydrate);
        showProtein = findViewById(R.id.showProtein);
        showFat = findViewById(R.id.showFat);
        showBreakfast = findViewById(R.id.showBreakFast);
        showLunch = findViewById(R.id.showLunch);
        showDinner = findViewById(R.id.showDinner);

        showBmiBmrValue();



    }//End of onCreate Method


    private void showBmiBmrValue() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String Uphonenumber = user.getPhoneNumber();
        final String usernumber = Objects.requireNonNull(Uphonenumber);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UsersData");
        Query checkUser = reference.orderByChild("phone").equalTo(usernumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    bmiFromDB = dataSnapshot.child(usernumber).child("dietValue").child("bmi").getValue(String.class);
                    bmrFromDB = dataSnapshot.child(usernumber).child("dietValue").child("bmr").getValue(String.class);
                    genderFromDB = dataSnapshot.child(usernumber).child("dietValue").child("gender").getValue(String.class);
                    showBMI.setText(bmiFromDB);
                    showBMR.setText(bmrFromDB);
                    predictingDietChart();
                }
                else{
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }//End of showBmiBmrValue Method

    @SuppressLint("DefaultLocale")
    public  void predictingDietChart() {

        String customer_bmi = bmiFromDB;
        double customer_bmr = 0;
        try{
            customer_bmr = Double.parseDouble(bmrFromDB);
        }catch (Exception e){

        }
        String customer_gender = genderFromDB;
        double total_energy_req = 0.0;

         //Calculating pal
        if(customer_gender.equals("Male")) {
            total_energy_req = customer_bmr * 1.78;
        }
        else {
            total_energy_req = customer_bmr * 1.64;
        }

        //Distribution of Calories
        double CHO = 0.6 * total_energy_req;
        double CHON = 0.15 * total_energy_req;
        double FAT = 0.25 * total_energy_req;



        //Calculation of required amount
        double amount_of_CHO = CHO / 4;
        double amount_of_CHON = CHON / 4;
        double amount_of_FAT = FAT / 9;
        Log.d("Kire Bhai"," "+amount_of_FAT);
        //Ekhane dekhao
        try {
            showCarbo.setText(String.format("%.2f",amount_of_CHO)+"gm");
            showProtein.setText(String.format("%.2f",amount_of_CHON)+"gm");
            showFat.setText(String.format("%.2f",amount_of_FAT)+"gm");
        }
        catch (Exception e){

        }

        //Calculation of No. Serving
        if(customer_bmi.equals("Underweight")) {
            double No_Serving_CHO = amount_of_CHO / 15;
            double No_Serving_CHON = amount_of_CHON/15;
            double No_Serving_FAT = amount_of_FAT / 15;
            System.out.println("Carolies need for morning"+((amount_of_CHO + amount_of_CHON + amount_of_FAT)/3));
            System.out.println("Milk"+" "+(No_Serving_CHO)+" "+"cal");
            System.out.println("Bread"+" "+(No_Serving_CHON)+" "+"cal");
            System.out.println("Mix_Vegetable"+" "+(No_Serving_FAT)+" "+"cal");

            System.out.println("Carolies need for lunch"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3));

            System.out.println("Rice"+" "+(No_Serving_CHO*2)+" "+"cal");
            System.out.println("Dal"+" "+(No_Serving_CHON*3)+" "+"cal");
            System.out.println("Meat"+" "+(No_Serving_FAT*1.5)+" "+"cal");

            System.out.println("Carolies need for dinner"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3));

            System.out.println("Rice"+" "+(No_Serving_CHO*2)+" "+"cal");
            System.out.println("Dal"+" "+(No_Serving_CHON*3)+" "+"cal");
            System.out.println("Meat"+" "+(No_Serving_FAT*1.5)+" "+"cal");
        }

        else if(customer_bmi.equals("Normal")) {
            double No_Serving_CHO=amount_of_CHO/15;
            double No_Serving_CHON=amount_of_CHON/15;
            double No_Serving_FAT=amount_of_FAT/15;


            System.out.println("Carolies need for morning"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3));
            System.out.println("Milk"+" "+(No_Serving_CHO)+" "+"cal");
            System.out.println("Bread"+" "+(No_Serving_CHON)+" "+"cal");
            System.out.println("Mix_Vegetable"+" "+(No_Serving_FAT)+" "+"cal");
            showBreakfast.setText("Calories need for morning"+String.format("%.2f",(amount_of_CHO+amount_of_CHON+amount_of_FAT)/3)+
                    "\nMilk"+" "+String.format("%.2f",No_Serving_CHO)+" "+"cal\n"+"Bread"+" "+String.format("%.2f",No_Serving_CHON)+" "+
                    "cal\n"+"Mix_Vegetable"+" "+String.format("%.2f",No_Serving_FAT)+" "+"cal");
            System.out.println("Carolies need for lunch"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3));

            System.out.println("Rice"+" "+(No_Serving_CHO*2)+" "+"cal");
            System.out.println("Dal"+" "+(No_Serving_CHON*3)+" "+"cal");
            System.out.println("Meat"+" "+(No_Serving_FAT*1.5)+" "+"cal");
            showLunch.setText("Calories need for lunch"+String.format("%.2f",(amount_of_CHO+amount_of_CHON+amount_of_FAT)/3)+
                    "\nRice"+" "+String.format("%.2f",No_Serving_CHO*2)+" "+"cal\n"+ "Dal"+" "+String.format("%.2f",No_Serving_CHON*3)+" "+
                    "cal\n"+"Meat"+" "+String.format("%.2f",No_Serving_FAT*1.5)+" "+"cal");

            System.out.println("Calories need for dinner"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3));

            System.out.println("Rice"+" "+(No_Serving_CHO*2)+" "+"cal");
            System.out.println("Dal"+" "+(No_Serving_CHON*3)+" "+"cal");
            System.out.println("Meat"+" "+(No_Serving_FAT*1.5)+" "+"cal");

            showDinner.setText("Calories need for dinner"+String.format("%.2f",(amount_of_CHO+amount_of_CHON+amount_of_FAT)/3)+
                    "\nRice"+" "+String.format("%.2f",No_Serving_CHO*2)+" "+"cal"+"\nDal"+" "+String.format("%.2f",No_Serving_CHON*3)+" "+"cal"+
                    "\nMeat"+" "+String.format("%.2f",No_Serving_FAT*1.5)+" "+"cal");
        }

        else if(customer_bmi.equals("Overweight")) {
            double No_Serving_CHO=amount_of_CHO/15;
            double No_Serving_CHON=amount_of_CHON/15;
            double No_Serving_FAT=amount_of_FAT/15;


            System.out.println("Carolies need for morning"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3+10));
            System.out.println("Milk"+" "+(No_Serving_CHO)+" "+"cal");
            System.out.println("Bread"+" "+(No_Serving_CHON)+" "+"cal");
            System.out.println("Mix_Vegetable"+" "+(No_Serving_FAT)+" "+"cal");

            System.out.println("Carolies need for lunch"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3));

            System.out.println("Rice"+" "+(No_Serving_CHO*2)+" "+"cal");
            System.out.println("Dal"+" "+(No_Serving_CHON*3)+" "+"cal");
            System.out.println("Meat"+" "+(No_Serving_FAT*1.5)+" "+"cal");

            System.out.println("Carolies need for dinner"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3-10));

            System.out.println("Rice"+" "+(No_Serving_CHO*2)+" "+"cal");
            System.out.println("Dal"+" "+(No_Serving_CHON*3)+" "+"cal");
            System.out.println("Meat"+" "+(No_Serving_FAT*1.5)+" "+"cal");
        }

        else if(customer_bmi.equals("Obesity")) {
            double No_Serving_CHO=amount_of_CHO/15;
            double No_Serving_CHON=amount_of_CHON/15;
            double No_Serving_FAT=amount_of_FAT/15;

            System.out.println("Carolies need for morning"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3+10));
            System.out.println("Milk"+" "+(No_Serving_CHO)+" "+"cal");
            System.out.println("Bread"+" "+(No_Serving_CHON)+" "+"cal");
            System.out.println("Mix_Vegetable"+" "+(No_Serving_FAT)+" "+"cal");

            System.out.println("Carolies need for lunch"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3));

            System.out.println("Rice"+" "+(No_Serving_CHO*2)+" "+"cal");
            System.out.println("Dal"+" "+(No_Serving_CHON*3)+" "+"cal");
            //System.out.println("Meat"+" "+(No_Serving_FAT*1.5)+" "+"cal");

            System.out.println("Carolies need for dinner"+((amount_of_CHO+amount_of_CHON+amount_of_FAT)/3-20));

            System.out.println("Rice"+" "+(No_Serving_CHO*2)+" "+"cal");
            System.out.println("Dal"+" "+(No_Serving_CHON*3)+" "+"cal");
            //System.out.println("Meat"+" "+(No_Serving_FAT*1.5)+" "+"cal");
        }

    }
}