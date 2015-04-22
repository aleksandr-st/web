package com.homework.webProject.dto;

public class HobbyDto {
	private String title;
	private String description;
	
	public HobbyDto(){		
	}
	public HobbyDto(String title, String description){
		this.title = title;
		this.description = description;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HobbyDto other = (HobbyDto) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString(){
		return "Hobby Id: " + title + ", Description: " + description;
	}
}
