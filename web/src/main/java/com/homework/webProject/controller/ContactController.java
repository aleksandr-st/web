package com.homework.webProject.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.homework.webProject.dto.ContactDetailDto;
import com.homework.webProject.dto.ContactDto;
import com.homework.webProject.form.MessageMS;
import com.homework.webProject.model.Contact;
import com.homework.webProject.service.ContactService;
import com.homework.webProject.util.UrlUtil;

@RequestMapping("/contacts")
@Controller
public class ContactController {

	final Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	MessageSource messageSource;
	
	@Resource(name="contactService")
	private ContactService contactService;

	@RequestMapping(method = RequestMethod.GET)
	public String showList(Model uiModel) {
		System.out.println("in controller");
		
		List<ContactDto> contacts = contactService.findAll();
		for(ContactDto contact: contacts){
			System.out.println(contact);
		}
		
		logger.info("No. of contacts " + contacts.size());
		
		uiModel.addAttribute("contacts", contacts);		
		return "contacts/list";
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model uiModel){
		ContactDto contact = contactService.findById(id);
		uiModel.addAttribute("contact", contact);
		return "contacts/show";
	}
	
	@RequestMapping(value="/{id}", params = "form", method = RequestMethod.POST)
	public String update(ContactDto contact, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale){
		logger.info("Updating contact");
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new MessageMS("error", messageSource.getMessage("contact_save_fail", new Object[]{}, locale)));
			uiModel.addAttribute("contact", contact);
			return "contacts/update";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute("message", new MessageMS("success", messageSource.getMessage("contact_save_success", 
				new Object[] {}, locale)));
		ContactDto contactForUpdate;
		
		contactForUpdate = contactService.findById(contact.getId());
		contactForUpdate.setFirstName(contact.getFirstName());
		contactForUpdate.setLastName(contact.getLastName());
		contactForUpdate.setBirthDate(contact.getBirthDate());
		contactForUpdate.setVersion(contact.getVersion());
		
		contactService.addOrUpdate(contactForUpdate);
		return "redirect:/contacts/"
				+ UrlUtil.encodeUrlPathSegment(contact.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value="/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		ContactDto contact = contactService.findById(id);
		uiModel.addAttribute("contact", contact);
		uiModel.addAttribute("unusedHobbies", contactService.unusedHobbies(contact));
		uiModel.addAttribute("unusedPlaces", contactService.unusedPlaces(contact));
		return "contacts/update";		
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String create(ContactDto contact, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale){
		logger.info("Creating contact");
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new MessageMS("error", messageSource.getMessage("contact_save_fail", new Object[]{}, locale)));
			uiModel.addAttribute("contact", contact);
			return "contacts/update";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute("message", new MessageMS("success", messageSource.getMessage("contact_save_success", 
				new Object[] {}, locale)));
		
		logger.info("Contact Id: " + contact.getId());
		contactService.addOrUpdate(contact);
		return "redirect:/contacts/"
				+ UrlUtil.encodeUrlPathSegment(contact.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		ContactDto contact = new ContactDto();
		uiModel.addAttribute("contact", contact);
		uiModel.addAttribute("unusedHobbies", contactService.unusedHobbies(contact));
		uiModel.addAttribute("unusedPlaces", contactService.unusedPlaces(contact));
		return "contacts/create";		
	}

	@RequestMapping(params = "jsonCreate", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ContactDto createJson(@RequestBody ContactDto contact){
		return contactService.addOrUpdate(contact);
	}

	@RequestMapping(value="/{id}", params = "jsonUpdate", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ContactDto updateJson(@RequestBody ContactDto contact, @PathVariable("id") Long id, 
			RedirectAttributes redirectAttributes){
		ContactDto contactForUpdate = contactService.findById(contact.getId());
		if ((contactForUpdate != null) && (contactForUpdate.getVersion()==contact.getVersion())){
			contactForUpdate.setFirstName(contact.getFirstName());
			contactForUpdate.setLastName(contact.getLastName());
			contactForUpdate.setBirthDate(contact.getBirthDate());
			return contactService.addOrUpdate(contactForUpdate);
		} else {
			return contactForUpdate;
		}
	}
	
	@RequestMapping(value="/{id}", params = "jsonHobbyUpdate", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ContactDto updateHobbiesJson(@RequestBody ContactDto contact, @PathVariable("id") Long id, 
			RedirectAttributes redirectAttributes){
		ContactDto contactForUpdate = contactService.findById(contact.getId());
		if ((contactForUpdate != null) && (contactForUpdate.getVersion()==contact.getVersion())){
			contactForUpdate.setHobbies(contact.getHobbies());
			return contactService.addOrUpdate(contactForUpdate);
		} else {
			return contactForUpdate;
		}
	}
	
	@RequestMapping(value="/{id}", params = "jsonPlaceUpdate", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ContactDto updatePlacesJson(@RequestBody ContactDto contact, @PathVariable("id") Long id, 
			RedirectAttributes redirectAttributes){
		ContactDto contactForUpdate = contactService.findById(contact.getId());
		if ((contactForUpdate != null) && (contactForUpdate.getVersion()==contact.getVersion())){
			contactForUpdate.setPlaces(contact.getPlaces());
			return contactService.addOrUpdate(contactForUpdate);
		} else {
			return contactForUpdate;
		}
	}
	
	@RequestMapping(value="/{id}", params = "deleteDetail", method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ContactDto deleteDetail(@RequestBody ContactDto contact, @PathVariable("id") Long id,
			RedirectAttributes redirectAttributes, @RequestParam(value="deleteDetail", required=true) Long detailId){
		ContactDto contactForUpdate = contactService.findById(contact.getId());
		if ((contactForUpdate != null) && (contactForUpdate.getVersion()==contact.getVersion())){
			ContactDetailDto detail = new ContactDetailDto(detailId);
			return contactService.deleteDetail(detail);
		} else {
			return contactForUpdate;
		}
	}

	@RequestMapping(value="/{id}", params = "AddDetail", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ContactDto addDetail(@RequestBody ContactDto contact, 
			RedirectAttributes redirectAttributes){
		ContactDto contactForUpdate = contactService.findById(contact.getId());

		Set<ContactDetailDto> details = contact.getContactDetails();
		ContactDetailDto detail = new ContactDetailDto();
		Iterator<ContactDetailDto> it = details.iterator();
		if (it.hasNext()){
			detail = it.next();
		}
		detail.setContact(contact);

		if ((contactForUpdate != null) && (contactForUpdate.getVersion()==contact.getVersion())){
			redirectAttributes.addAttribute("detailId", "1");
			return contactService.addDetail(detail);
		} else {
			return contactForUpdate;
		}
	}

	@RequestMapping(value="/delete/{id}", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,String> deleteContact(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
		ContactDto contactForDelete = contactService.findById(id);
		String result = contactService.deleteContact(contactForDelete);
		Map<String,String> answer = new HashMap<String, String>();
		answer.put("answer", result);
		return answer;
	}

	@ExceptionHandler
	public void exceptionHandler(Exception ex){
		ex.printStackTrace();
	}
	
}
