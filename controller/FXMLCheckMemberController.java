package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;
import model.Book;
import model.CheckoutRecord;
import model.LendableCopy;
import model.LibraryMember;
import model.Periodical;
import dataAccess.DataAccessFacade;
import dataAccess.DataAccessFacade.Pair;

public class FXMLCheckMemberController implements FXMLController{
	protected static boolean hasInstance = false;
	public static Stage checkMemberStage = new Stage();
	private String memberID;
	private LibraryMember member = null;
	@FXML private TextField FXMLMemberID;
	@FXML private Text FXMLMemberDetail = null;
	@FXML private Button FXMLRecordButton;
	@FXML void handleSearchMemberAction(){
		if(!setMemberID()) return;
		
		if (this.member != null){
			this.FXMLMemberDetail.setText(member.printDetail());
			this.FXMLRecordButton.setDisable(false);
		}
		else{
			System.out.println("No member found!");
		}
	}
	
	@FXML void handleCheckoutRecordAction(){
		CheckoutRecord record = this.member.getRecord();
		CheckoutRecord.printEntries(record.getEntry());
	}
	
	public void initPanel() throws IOException{
		setHasInstance(true);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/checkMember.fxml"));
	    Parent root = loader.load();
	    checkMemberStage.setTitle("Check Member");
	    checkMemberStage.setScene(new Scene(root, 600, 400));
	}
	public void showPanel(){
		checkMemberStage.show();
		checkMemberStage.toFront();
	}
	public boolean getHasInstance() {
		return hasInstance;
	}
	public void setHasInstance(boolean hasInstance) {
		FXMLCheckMemberController.hasInstance = hasInstance;
	}
	void showWarningMsg(String msg){
		MessageBox.show(checkMemberStage,
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
}
