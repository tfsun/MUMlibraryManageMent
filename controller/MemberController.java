package controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;
import model.Address;
import model.CheckOutRecord;
import model.CheckoutRecordEntry;
import model.LibraryMember;
import Services.UserService;

import java.time.LocalDate;
import java.util.List;

public class MemberController {

    private TableView<LibraryMember> table1 = new TableView<LibraryMember>();


    public void showAllMembers(){

        List<LibraryMember> members = new UserService().getAllMembers();

        final ObservableList<LibraryMember> libraryMembers =
                FXCollections.observableArrayList(
                        members
                );
        Stage stage=new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("List of Library Members");
        stage.setWidth(550);
        stage.setHeight(500);

        final Label label = new Label("Library Members");
        label.setFont(new Font("Arial", 20));

        table1.setEditable(true);

        TableColumn idCol = new TableColumn("Mem ID");
        idCol.setMinWidth(25);
        idCol.setCellValueFactory(
                new PropertyValueFactory<LibraryMember,Integer>("memberId")
        );
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<LibraryMember, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<LibraryMember, String>("lastName"));

        TableColumn phoneCol = new TableColumn("Phone");
        phoneCol.setMinWidth(200);
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<LibraryMember, String>("phone"));

        table1.setItems(libraryMembers);
        table1.getColumns().addAll(idCol,firstNameCol, lastNameCol, phoneCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table1);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }



	public void getInformation() {
        Stage stage = new Stage();
        stage.setTitle("Add Information");

        GridPane gridPane=new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));


        Label firstNameLabel=new Label("First Name");
        gridPane.add(firstNameLabel, 0, 0);

        TextField firstNameTextField=new TextField();
        firstNameTextField.setMaxWidth(300);
        gridPane.add(firstNameTextField, 1, 0);

        Label lastNameLabel=new Label("Last Name");
        gridPane.add(lastNameLabel, 0, 1);

        TextField lastNameTextField=new TextField();
        lastNameTextField.setMaxWidth(300);
        gridPane.add(lastNameTextField, 1, 1);

        Label phoneLabel=new Label("Phone Number");
        gridPane.add(phoneLabel, 0, 2);

        TextField phoneTextField=new TextField();
        phoneTextField.setMaxWidth(300);
        gridPane.add(phoneTextField, 1, 2);

        Label memberIdLabel=new Label("Member Id");
        gridPane.add(memberIdLabel, 0, 3);

        TextField memberIdTextField=new TextField();
        memberIdTextField.setMaxWidth(300);
        gridPane.add(memberIdTextField, 1, 3);

        GridPane addressGrid = getAddressGridPane();

        
        Label addressLabel=new Label("Address");
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressGrid, 1, 4);

        Button addButton=new Button();
        addButton.setAlignment(Pos.TOP_LEFT);
        addButton.setText("Add Member");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(addButton);
        gridPane.add(addButton, 0, 5, 2, 3);

        Text message=new Text();

        gridPane.add(message,1,6);

        addButton.setOnAction(event -> {


        	String firstName=firstNameTextField.getText();
        	String lastName= lastNameTextField.getText();
        	String phone = phoneTextField.getText();
            String id = memberIdTextField.getText();
            if(firstName.length() ==0 || lastName.length()==0 || phone.length() == 0 ||
                    id.length()==0){
                MessageBox.show(stage,
                        "All Input must have value!",
                        "Error",
                        MessageBox.ICON_INFORMATION | MessageBox.OK);
            }

            int memberId = 0;
            try {
                memberId = Integer.parseInt(id);

            }  catch (NumberFormatException e) {
                    MessageBox.show(stage,
                            "Member ID must be an integer!",
                            "Error",
                            MessageBox.ICON_INFORMATION | MessageBox.OK);
                }

            if( memberId ==0){
                MessageBox.show(stage,
                        "Member Id can't be zero!",
                        "Error",
                        MessageBox.ICON_INFORMATION | MessageBox.OK);
            }

            LibraryMember libraryMember = new LibraryMember();

            libraryMember.setFirstName(firstName);
            libraryMember.setLastName(lastName);
            libraryMember.setPhone(phone);
            libraryMember.setMemberId(memberId);


            libraryMember.setAddress(getAddress(addressGrid,stage));

            if(new UserService().addUser(libraryMember)){
                MessageBox.show(stage,
                        "Member is added to database!",
                        "Success",
                        MessageBox.ICON_INFORMATION | MessageBox.OK);
            }else {
                MessageBox.show(stage,
                        "Member with this ID is already exists",
                        "Error",
                        MessageBox.ICON_INFORMATION | MessageBox.OK);
            }


        });
        Scene scene=new Scene(gridPane);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../view/Login.css").toExternalForm());
        stage.show();
    }
	
	private GridPane getAddressGridPane(Address address) {
        GridPane addressGrid=new GridPane();
        addressGrid.setAlignment(Pos.CENTER);
        addressGrid.setHgap(10);
        addressGrid.setVgap(10);
        addressGrid.setPadding(new Insets(25, 25, 25, 25));


        Label streetLabel=new Label("Street");
        addressGrid.add(streetLabel, 0, 0);

        TextField streetTextField=new TextField(address.getStreet());
        streetTextField.setId("streetText");
        addressGrid.add(streetTextField,1,0);

        Label cityLabel=new Label("City");
        addressGrid.add(cityLabel, 2, 0);

        TextField cityTextField=new TextField(address.getCity());
        cityTextField.setId("cityText");
        addressGrid.add(cityTextField,3,0);

        Label stateLabel=new Label("State");
        addressGrid.add(stateLabel, 0, 1);

        TextField stateTextField=new TextField(address.getState());
        stateTextField.setId("stateText");
        addressGrid.add(stateTextField,1,1);

        Label zipLabel=new Label("Zip code");
        addressGrid.add(zipLabel, 2, 1);

        TextField zipTextField=new TextField(Integer.toString(address.getZip()));
        zipTextField.setId("zipText");
        addressGrid.add(zipTextField, 3, 1);

        return addressGrid;
    }

    private Address getAddress(GridPane addressGrid,Stage stage) {
    	Address address = new Address();
		TextField streetTextField=(TextField) addressGrid.lookup("#streetText");
        String street=streetTextField.getText();

		
		TextField cityTextField=(TextField) addressGrid.lookup("#cityText");
        String city=cityTextField.getText();

		
		TextField stateTextField=(TextField) addressGrid.lookup("#stateText");
        String state = stateTextField.getText();

		
		TextField zipTextField=(TextField) addressGrid.lookup("#zipText");

        String zip=zipTextField.getText();

        if(street.length()==0 || city.length() ==0 || state.length() ==0 || zip.length()==0){
            MessageBox.show(stage,
                    "All field must be filled!",
                    "Error",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
        }
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        try {
            address.setZip(Integer.parseInt(zip));
        } catch (NumberFormatException e) {
            MessageBox.show(stage,
                    "Zip code must be integer!",
                    "Error",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
        }

        return address;
	}


    private GridPane getAddressGridPane() {
        GridPane addressGrid=new GridPane();
        addressGrid.setAlignment(Pos.CENTER);
        addressGrid.setHgap(10);
        addressGrid.setVgap(10);
        addressGrid.setPadding(new Insets(25, 25, 25, 0));

        Label streetLabel=new Label("Street");
        addressGrid.add(streetLabel, 0, 0);

        TextField streetTextField=new TextField();
        streetTextField.setId("streetText");
        addressGrid.add(streetTextField, 1, 0);

        Label cityLabel=new Label("City");
        addressGrid.add(cityLabel, 2, 0);

        TextField cityTextField=new TextField();
        cityTextField.setId("cityText");
        addressGrid.add(cityTextField,3,0);

        Label stateLabel=new Label("State");
        addressGrid.add(stateLabel, 0, 1);

        TextField stateTextField=new TextField();
        stateTextField.setId("stateText");
        addressGrid.add(stateTextField, 1, 1);

        Label zipLabel=new Label("Zip code");
        addressGrid.add(zipLabel, 2, 1);

        TextField zipTextField=new TextField();
        zipTextField.setId("zipText");
        addressGrid.add(zipTextField, 3, 1);

        return addressGrid;
    }
    
    public void searchMember() {
        Stage searchStage=new Stage();
        searchStage.setTitle("Search Member");

        GridPane gridPane1=new GridPane();
        gridPane1.setAlignment(Pos.CENTER);
        gridPane1.setHgap(10);
        gridPane1.setVgap(10);
        gridPane1.setPadding(new Insets(25, 25, 25, 25));

        TextField searchTextField=new TextField();
        searchTextField.setPromptText("Enter user id");
        gridPane1.add(searchTextField, 0, 1);

        Button searchButton=new Button("Search");
        gridPane1.add(searchButton, 1, 1);

        Scene scene=new Scene(gridPane1);
        searchStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../view/Login.css").toExternalForm());
        searchStage.show();

        searchButton.setOnAction(event1 -> {
            String id = searchTextField.getText();
            int id1 = Integer.parseInt(id);
            LibraryMember libraryMember = new UserService().searchMember(id1);
                System.out.println("DEBUG: " + libraryMember.getRecord().toString());

            if (libraryMember != null) {
                showLibraryMember(libraryMember);

            } else {
                Label messageLabel = new Label("Data not found...");
//                messageLabel.setFont(Font.font("Tahoma"), FontWeight.BOLD,15);
                gridPane1.add(messageLabel, 0, 3);
            }

        });
    }

    private void editLibraryMember(LibraryMember libraryMember) {
        Stage showMember = new Stage();
        showMember.setTitle("User Information");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Label firstNameLabel = new Label("First Name");
        gridPane.add(firstNameLabel, 0, 0);

        TextField firstNameTextField = new TextField(libraryMember.getFirstName());
        gridPane.add(firstNameTextField, 1, 0);

        Label lastNameLabel = new Label("Last Name");
        gridPane.add(lastNameLabel, 0, 1);

        TextField lastNameTextField = new TextField(libraryMember.getLastName());
        gridPane.add(lastNameTextField, 1, 1);

        Label phoneLabel = new Label("Phone Number");
        gridPane.add(phoneLabel, 0, 2);

        TextField phoneTextField = new TextField(libraryMember.getPhone());
        gridPane.add(phoneTextField, 1, 2);

        Label memberIdLabel = new Label("Member Id");
        gridPane.add(memberIdLabel, 0, 3);

        TextField memberIdTextField = new TextField(Integer.toString(libraryMember.getMemberId()));
        memberIdTextField.setDisable(true);
        ;
        gridPane.add(memberIdTextField, 1, 3);

        GridPane addressGrid = getAddressGridPane(libraryMember.getAddress());


        Label addressLabel = new Label("Address");
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressGrid, 1, 4);

        Button editButton = new Button();
        editButton.setText("Save");
        gridPane.add(editButton, 1, 5);

        editButton.setOnAction(event -> {
            libraryMember.setFirstName(firstNameTextField.getText());
            libraryMember.setLastName(lastNameTextField.getText());
            libraryMember.setPhone(phoneTextField.getText());

            libraryMember.setAddress(getAddress(addressGrid, showMember));

            if (new UserService().addUser(libraryMember)) {
                MessageBox.show(showMember,
                        "Information Updated!",
                        "Success",
                        MessageBox.ICON_INFORMATION | MessageBox.OK);
            } else {
                MessageBox.show(showMember,
                        "Something went wrong!",
                        "Error",
                        MessageBox.ICON_INFORMATION | MessageBox.OK);
            }

        });
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        gridPane.add(cancelButton, 2, 5);

        cancelButton.setOnAction(event -> {
            showMember.close();
        });


        Scene scene=new Scene(gridPane);
        showMember.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../view/Login.css").toExternalForm());
        showMember.show();
    }

    private void showLibraryMember(LibraryMember libraryMember) {
		Stage showMember=new Stage();
		showMember.setTitle("User Information");
		
		GridPane gridPane=new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 75));

        Label nameLabel=new Label("Name");
        gridPane.add(nameLabel, 0, 0);

        Label nameText=new Label(libraryMember.getFirstName()+" "+libraryMember.getLastName());
        gridPane.add(nameText,1,0);

        Label phoneLabel=new Label("Phone Number");
        gridPane.add(phoneLabel, 0, 1);

        Label phoneText=new Label(libraryMember.getPhone());
        gridPane.add(phoneText,1,1);

        Label memberIdLabel=new Label("Member Id");
        gridPane.add(memberIdLabel, 0, 2);

        Label memberIdText=new Label(Integer.toString(libraryMember.getMemberId()));
        gridPane.add(memberIdText, 1, 2);

        Address address= libraryMember.getAddress();

        
        Label addressLabel=new Label("Address");
        gridPane.add(addressLabel, 0, 3);

        Label addressLine1=new Label(address.getStreet()+" "+address.getCity());
        gridPane.add(addressLine1, 1, 3);

        Label addressLine2 = new Label(address.getState()+", "+address.getZip());
        gridPane.add(addressLine2,1,4);

        Button editButton=new Button();
        editButton.setText("Edit");
        gridPane.add(editButton, 1, 5);
        

        final Label label=new Label("Books CheckOut");
        TableView table;
        label.setFont(new Font("Arial", 20));

        table=getTable(libraryMember.getRecord());
        table.setEditable(true);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        Scene scene=new Scene(gridPane);
