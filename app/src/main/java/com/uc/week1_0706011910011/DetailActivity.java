package com.uc.week1_0706011910011;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uc.week1_0706011910011.model.User;
import com.uc.week1_0706011910011.model.UserData;
import com.uc.week1_0706011910011.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ArrayList<User> mContacts = UserData.saveList;
    Intent intent;
    FloatingActionButton button_delete;
    FloatingActionButton button_edit;
    int con;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.tool_details);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        intent = getIntent();
        con = intent.getIntExtra("mContact", 0);

        mContacts.get(con).getName();
        mContacts.get(con).getAge();
        mContacts.get(con).getAddress();

        TextView set_name = (TextView) findViewById(R.id.view_name);
        set_name.setText(mContacts.get(con).getName());

        TextView set_age = (TextView) findViewById(R.id.view_age);
        set_age.setText(mContacts.get(con).getAge(). concat(" years old"));

        TextView set_address = (TextView) findViewById(R.id.view_address);
        set_address.setText(mContacts.get(con).getAddress());

        button_delete = findViewById(R.id.button_delete);

        button_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertMessage();
            }
        });

        button_edit = findViewById(R.id.button_edit);

        button_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent (DetailActivity.this, AddUserActivity.class);
                intent.putExtra("mContact", "value");
                intent.putExtra("position", con);
                startActivity(intent);
                finish();
            }
        });

    }

    final Loading loading = new Loading(DetailActivity.this);

    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:


                        mContacts.remove(con);
                        Log.d("test", String.valueOf(con));

                        // Yes
                        Toast.makeText(DetailActivity.this, "Delete Success",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        startActivity(intent);

                        loading.startLoadingDialog();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 5000);
                        break;


                    case DialogInterface.BUTTON_NEGATIVE:

                        //No
                        dialog.dismiss();
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to delete " + mContacts.get(con).getName() + " data?")
                .setIcon(R.drawable.button_selector)
                .setTitle("Confirmation")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
    public boolean doublebackToExitPressedOnce = false;
    @Override
    protected void onResume(){
        super.onResume();
        this.doublebackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doublebackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        this.doublebackToExitPressedOnce = true;
        Toast.makeText(this, "Press back once again to exit the apps!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublebackToExitPressedOnce = false;
            }
        }, 5000);
    }


}
