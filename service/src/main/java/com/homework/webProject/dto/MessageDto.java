package com.homework.webProject.dto;

import org.joda.time.DateTime;

public class MessageDto {
	private DateTime date = null;
	private ContactDto from = null;
	private ContactDto to = null;
	private String content = null;
	
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
}
