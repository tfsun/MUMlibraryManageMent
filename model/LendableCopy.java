package model;

import java.io.Serializable;

import Services.CopyService;

public class LendableCopy implements Serializable {
	private Publication publication;
	private String copyNo; 

	public LendableCopy(Publication publication) {
		setPublication(publication);
		copyNo = CopyService.getCopyIDbyPublication(publication);
		//this.copyId = copyId;
	}
	
//	public LendableCopy() {
//	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}
	
	public String getCopyNo() {
		return copyNo;
	}

	public String toString() {
		return publication.toString();
	}
	
}
