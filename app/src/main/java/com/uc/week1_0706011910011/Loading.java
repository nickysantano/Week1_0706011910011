package com.uc.week1_0706011910011;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.uc.week1_0706011910011.R;

public class Loading {

    private Activity activity;
    private AlertDialog dialog;

    public Loading(Activity activity) {
        this.activity = activity;
    }

    void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_loading, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();

    }

    void dismissDialog(){
        dialog.dismiss();
    }
}
