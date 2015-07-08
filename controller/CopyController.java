package controller;

import java.util.HashMap;



//import projectstartup.librarysample.dataaccess.DataAccessFacade.Pair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import jfx.messagebox.MessageBox;
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
    
    static private CopyController instance=null;   
    static public CopyController getInstance() {
    	if (null==instance) {
			instance = new CopyController();
		}
    	return instance;
    }
    
    public void openCopyUI(ActionEvent event) {
    	if (stage!=null && stage.isShowing()) {
    		System.out.println("Already open the Copy UI!");
    		return;
		}
    	//stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/Copy.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("AddCopy");
            stage.setScene(scene);
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(
//                ((Node)event.getSource()).getScene().getWindow() );       

            System.out.println(event.getClass());
            stage.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private StorageType storageType = StorageType.BOOK;
    @FXML protected void AddCopy(ActionEvent event) {
    	switch (storageType) {
		case BOOK:
			saveBookCopy();
			//return dataAccess.saveNewBook(book);
			break;
		case PERIODICAL:
			savePeriodicalCopy();
		default:
			break;
		}

    }
    
    @FXML protected void Cancel(ActionEvent event) {
//    	MessageBox.show(stage,
//    		    "Message Body",
//    		    "Message Title", 
//    		    MessageBox.ICON_INFORMATION | MessageBox.OK);
    }
    @FXML private boolean savePeriodicalCopy() {
    	boolean bRet = false;
    	try {
        	String strIssueNumber =  ISBN.getText();
        	String strTitle =  Title.getText();	
        	if (strIssueNumber.length()<1 || strTitle.length()<1) {
            	MessageBox.show(stage,
    		    "IssueNO and strTitle must has value!",
    		    "Error", 
    		    MessageBox.ICON_INFORMATION | MessageBox.OK);
            	return false;
    		}
        	DataAccess dataAccess = new DataAccessFacade();
        	HashMap<Pair<String,String>, Periodical> periodcalMap = dataAccess.readPeriodicalsMap();
        	Pair<String, String> periodKey = new Pair(strTitle, strIssueNumber);
        	if (periodcalMap==null || periodcalMap != null && periodcalMap.containsKey(periodKey) == false) {
            	MessageBox.show(stage,
    		    "No periodcal found for the Input!:",
    		    "Error", 
    		    MessageBox.ICON_INFORMATION | MessageBox.OK);
			}
        	else if (periodcalMap.containsKey(periodKey)) {
        		Periodical periodical = periodcalMap.get(periodKey);
        		periodical.addCopy();
        		bRet = dataAccess.updatePeriodical(periodical);
    		}
        	
		} 
    	catch (Exception e) {
    		e.printStackTrace();
		}
    	if (bRet == true) {
        	MessageBox.show(stage,
		    "Add periodical copy success!",
		    "success", 
		    MessageBox.ICON_INFORMATION | MessageBox.OK);
		}
    	return bRet;
    	//Pair<String, String> periodKey = new Pair(strTitle, strIssueNumber);
    }
    
   private boolean saveBookCopy() {
    	boolean bRet = false;
    	try {
        	String strISBN =  ISBN.getText();
        	if (ISBN.getLength()<1) {
            	MessageBox.show(stage,
    		    "ISBN must has value!",
    		    "Error", 
    		    MessageBox.ICON_INFORMATION | MessageBox.OK);
            	return false;
			}
        	DataAccess dataAccess = new DataAccessFacade();
        	HashMap<String,Book> books = dataAccess.readBooksMap();
        	if (books==null || books != null && books.containsKey(strISBN) == false) {
            	MessageBox.show(stage,
    		    "No book has the ISBN:" + strISBN + "!",
    		    "Error", 
    		    MessageBox.ICON_INFORMATION | MessageBox.OK);
			}
        	else if (books.containsKey(strISBN)) {
        		Book book = books.get(strISBN);
        		book.addCopy();
        		bRet = dataAccess.updateBook(book);
    		}
		} 
    	catch (Exception e) {
    		e.printStackTrace();
			// TODO: handle exception
		}
    	if (bRet == true) {
        	MessageBox.show(stage,
		    "Add bookcopy success!",
		    "success", 
		    MessageBox.ICON_INFORMATION | MessageBox.OK);
		}
    	return bRet;
    }
    
    @FXML protected void setBookType(ActionEvent event) {
    	storageType =  StorageType.BOOK;
    	AddPeriodicalCopy.setSelected(false);
    	AddBookCopy.setSelected(true);
    	LbTitle.setVisible(false);
    	Title.setVisible(false);
    	LbISBN.setText("ISBN");
    }
    
    @FXML protected void setPeriodicalType(ActionEvent event) {
    	storageType =  StorageType.PERIODICAL;
    	AddBookCopy.setSelected(false);
    	AddPeriodicalCopy.setSelected(true);
    	LbTitle.setVisible(true);
    	Title.setVisible(true);
    	LbISBN.setText("IssueNo");
    }
}
