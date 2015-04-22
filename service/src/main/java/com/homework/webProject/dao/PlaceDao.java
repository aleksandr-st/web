package com.homework.webProject.dao;

import java.util.List;

import com.homework.webProject.model.Contact;
import com.homework.webProject.model.Place;


public interface PlaceDao {
	Place addPlace(Place place);
	
	List<Contact> getAllContactsForPlace(Place place);

}
