package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import Services.BookService;
import Services.PeriodicalService;
import model.Book;
import model.LendableCopy;
import model.Periodical;
import dataAccess.DataAccessFacade;
import dataAccess.DataAccessFacade.Pair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;

public class FXMLCheckCopyController implements FXMLController{
	protected static boolean hasInstance = false;
	public static Stage checkCopyStage = new Stage();
	private String copyID;
	@FXML private TextField FXMLCopyNo;
	@FXML private Text FXMLCopyDetail;
	@FXML void handleCheckCopyAction(ActionEvent evt){
		if(!setCopyNo()) return;

		if (this.copyID.startsWith("Book")){
			HashMap<String, Book> books = new BookService().readBooksMap();
			if (books != null){
				for (String key : books.keySet()) {
					setCopyDetail(books.get(key).getCopys());
				}
				if (this.FXMLCopyDetail == null){
					showWarningMsg("Book: CopyNo not found, please try again!");
				}
			}
			else{
				System.out.println("No book copy found!");
			}
		}
		if (this.copyID.startsWith("Periodical")){
			HashMap<Pair<String,String>,Periodical> periodicals = new PeriodicalService().readPeriodicalsMap();
			if (periodicals != null){
				for (Pair<String, String> key : periodicals.keySet()) {
					setCopyDetail(periodicals.get(key).getCopys());
				}
				if (this.FXMLCopyDetail == null){
					showWarningMsg("Periodical: CopyNo not found, please try again!");
				}
			}
			else{
				System.out.println("No periodical found!");
			}
		}
	}
	public void initPanel() throws IOException{
		setHasInstance(true);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/checkCopy.fxml"));
	    Parent root = loader.load();
	    checkCopyStage.setTitle("Check Copy");
	    checkCopyStage.setScene(new Scene(root, 600, 400));
	}
	public void showPanel(){
		checkCopyStage.show();
		checkCopyStage.toFront();
	}
	public boolean getHasInstance() {
		return hasInstance;
	}
	public void setHasInstance(boolean hasInstance) {
		FXMLCheckCopyController.hasInstance = hasInstance;
	}
	void showWarningMsg(String msg){
		MessageBox.show(checkCopyStage,
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
	// Use Case 7, we need add check in logic for the overdue checking
	public boolean isOverdue(LendableCopy copy){
		return false;
		//if (copy.getPublication().getDateDue().isAfter(LocalDate.now()) && !copy.isCheckOut()){
		//	return true;
		//}
		//return false;
	}
	private boolean setCopyDetail(List<LendableCopy> Copys){
		for (LendableCopy copy: Copys){
			if (this.copyID.equals(copy.getCopyNo())){
				//System.out.println(copy.toString());
				String overDue = null;
				if (isOverdue(copy)) {
					overDue = "Overdue! Please return soon.\n";
				}
				else{
					overDue = "Copy details:\n";
				}
				this.FXMLCopyDetail.setText(overDue + copy.checkoutDetail(copy));
				return true;
			}
		}
		return false;
	}
	

}
