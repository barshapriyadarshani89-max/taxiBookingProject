package com.taxi.taxibooking.service;

import java.util.List;

import com.taxi.taxibooking.model.ContactForm;

public interface contactFormService {
public ContactForm saveContactFormService(ContactForm contactForm);
	public  List <ContactForm>readAllContactsService();
	public  void deleteContactService(int id);

}
