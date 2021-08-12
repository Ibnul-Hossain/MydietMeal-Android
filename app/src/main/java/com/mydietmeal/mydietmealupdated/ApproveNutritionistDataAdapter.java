package com.mydietmeal.mydietmealupdated;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ApproveNutritionistDataAdapter extends ArrayAdapter {
    private Activity mContext;
    List<NutritionistSignUpHelperClass> nutritionistList;
    TextView txtNutriNumber, txtNutriName,txtNutriGraduation;
    List number;

    public ApproveNutritionistDataAdapter(Activity mContext,List<NutritionistSignUpHelperClass> nutritionistList, List number){
        super(mContext,R.layout.show_nutrtionist_info,nutritionistList);
        this.mContext=mContext;
        this.nutritionistList=nutritionistList;
        this.number = number;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater= mContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.show_nutrtionist_info,null,true);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.setPosition(position);
            holder.bindViews();
            return convertView;
    }

    public class ViewHolder {

        Button btnDelete;
        View itemView;
        int position;

        public ViewHolder(View view) {
            itemView = view;
            txtNutriNumber = view.findViewById(R.id.nutriNumber);
            txtNutriName = view.findViewById(R.id.nutriName);
            txtNutriGraduation = view.findViewById(R.id.nutriGraduation);
            btnDelete = view.findViewById(R.id.btnDelete1);


        }

        public void setPosition(int position) {
            this.position = position;
        }


        public void bindViews() {
            NutritionistSignUpHelperClass consumerHelperClass = nutritionistList.get(position);
            try {
                txtNutriNumber.setText((String) number.get(position));
            }
            catch (Exception e){

            }


            txtNutriName.setText(consumerHelperClass.getNutritionistName());
            txtNutriGraduation.setText(consumerHelperClass.getNutritionistGraduation());
            btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDeleteItem(position);
                        Toast.makeText(mContext, "Contact Deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    private void onDeleteItem(final int position) {
        final NutritionistSignUpHelperClass consumerHelperClass = nutritionistList.get(position);
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("Nutritionist");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    NutritionistSignUpHelperClass consumerHelperClass1=ds.getValue(NutritionistSignUpHelperClass.class);
                    if (consumerHelperClass.getNutritionistPhone().equals(consumerHelperClass1.getNutritionistPhone())){
                        db.child(ds.getKey().toString()).removeValue();
                        nutritionistList.remove(position);
                        notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
