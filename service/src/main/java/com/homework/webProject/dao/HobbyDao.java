package com.homework.webProject.dao;

import java.util.List;

import com.homework.webProject.model.Contact;
import com.homework.webProject.model.Hobby;


public interface HobbyDao {
	public Hobby addHobby(Hobby hobby);
	
	public void removeHobby(Hobby hobby);
	
	public List<Contact> getAllContactsWithHobby(Hobby hobby);
	
	public Hobby findById(String id);
	
	public List<Hobby> findAll();

	public List<Hobby> findAllUnusedForContact(Contact contact);
}
