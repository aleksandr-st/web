package com.homework.webProject.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.homework.webProject.dto.ContactDto;
import com.homework.webProject.model.Contact;
import com.homework.webProject.service.ContactService;

public class TestContactController {
	
	private final List<Contact> contacts = new ArrayList<Contact>();
	private final List<ContactDto> contactsDto = new ArrayList<ContactDto>();
	
	private ContactService contactService;
	
	private MessageSource messageSource;

	@Before
	public void setUp() throws Exception {
		contacts.clear();
		contactsDto.clear();
		Contact contact = new Contact();
		contact.setId(1l);
		contact.setFirstName("Kim");
		contact.setLastName("Chen");
		contact.setBirthDate(new DateTime(2000, 1, 2, 0, 0));
		contacts.add(contact);
		ContactDto contactDto = new ContactDto();
		contactDto.setId(1l);
		contactDto.setFirstName("Kim");
		contactDto.setLastName("Chen");
		contactDto.setBirthDate(new DateTime(2000, 1, 2, 0, 0));
		contactsDto.add(contactDto);
		contactService = mock(ContactService.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testShowList() {
		when(contactService.findAll()).thenReturn(contactsDto);
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = contactController.showList(uiModel);
		assertNotNull(result);
		assertEquals("contacts/list", result);
		@SuppressWarnings("unchecked")
		List<Contact> modelContacts = (List<Contact>) uiModel.get("contacts");
		assertEquals(1, modelContacts.size());
	}

	@Test
	public void testShow() {
		when(contactService. findById(1l)).thenReturn(contactsDto.get(0));
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = contactController.show(1l, uiModel);
		assertNotNull(result);
		assertEquals("contacts/show", result);
		ContactDto modelContact = (ContactDto) uiModel.get("contact");
		assertEquals((Long) 1l, modelContact.getId());
	}

	@Test
	public void testUpdate() {
		final ContactDto contact = contactsDto.get(0); 
		when(contactService.findById(1l)).thenReturn(contact);
		when(contactService.addOrUpdate(contact)).thenAnswer(new Answer<ContactDto>(){
			public ContactDto answer(InvocationOnMock invocation) throws Throwable{
				contact.setLastName("Chen");
				contact.setVersion(2);
				return contact;
			}
		});
		messageSource = mock(MessageSource.class);
		when(messageSource.getMessage("save_contact_success", new Object[] {}, Locale.ENGLISH)).thenReturn("success");
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		ReflectionTestUtils.setField(contactController, "messageSource", messageSource);
		ExtendedModelMap uiModel = new ExtendedModelMap();
		BindingResult bindingResult = new BeanPropertyBindingResult(contact, "contact");
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		Locale locale = Locale.ENGLISH;
		
		String result = contactController.update(contact, bindingResult, uiModel, httpServletRequest, 
				redirectAttributes, locale);
		assertNotNull(result);
		assertEquals("redirect:/contacts/1", result);
		assertEquals(1, contactsDto.size());
		assertEquals(2, contactsDto.get(0).getVersion());
		assertEquals("Chen", contacts.get(0).getLastName());
	}

	@Test
	public void testUpdateHasError() {
		final ContactDto contact = contactsDto.get(0); 
		messageSource = mock(MessageSource.class);
		when(messageSource.getMessage("save_contact_fail", new Object[] {}, Locale.ENGLISH)).thenReturn("fail");
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		ReflectionTestUtils.setField(contactController, "messageSource", messageSource);
		ExtendedModelMap uiModel = new ExtendedModelMap();
		BindingResult bindingResult = new BeanPropertyBindingResult(contact, "contact");
		bindingResult. addError(new FieldError("contact","lastName","error"));
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		Locale locale = Locale.ENGLISH;
		
		String result = contactController.update(contact, bindingResult, uiModel, httpServletRequest, 
				redirectAttributes, locale);
		assertNotNull(result);
		assertEquals("contacts/update", result);
		assertEquals(1, contacts.size());
	}

	@Test
	public void testUpdateForm() {
		when(contactService. findById(1l)).thenReturn(contactsDto.get(0));
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = contactController.updateForm(1l, uiModel);
		assertNotNull(result);
		assertEquals("contacts/update", result);
		ContactDto modelContact = (ContactDto) uiModel.get("contact");
		assertEquals((Long) 1l, modelContact.getId());
	}

	@Test
	public void testCreate() {
		final ContactDto newContact = new ContactDto();
		newContact.setId(90l);
		newContact.setFirstName("Jet");
		newContact.setLastName("Lee");
		newContact.setBirthDate(new DateTime(1990, 10, 12, 0, 0));
		when(contactService.addOrUpdate(newContact)).thenAnswer(new Answer<ContactDto>(){
			public ContactDto answer(InvocationOnMock invocation) throws Throwable{
				contactsDto.add(newContact);
				return newContact;
			}
		});
		messageSource = mock(MessageSource.class);
		when(messageSource.getMessage("save_contact_success", new Object[] {}, Locale.ENGLISH)).thenReturn("success");
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		ReflectionTestUtils.setField(contactController, "messageSource", messageSource);
		ExtendedModelMap uiModel = new ExtendedModelMap();
		BindingResult bindingResult = new BeanPropertyBindingResult(newContact, "contact");
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		Locale locale = Locale.ENGLISH;
		
		String result = contactController.create(newContact, bindingResult, uiModel, httpServletRequest, 
				redirectAttributes, locale);
		assertNotNull(result);
		assertEquals("redirect:/contacts/90", result);
		assertEquals(2, contactsDto.size());
	}

	@Test
	public void testCreateHasErrors() {
		final ContactDto newContact = new ContactDto();
		messageSource = mock(MessageSource.class);
		when(messageSource.getMessage("save_contact_fail", new Object[] {}, Locale.ENGLISH)).thenReturn("fail");
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		ReflectionTestUtils.setField(contactController, "messageSource", messageSource);
		ExtendedModelMap uiModel = new ExtendedModelMap();
		BindingResult bindingResult = new BeanPropertyBindingResult(newContact, "contact");
		bindingResult. addError(new FieldError("contact","lastName","error"));
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		Locale locale = Locale.ENGLISH;
		
		String result = contactController.create(newContact, bindingResult, uiModel, httpServletRequest, 
				redirectAttributes, locale);
		assertNotNull(result);
		assertEquals("contacts/update", result);
		assertEquals(1, contactsDto.size());
	}

	@Test
	public void testCreateForm() {
		contactService = mock(ContactService.class);
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = contactController.createForm(uiModel);
		assertNotNull(result);
		assertEquals("contacts/create", result);
		ContactDto modelContact = (ContactDto) uiModel.get("contact");
		assertNotNull(modelContact);
		assertNull(modelContact.getId());
	}

	@Test
	public void testCreateJson() {
		final ContactDto contactDto = new ContactDto("Bruce","Lee",new DateTime(1930, 10, 12, 0, 0));
		when(contactService.addOrUpdate(contactDto)).thenAnswer(new Answer<ContactDto>(){
			public ContactDto answer(InvocationOnMock invocation) throws Throwable{
				contactDto.setId(new Long(5l));
				contactsDto.add(contactDto);
				return contactDto;
			}
		});
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		ContactDto result = contactController.createJson(contactDto);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals((Long)5l, result.getId());
	}

	@Test
	public void testDeleteContact() {
		final ContactDto contactForDelete = contactsDto.get(0);
		when(contactService. findById(1l)).thenReturn(contactForDelete);
		final String answer = "Contact was deleted!";
		when(contactService.deleteContact(contactForDelete)).thenAnswer(new Answer<String>(){
			public String answer(InvocationOnMock invocation) throws Throwable{
				contactsDto.remove(contactForDelete);
				return answer;
			}
		});
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		RedirectAttributes redirectedAttributes = new RedirectAttributesModelMap();
		Map<String,String> result = contactController.deleteContact(contactForDelete.getId(),redirectedAttributes);
		assertNotNull(result);
		String resultAnswer = result.get("answer");  
		assertNotNull(resultAnswer);
		assertEquals(answer, resultAnswer);
		assertEquals(0, contactsDto.size());
	}

	@Test
	public void testFindFriends() {
		final ContactDto contactForSearch = contactsDto.get(0);
		final List<ContactDto> contactList = new ArrayList<ContactDto>();
		contactList.add(new ContactDto("Contact1","1",new DateTime()));
		contactList.add(new ContactDto("Contact2","2",new DateTime()));
		final String nameForSearch = "A";
		when(contactService. findById(1l)).thenReturn(contactForSearch);
		when(contactService.findbyName(contactForSearch,nameForSearch+"%")).thenReturn (contactList);
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		RedirectAttributes redirectedAttributes = new RedirectAttributesModelMap();
		List<ContactDto> result = contactController.findFriends(contactForSearch.getId(),redirectedAttributes,nameForSearch);
		assertNotNull(result);
		assertEquals(2, result.size());
	}

	@Test
	public void testFindFriendsNullExpect() {
		final String nameForSearch = "A";
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		RedirectAttributes redirectedAttributes = new RedirectAttributesModelMap();
		List<ContactDto> result = contactController.findFriends(null,redirectedAttributes,nameForSearch);
		assertNull(result);
	}

	@Test
	public void testUpdateJson() {
		final ContactDto contactDto = contactsDto.get(0);
		final ContactDto contactUpdated = new ContactDto(contactDto.getFirstName(),
				contactDto.getLastName(),contactDto.getBirthDate());
		contactUpdated.setId(contactDto.getId());
		contactUpdated.setVersion(contactDto.getVersion());
		contactDto.setFirstName("Mike");
		when(contactService. findById(1l)).thenReturn(contactUpdated);
		when(contactService.addOrUpdate(contactUpdated)).thenReturn(contactUpdated);
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		RedirectAttributes redirectedAttributes = new RedirectAttributesModelMap();
		ContactDto result = contactController.updateJson(contactDto, contactDto.getId(), redirectedAttributes);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals((Long)1l, result.getId());
		assertEquals(contactDto.getFirstName(), result.getFirstName());
	}

	@Test
	public void testUpdateJsonDiffVersions() {
		final ContactDto contactDto = contactsDto.get(0);
		final ContactDto contactUpdated = new ContactDto(contactDto.getFirstName(),
				contactDto.getLastName(),contactDto.getBirthDate());
		contactUpdated.setId(contactDto.getId());
		contactUpdated.setVersion(4);
		contactDto.setFirstName("Mike");
		when(contactService. findById(1l)).thenReturn(contactUpdated);
		when(contactService.addOrUpdate(contactUpdated)).thenReturn(contactUpdated);
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService", contactService);
		RedirectAttributes redirectedAttributes = new RedirectAttributesModelMap();
		ContactDto result = contactController.updateJson(contactDto, contactDto.getId(), redirectedAttributes);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals((Long)1l, result.getId());
		assertNotEquals(contactDto.getFirstName(), result.getFirstName());
	}
}
