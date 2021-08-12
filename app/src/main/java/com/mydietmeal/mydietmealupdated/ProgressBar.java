package com.mydietmeal.mydietmealupdated;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class ProgressBar {
    Activity activity;
    AlertDialog alertDialog;
    ProgressBar(Activity myActivity){
        activity=myActivity;
}
    void StartLoadingDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.progressdialog,null));
        builder.setCancelable(false);
        alertDialog=builder.create();
        alertDialog.show();

    }

    void DismissDialog(){
        alertDialog.dismiss();
    }
}
