package com.example.sisonkebankapp;

public class UserDetails {
    private int user_ID;
    private String user_FirstName;
    private String user_LastName;
    private String user_Email;
    private String user_Gender;
    private String user_Mobile;
    private String user_Password;
    private int currentAccount;
    private int savingsAccount;

    public UserDetails(int user_ID, String user_FirstName, String user_LastName, String user_Email, String user_Gender, String user_Mobile, String user_Password, int currentAccount, int savingsAccount) {
        /*
        This class is used to create an instance of a user.
        It can store all the users information.
        It has getter and setter methods.
        It is used when creating a new user.
         */
        this.user_ID = user_ID;
        this.user_FirstName = user_FirstName;
        this.user_LastName = user_LastName;
        this.user_Email = user_Email;
        this.user_Gender = user_Gender;
        this.user_Mobile = user_Mobile;
        this.user_Password = user_Password;
        this.currentAccount = currentAccount;
        this.savingsAccount = savingsAccount;
    }

    public int getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(int currentAccount) {
        this.currentAccount = currentAccount;
    }

    public int getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(int savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public UserDetails() {
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public String getUser_FirstName() {
        return user_FirstName;
    }

    public void setUser_FirstName(String user_FirstName) {
        this.user_FirstName = user_FirstName;
    }

    public String getUser_LastName() {
        return user_LastName;
    }

    public void setUser_LastName(String user_LastName) {
        this.user_LastName = user_LastName;
    }

    public String getUser_Email() {
        return user_Email;
    }

    public void setUser_Email(String user_Email) {
        this.user_Email = user_Email;
    }

    public String getUser_Gender() {
        return user_Gender;
    }

    public void setUser_Gender(String user_Gender) {
        this.user_Gender = user_Gender;
    }

    public String getUser_Mobile() {
        return user_Mobile;
    }

    public void setUser_Mobile(String user_Mobile) {
        this.user_Mobile = user_Mobile;
    }

    public String getUser_Password() {
        return user_Password;
    }

    public void setUser_Password(String user_Password) {
        this.user_Password = user_Password;
    }
}
