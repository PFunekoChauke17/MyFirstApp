/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationlogin;

/**
 *
 * @author Student
 */
public class Login {

    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    public Login() {
    }

    // Username must contain an underscore and be no more than 5 characters
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Password: 8+ letters ,chars, capital letter, number, special character
    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    // SA number must contain +27 followed by 9 digits (max 10 chars)
    // Regex reference: adapted from GeeksforGeeks, "Validate Phone Number in Java"
    // https://www.geeksforgeeks.org/ (add your exact source here for attribution)
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber.matches("^\\+27\\d{9}$");
    }

    public String registerUser(String username, String password,
            String cellPhoneNumber, String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your "
                    + "username contains an underscore and is no more than five "
                    + "characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the "
                    + "password contains at least eight characters, a capital "
                    + "letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell number is incorrectly formatted or does not contain an "
                    + "international code; please correct the number and try again.";
        }
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        return "User has been registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        return this.username != null
                && this.username.equals(username)
                && this.password.equals(password);
    }

    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you.";
        }
        return "Username or password incorrect, please try again.";
    }
}