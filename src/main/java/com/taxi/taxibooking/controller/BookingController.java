package com.taxi.taxibooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taxi.taxibooking.model.BookingForm;
import com.taxi.taxibooking.model.ContactForm;
import com.taxi.taxibooking.model.ServiceForm;
import com.taxi.taxibooking.service.BookingFormService;
import com.taxi.taxibooking.service.ContactFormServiceImpl;
import com.taxi.taxibooking.service.ServiceFormService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class BookingController {
	
	private ContactFormServiceImpl contactFormService;
	private BookingFormService bookingFormService;
	private ServiceFormService serviceFormService;
	@Autowired
	public void setServiceFormService(ServiceFormService serviceFormService) {
		this.serviceFormService = serviceFormService;
	}
	@Autowired
	public void setBookingFormService(BookingFormService bookingFormService) {
		this.bookingFormService = bookingFormService;
	}


	@Autowired
	public void setContactFormService(ContactFormServiceImpl contactFormService) {
		this.contactFormService = contactFormService;
	}
	
	
	@GetMapping(path= {"/","home","welcome","index"})
public String welcomeView(HttpServletRequest req,Model m) {
		String requestURI=req.getRequestURI();
		m.addAttribute("mycurrentpage",requestURI);
		m.addAttribute("bookingForm",new BookingForm());
	return "index";
	
}
	@GetMapping("about")
	public String aboutView(HttpServletRequest req,Model m) {
		String requestURI=req.getRequestURI();
		m.addAttribute("mycurrentpage",requestURI);
		return "about";
		
	}

	@GetMapping("cars")
	public String carsView(HttpServletRequest req,Model m) {
		String requestURI=req.getRequestURI();
		m.addAttribute("mycurrentpage",requestURI);
		return "cars";
		
	}

	@GetMapping("services")
	public String serviceView(HttpServletRequest req,Model m) {
		String requestURI=req.getRequestURI();
		m.addAttribute("mycurrentpage",requestURI);
		
		//data collection
		List<ServiceForm> allServices=serviceFormService.readAllServices();
		m.addAttribute("allservices",allServices);
		return "services";
		
	}

	@GetMapping("contacts")
	public String contactView(HttpServletRequest req,Model m) {
		String requestURI=req.getRequestURI();
		m.addAttribute("mycurrentpage",requestURI);
		m.addAttribute("contactForm", new ContactForm());
		return "contacts";
		
	}
	@GetMapping("/login")
	public String adminLoginView(HttpServletRequest request,Model model) {
		ServletContext servletContext=request.getServletContext();
		Object attribute=servletContext.getAttribute("logout");
		if(attribute instanceof Boolean) {
			model.addAttribute("logout",attribute);
			servletContext.removeAttribute("logout");
		}
		
		return"adminlogin";
	}
	@PostMapping("contactform")
	public String contactForm( @Valid @ModelAttribute ContactForm contactForm,BindingResult bindingResult,  Model m ,RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			m.addAttribute("bindingResult" , bindingResult);
			return "contacts";
		}
		ContactForm saveContactFormService=contactFormService.saveContactFormService(contactForm);
		if(saveContactFormService!=null) {
			redirectAttributes.addFlashAttribute("message","message sent successfully");
		}
		else {
			redirectAttributes.addFlashAttribute("message","something went wrong");
		}
		return "redirect:/contacts";
        }
	
	@PostMapping("bookingform")
	public String bookingForm(@Valid @ModelAttribute BookingForm bookingForm,
	                          BindingResult bindingResult, Model m,
	                          RedirectAttributes redirectAttributes) {

	    if (bindingResult.hasErrors()) {
	        m.addAttribute("bindingResult", bindingResult);
	        return "index";
	    } else if (bookingForm.getAdult() + bookingForm.getChildren() > 4) {
	        m.addAttribute("message", "The total no of adult and children cannot exceed 4");
	        return "index";
	    }

	   
	    // service
	    BookingForm saveBookingFormService=bookingFormService.saveBookingFormService(bookingForm);
	    if(saveBookingFormService!=null) {
			redirectAttributes.addFlashAttribute("message","booked successfully");
		}
		else {
			redirectAttributes.addFlashAttribute("message","something went wrong");
		}
	    return "redirect:/index";
	}


	
	
	
	
}
