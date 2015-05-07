package com.homework.webProject.form;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageMSTest {

	@Test
	public void testMessageMSStringString() {
		String type = "error";
		String messageText = "message";
		MessageMS message = new MessageMS(type, messageText);
		assertNotNull(message);
		assertEquals(type, message.getType());
		assertEquals(messageText, message.getMessage());
	}

	@Test
	public void testSetGetType() {
		MessageMS message = new MessageMS();
		String type = "error";
		message.setType(type);
		assertNotNull(message.getType());
		assertEquals(type, message.getType());
	}

	@Test
	public void testSetGetMessage() {
		MessageMS message = new MessageMS();
		String messageText = "message";
		message.setMessage(messageText);;
		assertNotNull(message.getMessage());
		assertEquals(messageText, message.getMessage());
	}

}
