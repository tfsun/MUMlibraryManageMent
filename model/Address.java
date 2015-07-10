package model;

import java.io.Serializable;

/**
 * Created by rajkumar on 6/29/2015.
 */
public class Address implements Serializable{
    public Address(){}
    public Address(String street,String city,String state,int zip){
        setStreet(street);
        setCity(city);
        setState(state);
        setZip(zip);
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

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    private String street;
    private String state;
    private String city;
    private int zip;

}
