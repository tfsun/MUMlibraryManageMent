package dataAccess;

import java.util.HashMap;

import model.Book;
import model.LendableCopy;
import model.LibraryMember;
import model.Periodical;
import dataAccess.DataAccessFacade.Pair;
//import projectstartup.librarysample.dataaccess.DataAccessFacade.Pair;
//import model.dataaccess.DataAccessFacade.StorageType;



public interface DataAccess {
	public LibraryMember searchMember(String memberId);
	
	///////save methods
	public void saveNewMember(LibraryMember member);
	public void updateMember(LibraryMember member);
	
	//save new lendable item
	public boolean saveNewBook(Book book);
	public boolean saveNewPeriodical(Periodical periodical);
	
	public boolean saveCopy(LendableCopy copy);
	
	//////read methods 
	public HashMap<String,Book> readBooksMap();
	public HashMap<Pair<String, String>, Periodical> readPeriodicalsMap();
	public HashMap<String, LibraryMember> readMemberMap();
}
