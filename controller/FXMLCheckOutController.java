package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import Services.UserService;
import model.Book;
import model.CheckOutRecord;
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
import javafx.scene.control.Dialog;
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
	@FXML void handleCheckOutBookAction(){
		if(!setMemberID()) return;
		if(bookISBN.getText().isEmpty()){
			emptyWarning(bookISBN.getText(), "book ISBN");
		}else{
			setPublication(bookISBN.getText());
			if(isPubEmpty()) return;
			CheckOutAction();
		}
	}
	@FXML void handleCheckOutPeriodicalAction(){
		if(setMemberID()) return;
		if(periodicalTitle.getText().isEmpty() || periodicalIssueNumber.getText().isEmpty()){
			emptyWarning(periodicalTitle.getText() + periodicalIssueNumber.getText(), "periodical Title or Issue Number");
		}else{
			setPublication(periodicalIssueNumber.getText(), periodicalTitle.getText());
			if(isPubEmpty()) return;
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
		MessageBox.show(checkoutStage,
				 confirmMsg,
		         "Question dialog",
		         MessageBox.ICON_QUESTION | MessageBox.YES | MessageBox.NO | MessageBox.DEFAULT_BUTTON2);
		setLibraryMember(memberID.getText());
		saveCheckOutRecord();
	}
	
	public void saveCheckOutRecord(){
		LendableCopy copy = new LendableCopy(this.pub);
		//copy.setPublication(this.pub);
		//copy.setCopyId(1);
		
		this.member.checkout(copy, LocalDate.now(), LocalDate.now().plus(this.pub.getMaxCheckoutLength(), ChronoUnit.DAYS));
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(this.member);
	}
	public boolean getHasInstance() {
		return hasInstance;
	}
	public void setHasInstance(boolean hasInstance) {
		FXMLCheckOutController.hasInstance = hasInstance;
	}
}
