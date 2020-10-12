package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {
    int user_ID;
    sqlDatabase db = new sqlDatabase(MainPageActivity.this);
    TextView userFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //gets the user ID from the login page which is passed using an Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_ID = extras.getInt("user_ID");
        }
        setFirstName();
    }

    public void viewAccountBalance(View view){
        //Sends the user to the View Account Balance Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_ID = extras.getInt("user_ID");
        }

        //Sends the user ID using an Intent
        Intent intent = new Intent(this, ViewAccountBalanceActivity.class);
        intent.putExtra("user_ID", user_ID);
        startActivity(intent);
    }

    public void transferBetweenAccounts(View view){
        //Sends the user to the Transfer Between Accounts Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_ID = extras.getInt("user_ID");
        }

        //Sends the user ID using an Intent
        Intent intent = new Intent(this, TransferBetweenAccountsActivity.class);
        intent.putExtra("user_ID", user_ID);
        startActivity(intent);
    }

    public void logout(View view){
        //Sends the user back to the Login Activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void setFirstName(){
        //Sets the welcome message to using the first name of the user
        String user_First_Name = db.getUserFirstName(db.getReadableDatabase(),user_ID);
        userFirstName = findViewById(R.id.tv_userFirstName);
        userFirstName.setText(user_First_Name);
    }
}