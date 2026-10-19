/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationlogin;

import java.util.Scanner;

public class Quickchat {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {

        // Requirement 1: only logged-in users may send messages
        if (!login()) {
            System.out.println("Login failed. Only logged-in users can send messages.");
            return;
        }

        System.out.println("Welcome to QuickChat.");

        // Requirement 5: user defines the number of messages at the start
        int numMessages = readInt("How many messages would you like to enter? ");
        int messagesEntered = 0;

        // Requirement 4: runs until the user selects 'quit'
        boolean running = true;
        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");

            int option = readInt("Enter your choice: ");
            switch (option) {
                case 1 -> {
                    if (messagesEntered >= numMessages) {
                        System.out.println("You may only enter " + numMessages + " message(s), and you have already entered them all.");
                        break;
                    }
                    for (int i = messagesEntered; i < numMessages; i++) {
                        Message msg = new Message(i);
                        System.out.println("Message ID generated: " + msg.getMessageID());

                        while (true) {   // recipient: re-prompt until valid
                            System.out.print("Enter recipient cell number (with international code, e.g. +27718693002): ");
                            msg.setRecipientCell(SC.nextLine());
                            String result = msg.checkRecipientCell();
                            System.out.println(result);
                            if (result.equals("Cell phone number successfully captured.")) break;
                        }

                        while (true) {   // message: re-prompt until <= 250 chars
                            System.out.print("Enter your message (max 250 characters): ");
                            msg.setMessageContent(SC.nextLine());
                            String result = msg.checkMessage();
                            System.out.println(result);
                            if (result.equals("Message ready to send.")) break;
                        }

                        System.out.println("Message hash: " + msg.createMessageHash());
                        System.out.println(msg.SentMessage());
                        messagesEntered++;
                    }
                }
                case 2 -> System.out.println("Coming Soon.");
                case 3 -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("\nTotal messages sent: " + Message.returnTotalMessages());
        System.out.println(Message.printMessages());
        System.out.println("Thank you for using QuickChat. Goodbye!");
    }

    // === PART 1 INTEGRATION POINT ===
    // Replace this body with your Part 1 Login class, e.g.
    //   Login user = new Login(username, password, firstName, lastName);
    //   return user.loginUser(username, password);
    private static boolean login() {
        System.out.println("===== QUICKCHAT LOGIN (Part 1) =====");
        System.out.print("Enter username: ");
        String username = SC.nextLine();
        System.out.print("Enter password: ");
        String password = SC.nextLine();
        return username != null && !username.isBlank() && password != null && !password.isBlank();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(SC.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Please enter a valid number."); }
        }
    }
}