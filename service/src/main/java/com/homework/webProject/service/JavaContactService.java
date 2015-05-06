package com.homework.webProject.service;

import java.util.*;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.homework.webProject.dao.ContactDao;
import com.homework.webProject.dao.ContactDetailDao;
import com.homework.webProject.dao.HobbyDao;
import com.homework.webProject.dao.MessageDao;
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
	@Resource(name="contactDetailDao")
	private ContactDetailDao contactDetailDao;
	@Resource(name="messageDao")
	private MessageDao messageDao;
	
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
	public List<ContactDto> getFriendList(ContactDto contactDto){
		List<ContactDto> friendsDto = new ArrayList<ContactDto>();
		if (contactDto.getId() != null) {
			Contact contact = contactDao.findById(contactDto.getId());
			if (contact.getFriends() != null) {
				for (Contact friend : contact.getFriends()) {
					friendsDto.add(contactToContactDto(friend));
				}
			}
		}
		return friendsDto;
	}
	public List<MessageDto> getConversation(ContactDto contactSender, ContactDto contactRecipient){
		List<MessageDto> messagesDto = new ArrayList<MessageDto>();
		Contact contact1 = contactDao.findByIdWithoutDetails(contactSender.getId());
		Contact contact2 = contactDao.findByIdWithoutDetails(contactRecipient.getId());
		List<Message> messages = messageDao.getConvercation(contact1, contact2);
		Iterator<Message> it = messages.iterator();
		Message message = null;
		while (it.hasNext()){
			message = it.next();
			messagesDto.add(messageToMessageDto(message));
			System.out.println(message);
		}
		return messagesDto;
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
		if (hobbies != null) {
			for (Hobby hobby : hobbies) {
				hobbiesDto.add(hobbyToHobbyDto(hobby));
			};
		};
		contactDto.setHobbies(hobbiesDto);
		Set<Place> places = contact.getPlaces();
		Set<PlaceDto> placesDto = new HashSet<PlaceDto>();
		if (places != null) {
			for (Place place : places) {
				placesDto.add(placeToPlaceDto(place));
			};
		};
		contactDto.setPlaces(placesDto);
		Set<Contact> friends = contact.getFriends();
		Set<ContactDto> friendsDto = new HashSet<ContactDto>();
		if (friends != null) {
			for (Contact friend : friends) {
				friendsDto.add(contactToContactDto(friend));
			};
		};
		contactDto.setFriends(friendsDto);
		Set<ContactDetail> details = contact.getContactDetails();
		Set<ContactDetailDto> detailsDto = new HashSet<ContactDetailDto>();
		if (details != null) {
			for (ContactDetail detail : details) {
				detailsDto.add(detailToDetailDto(detail));
			};
		};
		contactDto.setContactDetails(detailsDto);
		return contactDto;
	}
	public HobbyDto hobbyToHobbyDto(Hobby hobby) {
		HobbyDto hobbyDto = new HobbyDto(hobby.getTitle(),hobby.getDescription());
		return hobbyDto;
	}
	public MessageDto messageToMessageDto(Message message) {
		ContactDto from = contactToContactDto(message.getFrom());
		ContactDto to = contactToContactDto(message.getTo());
		MessageDto messageDto = new MessageDto(message.getDate(), from, 
				to, message.getContent());
		messageDto.setId(message.getId());
		return messageDto;
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
		contact.setVersion(contactDto.getVersion());
		if (contactDto.getHobbies() != null){
			HashSet<Hobby> newHobbies = new HashSet<Hobby>();
			for (HobbyDto hobbyDto: contactDto.getHobbies()){
				newHobbies.add(hobbyDao.findById(hobbyDto.getTitle()));
			}
			contact.setHobbies(newHobbies);
		}
		if (contactDto.getPlaces() != null){
			HashSet<Place> newPlaces = new HashSet<Place>();
			for (PlaceDto placeDto: contactDto.getPlaces()){
				newPlaces.add(placeDao.findById(placeDto.getTitle()));
			}
			contact.setPlaces(newPlaces);
		}
		if (contactDto.getFriends() != null){
			HashSet<Contact> newFriends = new HashSet<Contact>();
			for (ContactDto friendDto: contactDto.getFriends()){
				newFriends.add(contactDao.findById(friendDto.getId()));
			}
			contact.setFriends(newFriends);
		}
		if (contactDto.getContactDetails() != null){
			HashSet<ContactDetail> newDetails = new HashSet<ContactDetail>();
			for (ContactDetailDto detailDto: contactDto.getContactDetails()){
				newDetails.add(contactDetailDao.findById(detailDto.getId()));
			}
			contact.setContactDetails(newDetails);
		}
		return contact;
	}
	public ContactDto removeFriendship(ContactDto contact,
			ContactDto contactFriend) {
		Contact contactFirst = contactDao.findById(contact.getId());
		Contact contactSecond = contactDao.findById(contactFriend.getId());
		return contactToContactDtoWithDetails(contactDao.removeFriendship(contactFirst, contactSecond));
	}
	public List<PlaceDto> unusedPlaces(ContactDto contact) {
		Contact contactForRequest = contactDao.findById(contact.getId());
		List<Place> places = placeDao.findAllUnusedForContact(contactForRequest);
		List<PlaceDto> placesDto = new ArrayList<PlaceDto>();
		for (Place place: places){
			placesDto.add(placeToPlaceDto(place));
		}
		return placesDto;
	}
	public ContactDto deleteDetail(ContactDetailDto detail) {
		ContactDetail contactDetail = contactDetailDao.findById(detail.getId());
		Contact contact = contactDao.findById(contactDetail.getContact().getId());
		Set<ContactDetail> details = contact.getContactDetails();
		details.remove(contactDetail);
		contact.setContactDetails(details);
		return contactToContactDto(contactDao.addContact(contact));
	}
	public ContactDto addDetail(ContactDetailDto detail) {
		ContactDetail contactDetail = new ContactDetail(detail.getType(),detail.getData());
		Contact contact = contactDao.findById(detail.getContact().getId());
		contactDetail.setContact(contact);
		Set<ContactDetail> details = contact.getContactDetails();
		details.add(contactDetail);
		contact.setContactDetails(details);
		return contactToContactDtoWithDetails(contactDao.addContact(contact));
	}
	public String deleteContact(ContactDto contact) {
		Contact contactForDelete = contactDao.findById(contact.getId());
		contactDao.deleteContact(contactForDelete);
		return "Contact was deleted successfull.";
	}
	public List<ContactDto> findbyName(ContactDto contactDto, String name) {
		Contact contact = contactDao.findById(contactDto.getId());
		List<Contact> foundContacts = contactDao.findByName(name);
		List<ContactDto> foundContactsDto = new ArrayList<ContactDto>();
		Set<Contact> friends = contact.getFriends();
		Contact contactIt;
		Iterator<Contact> it = foundContacts.iterator();
		while (it.hasNext()) {
			contactIt = it.next();
			if (!contactIt.equals(contact)) {
				if (!friends.contains(contactIt)) {
					foundContactsDto.add(contactToContactDto(contactIt));
				}
			}
		}
		return foundContactsDto;
	}
	public MessageDto addMessage(ContactDto sender, ContactDto recipient,
			String content) {
		Message message = new Message(new DateTime(), contactDao.findById(sender.getId()),
				contactDao.findById(recipient.getId()), content);
		return messageToMessageDto(messageDao.addMessage(message));
	}
	public ContactDto findByIdWithoutDetails(Long id) {
		Contact contact = contactDao.findByIdWithoutDetails(id); 
		return contactToContactDto(contact);
	}
	public List<MessageDto> newIncomeMessages(ContactDto sender,
			ContactDto recipient, Long lastId) {
		Contact contact1 = contactDao.findByIdWithoutDetails(sender.getId());
		Contact contact2 = contactDao.findByIdWithoutDetails(recipient.getId());
		List<MessageDto> messagesDto = new ArrayList<MessageDto>();
		List<Message> messages = messageDao.getNewIncome(contact1, contact2, lastId);
		Message messageIt;
		Iterator<Message> it = messages.iterator();
		while (it.hasNext()) {
			messageIt = it.next();
			messagesDto.add(messageToMessageDto(messageIt));
		}
		return messagesDto;
	}

}
