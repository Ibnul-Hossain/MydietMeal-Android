package com.mydietmeal.mydietmealupdated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTPVerification extends AppCompatActivity {
    String name,phone,password;
    EditText otpcode;
    Button verify;
    String otpid;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);
        name=getIntent().getStringExtra("fullname");
        phone=getIntent().getStringExtra("phone");
        password=getIntent().getStringExtra("password");
        otpcode=findViewById(R.id.otp_code);
        verify=findViewById(R.id.verify);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("UsersData");
        initiateOTP();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otpcode.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Blank field can not be processed",Toast.LENGTH_SHORT).show();
                }
                else if (otpcode.getText().toString().length()!=6){
                    Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
                }
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid,otpcode.getText().toString());
                    signInWithPhoneAuthCredential(credential);

                }
            }

        });

    }

    private void initiateOTP() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid=s;
                                Log.i("OTP", s);
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserHelperClass loginData=new UserHelperClass(name,phone,password);
                            databaseReference.child(phone).setValue(loginData);
                            Toast.makeText(getApplicationContext(),"Verification Success",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(OTPVerification.this,FirstScreenNew.class);
//                            intent.putExtra("name",name);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(),"Verification Failed",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}