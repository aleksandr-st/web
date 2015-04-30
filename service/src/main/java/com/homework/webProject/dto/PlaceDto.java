package com.homework.webProject.dto;

public class PlaceDto {
	private String title;
	private String description;
	private Double longitude;
	private Double latitude;

	public PlaceDto(){
	}
	public PlaceDto(String title){
		this.title = title;
	}
	public PlaceDto(String title, String description, Double longitude, Double latitude){
		this.title = title;
		this.description = description;
		this.longitude = longitude;
		this.latitude = latitude;
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
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	@Override
	public String toString(){
		return "Place Id: " + title + ", Description: " + description;
	}
}
