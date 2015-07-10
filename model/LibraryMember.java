package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by rajkumar on 6/29/2015.
 */
public class LibraryMember extends Person implements Serializable{
    /**
	 * 
	 */
	private CheckOutRecord record = new CheckOutRecord();

    public LibraryMember(){}
    public LibraryMember(int memberId,String firstName,String lastName,String phone,Address address){
        super(firstName,lastName, phone,address);
        setMemberId(memberId);

    }
	public void checkout(LendableCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
		CheckoutRecordEntry entry = new CheckoutRecordEntry(copy, checkoutDate, dueDate);
		record.addEntry(entry);
	}
	
	public void addCheckoutEntry(CheckoutRecordEntry checkoutRecordEntry) {
        System.out.println(checkoutRecordEntry.toString());
        record.addEntry(checkoutRecordEntry);
	}

    public CheckOutRecord getRecord(){
        return record;
    }
	private static final long serialVersionUID = -2049995963462925156L;
	public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    private Address address;
    private int memberId;
    
    public String toString(){
    	//return super.toString() + ", memberId:" + memberId + ", address:" + address.toString();
    	return "memberId:" + memberId + ", address:" + address; //.toString();
    }
}
