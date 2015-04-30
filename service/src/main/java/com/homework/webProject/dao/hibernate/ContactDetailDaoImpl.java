package com.homework.webProject.dao.hibernate;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.homework.webProject.dao.ContactDetailDao;
import com.homework.webProject.model.ContactDetail;

@Repository("contactDetailDao")
@Transactional
public class ContactDetailDaoImpl implements ContactDetailDao {
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory(){
		return this.sessionFactory;
	}
	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	public ContactDetail findById(Long id) {
		return (ContactDetail)sessionFactory.getCurrentSession().getNamedQuery("ContactDetail.findById").setParameter("id", id).uniqueResult();
	}

}
