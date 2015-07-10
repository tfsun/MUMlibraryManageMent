package Services;

import dataAccess.DataAccessFacade;
import dataAccess.StorageType;
import javafx.util.Pair;
import model.Book;
import model.LendableCopy;
import model.Periodical;

import java.io.ObjectInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class BookService {
    private static HashMap<String,Book> books;

    //bReplace:true,update, false,add
    public boolean updateBook(Book book, Boolean bReplace) {
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
        new DataAccessFacade().saveToStorage(StorageType.BOOK, bookMap);
        return true;
    }

    public Book getBookByISBN(String ISBN) {
        HashMap<String, Book> bookMap = readBooksMap();
        if (bookMap == null || bookMap != null && false == bookMap.containsKey(ISBN)) {
            return null;
        }
        else {
            return bookMap.get(ISBN);
        }
    }



    //////read methods that return full maps
    ///// programming idiom: when saves are done, the corresponding map
    ////  is updated, then saved to storage, so when a read is done
    ////  it is not necessary to retrieve from storage -- just read
    ////  the map provided in this class

    @SuppressWarnings("unchecked")
    public  HashMap<String,Book> readBooksMap() {
        if(books == null) {
            books = (HashMap<String,Book>) new DataAccessFacade().readFromStorage(StorageType.BOOK);
        }
        return books;
    }

    public boolean saveNewBook(Book book) {
        return updateBook(book, false);
    }



    public static void loadBookMap(List<Book> bookList) {
        books = new HashMap<String, Book>();
        bookList.forEach(book -> books.put(book.getIsbn(), book));
        new DataAccessFacade().saveToStorage(StorageType.BOOK, books);
    }



}
