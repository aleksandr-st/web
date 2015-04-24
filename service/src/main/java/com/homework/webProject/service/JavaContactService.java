package com.homework.webProject.service;

import java.util.*;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.homework.webProject.dao.ContactDao;
import com.homework.webProject.dao.HobbyDao;
import com.homework.webProject.dao.PlaceDao;
import com.homework.webProject.dto.ContactDetailDto;
import com.homework.webProject.dto.ContactDto;
import com.homework.webProject.dto.HobbyDto;
import com.homework.webProject.dto.MessageDto;
import com.homework.webProject.dto.PlaceDto;
import com.homework.webProject.model.Contact;
import com.homework.webProject.model.ContactDetail;
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
	public ContactDto createContact(String firstName, String lastName, DateTime birthDate){
		Contact newContact = contactDao.addContact(new Contact(firstName, lastName, birthDate));
		return contactToContactDto(newContact);
	}
	public HobbyDto addHobby(String title, String description){
		Hobby newHobby = hobbyDao.addHobby(new Hobby(title, description)); 
		return hobbyToHobbyDto(newHobby);
	}
	public PlaceDto addPlace(String title, String description, Double longitude, Double latitude){
		Place newPlace = placeDao.addPlace(new Place(title, description, longitude, latitude)); 
		return placeToPlaceDto(newPlace);
	}
	public ContactDto addFriendship(ContactDto contact, ContactDto contactFriend){
		Contact contactFirst = contactDao.findById(contact.getId());
		Contact contactSecond = contactDao.findById(contactFriend.getId());
		return contactToContactDtoWithDetails(contactDao.addFriendship(contactFirst, contactSecond));
	}
	public Set<ContactDto> getFriendList(ContactDto contact){
		Set<ContactDto> contactSet = null;
		
		return contactSet;
	}
	public List<MessageDto> getConversation(ContactDto contactSender, ContactDto contactRecipient){
		List<MessageDto> messageList = null;
		
		return messageList;
	}
	public ContactDto findById(Long id) {
		Contact contact = contactDao.findById(id); 
		return contactToContactDtoWithDetails(contact);
	}
	public List<ContactDto> findAll() {
		List<Contact> allContacts = contactDao.findAll();
		List<ContactDto> allContactsDto = new ArrayList<ContactDto>();
		for (Contact contact: allContacts){
			allContactsDto.add(contactToContactDto(contact));
		}
		return allContactsDto;
	}
	public ContactDto addOrUpdate(ContactDto contact) {
		Contact contactForUpdate = contactDtoToContact(contact); 
		contactForUpdate = contactDao.addContact(contactForUpdate);
		return contactToContactDtoWithDetails(contactForUpdate);
	}
	public List<HobbyDto> unusedHobbies(ContactDto contact) {
		Contact contactForRequest = contactDao.findById(contact.getId());
		List<Hobby> hobbies = hobbyDao.findAllUnusedForContact(contactForRequest);
		List<HobbyDto> hobbiesDto = new ArrayList<HobbyDto>();
		for (Hobby hobby: hobbies){
			hobbiesDto.add(hobbyToHobbyDto(hobby));
		}
		return hobbiesDto;
	}
	//to Dto and from Dto conversion
	public ContactDto contactToContactDto(Contact contact) {
		ContactDto contactDto = new ContactDto(contact.getFirstName(),
				contact.getLastName(), contact.getBirthDate());
		contactDto.setId(contact.getId());
//		System.out.println("time: "+contact.getBirthDate()+"; new time: "+contactDto.getBirthDate());
		contactDto.setVersion(contact.getVersion());
		return contactDto;
	}
	public ContactDto contactToContactDtoWithDetails(Contact contact) {
		ContactDto contactDto = contactToContactDto(contact);
		Set<Hobby> hobbies = contact.getHobbies();
		Set<HobbyDto> hobbiesDto = new HashSet<HobbyDto>();
		for (Hobby hobby: hobbies){
			hobbiesDto.add(hobbyToHobbyDto(hobby));
		}
		contactDto.setHobbies(hobbiesDto);
		Set<Place> places = contact.getPlaces();
		Set<PlaceDto> placesDto = new HashSet<PlaceDto>();
		for (Place place: places){
			placesDto.add(placeToPlaceDto(place));
		}
		contactDto.setPlaces(placesDto);
		Set<Contact> friends = contact.getFriends();
		Set<ContactDto> friendsDto = new HashSet<ContactDto>();
		for (Contact friend: friends){
			friendsDto.add(contactToContactDto(friend));
		}
		contactDto.setFriends(friendsDto);
		Set<ContactDetail> details = contact.getContactDetails();
		Set<ContactDetailDto> detailsDto = new HashSet<ContactDetailDto>();
		for (ContactDetail detail: details){
			detailsDto.add(detailToDetailDto(detail));
		}
		contactDto.setContactDetails(detailsDto);
		return contactDto;
	}
	public HobbyDto hobbyToHobbyDto(Hobby hobby) {
		HobbyDto hobbyDto = new HobbyDto(hobby.getTitle(),hobby.getDescription());
		return hobbyDto;
	}
	public MessageDto messageToMessageDto(Message message) {
		// TODO Auto-generated method stub
		return null;
	}
	public PlaceDto placeToPlaceDto(Place place) {
		PlaceDto placeDto = new PlaceDto(place.getTitle(),
				place.getDescription(), place.getLongitude(), place.getLatitude());
		return placeDto;
	}
	public ContactDetailDto detailToDetailDto(ContactDetail detail) {
		ContactDetailDto detailDto = new ContactDetailDto(detail.getType(),
				detail.getData());
		detailDto.setId(detail.getId());
		return detailDto;
	}
	public Contact contactDtoToContact(ContactDto contactDto) {
		Contact contact = contactDao.findById(contactDto.getId());
		if (contact == null){
			contact = new Contact();
		};
		contact.setFirstName(contactDto.getFirstName());
		contact.setLastName(contactDto.getLastName());
		contact.setBirthDate(contactDto.getBirthDate());
//		System.out.println("time: "+contactDto.getBirthDate()+"; new time: "+contact.getBirthDate());
		contact.setVersion(contactDto.getVersion());
		return contact;
	}
	public ContactDto removeFriendship(ContactDto contact,
			ContactDto contactFriend) {
		Contact contactFirst = contactDao.findById(contact.getId());
		Contact contactSecond = contactDao.findById(contactFriend.getId());
		return contactToContactDtoWithDetails(contactDao.removeFriendship(contactFirst, contactSecond));
	}

}
