package com.homework.webProject.service;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.homework.webProject.model.Contact;
import com.homework.webProject.model.Hobby;
import com.homework.webProject.model.Message;
import com.homework.webProject.model.Place;

public interface ContactService {
	
	Contact createContact(String firstName, String lastName, DateTime birthDate);
	
	Hobby addHobby(String title, String description);
	
	List<Hobby> unusedHobbies(Contact contact);
	
	Place addPlace(String title, String description, Double longitude, Double latitude);
	
	void addFriendship(Contact contact, Contact contactFriend);
	
	Set<Contact> getFriendList(Contact contact);
	
	List<Message> getConversation(Contact contactSernder, Contact contactRecipient);
	
	Contact findById(Long id);
	
	List<Contact> findAll();
	
	Contact addOrUpdate(Contact contact);

}
