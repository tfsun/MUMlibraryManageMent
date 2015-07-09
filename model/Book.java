package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
	private List<Author> Authors = new ArrayList<Author>();
	
	public Book(String isbn, String title, int maxCheckoutLength) {
		this(curID, isbn, title, maxCheckoutLength);
		//curID++;
	}
	public Book(int id, String isbn, String title, int maxCheckoutLength) {
		super(title, maxCheckoutLength);
		this.id = id;
		this.isbn = isbn;
	}
	//add(new Book("23-11451", "The Big Fish", 21, Arrays.asList(allAuthors.get(0), allAuthors.get(1))));
	public Book(String isbn, String title, int maxCheckoutLength, List<Author> asList) {
		this(isbn, title, maxCheckoutLength);
		this.Authors = asList;
	}
	public void isAvailable(boolean b) {
		available = b;
	}

	public List<Author> getAuthors() {
		return Authors;
	}
	public void AddAuthor(Author author) {
		Authors.add(author);
	}
	public void setAuthors(List<Author> curAuthors) {
		this.Authors = curAuthors;
	}
//	@Override
//	public boolean addCopy() {
//		// TODO Auto-generated method stub
//		LendableCopy copy = new LendableCopy(this);
//		return false;
//	}
	@Override
	public String toString() {
		return "[BOOK] " + super.toString() + ", id: " + id + ", isbn: " + isbn + ", available: " + available;
	}

}
