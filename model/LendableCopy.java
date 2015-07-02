package model;

import java.io.Serializable;

public class LendableCopy implements Serializable {
	private Publication publication;
	private int copyId; 

	public LendableCopy(Publication publication, int copyId) {
		setPublication(publication);
		this.copyId = copyId;
	}
	
	public LendableCopy() {
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}
	

	
	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}
	public String toString() {
		return publication.toString();
	}
	
}
