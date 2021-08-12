package com.mydietmeal.mydietmealupdated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

public class Dashboard extends AppCompatActivity {

    private String bmi = "BMI", bmr = "BMR";
    TextView dashboardUsername, dashboardPhoneNumber;
    private CardView cardDietChart, cardMakeSubscription, cardPayment,
            cardConsultWithNutritionist, cardUpdatePackage, cardCancelSubscription;
    ProgressBar progressBar;
    String nameFromDB, phoneFromDB, phoneFromLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);


        progressBar = new ProgressBar(Dashboard.this);
        progressBar.StartLoadingDialog();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        phoneFromLogin = getIntent().getStringExtra("phone");

        dashboardUsername = findViewById(R.id.dashboardUserName);
        dashboardPhoneNumber = findViewById(R.id.dashboardUserPhone);

        //Card View
        cardDietChart = findViewById(R.id.cardDietChart);
        cardMakeSubscription = findViewById(R.id.cardMakeSubscription);
        cardConsultWithNutritionist = findViewById(R.id.cardConsultNutritionist);
        cardPayment = findViewById(R.id.cardPayment);
        cardUpdatePackage = findViewById(R.id.cardUpdatePackage);
        cardCancelSubscription = findViewById(R.id.cardCancelSubscription);

        isUser();

    }//End of onCreate method



    public void cardViewClick(View view)  {
        Intent intent;

        switch (view.getId()) {
            case R.id.cardDietChart:
                intent = new Intent(this, DietChart.class);
                startActivity(intent);
                break;
            case R.id.cardMakeSubscription:
                intent = new Intent(this, MakeSubscription.class);
                intent.putExtra("name", nameFromDB);
                intent.putExtra("phone", phoneFromDB);
                startActivity(intent);
                break;
            case R.id.cardConsultNutritionist:
                intent = new Intent(this, ConsultWithNutritionist.class);
                startActivity(intent);
                break;
            case R.id.cardPayment:
                intent = new Intent(this, PaymentStatus.class);
                startActivity(intent);
                break;
            case R.id.cardUpdatePackage:
                intent = new Intent(this, Packages.class);
                startActivity(intent);
                break;
            case R.id.cardCancelSubscription:
                intent = new Intent(this, CancelSubscription.class);
                startActivity(intent);
                break;
            default:break;
        }//End of switch statement
    } //End of cardViewClick method

    private void isUser() {
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

                    nameFromDB = dataSnapshot.child(usernumber).child("name").getValue(String.class);
                    phoneFromDB = dataSnapshot.child(usernumber).child("phone").getValue(String.class);
                    dashboardUsername.setText(nameFromDB);
                    dashboardPhoneNumber.setText(phoneFromDB);
                    progressBar.DismissDialog();


                    }
                else{
                    Toast.makeText(getApplicationContext(),"User data didn't matched! ", Toast.LENGTH_LONG).show();
                }

                }//End of onDataChange method

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//End of addListenerForSingleValueEvent method


    }//End of isUser Method


    public void signOut(View view) {
        startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
    }//End of signOut method

}//End of Dashboard Class