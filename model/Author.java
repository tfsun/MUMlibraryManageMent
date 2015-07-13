package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Author extends Person implements Serializable{
//	private String firstName;
//	private String lastName;
	private String credentials;
//	private Address address;
	private String phone;
	private int ID;
	static private int curID;
	
	static{
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");  
        curID = Integer.valueOf(sdf.format(d));  
	}

//	public Author(String firstName, String lastName, String credentials,
//			String phone) {
//		super(firstName, lastName, phone, address)
//		(String firstName,String lastName,String phone, Address address)
//                super();
////                this.firstName = firstName;
////		this.lastName = lastName;
////		this.credentials = credentials;
//		this.phone = phone;
//		this.ID = curID++;
//	}
	
	public long getID() {
		return ID;
	}

	public Author(String firstName, String lastName, String phone, 
			Address address, String credentials) {
		super(firstName, lastName, phone, address);
		this.credentials = credentials;
		this.ID = curID++;
	}

	public String getFirstName() {
		return super.getFirstName();
	}
	public void setFirstName(String firstName) {
		super.setFirstName(firstName);;
	}
	public String getLastName() {
		return super.getLastName();
	}
	public void setLastName(String lastName) {
		super.setLastName(lastName);;
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
		return super.toString();
	}

}
