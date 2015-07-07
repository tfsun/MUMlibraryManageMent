package model;

import java.io.Serializable;

/**
 * Created by rajkumar on 6/29/2015.
 */
public class Person implements Serializable{
    public Person(){}
    public Person(String firstName,String lastName,String phone, Address address){
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setAddress(address);
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    private String firstName;
    private String lastName;
    private String phone;
    private Address address;
    
    public String toString(){
    	return "firstName: " + firstName + 
    			", lastName: " + lastName +
    			", phone: " + phone +
    			", address: " + address.getStreet();
    }
}