//        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        gridPane.add(table, 0, 7, 3, 1);

        editButton.setOnAction(event -> {
            editLibraryMember(libraryMember);
        });
//        Scene scene=new Scene(gridPane);
        showMember.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../view/Login.css").toExternalForm());
        showMember.show();
		
	}

    private TableView getTable(CheckOutRecord record){
        List<CheckoutRecordEntry> entries = record.getEntry();
        final TableView<CheckoutRecordEntry> table1=new TableView<>();
//        List<LibraryMember> members = new UserService().getAllMembers();

        final ObservableList<CheckoutRecordEntry> recordEntries =
                FXCollections.observableArrayList(
                        entries
                );

        table1.setEditable(true);

        System.out.println(entries.get(0).getCheckoutDate()+" Fuck "+entries.get(0).getDueDate());
        TableColumn idCol = new TableColumn("Checkout Date");
        idCol.setMinWidth(25);
        idCol.setCellValueFactory(
                new PropertyValueFactory<CheckoutRecordEntry, LocalDate>("checkoutDate")
        );
        TableColumn firstNameCol = new TableColumn("Due Date");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<CheckoutRecordEntry, LocalDate>("dueDate"));

/*        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<LibraryMember, String>("lastName"));

        TableColumn phoneCol = new TableColumn("Phone");
        phoneCol.setMinWidth(200);
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<LibraryMember, String>("phone"));*/

        table1.setItems(recordEntries);
        table1.getColumns().addAll(idCol, firstNameCol);

        return table1;
