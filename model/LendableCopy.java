package model;

import java.io.Serializable;
import java.time.LocalDate;

import Services.CopyService;
import Services.UserService;

public class LendableCopy implements Serializable {
	private Publication publication;
	private String copyNo;
	private int memberID = 0; // Use Case 7, add member into copy
	private boolean checkOut = false; // Use Case 7, overdue if after due date and checkIn is false

	public boolean isCheckOut() {
		return checkOut;
	}

	public void setCheckOut(boolean checkOut) {
		this.checkOut = checkOut;
	}

	public LendableCopy(Publication publication) {
		setPublication(publication);
		this.copyNo = CopyService.getCopyIDbyPublication(publication);
		//this.copyId = copyId;
	}
	
//	public LendableCopy() {
//	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}
	public Publication getPublication(){
		return this.publication;
	}
	
	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public String getCopyNo() {
		return copyNo;
	}

	public String toString() {
		return  "copyNo: " + this.copyNo + ", memberID: " + this.memberID + 
				this.publication.toString();
	}

	public String checkoutDetail(LendableCopy copy){
	    LibraryMember member = new UserService().searchMember(copy.getMemberID());

		return "CopyNo: " + copy.copyNo +
				"\nMemberID: " + member.getMemberId() +
				"\nMemberName: " + member.getFirstName() + " " + member.getLastName() +
				"\nTitle: " + copy.publication.getTitle() +
				"\nDateDue: " + copy.publication.getDateDue();
	}
}
