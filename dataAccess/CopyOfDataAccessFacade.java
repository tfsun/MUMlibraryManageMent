package dataAccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataAccess.DataAccessFacade.Pair;
import javadb.DBManager;
import model.Address;
import model.Author;
import model.Book;
import model.LendableCopy;
import model.LibraryMember;
import model.Periodical;

public class CopyOfDataAccessFacade implements CopyOfDataAccess {
	
	private DBManager dbManager = new DBManager();
	private final String sqlgetPeriodical = "select * from PUBLICATION where title=%s and isbn_issuenum=%s;";
	private final String sqlgetAllPeriodical = "select * from PUBLICATION";
	
	private final String sqlgetAllBook = "select * from BOOK";
	private final String sqlgetAllauthor = "select * from AUTHOR";
	private final String sqlgetAllAddress = "select * from ADDRESS";

	private final String sqlsavePeriodical = "insert into PUBLICATION(pubtype,title,isbn_issuenum,maxcheckoutlength) "
			+ "values('book','%s','%s',%d);";	
	
	//private final String sqlgetauthorId = "select authorid from PUBLICATIONAUTHOR where pubid=%s";
	private final String sqlgetauthor = "select * from AUTHOR where id=%s";
	private final String sqlgetaddress = "select * from ADDRESS where id=%s";
	String sql = "insert into periodical(NO,name) values('2012001','tengfei sun')";	
	private static HashMap<String,Book> books;
	private static HashMap<Pair<String, String>,Periodical> periodicals;
	private static HashMap<String, LibraryMember> members;
	private static HashMap<String, LendableCopy> copys;

//	private static HashMap<String,Author> authors;
//	private static HashMap<String,Address> addresses;
	
//	@SuppressWarnings("finally")
//	@Override
//	public boolean saveNewPeriodical(Periodical periodical) {
//		boolean rs = false;
//		try{
//			String sql =  String.format(sqlsavePeriodical, periodical.getTitle(),
//					periodical.getIssueNumber(),periodical.getMaxCheckoutLength());
//			System.out.println(sql);
//			rs = dbManager.execute(sql);
//		}catch (Exception e) {
//			 e.printStackTrace();
//		}finally {
//			return rs;
//	    }
//	}
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
		saveToDB(StorageType.BOOK, bookMap);	
		return true;
	}
	@Override
	public boolean saveNewPeriodical(Periodical periodical) {
		return updatePeriodical(periodical,false);
	}
	@Override
	public boolean updatePeriodical(Periodical periodical) {
		return updatePeriodical(periodical,true);
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
		saveToDB(StorageType.PERIODICAL, periodMap);	
		System.out.println("save Periodical success!");
		return true;
	}

//	@SuppressWarnings("finally")
//	@Override
//	public Periodical getPeriodical(String issueNumber, String title)  {
//		Periodical periodical = null;
//		try{
//				String sql =  String.format(sqlgetPeriodical, title,issueNumber);
//				System.out.println(sql);
//				ResultSet rs = dbManager.executeQuery(sql);
//					while (rs.next()) {
//					periodical = new Periodical(Integer.valueOf(issueNumber), title, rs.getInt(5));
//				}
//		}catch (SQLException e) {
//			 e.printStackTrace();
//		}finally {
//			return periodical;
//        }
//	}

	@SuppressWarnings("unchecked")
	public HashMap<Pair<String,String>, Periodical> readPeriodicalsMap() {
		if(periodicals == null) {
			periodicals = (HashMap<Pair<String,String>, Periodical>) readFromDB(
				StorageType.PERIODICAL);
		}
		return periodicals;
	}
	
	@SuppressWarnings("unchecked")
	public  HashMap<String,Book> readBooksMap() {
		if(books == null) {
			books = (HashMap<String,Book>) readFromDB(StorageType.BOOK);
		}
		return books;
	}
	
	public Object readFromDB(StorageType type) {
		try {

			switch (type) {
			case PERIODICAL:
				periodicals = readPeriodiclaMapFromDB();
			case BOOK:
				books = readBookMapFromDB();
			default:
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public int saveToDB(StorageType type, Object ob) {
		String sql = null;
		int rs =-1;
		try {
			switch (type) {
			case PERIODICAL:
				savePeriodiclMap2DB(ob);
				break;
			default:
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	private  HashMap<Pair<String, String>,Periodical> readPeriodiclaMapFromDB()
	{
		periodicals = new HashMap<>();	
		Periodical periodical = null;
		Integer issueNumber;
		try {
			ResultSet rs = dbManager.executeQuery(sqlgetAllPeriodical);
			while (rs.next()) {
				issueNumber = Integer.valueOf(rs.getString(4));
				String title = rs.getString(3);
				periodical = new Periodical(Integer.valueOf(issueNumber), title, rs.getInt(5));
				Pair<String, String> periodKey = new Pair(periodical.getTitle(), periodical.getIssueNumber());
				periodicals.put(periodKey, periodical);
			}
		} catch (SQLException e) {
			System.err.println("readPeriodiclaFromDB error!");
		}
		return periodicals;
	}

	private int savePeriodiclMap2DB(Object ob){
		try {
			List<String> sqls = new ArrayList<>();
			HashMap<Pair<String, String>,Periodical> periodicalmap = (HashMap<Pair<String, String>,Periodical>)ob;
			for (Periodical periodical : periodicalmap.values()) {
				sql =  String.format(sqlsavePeriodical, periodical.getTitle(),
						periodical.getIssueNumber(),periodical.getMaxCheckoutLength());
				sqls.add(sql);
			}
			dbManager.processTransaction(sqls);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("savePeriodicl2DB error!");
		}
		return 1;
	}
	private  HashMap<String,Book> readBookMapFromDB()
	{
		books = new HashMap<>();	
		Book book = null;
		try {
			ResultSet rs = dbManager.executeQuery(sqlgetAllBook);
			while (rs.next()) {
				String title = rs.getString(3);
				String ISBN = rs.getString(4);		
				int maxCheckoutLength = Integer.valueOf(rs.getString(5));
				String authorIds = rs.getNString(6);	
				book = new Book(ISBN, title, maxCheckoutLength);
				String [] idArrayStrings = authorIds.split(",");
				for (String authorId : idArrayStrings) {
//					String sql = sqlgetauthor.format(format, args)
//					ResultSet rs1 = dbManager.executeQuery(sqlgetauthor);
				}
				
				books.put(ISBN,book);
			}
		} catch (SQLException e) {
			System.err.println("readPeriodiclaFromDB error!");
		}
		return books;
	}
	
//	private  HashMap<String,Author> readAuthorMapFromDB()
//	{
//		authors = new HashMap<>();	
//		Author author = null;
//		try {
//			ResultSet rs = dbManager.executeQuery(sqlgetAllauthor);
//			while (rs.next()) {
//		    	//Address address = new Address(strstreet, strcity, strstate, strzip);
//				int addressId = rs.getInt(2);
//				String strphone = rs.getString(3);
//				String strfirstName = rs.getString(4);
//				String strlastName = rs.getString(5);
////				 Author(String firstName, String lastName, String credentials,
////							String phone, int addressId)
//		    	author = new Author(strfirstName,strlastName,"",strphone,addressId);
//				authors.put(strlastName+strphone,author);
//			}
//		} catch (SQLException e) {
//			System.err.println("readPeriodiclaFromDB error!");
//		}
//		return books;
//	}
}