package controller;

import javafx.stage.Stage;

public abstract class BaseController {
	protected Stage  stage = new Stage();

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
}
