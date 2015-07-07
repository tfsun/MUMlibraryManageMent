package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import model.Book;
import model.LendableCopy;
import model.LibraryMember;
import model.Periodical;
import javafx.fxml.FXML;
import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;
import dataAccess.DataAccessFacade.Pair;

public class FXMLMenuController {
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
	@FXML protected void handleMenuListByMember(){
		
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
			}
		}
		else{
			System.out.println("No memeber found!");
		}
	}
	@FXML protected void handleMenuAddPublication(){
		
	}
	@FXML protected void handleMenuAddCopy(){
		
	}
	@FXML protected void handleMenuSearchCopy(){
		
	}
	@FXML protected void handleMenuListCopy(){
		DataAccess daf = new DataAccessFacade();
		HashMap<String, Book> books = daf.readBooksMap();
		HashMap<Pair<String,String>,Periodical> periodicals = daf.readPeriodicalsMap();
		if (books != null){
			for (String key : books.keySet()) {
			    System.out.println(books.get(key).toString());
			}
		}
		else{
			System.out.println("No book found!");
		}
		if(periodicals != null){
			for (Pair<String, String> key : periodicals.keySet()) {
			    System.out.println(periodicals.get(key).toString());
			}
		}
		else{
			System.out.println("No periodical found!");
		}
	}
}
