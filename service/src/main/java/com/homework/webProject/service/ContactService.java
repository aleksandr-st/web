package com.homework.webProject.service;

import java.util.List;
import org.joda.time.DateTime;

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

public interface ContactService {
	
	ContactDto contactToContactDto(Contact contact);
	ContactDto contactToContactDtoWithDetails(Contact contact);
	HobbyDto hobbyToHobbyDto(Hobby hobby);
	MessageDto messageToMessageDto(Message message);
	PlaceDto placeToPlaceDto(Place place);
	ContactDetailDto detailToDetailDto(ContactDetail detail);
	Contact contactDtoToContact(ContactDto contactDto);
	
	ContactDto createContact(String firstName, String lastName, DateTime birthDate);
	
	HobbyDto addHobby(String title, String description);
	
	List<HobbyDto> unusedHobbies(ContactDto contact);

	List<PlaceDto> unusedPlaces(ContactDto contact);

	PlaceDto addPlace(String title, String description, Double longitude, Double latitude);
	
	ContactDto addFriendship(ContactDto contact, ContactDto contactFriend);

	ContactDto removeFriendship(ContactDto contact, ContactDto contactFriend);

	List<ContactDto> getFriendList(ContactDto contact);
	
	List<MessageDto> getConversation(ContactDto contactSernder, ContactDto contactRecipient);
	
	ContactDto findById(Long id);

	ContactDto deleteDetail(ContactDetailDto detail);

	ContactDto addDetail(ContactDetailDto detail);

	List<ContactDto> findAll();
	
	ContactDto addOrUpdate(ContactDto contact);

	String deleteContact(ContactDto contact);
}
