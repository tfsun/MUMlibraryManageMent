package model;

import java.io.Serializable;
import java.time.LocalDate;

abstract public class Publication implements Serializable {
//	public enum PUBTYPE {
//		BOOK,
//		PERIODICAL;
//	};

	private static final long serialVersionUID = 2010893663327964921L;
	private LocalDate dateDue;
	private String title;
	int maxCheckoutLength;

	
	protected void setDateDue(LocalDate d) {
		dateDue = d;
	}
	public Publication(String title, int maxCheckoutLength) {
		this.title = title;
		this.maxCheckoutLength = maxCheckoutLength;
	}
	public LocalDate getDateDue() {
		return dateDue;
	}
	public int getMaxCheckoutLength() {
		return maxCheckoutLength;
	}
	public String getTitle() {
		return title;
	}
	public abstract boolean addCopy();
}
