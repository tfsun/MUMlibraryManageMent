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
import model.LendableCopy;
import model.LibraryMember;
import model.Periodical;

public class DataAccessFacade implements DataAccess {

	public static final String OUTPUT_DIR = System.getProperty("user.dir") 
			+ "\\src\\dataAccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	private static HashMap<String,Book> books;
	private static HashMap<Pair<String, String>,Periodical> periodicals;
	private static HashMap<String, LibraryMember> members;
	private static HashMap<String, LendableCopy> copys;
	
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
	@Override
	public boolean saveNewBook(Book book) {
		return updateBook(book, false);
	}
	@Override
	public boolean updateBook(Book book) {
		return updateBook(book, true);
	}
	//bReplace:true,update, false,add
	private boolean updateBook(Book book, Boolean bReplace) {
		HashMap<String, Book> bookMap = readBooksMap();
		String isbn = book.getIsbn();
		if (bReplace == false) {
			if (bookMap == null) {
				bookMap = new HashMap<String, Book>();
			}
			if (bookMap.containsKey(isbn)) {
				System.out.println("book already exist!");
				return false;
			}
			bookMap.put(isbn, book);
		}
		else {
			if (bookMap == null || bookMap != null && false == bookMap.containsKey(isbn)) {
				System.out.println("book not exist!");
				return false;
			}
			bookMap.replace(isbn, book);
		}
		books = bookMap;
		saveToStorage(StorageType.BOOK, bookMap);	
		return true;
	}
	@Override
	public Book getBookByISBN(String ISBN) {
		HashMap<String, Book> bookMap = readBooksMap();
		if (bookMap == null || bookMap != null && false == bookMap.containsKey(ISBN)) {
			return null;
		}
		else {
			return bookMap.get(ISBN);
		}
	}
	@Override
	public boolean saveNewPeriodical(Periodical periodical) {
		return updatePeriodical(periodical,false);
	}
	@Override
	public boolean updatePeriodical(Periodical periodical) {
		return updatePeriodical(periodical,true);
	}
	
	//bReplace:true,update, false,add
	private boolean updatePeriodical(Periodical periodical, Boolean bReplace) {
		HashMap<Pair<String, String>, Periodical> periodMap = readPeriodicalsMap();
		Pair<String, String> periodKey = new Pair(periodical.getTitle(), periodical.getIssueNumber());
		if (bReplace==false) {
			if (periodMap == null) {
				periodMap = new HashMap<Pair<String, String>, Periodical>();
			}
			if (periodMap.containsKey(periodKey)) {
				System.out.println("periodical already exist!");
				return false;
			}
			periodMap.put(periodKey, periodical);
		}
		else {
			if (periodMap == null || periodMap != null && false == periodMap.containsKey(periodKey)) {
				System.out.println("periodical not exist!");
				return false;
			}
			periodMap.replace(periodKey, periodical);
		}		
		periodicals = periodMap;
		saveToStorage(StorageType.PERIODICAL, periodMap);	
		System.out.println("save Periodical success!");
		return true;
	}
	@Override
	public Periodical getPeriodical(String issueNo, String title) {
		HashMap<Pair<String, String>, Periodical> periodMap = readPeriodicalsMap();
		Pair<String, String> periodKey = new Pair(title, issueNo);
		if (periodMap == null || periodMap != null && false == periodMap.containsKey(periodKey)) {
			return null;
		}
		return periodMap.get(periodKey);
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
	
	@SuppressWarnings("unchecked")
	public HashMap<String, LendableCopy> readCopyMap() {
		if(copys == null) {
			copys = (HashMap<String, LendableCopy>) readFromStorage(
					StorageType.COPY);
		}
		return copys;
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
	
	private static void loadCopysMap(List<LendableCopy> copyList) {
		copys = new HashMap<String, LendableCopy>();
		copyList.forEach(copy -> copys.put(copy.getCopyNo(), copy));
		saveToStorage(StorageType.COPY, copys);
	}

	public static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} 
		catch(java.nio.file.NoSuchFileException e) {
			System.err.println("please check the file path:" + e.getFile());
		} 
		catch(java.io.NotSerializableException e) {
			System.err.println("can't serializable:" + e.getMessage());
		} 
		catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}
	
	public static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}

	
	public final static class Pair<S,T> implements Serializable {
		S first;
		T second;
		public Pair(S s, T t) {
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