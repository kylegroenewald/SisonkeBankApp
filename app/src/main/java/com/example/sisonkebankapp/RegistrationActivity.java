package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    EditText user_Email;
    EditText user_Password;
    EditText user_FirstName;
    EditText user_LastName;
    EditText user_Phone;
    String user_Gender = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public boolean validateNames(){
        /*
        This method will make sure that the user has entered something in both the first name and last name fields
         */
        user_FirstName = findViewById(R.id.in_FirstName);
        user_LastName = findViewById(R.id.in_LastName);

        if(user_FirstName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter your first name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user_LastName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter your last name", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    public boolean validateEmail() {
        /*
        This method will ensure the email has been entered and that it follows the correct format
         */
        user_Email = findViewById(R.id.in_Email);
        if (user_Email.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter email address", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (user_Email.getText().toString().trim().matches(emailPattern)) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public boolean validatePassword(){
        /*
        This method will make sure a password has been entered and that it is at least 5 characters long
         */
        user_Password = findViewById(R.id.in_UserPassword);

        if(user_Password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(user_Password.getText().toString().length()>4){
                return true;
            } else {
                Toast.makeText(getApplicationContext(),"Password is too short", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public boolean validateMobile(){
        /*
        This method will make sure that the user has entered a mobile number
        The enter text field has been set so that the user can only enter an number on the XML layout page
         */
        user_Phone = findViewById(R.id.in_Phone);

        if(user_Phone.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    public boolean validateGender(){
        /*
        This method will ensure that a gender has been selected
         */
        if(user_Gender.equals("")){
            Toast.makeText(getApplicationContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    public boolean checkUserExists(){
        /*
        This method is called after the validations have been checked.
        It will then check if a user with the entered email already exists
         */
        sqlDatabase db = new sqlDatabase(RegistrationActivity.this);
        if(db.getUserEmail(db.getReadableDatabase(), user_Email.getText().toString()).equals(user_Email.getText().toString())){
            return true;
        } else return false;
    }

    public void createAccount(View view){
        /*
        This method will be run when the user taps the CREATE ACCOUNT button
        it runs the validation methods, checks if the user exists and finally will add the user
         */
        if (validateNames() == true && validateEmail() == true && validatePassword() == true && validateMobile() == true && validateGender() == true){
            if(checkUserExists()){
                Toast.makeText(getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT).show();
            } else {
                addUser();
                Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    public void addUser(){
        /*
        This method will make use of the UserDetails class in order to add a user to the SQLite Database
         */
        UserDetails ud;
        try{
            ud = new UserDetails(-1, user_FirstName.getText()+"", user_LastName.getText()+"", user_Email.getText()+"", user_Gender, user_Phone.getText()+"", user_Password.getText()+"",5500,11987);
        }catch(Exception e){
            ud = new UserDetails(-1, "error", "error", "error", "error", "error", "false",0,0);
            Toast.makeText(RegistrationActivity.this, "Error creating the customer", Toast.LENGTH_SHORT).show();
        }
        sqlDatabase database = new sqlDatabase(RegistrationActivity.this);
        database.addUser(ud);
    }

    public void toLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        /*
        This method will be run when the radio button is selected and will set the users gender
         */
        RadioGroup rg = findViewById(R.id.rd_Group);
        user_Gender = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
    }
}