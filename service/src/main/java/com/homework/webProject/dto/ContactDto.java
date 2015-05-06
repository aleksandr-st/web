package com.homework.webProject.dto;

import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.homework.webProject.dto.ContactDetailDto;

public class ContactDto {
	private Long id;
	private int version;
	private String firstName;
	private String lastName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private DateTime birthDate;
	private Set<HobbyDto> hobbies;
	private Set<PlaceDto> places;
	private Set<ContactDto> friends;
	private Set<ContactDetailDto> contactDetails;
	
	public ContactDto(){
	}
	public ContactDto(String firstName, String lastName, DateTime birthDate){
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVersion() {
		return this.version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFullName() {
		return this.firstName+" "+this.lastName;
	}
	public DateTime getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(DateTime birthDate) {
		this.birthDate = birthDate;
	}
	public Set<HobbyDto> getHobbies() {
		return this.hobbies;
	}
	public void setHobbies(Set<HobbyDto> hobbies) {
		this.hobbies = hobbies;
	}
	public Set<PlaceDto> getPlaces() {
		return this.places;
	}
	public void setPlaces(Set<PlaceDto> places) {
		this.places = places;
	}
	public Set<ContactDto> getFriends() {
		return friends;
	}
	public void setFriends(Set<ContactDto> friends) {
		this.friends = friends;
	}
	public Set<ContactDetailDto> getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(Set<ContactDetailDto> contactDetails2) {
		this.contactDetails = contactDetails2;
	}
	@Override
	public String toString(){
		return firstName + "\n" + lastName + "\n" + birthDate;
	}
	@Override
	public boolean equals(Object obj) {
		ContactDto contact1 = (ContactDto)obj;
		if (id.equals(contact1.getId())) {
			return true;
		}
		return false;
	}
}
