package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TransferBetweenAccountsActivity extends AppCompatActivity {
    int user_ID;
    String[] arraySpinner = new String[] {"Current to Savings", "Savings to Current"};
    sqlDatabase db = new sqlDatabase(TransferBetweenAccountsActivity.this);
    TextView userCurrent;
    TextView userSavings;
    EditText transferAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_between_accounts);
        //Adds a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateSpinner();

        //Fetches the user ID using an Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_ID = extras.getInt("user_ID");
        }

        //Calls methods to set the balances of the user
        setCurrentAccount();
        setSavingsAccount();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Provides functionality for the back button
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void populateSpinner(){
        //This method will populate the spinner with the options of what transfer to do
        Spinner s = (Spinner) findViewById(R.id.sp_AccountTransfer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    public boolean validateEntry(){
        //This methode ensures the user has entered a value they want to transfer
        TextView view = findViewById(R.id.in_TransferAmount);
        if(view.getText().toString().equals("")){
            return false;
        } else return true;
    }

    public void transfer(View view){
        //Does the transfer by calling and using the appropriate methods
        Spinner s = (Spinner) findViewById(R.id.sp_AccountTransfer);

        if(validateEntry()){
            transferAmount = findViewById(R.id.in_TransferAmount);
            int amount = Integer.parseInt(transferAmount.getText().toString());
            //Toast.makeText(getApplicationContext(),amount+"", Toast.LENGTH_SHORT).show();
            if(s.getSelectedItem().toString().equals("Current to Savings")){
                if(db.getCurrentAccount(db.getReadableDatabase(),user_ID)>=amount){
                    makeTransferFromCurrent(amount);
                } else {
                Toast.makeText(getApplicationContext(),"Insufficient funds", Toast.LENGTH_SHORT).show();
                }
            } else {
                 if(db.getSavingsAccount(db.getReadableDatabase(),user_ID)>=amount) {
                    makeTransferFromSavings(amount);
                 } else {
                    Toast.makeText(getApplicationContext(),"Insufficient funds", Toast.LENGTH_SHORT).show();
                 }
            }
        } else Toast.makeText(getApplicationContext(),"Please enter the amount you'd like to transfer", Toast.LENGTH_SHORT).show();
        setCurrentAccount();
        setSavingsAccount();
    }

    public void makeTransferFromCurrent( int amount){
        //Update the balance of the users accounts when a transfer is made from current to savings
        db.updateBalance(db.getWritableDatabase(),user_ID,getNewCurrentValueMinus(amount),getNewSavingsValueAdd(amount));
        Toast.makeText(getApplicationContext(),"Transfer completed successfully", Toast.LENGTH_SHORT).show();
        transferAmount.setText("");
    }

    public void makeTransferFromSavings(int amount){
        //Update the balance of the users accounts when a transfer is made from savings to current
        db.updateBalance(db.getWritableDatabase(),user_ID,getNewCurrentValueAdd(amount),getNewSavingsValueMinus(amount));
        Toast.makeText(getApplicationContext(),"Transfer completed successfully", Toast.LENGTH_SHORT).show();
        transferAmount.setText("");
    }

    public int getNewCurrentValueMinus(int amount){
        //works out the new current account if the transfer is from current to savings
        int currentVal = db.getCurrentAccount(db.getReadableDatabase(),user_ID);
        int newVal = currentVal-amount;
        return newVal;
    }

    public int getNewCurrentValueAdd(int amount){
        //works out the new current account if the transfer is from savings to current
        int currentVal = db.getCurrentAccount(db.getReadableDatabase(),user_ID);
        int newVal = currentVal+amount;
        return newVal;
    }

    public int getNewSavingsValueMinus(int amount){
        //works out the new savings account if the transfer is from savings to current
        int currentVal = db.getSavingsAccount(db.getReadableDatabase(),user_ID);
        int newVal = currentVal-amount;
        return newVal;
    }

    public int getNewSavingsValueAdd(int amount){
        //works out the new savings account if the transfer is from current to savings
        int currentVal = db.getSavingsAccount(db.getReadableDatabase(),user_ID);
        int newVal = currentVal+amount;
        return newVal;
    }

    public void setCurrentAccount(){
        //set the values of the current account that the user can see
        String currentAccount = db.getCurrentAccount(db.getReadableDatabase(),user_ID)+"";
        userCurrent = findViewById(R.id.tv_CurrentAccountBalance);
        userCurrent.setText("R"+currentAccount);
    }

    public void setSavingsAccount(){
        //set the values of the savings account that the user can see
        String savingsAccount = db.getSavingsAccount(db.getReadableDatabase(),user_ID)+"";
        userSavings = findViewById(R.id.tv_SavingsAccountBalance);
        userSavings.setText("R"+savingsAccount);
    }
}