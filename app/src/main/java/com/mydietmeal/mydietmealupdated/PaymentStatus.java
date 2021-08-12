package com.mydietmeal.mydietmealupdated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PaymentStatus extends AppCompatActivity {
    TextInputLayout txtPackName,txtTotalPay, txtStatus;
    TextView txtConName;
    Button btnBackToD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);

        txtPackName = findViewById(R.id.packName);
        txtTotalPay = findViewById(R.id.tPayable);
        txtStatus = findViewById(R.id.statusOfPay);
        txtConName = findViewById(R.id.consumerName);
        btnBackToD = findViewById(R.id.btnBackToD);
        btnBackToD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
            }
        });

        checkStatus();

    }//End of onCreate Method

    private void checkStatus() {
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

                    String conName = dataSnapshot.child(usernumber).child("name").getValue(String.class);
                    String packageName = dataSnapshot.child(usernumber).child("subscriptionInfo").child("packageName").getValue(String.class);
                    String totalPayable = dataSnapshot.child(usernumber).child("subscriptionInfo").child("totalPayable").getValue(String.class);
                    String status = dataSnapshot.child(usernumber).child("subscriptionInfo").child("status").getValue(String.class);
                    txtConName.setText(conName);
                    txtPackName.getEditText().setText(packageName);
                    txtTotalPay.getEditText().setText(totalPayable);
                    txtStatus.getEditText().setText(status);


                }
                else{
                    txtPackName.setEnabled(false);
                    txtTotalPay.setEnabled(false);
                    txtStatus.setEnabled(false);
                }

            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}//End of PaymentStatus Class