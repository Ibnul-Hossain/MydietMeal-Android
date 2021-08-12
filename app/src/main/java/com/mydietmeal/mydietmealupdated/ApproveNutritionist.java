package com.mydietmeal.mydietmealupdated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApproveNutritionist extends AppCompatActivity {
    ListView listView;
    ArrayList<NutritionistSignUpHelperClass> nutritionistList ;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_nutritionist);

        listView=findViewById(R.id.listView1);
        nutritionistList=new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Nutritionist");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> numberArray = new ArrayList<>();
                String key = "";
                for(DataSnapshot ds: snapshot.getChildren()){

                    key = ds.getKey();
                    numberArray.add(key);
                    Log.d("Value", " "+key);
                    Log.d("User Value", " "+ds.getValue());
                    NutritionistSignUpHelperClass helperClass = ds.getValue(NutritionistSignUpHelperClass.class);
                    nutritionistList.add(helperClass);


                }
                ApproveNutritionistDataAdapter adapter = new ApproveNutritionistDataAdapter(ApproveNutritionist.this,nutritionistList,numberArray);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}