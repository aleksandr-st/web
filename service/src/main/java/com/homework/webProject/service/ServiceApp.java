package com.homework.webProject.service;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.homework.webProject.dao.ContactDao;
import com.homework.webProject.dao.HobbyDao;
import com.homework.webProject.dto.ContactDetailDto;
import com.homework.webProject.dto.ContactDto;
import com.homework.webProject.dto.HobbyDto;
import com.homework.webProject.model.Contact;
import com.homework.webProject.model.Hobby;

public class ServiceApp {

	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:data-source.xml");
		ctx.refresh();
		
		JavaContactService contactService = new JavaContactService();
		contactService.setContactDao(ctx.getBean("contactDao", ContactDao.class));
		contactService.setHobbyDao(ctx.getBean("hobbyDao", HobbyDao.class));
		DateTime dateTime = new DateTime(2000,2,10,0,0);
		contactService.createContact("Alex", "Gameson", dateTime);

		List<Contact> contacts = contactService.getContactDao().findAll();
		for(Contact contact: contacts){
			System.out.println(contact);
		}
		
		ContactDto contact = contactService.findById(1l);
		System.out.println("Contact friends:");
		for (ContactDto c_friend: contact.getFriends()){
			System.out.println("  f: " + c_friend.getFirstName());
		}
		System.out.println("Contact details:");
		for (ContactDetailDto c_detail: contact.getContactDetails()){
			System.out.println("  d: " + c_detail);
		}
		ContactDto friend = contactService.findById(2l);
		ContactDetailDto contactDetail = new ContactDetailDto("tel.","0356654666",contact);
		Set<ContactDetailDto> contactDetails = contact.getContactDetails();
		contactDetails.add(contactDetail);
		contact.setContactDetails(contactDetails);
		contactService.addFriendship(contact, friend);
		System.out.println("Contact friends after add:");
		for (ContactDto c_friend: contact.getFriends()){
			System.out.println("  f: " + c_friend.getFirstName());
		}
		contact = contactService.removeFriendship(contact, friend);
		System.out.println("Contact friends after remove:");
		for (ContactDto c_friend: contact.getFriends()){
			System.out.println("  f: " + c_friend.getFirstName());
		}
		System.out.println("Contact hobbies:");
		for (HobbyDto c_hobby: contact.getHobbies()){
			System.out.println("  h: " + c_hobby.getTitle());
		}
		Hobby hobby = contactService.getHobbyDao().findById("Programming");
		contactService.getHobbyDao().removeHobby(hobby);
		contact = contactService.findById(1l);
		System.out.println("Contact hobbies after remove one:");
		for (HobbyDto c_hobby: contact.getHobbies()){
			System.out.println("  h: " + c_hobby.getTitle());
		}
		System.out.println("Contact details after add one:");
		for (ContactDetailDto c_detail: contact.getContactDetails()){
			System.out.println("  d: " + c_detail);
		}
		
		System.out.println(contact);
		
		ctx.close();
}

}
