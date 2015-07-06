package model;

import java.io.Serializable;

public class Author implements Serializable{
	private String firstName;
	private String lastName;
	private String credentials;
	int phone;
	
	public Author(String firstName, String lastName, String credentials,
			int phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.credentials = credentials;
		this.phone = phone;
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
