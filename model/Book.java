package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book extends Publication implements Serializable {
	private int id;
	private String isbn;
	private boolean available;
	private static int curID = 1;
	
	public int getId() {
		return id;
	}
	public String getIsbn() {
		return isbn;
	}
	public static int getCurID() {
		return curID++;
	}
	private List<Author> curAuthors = new ArrayList<Author>();
	
//	public Book(String isbn, String title, int maxCheckoutLength) {
//		this(curID, isbn, title, maxCheckomaxCheckoutLengthutLength);
//		curID++;
//	}
	public Book(int id, String isbn, String title, int maxCheckoutLength) {
		super(title, maxCheckoutLength);
		this.id = id;
		this.isbn = isbn;
	}
	public void isAvailable(boolean b) {
		available = b;
	}
	@Override
	public String toString() {
		return "id: " + id + ", isbn: " + isbn + ", available: " + available;
	}
	public List<Author> getCurAuthors() {
		return curAuthors;
	}
	public void AddCurAuthor(Author author) {
		curAuthors.add(author);
	}
	public void setCurAuthors(List<Author> curAuthors) {
		this.curAuthors = curAuthors;
	}
	@Override
	public boolean addCopy() {
		// TODO Auto-generated method stub
		LendableCopy copy = new LendableCopy(this);
		return false;
	}
}
