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

import Services.BookService;
import Services.PeriodicalService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;
import model.Author;
import model.Book;
import model.Periodical;
import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;
import dataAccess.StorageType;
 
public class PublicationController extends BaseController{
	@FXML private TextField ID;
    @FXML private TextField ISBN;
    @FXML private TextField title;
    @FXML private TextField maxCheckoutLength;
    @FXML private Label LbID;
    @FXML private Label LbISBN;
    @FXML private Button BtnAddAuthor;
    
    @FXML private CheckBox AddBook;
    @FXML private CheckBox AddPeriodical;
    
	static private List<Author> Authors = new ArrayList<Author>();

    //private Publication.PUBTYPE pubtype = Publication.PUBTYPE.BOOK;
    private StorageType storageType =  StorageType.BOOK;

    static private PublicationController instance=null;
    static public PublicationController getInstance() {
    	if (null==instance) {
			instance = new PublicationController();
		}
    	return instance;
    }
    
    @FXML protected void GePublicationData(ActionEvent event) {
    	switch (storageType) {
		case BOOK:
			saveNewBook(event);
			//return dataAccess.saveNewBook(book);
			break;
		case PERIODICAL:
			savePeriodical(event);
		default:
			break;
		}

    }
    
    @FXML private boolean savePeriodical(ActionEvent event) {
    	boolean bRet = true;
    	try {
	    	//String IssueNumber =  ID.getText();
	    	int intIssueNo = Integer.valueOf(ID.getText());
	    	String strtitle =  title.getText();
         	if (strtitle.length()<1) {
            	MessageBox.show(stage,
            		    "title must have value!",
            		    "Error", 
            		    MessageBox.ICON_INFORMATION | MessageBox.OK);
    			return false;
    		}
	    	int nmaxCheckoutLength =  Integer.valueOf(maxCheckoutLength.getText());	
	    	Periodical periodical = new Periodical(intIssueNo,strtitle,nmaxCheckoutLength);
	    	periodical.addCopy();
	    	//AuthorController.ResetCurAuthors();

			bRet = new PeriodicalService().saveNewPeriodical(periodical);
    	}
    	catch (NumberFormatException e) {
        	MessageBox.show(stage,
		    "IssueNO must be number!",
		    "Error", 
		    MessageBox.ICON_INFORMATION | MessageBox.OK);
		}
    	if (bRet) {
        	MessageBox.show(stage,
		    "Add periodical success!",
		    "Success", 
		    MessageBox.ICON_INFORMATION | MessageBox.OK);
        	Close(event);
		}
    	return bRet;		
    }
    
    private boolean saveNewBook(ActionEvent event) {
    	try {
        	int nID =  Integer.valueOf(ID.getText());
        	String strISBN =  ISBN.getText();
        	String strtitle =  title.getText();
         	if (strISBN.length()<1 || strtitle.length()<1) {
            	MessageBox.show(stage,
            		    "ISBN and title must have value!",
            		    "Error", 
            		    MessageBox.ICON_INFORMATION | MessageBox.OK);
    			return false;
    		}
        	int nmaxCheckoutLength =  Integer.valueOf(maxCheckoutLength.getText());	
        	//List<Author> authors = new ArrayList<Author>(AuthorController.getCurAuthors());
        	if (Authors.size()<1) {
            	MessageBox.show(stage,
            		    "Add author first!",
            		    "Error", 
            		    MessageBox.ICON_INFORMATION | MessageBox.OK);
    			return false;
    		}
        	Book book = new Book(nID,strISBN,strtitle,nmaxCheckoutLength);
        	book.setAuthors(Authors);
        	book.addCopy();
        	//AuthorController.ResetCurAuthors();

    		boolean bRet = new BookService().saveNewBook(book);
    		if (bRet == true) {
            	MessageBox.show(stage,
	    		    "Add Book Success!",
	    		    "Congrations!", 
	    		    MessageBox.ICON_INFORMATION | MessageBox.OK);
			}
    		else {
            	MessageBox.show(stage,
	    		    "Add Book Failed!",
	    		    "Failed!", 
	    		    MessageBox.ICON_INFORMATION | MessageBox.OK);
			}
		} 
    	catch (NumberFormatException e) {
        	MessageBox.show(stage,
		    "ID and checkout must be number!",
		    "Error", 
		    MessageBox.ICON_INFORMATION | MessageBox.OK);
		}
    	Close(event);
    	return true;
    }
    
    @FXML protected void openAuthorUI(ActionEvent event) {
    	AuthorController.getInstance().openAuthorUI(event,this);
    }
    
    @FXML protected void setBookType(ActionEvent event) {
    	storageType =  StorageType.BOOK;
    	AddPeriodical.setSelected(false);
    	LbID.setText("ID");
    	ISBN.setVisible(true);
    	LbISBN.setVisible(true);
    	BtnAddAuthor.setDisable(false);
    }
    
    @FXML protected void SelectPeriodicalType(ActionEvent event) {
    	storageType =  StorageType.PERIODICAL;
    	LbID.setText("IssueNo");
    	AddBook.setSelected(false);
    	ISBN.setVisible(false);
    	LbISBN.setVisible(false);
    	BtnAddAuthor.setDisable(true);
    }
    
	@FXML protected void Close(ActionEvent event) {
		Node  source = (Node)  event.getSource(); 
		Stage stage  = (Stage) source.getScene().getWindow();
		stage.close();
    }
    
    public void openPublciationUI(ActionEvent event) {
    	if (stage!=null && stage.isShowing()) {
    		System.out.println("Already open the Publciation UI!");
    		stage.toFront();
    		return;
		}
    	//stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/Publication.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("AddPublication");
            stage.setScene(scene);
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(
//                ((Node)event.getSource()).getScene().getWindow() );    
            stage.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public boolean addAuthor(Author author) {
    	Authors.add(author);
    	return true;
    }
}
