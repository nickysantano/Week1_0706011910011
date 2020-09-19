package com.uc.week1_0706011910011;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.uc.week1_0706011910011.model.User;
import com.uc.week1_0706011910011.model.UserData;
import com.uc.week1_0706011910011.R;

import java.util.ArrayList;

public class AddUserActivity extends AppCompatActivity implements TextWatcher {

    TextInputLayout input_name, input_age, input_address;
    Button button2;
    String name, address, age;
    Intent intent;
    Intent intent2;
    String con;
    int daftar;
    ArrayList<User> mContacts = UserData.saveList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        input_name = findViewById(R.id.input_fname);
        input_age = findViewById(R.id.input_age);
        input_address = findViewById(R.id.input_address);
        button2 = findViewById(R.id.buttonAdd);
        toolbar = findViewById(R.id.tooladd);

        input_name.getEditText().addTextChangedListener(this);
        input_age.getEditText().addTextChangedListener(this);
        input_address.getEditText().addTextChangedListener(this);

        final Loading loading = new Loading(AddUserActivity.this);

        intent = getIntent();
        con = intent.getStringExtra("mContact");

        intent2 = getIntent();
        daftar = intent2.getIntExtra("position", 0);

        if (con.equalsIgnoreCase("main")) {
            toolbar.setTitle("Add User");
            button2.setText("Save Data");
        } else {
            toolbar.setTitle("Edit User");
            button2.setText("Update Data");
            TextInputLayout set_name = (TextInputLayout) findViewById(R.id.input_fname);
            set_name.getEditText().setText(mContacts.get(daftar).getName());

            TextInputLayout set_age = (TextInputLayout) findViewById(R.id.input_age);
            set_age.getEditText().setText(mContacts.get(daftar).getAge());
            String txt = " years old";

            TextInputLayout set_address = (TextInputLayout) findViewById(R.id.input_address);
            set_address.getEditText().setText(mContacts.get(daftar).getAddress());
        }

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (con.equalsIgnoreCase("main")) {
                    final User contact = new User(name, address, age);

                    UserData.saveList.add(contact);

                    loading.startLoadingDialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                            intent.putExtra("dataUser", contact);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                } else {

                    mContacts.get(daftar).setName(name);
                    mContacts.get(daftar).setAge(age);
                    mContacts.get(daftar).setAddress(address);
                    Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(con.equalsIgnoreCase("main")){
                    Intent intent1 = new Intent(AddUserActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                }else{
                    Intent intent1 = new Intent(AddUserActivity.this, DetailActivity.class);
                    intent1.putExtra("mContact", daftar);
                    startActivity(intent1);
                    finish();
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        name = input_name.getEditText().getText().toString().trim();
        age = input_age.getEditText().getText().toString().trim();
        address = input_address.getEditText().getText().toString().trim();

        if (!name.isEmpty() && !address.isEmpty() && !age.isEmpty()) {
            button2.setEnabled(true);
        } else {
            button2.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
