package com.mydietmeal.mydietmealupdated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class NutritionistLogin extends AppCompatActivity {
    ImageView image;
    TextView slogan ,logoText;
    TextInputLayout txtPhone, txtPassword;
    Button btnLogIn, btnSignUp, forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_login);

        txtPhone = findViewById(R.id.nLoginPhone);
        txtPassword = findViewById(R.id.nLoginPassword);

        btnLogIn = findViewById(R.id.btnNutriLogin);
        btnSignUp = findViewById(R.id.btnNutriNewUser);
        forgetPassword = findViewById(R.id.btnForgotPassword);
    }//End of onCreate Method

    private Boolean validatePhone(){
        String userVal = txtPhone.getEditText().getText().toString();
        if(userVal.isEmpty()){
            txtPhone.setError("Field cannot be empty");
            return false;
        }
        else {
            txtPhone.setError(null);
            txtPhone.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String passVal = Objects.requireNonNull(txtPassword.getEditText()).getText().toString();
        if(passVal.isEmpty()){
            txtPassword.setError("Field cannot be empty");
            return false;
        }
        else {
            txtPassword.setError(null);
            txtPassword.setErrorEnabled(false);
            return true;
        }
    }



    public void nutriLogIn(View view) {
        if (!validatePhone() || !validatePassword()){
        return;
    }
    else {
        isUser();
    }
    }//End of nutriLogin method

    private void isUser() {
        final String userEnteredPhone = Objects.requireNonNull(txtPhone.getEditText()).getText().toString();
        final String userEnteredPassword = Objects.requireNonNull(txtPassword.getEditText()).getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Nutritionist");
        Query checkUser = reference.orderByChild("nutritionistPhone").equalTo(userEnteredPhone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    txtPhone.setError("null");
                    txtPhone.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(userEnteredPhone).child("nutritionistPassword").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {

                        txtPhone.setError("null");
                        txtPhone.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredPhone).child("nutritionistName").getValue(String.class);
                        String phoneFromDB = dataSnapshot.child(userEnteredPhone).child("nutritionistPhone").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), NutritionistDashboard.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("phone", phoneFromDB);

                        startActivity(intent);
                        finish();
                    } else {
                        txtPassword.setError("Wrong Password");
                        txtPassword.requestFocus();
                    }
                } else {
                    txtPhone.setError("No such user exist");
                    txtPhone.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void nutriSignUp(View view) {
        startActivity(new Intent(NutritionistLogin.this, NutritionistSignUp.class));
    }//End of nutriSignUp method
}