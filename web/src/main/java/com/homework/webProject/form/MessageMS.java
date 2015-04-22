package com.homework.webProject.form;

public class MessageMS {
	private String type;
	
	private String message;
	
	public MessageMS() {
	}
	public MessageMS(String type, String message){
		this.setType(type);
		this.setMessage(message);
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
