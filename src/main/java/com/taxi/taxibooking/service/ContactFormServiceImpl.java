package com.taxi.taxibooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxi.taxibooking.dao.ContactFormCrud;
import com.taxi.taxibooking.model.ContactForm;
@Service
public class ContactFormServiceImpl implements contactFormService  {
	private ContactFormCrud contactFormCrud;
	
   @Autowired
	public void setContactFormCrud(ContactFormCrud contactFormCrud) {
		this.contactFormCrud = contactFormCrud;
	}


	@Override
	public ContactForm saveContactFormService(ContactForm contactForm) {
		// TODO Auto-generated method stub
		
	
	return contactFormCrud.save(contactForm);
	}


	@Override
	public List<ContactForm> readAllContactsService() {
		
		return contactFormCrud.findAll();
	}


	@Override
	public void deleteContactService(int id) {
		// TODO Auto-generated method stub
		contactFormCrud.deleteById(id);
	}


	
}
