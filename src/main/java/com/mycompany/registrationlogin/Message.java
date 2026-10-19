/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationlogin;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private final String messageID;      // random ten-digit tracking number
    private final int numMessagesSent;   // sequence number (0, 1, 2 ... auto-incremented)
    private String recipientCell;
    private String messageContent;       // max 250 characters
    private String messageHash;

    private static int totalMessagesSent = 0;
    private static final ArrayList<Message> SENT_MESSAGES = new ArrayList<>();
    private static final String JSON_FILE = "messages.json";
    private static final Scanner SC = new Scanner(System.in);

    // Creates a message with a freshly generated random 10-digit ID
    public Message(int numMessagesSent) {
        this.messageID = generateMessageID();
        this.numMessagesSent = numMessagesSent;
    }

    // Constructor used by the unit tests (lets us control the ID)
    public Message(String messageID, int numMessagesSent, String recipientCell, String messageContent) {
        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
    }

    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) id.append(rand.nextInt(10));
        return id.toString();
    }

    // Boolean: checks the message ID is not more than ten characters
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // String: checks recipient number (international code + max 10 digits)
    public String checkRecipientCell() {
        if (recipientCell != null && recipientCell.startsWith("+") && recipientCell.length() <= 12) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. "
             + "Please correct the number and try again.";
    }

    // Checks the message is not more than 250 characters (used by unit tests)
    public String checkMessage() {
        final int MAX = 250;
        if (messageContent != null && messageContent.length() <= MAX) return "Message ready to send.";
        return "Message exceeds 250 characters by " + (messageContent.length() - MAX) + "; please reduce the size.";
    }

    // String: builds the Message Hash, e.g. 00:0:HITONIGHT
    public String createMessageHash() {
        String[] words = messageContent.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last  = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        messageHash = messageID.substring(0, 2) + ":" + numMessagesSent + ":"
                    + (first + last).toUpperCase();
        return messageHash;
    }

    // String: lets the user Send, Disregard or Store the message
    public String SentMessage() {
        System.out.println("What would you like to do with this message?");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message");
        int choice = Integer.parseInt(SC.nextLine().trim());

        switch (choice) {
            case 1:
                SENT_MESSAGES.add(this);
                totalMessagesSent++;
                storeMessage();
                return "Message successfully sent.";
            case 2:
                System.out.println("Press 0 to delete the message.");
                int delete = Integer.parseInt(SC.nextLine().trim());
                if (delete == 0) return "Message disregarded and deleted.";
                return "Message kept.";
            case 3:
                SENT_MESSAGES.add(this);
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    // String: returns all messages sent while the program is running
    public static String printMessages() {
        if (SENT_MESSAGES.isEmpty()) return "No messages have been sent yet.";
        StringBuilder sb = new StringBuilder("Messages sent while the program was running:\n");
        for (Message m : SENT_MESSAGES) {
            sb.append("----------------------------------\n")
              .append("Message ID : ").append(m.messageID).append("\n")
              .append("Recipient  : ").append(m.recipientCell).append("\n")
              .append("Message    : ").append(m.messageContent).append("\n")
              .append("Hash       : ").append(m.messageHash).append("\n");
        }
        return sb.toString();
    }

    // Int: returns the total number of messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Own method: stores messages in JSON (no external libraries needed)
    public void storeMessage() {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < SENT_MESSAGES.size(); i++) {
            Message m = SENT_MESSAGES.get(i);
            json.append("  {\n")
                .append("    \"messageID\": \"").append(m.messageID).append("\",\n")
                .append("    \"numMessagesSent\": ").append(m.numMessagesSent).append(",\n")
                .append("    \"recipientCell\": \"").append(escapeJson(m.recipientCell)).append("\",\n")
                .append("    \"message\": \"").append(escapeJson(m.messageContent)).append("\",\n")
                .append("    \"messageHash\": \"").append(m.messageHash).append("\"\n")
                .append("  }").append(i < SENT_MESSAGES.size() - 1 ? "," : "").append("\n");
        }
        json.append("]");
        try (PrintWriter writer = new PrintWriter(new FileWriter(JSON_FILE))) {
            writer.print(json.toString());
            System.out.println("Message saved to " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("Could not store the message: " + e.getMessage());
        }
    }

    private String escapeJson(String value) {
        return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // getters & setters
    public String getMessageID()            { return messageID; }
    public int getNumMessagesSent()         { return numMessagesSent; }
    public String getRecipientCell()        { return recipientCell; }
    public void setRecipientCell(String r)  { this.recipientCell = r; }
    public String getMessageContent()       { return messageContent; }
    public void setMessageContent(String m) { this.messageContent = m; }
    public String getMessageHash()          { return messageHash; }
}
