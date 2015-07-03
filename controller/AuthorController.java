/*
 * Copyright (c) 2011, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package controller;
 
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Author;
 
public class AuthorController extends BaseController{
	
	static private List<Author> curAuthors = new ArrayList<Author>();
//	private Stage stage = new Stage();
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField credential;
    @FXML private TextField phone;
    
    static private AuthorController instance=null;   
    static public AuthorController getInstance() {
    	if (null==instance) {
			instance = new AuthorController();
		}
    	return instance;
    }
    
    //	why Singleton Conflict with getclass()?
//    private AuthorController() {
//    	
//    }
    
    public static List<Author> getCurAuthors() {
		return curAuthors;
	}

	public static void ResetCurAuthors() {
		AuthorController.curAuthors.clear();
	}

    public void openAuthorUI(Stage SuperStage) {
    	if (stage!=null && stage.isShowing()) {
			//stage.close();
    		System.out.println("Already open the author UI!");
    		return;
			//stage = null;
		}
    	//stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("./Author.fxml"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(SuperStage);
            Scene scene = new Scene(root);
            stage.setTitle("Author");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	@FXML protected void GetAuthorData(ActionEvent event) {
    	String strfirstName =  firstName.getText();
    	String strlastName =  lastName.getText();	
    	String strcredential =  credential.getText();
    	int intphone =  Integer.valueOf(phone.getText());	
    	curAuthors.add(new Author(strfirstName,strlastName,strcredential,intphone));
    	System.out.println(strfirstName+strlastName+strcredential+intphone);
    	//stage.close();
    	Close(event);
    }
   
	@FXML protected void Close(ActionEvent event) {
		Node  source = (Node)  event.getSource(); 
		Stage stage  = (Stage) source.getScene().getWindow();
		stage.close();
    }
    private boolean CheckData(String strData) {
    	if (strData.length()<1) {
    		System.out.println("Please check input data!");
    		return false;
		}
    	
    	return true;
    }
}
