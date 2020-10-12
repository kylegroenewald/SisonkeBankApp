package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewAccountBalanceActivity extends AppCompatActivity {
    int user_ID;
    sqlDatabase db = new sqlDatabase(ViewAccountBalanceActivity.this);
    TextView userFirstName;
    TextView userLastName;
    TextView userCurrent;
    TextView userSavings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_balance);
        //Adds a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fetches the user ID using an Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_ID = extras.getInt("user_ID");
        }
        //Calls the methods to set the TextViews
        setFirstName();
        setLastName();
        setCurrentAccount();
        setSavingsAccount();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Provides functionality to the back button
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setFirstName(){
        //sets the first name of the user
        String user_First_Name = db.getUserFirstName(db.getReadableDatabase(),user_ID);
        userFirstName = findViewById(R.id.tv_UserNameBalance);
        userFirstName.setText(user_First_Name);
    }

    public void setLastName(){
        //sets the last name of the user
        String user_Last_Name = db.getUserLastName(db.getReadableDatabase(),user_ID);
        userLastName = findViewById(R.id.tv_UserSurname);
        userLastName.setText(user_Last_Name);
    }

    public void setCurrentAccount(){
        //sets the current account value of the user
        String currentAccount = db.getCurrentAccount(db.getReadableDatabase(),user_ID)+"";
        userCurrent = findViewById(R.id.tv_CurrentAccountValue);
        userCurrent.setText("R"+currentAccount);
    }

    public void setSavingsAccount(){
        //sets the savings account value of the user
        String savingsAccount = db.getSavingsAccount(db.getReadableDatabase(),user_ID)+"";
        userSavings = findViewById(R.id.tv_SavingsAccountValue);
        userSavings.setText("R"+savingsAccount);
    }
}