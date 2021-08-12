package com.mydietmeal.mydietmealupdated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

public class CancelSubscription extends AppCompatActivity {
    private TextView packName, phoneNumber, totalPaid;
    private Button btnCancelSubs;
    String usernumber;
    Query checkUser;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_subscription);

        packName = findViewById(R.id.viewPackName);
        phoneNumber = findViewById(R.id.viewPhone);
        totalPaid = findViewById(R.id.viewTotalPaid);
        btnCancelSubs = findViewById(R.id.cancelSubs);
        checkStatus();

        btnCancelSubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference db = FirebaseDatabase.getInstance().getReference("UsersData");
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String key = "";
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            key = ds.getKey();
                            for(DataSnapshot ds2: dataSnapshot.child(key).getChildren()){
                                String k2 = ds2.getKey();
                                if(k2.equals("subscriptionInfo")){
                                    db.child(key).child(k2).removeValue();
                                    notify();
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });

    }//End of onCreate method

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

                    String packageName = dataSnapshot.child(usernumber).child("subscriptionInfo").child("packageName").getValue(String.class);
                    String totalPayable = dataSnapshot.child(usernumber).child("subscriptionInfo").child("totalPayable").getValue(String.class);
                    packName.setText(packageName);
                    phoneNumber.setText(usernumber);
                    totalPaid.setText(totalPayable);


                }
                else{
                    Toast.makeText(CancelSubscription.this, "You have no subscription", Toast.LENGTH_SHORT).show();
                }

            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}