/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registrationlogin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testCheckMessageID_Valid() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testCheckMessageID_TooLong() {
        Message msg = new Message("00123456789012", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertFalse(msg.checkMessageID());
    }

    @Test
    public void testCheckRecipientCell_Success() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Failure_NoInternationalCode() {
        Message msg = new Message("0012345678", 0, "0857597589", "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    @Test
    public void testMessageLength_Success() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessage());
    }

    @Test
    public void testMessageLength_Failure_Exceeds250() {
        Message msg = new Message("0012345678", 0, "+27718693002", "a".repeat(260));
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", msg.checkMessage());
    }

    @Test
    public void testCreateMessageHash_TestCase1() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    @Test
    public void testCreateMessageHash_LoopOverRemainingMessages() {
        String[] contents = { "Hi Mike, can you join us for dinner tonight?",
                              "Hi Keegan, did you receive the payment?" };
        String[] expected = { "00:0:HITONIGHT", "00:1:HIPAYMENT" };
        for (int i = 0; i < contents.length; i++) {
            Message msg = new Message("0012345678", i, "+27718693002", contents[i]);
            assertEquals(expected[i], msg.createMessageHash());
        }
    }
}
