package com.mydietmeal.mydietmealupdated;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApproveConsumer extends AppCompatActivity {
    ListView listView;
    ArrayList<confirmSubscriptionHelperClass> consumerList ;
    DatabaseReference reference;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_consumer);
        listView=findViewById(R.id.listView);
        consumerList=new ArrayList<>();



        reference = FirebaseDatabase.getInstance().getReference("UsersData");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> numberArray = new ArrayList<>();
                String key = "";
                for(DataSnapshot ds: snapshot.getChildren()){
                    numberArray.add(ds.getKey());
                    key = ds.getKey();
                    for(DataSnapshot ds2: snapshot.child(key).getChildren()){
                        String k2 = ds2.getKey();
                        if(k2.equals("subscriptionInfo") && ds2.exists()){

                            for(DataSnapshot ds3: snapshot.child(key).child(k2).getChildren()){
                                if(Objects.equals(ds3.getKey(), "status") && Objects.equals(ds3.getValue(), "Pending")){
                                    confirmSubscriptionHelperClass helperClass = ds2.getValue(confirmSubscriptionHelperClass.class);
                                    consumerList.add(helperClass);
                                }
                            }
                        }
                        else{
//                            tv.setVisibility(View.VISIBLE);
//                            Toast.makeText(ApproveConsumer.this, "All Consumer is checked", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                dataAdapter adapter = new dataAdapter(ApproveConsumer.this,consumerList,numberArray);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


            }//End of onCreate Method






}//End of ApproveConsumer class