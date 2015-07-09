package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by rajkumar on 6/29/2015.
 */
public class LibraryMember extends Person implements Serializable{
    /**
	 * 
	 */
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
	
	private static final long serialVersionUID = -2049995963462925156L;
	public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public CheckoutRecord getRecord(){
    	return this.record;
    }

    private Address address;
    private String memberId;
    
    public String toString(){
    	//return super.toString() + ", memberId:" + memberId + ", address:" + address.toString();
    	return "memberId:" + memberId + ", address:" + address; //.toString();
    }
}
