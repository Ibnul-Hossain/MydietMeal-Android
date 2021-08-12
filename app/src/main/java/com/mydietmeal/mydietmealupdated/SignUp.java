package com.mydietmeal.mydietmealupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    TextInputLayout  regFullName, regPassword;
    EditText regPhone ;
    Button btnSignUp, btnRegToLogin;
    CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regFullName = findViewById(R.id.fullName);
        regPhone = findViewById(R.id.phone);
        regPassword = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnRegToLogin = findViewById(R.id.btnRegToLogin);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(regPhone);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameVal = Objects.requireNonNull(regFullName.getEditText()).getText().toString();
                String phoneVal = regPhone.getText().toString();
                String password = Objects.requireNonNull(regPassword.getEditText()).getText().toString();
                if (TextUtils.isEmpty(nameVal)){
                    Toast.makeText(SignUp.this,"Please Enter your name",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phoneVal)){
                    Toast.makeText(SignUp.this,"Please Enter your Mobile Number",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this,"Please Enter your Password",Toast.LENGTH_SHORT).show();
                }
                else if(!TextUtils.isEmpty(nameVal) && !TextUtils.isEmpty(phoneVal) && !TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this,"Registration Success",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUp.this,OTPVerification.class);
                    intent.putExtra("fullname",nameVal);
                    intent.putExtra("phone",ccp.getFullNumberWithPlus().replace("",""));
                    intent.putExtra("password",password);
                    startActivity(intent);


                }
                else{
                    Toast.makeText(SignUp.this,"Something wrong",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

//    private Boolean validateName(){
//        String nameVal = regFullName.getEditText().getText().toString();
//        if(nameVal.isEmpty()){
//            regFullName.setError("Field cannot be empty");
//            return false;
//        }else {
//            regFullName.setError(null);
//            regFullName.setErrorEnabled(false);
//            return true;
//        }
//    }
//    private Boolean validatePhone(){
//        String phoneVal = regPhone.getEditText().getText().toString();
//        if(phoneVal.isEmpty()){
//            regPhone.setError("Field cannot be empty");
//            return false;
//        }else {
//            regPhone.setError(null);
//            regPhone.setErrorEnabled(false);
//            return true;
//        }
//    }
//    private Boolean validatePassword(){
//        String passVal = regPassword.getEditText().getText().toString();
//        String passwordCriteria =  "^" +
//                //"(?=.*[0-9])" +
//                //"(?=.*[a-z])"+
//                //"(?=.*[A-Z])"+
//                "(?=.*[a-zA-Z])"+
//                "(?=.*[@#$%^&+=])"+
//                "(?=\\s+$)"+
//                ".{4,}"+
//                "$";
//
//        if(passVal.isEmpty()){
//            regPassword.setError("Field cannot be empty");
//            return false;
//        }
//        else if(!passVal.matches(passwordCriteria)){
//            regPassword.setError("Password is too weak");
//            return false;
//        }
//        else {
//            regPassword.setError(null);
//            regPassword.setErrorEnabled(false);
//            return true;
//        }
//    }

//    public void signUp(View view){
//
//        if (!validateName() || !validatePhone() ){ //|| !validatePassword()
//            return;
//        }
//
//        String name = Objects.requireNonNull(regFullName.getEditText()).getText().toString();
//
//        String getUserEnteredPhoneNumber = Objects.requireNonNull(regPhone.getEditText()).getText().toString().trim(); //Get the phone number
//        final String phone = "+"+ ccp.getFullNumber() + getUserEnteredPhoneNumber;
//
//        String password = Objects.requireNonNull(regPassword.getEditText()).getText().toString();
//
//        Intent intent = new Intent(SignUp.this, OTPVerification.class);
//        intent.putExtra("fullname",name);
//        intent.putExtra("phone",phone);
//        intent.putExtra("password",password);
//        startActivity(intent);
//        finish();
//    }

    public void signupToLogin(View view){
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
        finish();
    }
}