/*        Stage stage=new Stage();
        Scene scene = new Scene(new Group());

        final Label label=new Label("Entries");
        TableView<CheckoutRecordEntry> table = new TableView<CheckoutRecordEntry>();
//        CheckOutRecord records = new LibraryMember().getRecord();

//        System.out.println("yogen"+record.toString());

        List<CheckoutRecordEntry> entries=record.getEntry();
//        System.out.println("Record 1: "+entries.get(0).toString());

        final ObservableList<CheckoutRecordEntry> recordEntries =
                FXCollections.observableArrayList(
                        entries
                );

        TableColumn checkOutDateCol = new TableColumn("Check Out Date");
        checkOutDateCol.setMinWidth(200);
        checkOutDateCol.setCellValueFactory(
                new PropertyValueFactory<CheckoutRecordEntry, LocaleData>("checkoutDate"));

        TableColumn dueDateColn = new TableColumn("Due Date");
        dueDateColn.setMinWidth(200);
        dueDateColn.setCellValueFactory(
                new PropertyValueFactory<CheckoutRecordEntry, LocaleData>("dueDate"));

       *//* TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email"));*//*

        table.setItems(recordEntries);
        table.getColumns().addAll(checkOutDateCol, dueDateColn);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);


        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

        return table;*/
//        final VBox vbox = new VBox();
//        vbox.setSpacing(5);
//        vbox.setPadding(new Insets(10, 0, 0, 10));
//        vbox.getChildren().addAll(label, table);
//
//        ((Group) scene.getRoot()).getChildren().addAll(vbox);

    }

}
