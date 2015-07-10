package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import model.Book;
import model.CheckoutRecord;
import model.CheckoutRecordEntry;
import model.LendableCopy;
import model.LibraryMember;
import model.Periodical;
import model.Publication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;
import dataAccess.DataAccessFacade.Pair;

public class FXMLMenuController {
	
	Stage superStage = null;
	@FXML protected void handleMenuCheckout() throws IOException{
		FXMLController checkoutController = new FXMLCheckOutController();
		if (!FXMLCheckOutController.hasInstance){
			checkoutController.initPanel();
			checkoutController.showPanel();
		}
		else{
			checkoutController.showPanel();
		}
	}
	@FXML protected void handleMenuListByMember() throws IOException{
		FXMLController checkMemberController = new FXMLCheckMemberController();
		if (!FXMLCheckMemberController.hasInstance){
			checkMemberController.initPanel();
			checkMemberController.showPanel();
		}
		else{
			checkMemberController.showPanel();
		}		
	}
	@FXML protected void handleMenuCheckCopy() throws IOException{
		FXMLController checkCopyController = new FXMLCheckCopyController();
		if (!FXMLCheckCopyController.hasInstance){
			checkCopyController.initPanel();
			checkCopyController.showPanel();
		}
		else{
			checkCopyController.showPanel();
		}
	}
	@FXML protected void handleMenuReturn() throws IOException{
		FXMLController returnController = new FXMLReturnController();
		if (!FXMLReturnController.hasInstance){
			returnController.initPanel();
			returnController.showPanel();
		}
		else{
			returnController.showPanel();
		}
	}
	@FXML protected void handleMenuAddMember(){
		
	}
	@FXML protected void handleMenuSearchMember(){
		
	}
	@FXML protected void handleMenuListMember(){
		DataAccess daf = new DataAccessFacade();
		HashMap<String, LibraryMember> mems = daf.readMemberMap();
		if (mems != null) {
			for (String key : mems.keySet()) {
			    System.out.println(mems.get(key).toString());
				CheckoutRecord record = mems.get(key).getRecord();
				CheckoutRecord.printEntries(record.getEntry());
			}
		}
		else{
			System.out.println("No memeber found!");
		}
	}
	@FXML protected void handleMenuAddPublication(ActionEvent event){
		PublciationController.getInstance().openPublciationUI(event);
	}
	@FXML protected void handleMenuAddCopy(ActionEvent event){
		CopyController.getInstance().openCopyUI(event);
	}

	private void printCopys(List<LendableCopy> Copys){
		for (LendableCopy copy: Copys){
			System.out.println(copy.toString());
		}
	}
	@FXML protected void handleMenuListCopy(){
		DataAccessFacade daf = new DataAccessFacade();
		HashMap<String, Book> books = daf.readBooksMap();
		HashMap<Pair<String,String>,Periodical> periodicals = daf.readPeriodicalsMap();
		if (books != null){
			for (String key : books.keySet()) {
				printCopys(books.get(key).getCopys());
			}
		}
		else{
			System.out.println("No book copy found!");
		}
		if(periodicals != null){
			for (Pair<String, String> key : periodicals.keySet()) {
				printCopys(periodicals.get(key).getCopys());
			}
		}
		else{
			System.out.println("No periodical found!");
		}
	}
}
