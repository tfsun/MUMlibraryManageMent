package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class LibraryMember extends Person implements Serializable{
    private Address address;
    private String memberId;
	private CheckoutRecord record = new CheckoutRecord();

    public LibraryMember(){}

    public LibraryMember(String memberId,String firstName,String lastName,String phone,Address address){
        super(firstName,lastName, phone,address);
        setMemberId(memberId);
    }
	public void checkout(LendableCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
		CheckoutRecordEntry entry = new CheckoutRecordEntry(copy, checkoutDate, dueDate);
		record.addEntry(entry);
	}
	
	public void addCheckoutEntry(CheckoutRecordEntry checkoutRecordEntry) {
		record.addEntry(checkoutRecordEntry);
	}
	
	public boolean removeCheckoutEntry(LendableCopy copy) {
		return record.removeEntry(copy);
	}
	
	public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public CheckoutRecord getRecord(){
    	return this.record;
    }

    public String toString(){
    	//return super.toString() + ", memberId:" + memberId + ", address:" + address.toString();
    	return "memberId:" + memberId + ", address:" + address; //.toString();
    }
    
    public String printDetail(){
    	return "MemberID: " + memberId + 
    			"\nName: " + super.getFirstName() + " " + super.getLastName() + 
    			"\nPhone: " + super.getPhone() + 
    			"\nAddress: " + super.getAddress().toString();
    }
}
