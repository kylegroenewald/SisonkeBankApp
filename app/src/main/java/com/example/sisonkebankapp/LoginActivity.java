package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText user_Email;
    EditText user_Password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    sqlDatabase db = new sqlDatabase(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        }

        public boolean validateFields(){
            /* This method will validate the fields that the user has entered
            * It will ensure all details have been entered and that they are in the correct format
            */
            user_Email = findViewById(R.id.in_UserEmail);
            user_Password = findViewById(R.id.in_Passsword);
            if(user_Email.getText().toString().isEmpty() || user_Password.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(),"Please enter email address and password",Toast.LENGTH_SHORT).show();
                return false;
            }else {
                if (user_Email.getText().toString().trim().matches(emailPattern)) {
                    if(user_Password.getText().toString().length()>4){
                        return true;
                    } else {
                        Toast.makeText(getApplicationContext(),"Password is too short", Toast.LENGTH_SHORT).show();
                        user_Password.setText("");
                        return false;
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    user_Email.setText("");
                    return false;
                }
            }
        }

    public void serve(View view) {
        /* If 'Don't have an account? Register Here' is clicked this will take the user to the registration page */
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public boolean checkUser(){
        /*Checking the user credentials against the database.
            Checks if the email exists and if the password matches the email.
         */
        String email = user_Email.getText().toString();
        String password = user_Password.getText().toString();
        String db_Email = db.getUserEmail(db.getReadableDatabase(),email);
        String db_Password = db.getUserPassword(db.getReadableDatabase(),email);

        if(db_Password.equals(password) && db_Email.equals(email)){
            return true;
        } else {
            Toast.makeText(getApplicationContext(),"Incorrect login details", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void login(View view) {
        /* If login is clicked:
            1. The fields the user entered will be validated
            2. The details the user entered will be checked against the database
         */
        if(validateFields() == true && checkUser()){
            Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainPageActivity.class);
            intent.putExtra("user_ID",db.getUserID(db.getReadableDatabase(),user_Email.getText().toString()));
            startActivity(intent);
            finish();
        }
    }
}