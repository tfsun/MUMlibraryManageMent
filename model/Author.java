package model;

import java.io.Serializable;

public class Author extends Person implements Serializable{
	private String firstName;
	private String lastName;
	private String credentials;
	private Address address;
	private String phone;

	public Author(String firstName, String lastName, String credentials,
			String phone) {
                super();
                this.firstName = firstName;
		this.lastName = lastName;
		this.credentials = credentials;
		this.phone = phone;
	}
	
	public Author(String firstName, String lastName, String phone, 
			Address address, String credentials) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.credentials = credentials;
		this.phone = phone;
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString()
	{
		return "author name: " + firstName + " " + lastName + ", author phone: " + phone + ";" ;
	}

}
