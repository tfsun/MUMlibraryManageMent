package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rajkumar on 6/29/2015.
 */
public class Address implements Serializable{
    private String street;
    private String state;
    private String city;
    private String zip;  
	private int ID;
	static private int curID;
	
	static{
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");  
        curID = Integer.valueOf(sdf.format(d));  
	}
    public Address(){}

    public Address(String street,String city,String state,String zip){
        setStreet(street);
        setCity(city);
        setState(state);
        setZip(zip);
        this.ID = curID++;
    }
    public long getID() {
		return ID;
	}

	public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


    public String toString(){
    	return "Street: " + street + ", " + 
    			"City: " + state + ", " +
    			"State: " + state + ", " +
    			"Zip: " + zip;
    }
}
