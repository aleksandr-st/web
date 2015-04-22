package com.homework.webProject.service;

import java.util.*;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.homework.webProject.dao.ContactDao;
import com.homework.webProject.dao.HobbyDao;
import com.homework.webProject.dao.PlaceDao;
import com.homework.webProject.model.Contact;
import com.homework.webProject.model.Hobby;
import com.homework.webProject.model.Message;
import com.homework.webProject.model.Place;

@Service("contactService")
public class JavaContactService implements ContactService{
	@Resource(name="contactDao")
	private ContactDao contactDao;
	@Resource(name="hobbyDao")
	private HobbyDao hobbyDao;
	@Resource(name="placeDao")
	private PlaceDao placeDao;
	
	public JavaContactService(){
	}
	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}
	public void setHobbyDao(HobbyDao hobbyDao) {
		this.hobbyDao = hobbyDao;
	}
	public void setPlaceDao(PlaceDao placeDao) {
		this.placeDao = placeDao;
	}
	public ContactDao getContactDao() {
		return contactDao;
	}
	public HobbyDao getHobbyDao() {
		return hobbyDao;
	}
	public PlaceDao getPlaceDao() {
		return placeDao;
	}
	public Contact createContact(String firstName, String lastName, DateTime birthDate){
		Contact newContact = new Contact(firstName, lastName, birthDate);
		return contactDao.addContact(newContact);
	}
	public Hobby addHobby(String title, String description){
		return hobbyDao.addHobby(new Hobby(title, description));
	}
	public Place addPlace(String title, String description, Double longitude, Double latitude){
		return placeDao.addPlace(new Place(title, description, longitude, latitude));
	}
	public void addFriendship(Contact contact, Contact contactFriend){
		contactDao.addFriendship(contact, contactFriend);
	}
	public Set<Contact> getFriendList(Contact contact){
		Set<Contact> contactSet = null;
		
		return contactSet;
	}
	public List<Message> getConversation(Contact contactSender, Contact contactRecipient){
		List<Message> messageList = null;
		
		return messageList;
	}
	public Contact findById(Long id) {
		return contactDao.findById(id);
	}
	public List<Contact> findAll() {
		return contactDao.findAll();
	}
	public Contact addOrUpdate(Contact contact) {
		contactDao.addContact(contact);
		return contact;
	}
	public List<Hobby> unusedHobbies(Contact contact) {
		return hobbyDao.findAllUnusedForContact(contact);
	}

}
