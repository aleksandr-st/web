package com.homework.webProject.dto;

import org.joda.time.DateTime;

public class MessageDto {
	private Long id;
	private DateTime date;
	private ContactDto from;
	private ContactDto to;
	private String content;
	
	public MessageDto(){
	}
	public MessageDto(DateTime date, ContactDto from, ContactDto to, String content){
		this.date = date;
		this.from = from;
		this.to = to;
		this.content = content;	
	}
	
	public void setDate(DateTime date){
		this.date = date;
	}
	public DateTime getDate(){
		return date;
	}
	public void setFrom(ContactDto from){
		this.from = from;
	}
	public ContactDto getFrom(){
		return from;
	}
	public void setTo(ContactDto to){
		this.to = to;
	}
	public ContactDto getTo(){
		return to;
	}
	public void setContent(String content){
		this.content = content;
	}
	public String getContent(){
		return content;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "MessageDto [id=" + id + ", date=" + date + ", from=" + from.getFullName()
				+ ", to=" + to.getFullName() + ", content='" + content + "']";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageDto other = (MessageDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
