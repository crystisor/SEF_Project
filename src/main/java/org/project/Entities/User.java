package org.project.Entities;
import org.project.Services.UserService;

public class User {

    String userId;
    String firstName;
    String lastName;
    String email;
    String address;
    String phone;
    String password;
    String balance;

    public String getUserId() {
        return userId;
    }

    public User(String firstName, String lastName, String email, String address, String phone, String password,String balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.balance = balance;
    }

    public int isValidUser(){

        UserService srv = new UserService();
        if( this.firstName.length()<3 || this.lastName.length()<3){
            return 1;
        }
        if( !srv.isEmailValid(this.email) ) {
            return 2;
        }
        if( this.address.length()<10 ) {
            return 3;
        }
        if( srv.isPhoneValid(this.phone) ) {
            return 4;
        }
        return 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
