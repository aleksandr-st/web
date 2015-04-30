package com.homework.webProject.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="place")
@NamedQueries({
	@NamedQuery(name="Place.findById",
		query="select p from Place p where p.title = :id"),
	@NamedQuery(name="Place.findAllWithPlace",
		query="select distinct c from Contact c join fetch c.places p where p.title = :placeId"),
})
public class Place {
	private String title;
	private String description;
	private Double longitude;
	private Double latitude;
	private Set<Contact> contacts;

	public Place(){
	}
	public Place(String title, String description, Double longitude, Double latitude){
		this.title = title;
		this.description = description;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	@Id
	@Column(name="PLACE_ID")
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="PLACE_DESCR")
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="PLACE_LONGITUDE")
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	@Column(name="PLACE_LATITUDE")
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	@ManyToMany
	@JoinTable(name="contact_place",
		joinColumns=@JoinColumn(name="PLACE_ID"),
		inverseJoinColumns=@JoinColumn(name="CONTACT_ID")
		)
	public Set<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (title == null) {
			if (other.getTitle() != null)
				return false;
		} else if (!title.equals(other.getTitle()))
			return false;
		return true;
	}
	@Override
	public String toString(){
		return "Place Id: " + title + ", Description: " + description;
	}
}
