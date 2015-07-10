package controller;

import java.io.IOException;

import javafx.stage.Stage;

public interface FXMLController {
	public void initPanel() throws IOException;
	public void showPanel();
	public boolean getHasInstance();
	public void setHasInstance(boolean hasInstance);
}
