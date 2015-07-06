package view;


import java.awt.Event;

import controller.CopyController;
import controller.PublciationController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import model.Address;
import model.LibraryMember;
import Services.UserService;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");


        GridPane grid=new GridPane();

        Button addNewMemberButton=new Button("Add Member");
        grid.add(addNewMemberButton, 0, 1);
        
        Button searchMemberButton=new Button("Search Member");
        grid.add(searchMemberButton, 1, 1);

        addNewMemberButton.setOnAction(event -> {
            getInformation();
        });
        
        searchMemberButton.setOnAction(event->{
            searchMember();
        	
        });
        
        Button addPubButton=new Button("Add Publication");
        grid.add(addPubButton, 2, 1);
        addPubButton.setOnAction(event -> {
            openPubUI(event);
        });
        
        Button addCopyButton=new Button("Add Copy");
        grid.add(addCopyButton, 3, 1);
        addCopyButton.setOnAction(event -> {
            openCopyUI(event);
        });

        primaryStage.setScene(new Scene(grid, 500, 275));

        primaryStage.show();

    }

    private TableView getTable(){


        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(200);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(200);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email"));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        return table;
//        final VBox vbox = new VBox();
//        vbox.setSpacing(5);
//        vbox.setPadding(new Insets(10, 0, 0, 10));
//        vbox.getChildren().addAll(label, table);
//
//        ((Group) scene.getRoot()).getChildren().addAll(vbox);

    }

    private void searchMember() {
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
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        searchStage.show();

        searchButton.setOnAction(event1->{
            String id=searchTextField.getText();
            LibraryMember libraryMember=new UserService().searchMember(id);
            if(libraryMember != null){
                showLibraryMember(libraryMember);

            }
            else {
                Label messageLabel=new Label("Data not found...");
//                messageLabel.setFont(Font.font("Tahoma"), FontWeight.BOLD,15);
                gridPane1.add(messageLabel,0,3);
            }

        });
    }


    private void showLibraryMember(LibraryMember libraryMember) {
		Stage showMember=new Stage();
		showMember.setTitle("User Information");
		
		GridPane gridPane=new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Label firstNameLabel=new Label("First Name");
        gridPane.add(firstNameLabel, 0, 0);

        TextField firstNameTextField=new TextField(libraryMember.getFirstName());
        gridPane.add(firstNameTextField,1,0);

        Label lastNameLabel=new Label("Last Name");
        gridPane.add(lastNameLabel, 0, 1);

        TextField lastNameTextField=new TextField(libraryMember.getLastName());
        gridPane.add(lastNameTextField,1,1);

        Label phoneLabel=new Label("Phone Number");
        gridPane.add(phoneLabel, 0, 2);

        TextField phoneTextField=new TextField(libraryMember.getPhone());
        gridPane.add(phoneTextField,1,2);

        Label memberIdLabel=new Label("Member Id");
        gridPane.add(memberIdLabel, 0, 3);

        TextField memberIdTextField=new TextField(libraryMember.getMemberId());
        memberIdTextField.setDisable(true);;
        gridPane.add(memberIdTextField, 1, 3);

        GridPane addressGrid = getAddressGridPane(libraryMember.getAddress());

        
        Label addressLabel=new Label("Address");
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressGrid, 1, 4);

        Button editButton=new Button();
        editButton.setText("Edit");
        gridPane.add(editButton, 1, 5);
        
        Button cancelButton=new Button();
        cancelButton.setText("Cancel");
        gridPane.add(cancelButton, 2, 5);
        
        Text message=new Text();
        gridPane.add(message, 1, 6);

        final Label label=new Label("Books CheckOut");
        TableView table;
        label.setFont(new Font("Arial", 20));

        table=getTable();
        table.setEditable(true);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        Scene scene=new Scene(gridPane);
