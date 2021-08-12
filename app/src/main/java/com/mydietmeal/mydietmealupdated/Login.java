package com.mydietmeal.mydietmealupdated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    ImageView image;
    TextView slogan ,logoText;
    TextInputLayout phone, password;
    Button btnLogIn, btnSignUp, forgetPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        image = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoText);
        slogan = findViewById(R.id.slogan);

        phone = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.loginPassword);

        btnLogIn = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnNewUser);
        forgetPassword = findViewById(R.id.btnForgotPassword);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent=new Intent(getApplicationContext(),Dashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private Boolean validateUserName(){
        String userVal = phone.getEditText().getText().toString();
        if(userVal.isEmpty()){
            phone.setError("Field cannot be empty");
            return false;
        }
        else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String passVal = Objects.requireNonNull(password.getEditText()).getText().toString();
        if(passVal.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void logIn(View view){
        if (!validateUserName() || !validatePassword()){
            return;
        }
        else {
            progressBar =new ProgressBar(Login.this);
            progressBar.StartLoadingDialog();
            isUser();
            progressBar.DismissDialog();

        }
    }

    private void isUser() {
        final String userEnteredPhone = Objects.requireNonNull(phone.getEditText()).getText().toString();
        final String userEnteredPassword = Objects.requireNonNull(password.getEditText()).getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UsersData");
        Query checkUser = reference.orderByChild("phone").equalTo(userEnteredPhone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    phone.setError("null");
                    phone.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(userEnteredPhone).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)){

                        String phoneFromDB = dataSnapshot.child(userEnteredPhone).child("phone").getValue(String.class);
                        phone.setError("null");
                        phone.setErrorEnabled(false);


                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.putExtra("phone", phoneFromDB);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else{
                    phone.setError("No such user exist");
                    phone.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void signUp(View view){
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);

    }


    public void NitriSignUp(View view) {
        startActivity(new Intent(Login.this, NutritionistLogin.class));

    }
}