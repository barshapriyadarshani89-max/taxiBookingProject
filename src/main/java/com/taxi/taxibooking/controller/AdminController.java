package com.taxi.taxibooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taxi.taxibooking.model.BookingForm;
import com.taxi.taxibooking.model.ContactForm;
import com.taxi.taxibooking.model.ServiceForm;
import com.taxi.taxibooking.service.AdminCredentialsService;
import com.taxi.taxibooking.service.BookingFormService;
import com.taxi.taxibooking.service.ContactFormServiceImpl;
import com.taxi.taxibooking.service.ServiceFormService;

@Controller
@RequestMapping("admin")
public class AdminController {
	private ContactFormServiceImpl contactFormService;
	private AdminCredentialsService adminCredentiasService;
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
	public void setAdminCredentiasService(AdminCredentialsService adminCredentiasService) {
		this.adminCredentiasService = adminCredentiasService;
	}

	@Autowired
	public void setContactFormService(ContactFormServiceImpl contactFormService) {
		this.contactFormService = contactFormService;
	}
	
    @GetMapping("dashboard")
	public String adminDashboard() {
    	
    	
		return "admin/dashboard";
	}
    @GetMapping("readAllContacts")
    public  String readAllContacts(Model model) {
    	model.addAttribute("allcontacts",contactFormService.readAllContactsService());
    	
    	return "admin/readallcontacts";
    	
    }
    @GetMapping("deleteContact/{id}")
    public  String deleteContact(@PathVariable int id ,RedirectAttributes redirectAttributes ) {
    	
    	contactFormService.deleteContactService(id);
    	redirectAttributes.addFlashAttribute("message","CONTACT DELETED SUCCESSFULLY");
		return "redirect:/admin/readAllContacts";
    	
    	
    }
    @GetMapping("changeCredentials")
    public  String changeCredentialsView( ) {
    	
    	
		return "admin/changecredentials";
    	
    	
    }
    @PostMapping("changeCredentials")
    public  String changeCredentials(
    		@RequestParam("oldusername")String oldusername,
    @RequestParam("oldpassword")String oldpassword,
    @RequestParam("newusername")String newusername,
    @RequestParam("newpassword")String newpassword,
    RedirectAttributes redirectAttributes
    		){
    	
    	String result=adminCredentiasService.checkAdminCredentials(oldusername, oldpassword);
    	if(result.equals("SUCCESS")) {
    		result=adminCredentiasService.updateAdminCredentials(newusername,newpassword,oldusername);
    		
    			redirectAttributes.addFlashAttribute("message",result);
    		
    	}else {
    		redirectAttributes.addFlashAttribute("message",result);
    	}
    	
    	
		return "redirect:/admin/dashboard";
    	
    	
    }
    @GetMapping("readAllBookings")
    public  String readAllBookings(Model model) {
    	
    	List<BookingForm> allBookingsService=bookingFormService.readAllBookingsService();
    	System.out.println(allBookingsService);
    	model.addAttribute("allBookings",bookingFormService.readAllBookingsService());
    	
    	return "admin/readallbookings";
    	
    }
    @GetMapping("deleteBooking/{id}")
    public  String deleteBooking(@PathVariable int id ,RedirectAttributes redirectAttributes ) {
    	
    	bookingFormService.deleteBookingService(id);
    	redirectAttributes.addFlashAttribute("message","booking DELETED SUCCESSFULLY");
		return "redirect:/admin/readAllBookings";
    	
    	
    }
    @GetMapping("addService")
    public  String addServiceView() {
    	
    	
		return "admin/addservice";
    	
    	
    }
    @InitBinder
    public void stopBinding(WebDataBinder webDataBinder) {
    	webDataBinder.setDisallowedFields("image")	;
    }
    @PostMapping("addService")
    public  String addService(@ModelAttribute ServiceForm serviceForm,@RequestParam("image")  MultipartFile multipartFile,RedirectAttributes redirectAttributes ) {
    	
    	String originalFileName=multipartFile .getOriginalFilename();
    	serviceForm.setImage(originalFileName);
    	try {
    		ServiceForm service=serviceFormService.addService(serviceForm, multipartFile);
    		if(service!=null) {
    			redirectAttributes.addFlashAttribute("msg","Service added successfully");
    		}else {
    			redirectAttributes.addFlashAttribute("msg","something went wrong");
    		}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg","something went wrong");
		}
    	
		return "redirect:/admin/addService";
    	
    	
    }

	
    
    
}
