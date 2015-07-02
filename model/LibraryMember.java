package model;

import java.io.Serializable;

/**
 * Created by rajkumar on 6/29/2015.
 */
public class LibraryMember extends Person implements Serializable{
    /**
	 * 
	 */

    public LibraryMember(){}
    public LibraryMember(String memberId,String firstName,String lastName,String phone,Address address){
        super(firstName,lastName, phone,address);
        setMemberId(memberId);

    }
	private static final long serialVersionUID = -2049995963462925156L;
	public String getMemberId() {
        return memberId;
    }


    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    private Address address;
    private String memberId;
}
