package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLCheckCopyController implements FXMLController{
	protected static boolean hasInstance = false;
	public static Stage checkCopyStage = new Stage();

	public void initPanel() throws IOException{
		setHasInstance(true);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("checkCopy.fxml"));
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
}
