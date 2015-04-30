package com.homework.webProject.dao.hibernate;

import java.util.*;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.homework.webProject.dao.PlaceDao;
import com.homework.webProject.model.Contact;
import com.homework.webProject.model.Place;

@Repository("placeDao")
@Transactional
public class PlaceDaoImpl implements PlaceDao{
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory(){
		return this.sessionFactory;
	}
	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	public Place addPlace(Place place){
		return null;
	}
	public List<Contact> getAllContactsForPlace(Place place){
		return sessionFactory.getCurrentSession().getNamedQuery("Place.findAllWithPlace").setParameter("placeId", place).list();
	}

	@Transactional(readOnly=true)
	public List<Place> findAll(){
		return sessionFactory.getCurrentSession().createQuery("from Place p").list();
	}

	@Transactional(readOnly=true)
	public List<Place> findAllUnusedForContact(Contact contact) {
		List<Place> allPlaces = findAll();
		if (contact == null){
			return allPlaces;
		}
		if (contact.getHobbies() == null){
			return allPlaces;
		}
		for (Place place: contact.getPlaces()){
			allPlaces.remove(place);
		};
		return allPlaces;
	}
	public Place findById(String id) {
		return (Place)sessionFactory.getCurrentSession().getNamedQuery("Place.findById").setParameter("id", id).uniqueResult();
	}
}
