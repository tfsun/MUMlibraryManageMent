package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

import Services.BookService;
import Services.PeriodicalService;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;
import model.Book;
import model.CheckoutRecordEntry;
import model.LendableCopy;
import model.LibraryMember;
import model.Periodical;
import model.Publication;
import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;
import dataAccess.DataAccessFacade.Pair;

public class FXMLReturnController implements FXMLController{
	protected static boolean hasInstance = false;
	public static Stage returnStage = new Stage();
	private LibraryMember member = null;
	private Publication pub = null;
	private String copyID; // the Copy Number to use
	private LendableCopy copy = null;
	private CheckoutRecordEntry entry = null;
	@FXML private TextField FXMLCopyNo;
	@FXML private TextField FXMLMemberID;
	
	@FXML void handleReturnAction(){
		if(!setMemberID()) return;
		if(!setCopyNo()) return;
		if(!setPublication()) return;
		if(!setCopy()) return;
		// remove entry from member
		// mark copy checkout as false
		if(saveReturnRecord()){
			MessageBox.show(returnStage,
					 "Return successfully!",
			         "Success Infomation",
			         MessageBox.ICON_INFORMATION | MessageBox.OK | MessageBox.DEFAULT_BUTTON2);
		}
	}
	public void initPanel() throws IOException{
		setHasInstance(true);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Return.fxml"));
	    Parent root = loader.load();
	    returnStage.setTitle("Return Copy");
	    returnStage.setScene(new Scene(root, 600, 400));
	}
	public void showPanel(){
		returnStage.show();
		returnStage.toFront();
	}
	public boolean getHasInstance() {
		return hasInstance;
	}
	public void setHasInstance(boolean hasInstance) {
		FXMLReturnController.hasInstance = hasInstance;
	}
	void showWarningMsg(String msg){
		MessageBox.show(returnStage,
		         msg,
		         "Warning dialog",
		         MessageBox.ICON_WARNING);
	}
	boolean emptyWarning(String txt, String msg){
		if (txt.isEmpty()){
			showWarningMsg("Please input " + msg + "!");
			return false;
		}
		return true;
	}
	boolean setCopyNo(){
		if (!emptyWarning(FXMLCopyNo.getText(), "Copy Nomber")){
			return false;
		}else{
			this.copyID = FXMLCopyNo.getText();
		}
		return true;
	}
	public void setLibraryMember(String memberID){
	    this.member = new UserService().searchMember(memberID);
	}
	
	boolean setMemberID(){
		if (!emptyWarning(FXMLMemberID.getText(), "Member ID")){
			return false;
		}else{
			setLibraryMember(FXMLMemberID.getText());
			if (this.member == null){
				showWarningMsg("Member is not found! Please check your input.");
				return false;
			}
		}
		return true;
	}
	public boolean setCopy(){
		for (LendableCopy copy: this.pub.getCopys()){
			if (this.copyID.equals(copy.getCopyNo())){
				this.copy = copy;
				return true;
			}
		}
		return false;
	}
	public void setPub(Publication pub){
		this.pub = pub;
	}
	private boolean setPublication(){
		DataAccessFacade daf = new DataAccessFacade();
		if (this.copyID.startsWith("Book")){
			HashMap<String, Book> books = daf.readBooksMap();
			if (books != null){
				for (String key : books.keySet()) {
					// check book ISBN before go through the copies
					if (this.copyID.contains(books.get(key).getIsbn())){
						setPub(books.get(key));
						return true;
					}
				}
			}
			else{
				System.out.println("No book copy found!");
				return false;
			}
		}
		if (this.copyID.startsWith("Periodical")){
			HashMap<Pair<String,String>,Periodical> periodicals = daf.readPeriodicalsMap();
			if (periodicals != null){
				for (Pair<String, String> key : periodicals.keySet()) {
					// check periodical Title and IssueNumber before go through the periodical
					if (this.copyID.contains(periodicals.get(key).getTitle() + "_" + periodicals.get(key).getIssueNumber())){
						setPub(periodicals.get(key));
						return true;
					}
				}
			}
			else{
				System.out.println("No periodical found!");
				return false;
			}
		}
		return false;
	}
	
	public boolean saveReturnRecord(){
		// for Use Case 7, to check whether a book is overDue or not
		//copy.setMemberID(memberID.getText());
		copy.setCheckOut(false);
		//copy.getPublication().setDateDue(dateDue);

		// save data into stream
		DataAccess da = new DataAccessFacade();
		this.member.removeCheckoutEntry(this.copy);;
		da.updateMember(this.member);

		if (copyID.startsWith("Book_")){
			new BookService().updateBook((Book)pub, true); // update Copies for Book
			//da.updateBook((Book)pub);
		}
		else if (copyID.startsWith("Periodical_")){
			new PeriodicalService().updatePeriodical((Periodical)pub, true); // update Copies for Book
			//da.updatePeriodical((Periodical)pub);
		}
		return true;
	}
}
