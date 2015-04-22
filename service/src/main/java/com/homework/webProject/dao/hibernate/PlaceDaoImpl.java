package com.homework.webProject.dao.hibernate;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.homework.webProject.dao.PlaceDao;
import com.homework.webProject.model.Contact;
import com.homework.webProject.model.Place;

@Repository("placeDao")
public class PlaceDaoImpl implements PlaceDao{
	
	public Place addPlace(Place place){
		return null;
	}
	public List<Contact> getAllContactsForPlace(Place place){
		List<Contact> contactSet = null;
		
		return contactSet;
	}

}
