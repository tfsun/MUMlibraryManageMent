package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import Services.UserService;
import model.Book;
import model.CheckoutRecord;
import model.CheckoutRecordEntry;
import model.LendableCopy;
import model.LibraryMember;
import model.Periodical;
import model.Publication;
import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;

public class FXMLCheckOutController implements FXMLController{
	protected static boolean hasInstance = false;
	public static Stage checkoutStage = new Stage();

	@FXML private TextField memberID;
	@FXML private TextField bookISBN;
	@FXML private TextField periodicalTitle;
	@FXML private TextField periodicalIssueNumber;

	private LibraryMember member;
	private LendableCopy copy;
	private Publication pub;
	private DataAccess dataAccess = new DataAccessFacade();;	

	public void setLibraryMember(String memberID){
	    this.member = new UserService().searchMember(memberID);
	}
	
	public void setPublication(String bookISBN){
		this.pub = dataAccess.getBookByISBN(bookISBN);
	}
	public void setPublication(String pubTitle, String issueNumber){
		this.pub = dataAccess.getPeriodical(issueNumber, pubTitle);
	}
	void showWarningMsg(String msg){
		MessageBox.show(checkoutStage,
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
	boolean setMemberID(){
		if (!emptyWarning(memberID.getText(), "Member ID")){
			return false;
		}else{
			setLibraryMember(memberID.getText());
			if (this.member == null){
				showWarningMsg("Member is not found! Please check your input.");
				return false;
			}
		}
		return true;
	}
	boolean isPubEmpty(){
		if (this.pub == null){
			showWarningMsg("Publication is not found! Please check your input.");
			return true;
		}
		return false;
	}
	// Use case 7, add member info into copy list, which is stores in Copys in Publication
	// Besides that, we have to add a function to return copy
	// The book is overdue only if:
	// 1. this copy is checkout by member
	// 2. the dateDue of this copy is before today
	private boolean setCopy(){
		//this.copy =  this.pub.getNextAvailableCopy();
		// Lambda expression
		//this.copy = Publication.NEXTAVAILABLECOPY.apply(this.pub.getCopys());
		this.copy = this.pub.getNextAvailableCopyLambda();
		if (this.copy == null){
			showWarningMsg("No extra copy found! Please try another one.");
			return false;
		}
		else{
			this.copy.setMemberID(memberID.getText());
			return true;
		}
	}
	@FXML void handleCheckOutBookAction(){
		if(!setMemberID()) return; // alert if member did not input or is not found
		if(bookISBN.getText().isEmpty()){
			emptyWarning(bookISBN.getText(), "book ISBN"); // alert if book did not input
		}else{
			setPublication(bookISBN.getText()); 
			if(isPubEmpty()) return; // alert if book is not found
			if(!setCopy()) return; // alert if copy is not found
			CheckOutAction(); // proceed check out
		}
	}
	@FXML void handleCheckOutPeriodicalAction(){
		if(!setMemberID()) return;
		if(periodicalTitle.getText().isEmpty() || periodicalIssueNumber.getText().isEmpty()){
			emptyWarning(periodicalTitle.getText() + periodicalIssueNumber.getText(), "periodical Title or Issue Number");
		}else{
			setPublication(periodicalTitle.getText(), periodicalIssueNumber.getText());
			if(isPubEmpty()) return;
			if(!setCopy()) return;
			CheckOutAction();
		}
	}
	public void initPanel() throws IOException{
		setHasInstance(true);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/checkOut.fxml"));
	    Parent root = loader.load();
	    checkoutStage.setTitle("CheckOut");
	    checkoutStage.setScene(new Scene(root, 600, 400));
	}
	public void showPanel(){
		checkoutStage.show();
		checkoutStage.toFront();
	}
	
	private void CheckOutAction(){
		String confirmMsg = member.getFirstName() + ":\nYou will checkout the book " + pub.getTitle() + ".\nPlease confirm!";
		int confirm = MessageBox.show(checkoutStage,
				 confirmMsg,
		         "Question dialog",
		         MessageBox.ICON_QUESTION | MessageBox.YES | MessageBox.NO | MessageBox.DEFAULT_BUTTON2);
		// proceed if confirmed
		if (confirm == MessageBox.YES){
			setLibraryMember(memberID.getText());
			if(saveCheckOutRecord()){
				MessageBox.show(checkoutStage,
						 "Check out successfully!",
				         "Success Infomation",
				         MessageBox.ICON_INFORMATION | MessageBox.OK | MessageBox.DEFAULT_BUTTON2);
			}
		}
	}
	
	public boolean saveCheckOutRecord(){
		// for Use Case 7, to check whether a book is overDue or not
		LocalDate dateDue = LocalDate.now().plus(this.pub.getMaxCheckoutLength(), ChronoUnit.DAYS);
		copy.setMemberID(memberID.getText());
		copy.setCheckOut(true);
		copy.getPublication().setDateDue(dateDue);
		
		// save data into stream
		this.member.checkout(copy, LocalDate.now(), dateDue);
		DataAccess da = new DataAccessFacade();
		da.updateMember(this.member);
		return true;
	}
	
	public boolean getHasInstance() {
		return hasInstance;
	}
	public void setHasInstance(boolean hasInstance) {
		FXMLCheckOutController.hasInstance = hasInstance;
	}
}
