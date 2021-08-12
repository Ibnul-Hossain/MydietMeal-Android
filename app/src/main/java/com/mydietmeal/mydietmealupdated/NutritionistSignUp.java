package com.mydietmeal.mydietmealupdated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NutritionistSignUp extends AppCompatActivity {
    TextInputLayout regGraduationInfo, regFullName, regEmail, regPhone, regPassword;
    Button btnSignUp, btnRegToLogin;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_sign_up);

        regFullName = findViewById(R.id.nFullName);
        regGraduationInfo = findViewById(R.id.graduationInfo);
        regEmail = findViewById(R.id.nEmail);
        regPhone = findViewById(R.id.nPhone);
        regPassword = findViewById(R.id.nPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnRegToLogin = findViewById(R.id.btnRegToLogin);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Nutritionist");
    }//End of onCreate method

    private Boolean validateName(){
        String nameVal = regFullName.getEditText().getText().toString();
        if(nameVal.isEmpty()){
            regFullName.setError("Field cannot be empty");
            return false;
        }else {
            regFullName.setError(null);
            regFullName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateGraduation(){
        String nameVal = regGraduationInfo.getEditText().getText().toString();
        if(nameVal.isEmpty()){
            regGraduationInfo.setError("Field cannot be empty");
            return false;
        }else {
            regGraduationInfo.setError(null);
            regGraduationInfo.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail(){
        String emailVal = regEmail.getEditText().getText().toString();
        String emailPatter = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(emailVal.isEmpty()){
            regEmail.setError ("Field cannot be empty");
            return false;
        }
        else if(!emailVal.matches(emailPatter)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhone(){
        String phoneVal = regPhone.getEditText().getText().toString();
        if(phoneVal.isEmpty()){
            regPhone.setError("Field cannot be empty");
            return false;
        }else {
            regPhone.setError(null);
            regPhone.setErrorEnabled(false);
            return true;
        }
    }

    public void signUp(View view) {
        if (!validateName() || !validateGraduation()  || !validatePhone() ){ //|| !validatePassword() || !validateEmail()
            return;
        }

        String name = regFullName.getEditText().getText().toString();
        String graduationInfo = regGraduationInfo.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phone = regPhone.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        NutritionistSignUpHelperClass helperClass = new NutritionistSignUpHelperClass(name, graduationInfo,email, phone, password);
        reference.child(phone).setValue(helperClass);
        Intent intent = new Intent(NutritionistSignUp.this, NutritionistLogin.class);
        startActivity(intent);
        finish();
    }//End of signUp method

    public void signupToLogin(View view) {

    }//End of signUpToLogin method
}