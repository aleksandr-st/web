package com.homework.webProject.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.homework.webProject.dao.MessageDao;
import com.homework.webProject.model.Contact;
import com.homework.webProject.model.Message;

@Repository("messageDao")
@Transactional
public class MessageDaoImpl implements MessageDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory(){
		return this.sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	public Message addMessage(Message message) {
		sessionFactory.getCurrentSession().saveOrUpdate(message);
		return message;
	}

	public List<Message> getConvercation(Contact contact1, Contact contact2) {
		return sessionFactory.getCurrentSession().getNamedQuery("Message.getConversation").setParameter("id1", contact1.getId()).setParameter("id2", contact2.getId()).list();
	}
	public List<Message> getNewIncome(Contact contact1, Contact contact2, Long lastId) {
		return sessionFactory.getCurrentSession().getNamedQuery("Message.getNewIncome").setParameter("id1", contact1.getId()).setParameter("id2", contact2.getId()).setParameter("lastId", lastId).list();
	}

}
