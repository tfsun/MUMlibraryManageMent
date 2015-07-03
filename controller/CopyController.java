package controller;

import java.util.HashMap;

//import projectstartup.librarysample.dataaccess.DataAccessFacade.Pair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Book;
import model.Periodical;
import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;
import dataAccess.DataAccessFacade.Pair;
import dataAccess.StorageType;

public class CopyController extends BaseController {
    @FXML private TextField ISBN;
    @FXML private Label LbISBN;
    @FXML private TextField Title;
    @FXML private Label LbTitle;

    @FXML private CheckBox AddBookCopy;
    @FXML private CheckBox AddPeriodicalCopy;
    
    //private DataAccessFacade.StorageType storageType =  DataAccessFacade.StorageType.BOOKS;
    
    private StorageType storageType = StorageType.BOOK;
    @FXML protected void GePublicationData(ActionEvent event) {
    	switch (storageType) {
		case BOOK:
			saveNewBook();
			//return dataAccess.saveNewBook(book);
			break;
		case PERIODICAL:
			savePeriodicalCopy();
		default:
			break;
		}

    }
    
    @FXML private boolean savePeriodicalCopy() {
    	String strIssueNumber =  ISBN.getText();
    	String strTitle =  Title.getText();
    	
    	DataAccess dataAccess = new DataAccessFacade();
    	HashMap<Pair<String,String>, Periodical> periodcalMap = dataAccess.readPeriodicalsMap();
    	return true;
    	//Pair<String, String> periodKey = new Pair(strTitle, strIssueNumber);
    }
    
    private boolean saveNewBook() {
    	String strISBN =  ISBN.getText();
//    	String strTitle =  Title.getText();
    	
    	DataAccess dataAccess = new DataAccessFacade();
    	HashMap<String,Book> books = dataAccess.readBooksMap();
    	if (books.containsKey(strISBN)) {
    		Book book = books.get(strISBN);
    		//Book.addCopy();		
		}
    	return true;
    }
    
    @FXML protected void setBookType(ActionEvent event) {
    	storageType =  StorageType.BOOK;
    	AddPeriodicalCopy.setSelected(false);
    	LbISBN.setText("ISBN");
    }
    
    @FXML protected void SelectPeriodicalType(ActionEvent event) {
    	storageType =  StorageType.PERIODICAL;
    	AddBookCopy.setSelected(false);
    	LbISBN.setText("IssueNo");
    }
}
