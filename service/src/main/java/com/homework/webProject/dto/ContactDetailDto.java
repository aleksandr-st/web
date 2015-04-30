package com.homework.webProject.dto;

public class ContactDetailDto {
	private Long id;
	private String type;
	private String data;
	private ContactDto contact;
	
	public ContactDetailDto(){
	}
	public ContactDetailDto(Long id){
		this.id = id;
	}
	public ContactDetailDto(String type, String data){
		this.type = type;
		this.data = data;
	}
	public ContactDetailDto(String type, String data, ContactDto contact){
		this.type = type;
		this.data = data;
		this.contact = contact;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public ContactDto getContact() {
		return contact;
	}
	public void setContact(ContactDto contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return " ContactDetail [type=" + type + ", data=" + data + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactDetailDto other = (ContactDetailDto) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	};
	
}
