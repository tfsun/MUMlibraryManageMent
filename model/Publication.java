package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract public class Publication implements Serializable {
//	public enum PUBTYPE {
//		BOOK,
//		PERIODICAL;
//	};

	private static final long serialVersionUID = 2010893663327964921L;
	private LocalDate dateDue = null;
	private String title;
	private List<LendableCopy> Copys = new ArrayList<>();
	int maxCheckoutLength;
	
	public final static Function<List<LendableCopy>, List<LendableCopy>> NEXTAVAILABLECOPY
	   = (list) -> list.stream()
                     .filter(c -> c.isCheckOut() == false)
                     .collect(Collectors.toList());
	   
	public void setDateDue(LocalDate d) {
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
	public boolean addCopy() {
		LendableCopy copy = new LendableCopy(this);
		Copys.add(copy);
		return true;
	}
	public List<LendableCopy> getCopys(){
		return this.Copys;
	}
	public LendableCopy getNextAvailableCopy() {
		if (Copys.size() <= 0){
			return null;
		}
		for (int i=0;i<Copys.size();i++){
			if (!Copys.get(i).isCheckOut()){ // if copy is not checkout yet
				return Copys.get(i);
			}
		}
		
		return null;
	}
	public LendableCopy getNextAvailableCopyLambda() {
		if (Copys.size() <= 0){
			return null;
		}
		if (!NEXTAVAILABLECOPY.apply(Copys).isEmpty()){
			return NEXTAVAILABLECOPY.apply(Copys).get(0);
		}
		return null;
	}
	public String toString() {
		return 	"MaxCheckoutLength : " + maxCheckoutLength + 
				", Title: " + title + 
				", DateDue: " + getDateDue();
	}
}
