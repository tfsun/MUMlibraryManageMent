package model;

import java.io.Serializable;

public class Author implements Serializable{
	private String firstName;
	private String lastName;
	private String credentials;
	private Address address;
	int phone;
	
	public Author(String firstName, String lastName, int phone, 
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
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	
}
