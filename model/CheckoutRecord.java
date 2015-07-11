package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable{

	private static final long serialVersionUID = -3119855589946373695L;
	private LibraryMember member;
	private List<CheckoutRecordEntry> entries = new ArrayList<>();
	
	public static void printEntries(List<CheckoutRecordEntry> entries){
		for (CheckoutRecordEntry cre: entries){
			System.out.println(cre.toString());
		}
	}	
	
	public void addEntry(CheckoutRecordEntry c) {
		entries.add(c);
	}
	public void removeEntry(LendableCopy copy) {
		for (CheckoutRecordEntry entry: entries){
			if (copy.equals(entry.getCopy())){
				entries.remove(entry);
			}
		}
	}
	public List<CheckoutRecordEntry> getEntry(){
		return this.entries;
	}
	
	public String toString() {
		return entries.toString();
	}
	
}