//        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        gridPane.add(table,0,7,3,1);

        cancelButton.setOnAction(event -> {
//        	TableViewSample.main(new String[]{});
            showMember.close();
        });
        editButton.setOnAction(event -> {
            libraryMember.setFirstName(firstNameTextField.getText());
            libraryMember.setLastName(lastNameTextField.getText());
            libraryMember.setPhone(phoneTextField.getText());

            libraryMember.setAddress(getAddress(addressGrid));

            new UserService().addUser(libraryMember);
            message.setText("Edited..:)");
        });
//        Scene scene=new Scene(gridPane);
        showMember.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        showMember.show();
		
	}


	private void getInformation() {
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

            LibraryMember libraryMember = new LibraryMember();

            libraryMember.setFirstName(firstNameTextField.getText());
            libraryMember.setLastName(lastNameTextField.getText());
            libraryMember.setPhone(phoneTextField.getText());
            libraryMember.setMemberId(memberIdTextField.getText());


            libraryMember.setAddress(getAddress(addressGrid));

            new UserService().addUser(libraryMember);
            message.setText("Added..:)");


        });
        Scene scene=new Scene(gridPane);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        stage.show();
    }
    private GridPane getAddressGridPane(Address address) {
        GridPane addressGrid=new GridPane();
        addressGrid.setAlignment(Pos.CENTER);
        addressGrid.setHgap(10);
        addressGrid.setVgap(10);
        addressGrid.setPadding(new Insets(25,25,25,25));


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

        TextField zipTextField=new TextField(address.getZip());
        zipTextField.setId("zipText");
        addressGrid.add(zipTextField, 3, 1);

        return addressGrid;
    }

    private Address getAddress(GridPane addressGrid) {
    	Address address = new Address();
		TextField streetTextField=(TextField) addressGrid.lookup("#streetText");
		address.setStreet(streetTextField.getText());
		
		TextField cityTextField=(TextField) addressGrid.lookup("#cityText");
		address.setCity(cityTextField.getText());
		
		TextField stateTextField=(TextField) addressGrid.lookup("#stateText");
		address.setState(stateTextField.getText());
		
		TextField zipTextField=(TextField) addressGrid.lookup("#zipText");
		address.setZip(zipTextField.getText());
		
		return address;
	}


    private GridPane getAddressGridPane() {
        GridPane addressGrid=new GridPane();
        addressGrid.setAlignment(Pos.CENTER);
        addressGrid.setHgap(10);
        addressGrid.setVgap(10);
        addressGrid.setPadding(new Insets(25,25,25,0));

        Label streetLabel=new Label("Street");
        addressGrid.add(streetLabel, 0, 0);

        TextField streetTextField=new TextField();
        streetTextField.setId("streetText");
        addressGrid.add(streetTextField,1,0);

        Label cityLabel=new Label("City");
        addressGrid.add(cityLabel, 2, 0);

        TextField cityTextField=new TextField();
        cityTextField.setId("cityText");
        addressGrid.add(cityTextField,3,0);

        Label stateLabel=new Label("State");
        addressGrid.add(stateLabel, 0, 1);

        TextField stateTextField=new TextField();
        stateTextField.setId("stateText");
        addressGrid.add(stateTextField,1,1);

        Label zipLabel=new Label("Zip code");
        addressGrid.add(zipLabel, 2, 1);

        TextField zipTextField=new TextField();
        zipTextField.setId("zipText");
        addressGrid.add(zipTextField, 3, 1);

        return addressGrid;
    }


    public static void main(String[] args) {
        launch(args);
    }


    private TableView<Person> table = new TableView<Person>();
    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("Jacob", "Smith", "jacob.smith@example.com"),
                    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
                    new Person("Ethan", "Williams", "ethan.williams@example.com"),
                    new Person("Emma", "Jones", "emma.jones@example.com"),
                    new Person("Michael", "Brown", "michael.brown@example.com")
            );

    public static class Person {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;

        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }
    }
    
	private void openPubUI(ActionEvent event) {
		PublciationController pubController = new PublciationController();
		pubController.openPublciationUI(event);
	}
	
	private void openCopyUI(ActionEvent event) {
		CopyController copyController = new CopyController();
		copyController.openCopyUI(event);
	}
	
}
