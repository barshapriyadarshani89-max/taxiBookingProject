package com.taxi.taxibooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="contactform")
public class ContactForm {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message="Name can not be empty")
	@Size(min=2,max=30,message="Invalid name size")
@Column(length=30)
private String name;
	@NotEmpty(message="Email cannot be empty")
	@Size(min=5,max=50,message="invalid Email Size ")
	@Column(length=50)
private String email;
	@NotNull(message="phone number cannot be empty")
	@Min(value=1000000000,message="phone no must be atleast 10 digits")
	@Max(value=9999999999L,message="phone no must be atleast 10 digits")
	@Column(length=1023)
private Long phone;
@NotEmpty(message="message cannot be empty")
@Size(min=5,max=50,message="invalid message Size ")
@Column(length=500)
private String message;
}
