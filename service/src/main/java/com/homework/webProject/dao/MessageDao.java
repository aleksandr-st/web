package com.homework.webProject.dao;

import java.util.List;

import com.homework.webProject.model.Contact;
import com.homework.webProject.model.Message;

public interface MessageDao {
	
	Message addMessage(Message message);
	
	List<Message> getConvercation(Contact contact1, Contact contact2);

	List<Message> getNewIncome(Contact contact1, Contact contact2, Long lastId);
}
