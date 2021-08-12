package com.mydietmeal.mydietmealupdated;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
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

import java.util.List;

public class dataAdapter extends ArrayAdapter {

    private Activity mContext;
    List<confirmSubscriptionHelperClass> consumerLists;
    TextView txtNumber, packageName,payable, transactionID,status, lunchP,lunchT,mealPrice;
    List number;
    public dataAdapter(Activity mContext,List<confirmSubscriptionHelperClass> consumerLists, List number){
        super(mContext,R.layout.show_consumer_info,consumerLists);
        this.mContext=mContext;
        this.consumerLists=consumerLists;
        this.number = number;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater= mContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.show_consumer_info,null,true);
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

        Button btnDelete, btnApprove;
        View itemView;
        int position;

        public ViewHolder(View view) {
            itemView = view;
            txtNumber = view.findViewById(R.id.consumerNumber);
            packageName = view.findViewById(R.id.packageName);
            payable = view.findViewById(R.id.totalPayable);
            transactionID = view.findViewById(R.id.transaction_id);
            status = view.findViewById(R.id.statusVal);
            btnApprove = view.findViewById(R.id.btnApprove);
            btnDelete = view.findViewById(R.id.btnDelete);

        }//End of ViewHolder method

        public void setPosition(int position) {
            this.position = position;
        }


        public void bindViews() {
            confirmSubscriptionHelperClass consumerHelperClass = consumerLists.get(position);
            txtNumber.setText((String) number.get(position));
            packageName.setText(consumerHelperClass.getPackageName());
            payable.setText(consumerHelperClass.getTotalPayable());
            transactionID.setText(consumerHelperClass.getTransactionID());
            status.setText(consumerHelperClass.getStatus());
            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onApproveItem(position);
                    Toast.makeText(mContext, "Subscription approved successfully", Toast.LENGTH_SHORT).show();
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteItem(position);
                    Toast.makeText(mContext, "Subscription Deleted successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }//End of bindViews method

    }//End of ViewHolder Class

    private void onApproveItem(final int positions) {
//        final confirmSubscriptionHelperClass consumerHelperClass = consumerLists.get(position);
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
                            for(DataSnapshot ds3: dataSnapshot.child(key).child(k2).getChildren()){
                                if(ds3.getKey().equals("status")){
                                    db.child(key).child(k2).child(ds3.getKey()).setValue("Approved");
                                    notifyDataSetChanged();
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "All consumer is checked", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void onDeleteItem(final int positions) {
//        final confirmSubscriptionHelperClass consumerHelperClass = consumerLists.get(position);
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
                            consumerLists.remove(positions);
                            notifyDataSetChanged();
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
}
