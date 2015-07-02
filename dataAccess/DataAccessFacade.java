package dataAccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import model.Book;
import model.LibraryMember;
import model.Periodical;

public class DataAccessFacade implements DataAccess {
	
//	public enum StorageType {
//		BOOKS, PERIODICALS, MEMBERS;
//	}
	
	public static final String OUTPUT_DIR = System.getProperty("user.dir") 
			+ "\\src\\projectstartup\\librarysample\\dataaccess\\storage";
			//+ "\\src\\project\\dataaccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	
	private static HashMap<String,Book> books;
	private static HashMap<Pair<String, String>,Periodical> periodicals;
	private static HashMap<String, LibraryMember> members;
	
	////specialized lookup methods
	public LibraryMember searchMember(String memberId) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		if(mems.containsKey(memberId)) {
			return mems.get(memberId);
		}
		return null;
	}
	
	
	
	///////save methods
	//saveNewMember
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		members = mems;
		saveToStorage(StorageType.MEMBERS, mems);	
	}
	
	public void updateMember(LibraryMember member) {
		saveNewMember(member);
	}
	
	//save new lendable item
	public boolean saveNewBook(Book book) {
		HashMap<String, Book> bookMap = readBooksMap();
		String isbn = book.getIsbn();
		if (bookMap == null) {
			bookMap = new HashMap<String, Book>();
		}
		if (bookMap.containsKey(isbn)) {
			System.out.println("book already exist!");
			return false;
		}
		bookMap.put(isbn, book);
		books = bookMap;
		saveToStorage(StorageType.BOOK, bookMap);	
		return true;
	}
	
	public boolean saveNewPeriodical(Periodical periodical) {
		HashMap<Pair<String, String>, Periodical> periodMap = readPeriodicalsMap();
		Pair<String, String> periodKey = new Pair(periodical.getTitle(), periodical.getIssueNumber());
		if (periodMap == null) {
			periodMap = new HashMap<Pair<String, String>, Periodical>();
		}
		periodMap.put(periodKey, periodical);
		periodicals = periodMap;
		saveToStorage(StorageType.PERIODICAL, periodMap);	
		System.err.println("save Periodical success!");
		return true;
	}
	
	//////read methods that return full maps
	///// programming idiom: when saves are done, the corresponding map
	////  is updated, then saved to storage, so when a read is done
	////  it is not necessary to retrieve from storage -- just read
	////  the map provided in this class
	
	@SuppressWarnings("unchecked")
	public  HashMap<String,Book> readBooksMap() {
		if(books == null) {
			books = (HashMap<String,Book>) readFromStorage(StorageType.BOOK);
		}
		return books;
	}
	@SuppressWarnings("unchecked")
	public HashMap<Pair<String,String>, Periodical> readPeriodicalsMap() {
		if(periodicals == null) {
			periodicals = (HashMap<Pair<String,String>, Periodical>) readFromStorage(
				StorageType.PERIODICAL);
		}
		return periodicals;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		if(members == null) {
			members = (HashMap<String, LibraryMember>) readFromStorage(
					StorageType.MEMBERS);
		}
		return members;
	}
	
	
	/////load methods - these place test data into the storage area
	///// - used just once at startup  
	public static void loadMemberMap(List<LibraryMember> memberList) {
		members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}
	public static void loadBookMap(List<Book> bookList) {
		books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOK, books);
	}
	public static void loadPeriodicalsMap(List<Periodical> periodicalList) {
		periodicals = new HashMap<Pair<String, String>, Periodical>();
		periodicalList.forEach(
			p -> periodicals.put(new Pair<String,String>(p.getTitle(), p.getIssueNumber()), p));
		saveToStorage(StorageType.PERIODICAL, periodicals);
	}
	public static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}
	
	public static Object readFromStorage(dataAccess.StorageType members2) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, members2.toString());
			System.out.println(path);
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(Exception e) {
			//e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}
	
	
	
	public final static class Pair<S,T> implements Serializable{
		
		S first;
		T second;
		Pair(S s, T t) {
			first = s;
			second = t;
		}
		@Override 
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(this == ob) return true;
			if(ob.getClass() != getClass()) return false;
			@SuppressWarnings("unchecked")
			Pair<S,T> p = (Pair<S,T>)ob;
			return p.first.equals(first) && p.second.equals(second);
		}
		
		@Override 
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}
		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}
		private static final long serialVersionUID = 5399827794066637059L;
	}
	
}
