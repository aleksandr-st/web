package com.homework.webProject.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="CONTACT_DETAIL")
@NamedQueries({
	@NamedQuery(name="ContactDetail.findById",
		query="select cd from ContactDetail cd where cd.id = :id")
})
public class ContactDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4644646565465401L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="DETAIL_ID")
	private Long id;
	@Column(name="DETAIL_TYPE")
	private String type;
	@Column(name="DETAIL_DATA")
	private String data;
	@ManyToOne
	@JoinColumn(name="CONTACT_ID")
	private Contact contact;
	
	public ContactDetail(){
	}
	public ContactDetail(String type, String data){
		this.type = type;
		this.data = data;
	}
	public ContactDetail(String type, String data, Contact contact){
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
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
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
		ContactDetail other = (ContactDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return " ContactDetail [type=" + type + ", data=" + data + "]";
	};
	
}
