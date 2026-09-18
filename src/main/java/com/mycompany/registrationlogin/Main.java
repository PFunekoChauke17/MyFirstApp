/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registrationlogin;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();
        boolean registered = false;
        int choice = 0;

        while (choice != 3) {
            System.out.println("===== CHAT APP =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter first name: ");
                    String first = sc.nextLine();
                    System.out.print("Enter last name: ");
                    String last = sc.nextLine();
                    System.out.print("Enter username: ");
                    String user = sc.nextLine();
                    System.out.print("Enter password: ");
                    String pass = sc.nextLine();
                    System.out.print("Enter SA cell number (e.g. +27838968976): ");
                    String cell = sc.nextLine();

                    String msg = login.registerUser(user, pass, cell, first, last);
                    System.out.println(msg);
                    if (msg.equals("User has been registered successfully.")) {
                        registered = true;
                    }
                    break;

                case 2:
                    if (!registered) {
                        System.out.println("No registered user. Register first.");
                        break;
                    }
                    System.out.print("Enter username: ");
                    String u = sc.nextLine();
                    System.out.print("Enter password: ");
                    String p = sc.nextLine();
                    System.out.println(login.returnLoginStatus(u, p));
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
        sc.close();
    }
}
