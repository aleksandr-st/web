package com.homework.webProject.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="message")
@NamedQueries({
	@NamedQuery(name="Message.getConversation",
		query="select m from Message m where ((m.from.id = :id1) and (m.to.id = :id2))"
			+" or ((m.from.id = :id2) and (m.to.id = :id1))"),
	@NamedQuery(name="Message.getNewIncome",
		query="select m from Message m where ((m.from.id = :id2) and (m.to.id = :id1) and (m.id > :lastId))")
})
public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -342425064056695348L;
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="ID")
	private Long id;
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(name="SEND_DATE")
	private DateTime date;
	@ManyToOne
	@JoinColumn(name="FROM_ID")
	private Contact from;
	@ManyToOne
	@JoinColumn(name="TO_ID")
	private Contact to;
	@Column(name="CONTENT")
	private String content;
	
	public Message(){
	}
	public Message(DateTime date, Contact from, Contact to, String content){
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
	public void setFrom(Contact from){
		this.from = from;
	}
	public Contact getFrom(){
		return from;
	}
	public void setTo(Contact to){
		this.to = to;
	}
	public Contact getTo(){
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
		Message other = (Message) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", date=" + date + ", from=" + from.getFullName()
				+ ", to=" + to.getFullName() + ", content='" + content + "']";
	}
	
}
