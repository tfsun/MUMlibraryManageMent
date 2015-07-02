package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dataAccess.DataAccessFacade;

public class CheckoutRecordEntry implements Serializable {
//	private LendableCopy copy;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	public CheckoutRecordEntry(LocalDate checkoutDate, LocalDate dueDate){ 
//		this.copy = copy;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
	}
	public String toString() {
		return "[" + "checkoutdate:" + 
	        checkoutDate.format(DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN)) +
	        ", dueDate: " + dueDate.format(DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN)) +"]";
//	        ", publication: " + copy + "]";
	}
}
