import java.util.*;
//access to standard Java utility classes and data structures and collections like Map
import javafx.application.Application;
//provides methods for setting up user interface (UI) and starting the application
import javafx.collections.FXCollections;
//helps create and manage observable lists
import javafx.collections.ObservableList;//observable lists
//they automatically update when the data changes
//dynamic collections that can be bound to UI components like tables
import javafx.scene.Scene;
//to create a scene to display the user interface of the application
import javafx.scene.control.*;
//includes various GUI controls- buttons, labels, text fields, and tables
import javafx.scene.layout.*;
//help organize and position GUI elements within a window
import javafx.stage.Stage;
//to set up and display main window0
import javafx.beans.property.SimpleStringProperty;
//used to create observable properties
import javafx.beans.property.StringProperty;
//abstract superclass of SimpleStringProperty
import java.io.BufferedReader;
//for reading text from an input stream efficiently
//by buffering characters into large blocks
import java.io.FileReader;
//for reading character files
import java.io.FileWriter;
//writing character files
import java.io.IOException;
// for exceptions thrown when an I/O operation fails
import javafx.geometry.Insets;
//to define the padding around GUI elements
import javafx.geometry.Pos;
//to set the alignment of UI elements in the layout

public class ContactManager extends Application {
    //==========================INITIALIZE===============================================
    private static final String CONTACTS_FILE = "C:\\Users\\MITALEEE\\Desktop\\contact.csv";
    //finalised path to the file where the contacts will be saved
    private TreeMap<String, Contact> contacts = new TreeMap<>();
    //TreeMap that maps strings (names) to Contact objects---each contact is associated with a unique name and sorted by name
    private TableView<Contact> contactTable = new TableView<>();
    //GUI component-table to display the list of contacts
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();
    //ObservableList- notifies any linked JavaFX UI controls when changes are made to the list
    //if you add, remove, or modify elements in the list, any UI components that are bound to this list will automatically reflect those changes
    //ObservableList is used to store a collection of Contact objects. Then, it's set as the data source for the contactTable TableView
    private TextField nameField = new TextField();
    private TextField phoneField = new TextField();
    private TextField emailField = new TextField();
    private TextField searchField = new TextField();
    //text fields to enter contact's name, phone number, email and search field
    //============================================= SAVE CONTACTS ===========================================================================================
    private void saveContacts() throws IOException {
        //IOException-error/interruption while performing input/output operations
        //eg. trying to read from a file that doesn't exist, to write to a file that is read-only, running out of disk space while writing to a file
        FileWriter writer = new FileWriter(CONTACTS_FILE);// initializes a new instance of FileWriter- to write character files
        for (Map.Entry<String , Contact> entry : contacts.entrySet()) {//for each key-value pair in the set of mappings in the map
            //(each element in this set is a Map.Entry object, which is a key-value pair)
            Contact contact = entry.getValue();//retrieve Contact object from the current entry in the map
            writer.write(contact.getName() + "," + contact.getPhone() + "," + contact.getEmail() + "\n");//construct comma-separated string
        }
        writer.close();
    }
    //============================================= LOAD CONTACTS ===========================================================================================
    private void loadContacts() throws IOException {
        //BufferedReader is a class used to read text from a character-input stream
        //reads in large chunks rather than character by character hence more efficient
        BufferedReader reader = new BufferedReader(new FileReader(CONTACTS_FILE));//FileReader reads data from the character file
        String line;//used to hold each line of text read from file
        while ((line = reader.readLine()) != null) {//reading one line at a time, as long as there are any
            String[] parts = line.split(",");//each comma separated line split into string array [name,phone,email]
            String name = parts[0];//name
            String phone = parts[1];//phone
            String email = parts[2];//email from parts array is extracted and
            Contact contact = new Contact(name, phone, email);//new contact is created and
            contacts.put(name, contact);//is put into the treemap with name as the key
        }
        reader.close();
        //update the displayed contacts in the contactTable
        contactList.setAll(contacts.values());//sets the content of contactList to the contacts in the treemap
    }
    //============================================= MAIN METHOD ===========================================================================================
    public static void main(String[] args) {
        launch(args);//method in JavaFX Application class
        //starts the JavaFX application
    }
    //============================================= START METHOD ===========================================================================================
    @Override
    public void start(Stage primaryStage) {//default stage
        //---------FILE HANDLING----------LOAD SAVED CONTACTS AS IT OPENS
        try {
            //----------------------load contacts from CSV
            loadContacts();
        } catch (IOException e) {
            showError("Error encountered while performing input/output operations");
        }
        //---------FILE HANDLING----------SAVE NEW CONTACTS AS IT CLOSES
        primaryStage.setOnCloseRequest(event -> {
            try {
                //-------------------save contacts to CSV
                saveContacts();
            } catch (IOException e) {
                showError("Error encountered while performing input/output operations");
            }
        });
        primaryStage.setTitle("Contact Manager");//set title for app
        setupUI(primaryStage);//set up UI
        setupButtonActions(primaryStage);//set up button actions
        primaryStage.show();//to show the frame/window
    }
    //============================================= SET UP UI METHOD ===========================================================================================
    private void setupUI(Stage stage) {
        //-------------------create columns for name, phone, and email-----------
        //--------using StringProperty that bind Table View and Observable Lists
        TableColumn<Contact, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(param -> param.getValue().nameProperty());
        // nameProperty-special property-allows value in the cell to be automatically updated if name of contact changes
        TableColumn<Contact, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(param -> param.getValue().phoneProperty());
        TableColumn<Contact, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(param -> param.getValue().emailProperty());
        contactTable.getColumns().addAll(nameCol, phoneCol, emailCol);//add the columns to the table
        contactTable.setItems (contactList);
        //-------------------style the table----------------------------------------------
        nameCol.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;");
        phoneCol.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;");
        emailCol.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;");
        //-------------------set column width using percentage----------------------------
        nameCol.prefWidthProperty().bind(contactTable.widthProperty().multiply(0.35));
        phoneCol.prefWidthProperty().bind(contactTable.widthProperty().multiply(0.20));
        emailCol.prefWidthProperty().bind(contactTable.widthProperty().multiply(0.45));
        //-------------------labels for the input fields----------------------------------
        Label nameLabel = new Label("Name:");
        Label phoneLabel = new Label("Phone:");
        Label emailLabel = new Label("Email:");
        Label searchLabel = new Label("Search:");
        //-------------------style the labels----------------------------------------------
        nameLabel.setStyle("-fx-text-fill: #460D67,");//font color
        phoneLabel.setStyle("-fx-text-fill: #460D67;");
        emailLabel.setStyle("-fx-text-fill: #460D67;");
        searchLabel.setStyle("-fx-text-fill: #460D67;");
        nameLabel.setStyle("-fx-font-weight: bold;");//bold font
        phoneLabel.setStyle("-fx-font-weight: bold;");
        emailLabel.setStyle("-fx-font-weight: bold;");
        searchLabel.setStyle("-fx-font-weight: bold;");
        //--------------------grid pane----------------------------------------------------
        //layout for the input fields-arranges its children in a grid
        GridPane inputsGrid = new GridPane();
        inputsGrid.setHgap(10);//set the horizontal gap between the cells in a GridPane
        inputsGrid.setVgap(10);//similarly, for vertical
        inputsGrid.setPadding(new Insets(10, 10, 10, 10));//set padding around the outside of the GridPane
        //           (         , col ,  row)------set position in grid
        inputsGrid.add(nameLabel, 0, 0);
        inputsGrid.add(nameField, 1, 0);
        inputsGrid.add(phoneLabel, 0, 1);
        inputsGrid.add(phoneField, 1, 1);
        inputsGrid.add(emailLabel, 0, 2);
        inputsGrid.add(emailField, 1, 2);
        inputsGrid.add(searchLabel, 0, 3);
        inputsGrid.add(searchField, 1, 3);
        //----------------------------------------- root -----------------------------------------
        //ROOT-creates the main layout for the GUI
        //BorderPane arranges its layout into 5 regions- layout manager
        //(top, left, center, right, and bottom) positions
        BorderPane root = new BorderPane();
        root.setTop(inputsGrid);//set its top region with the contents of inputsGrid(GridPane object)
        root.setCenter(contactTable);//set its centre region with the contents of contactTable(TableView object)
        root.setStyle("-fx-background-color: #D9B7ED;");//set main background colour
        //----------------------------------------- scene -----------------------------------------
        //SCENE- maintains uniformity of stage- need to set scene on stage before presenting
        Scene scene = new Scene(root, 600, 700);//create a scene and pass a root compulsory
        //stage.setWidth(600);//size of stage-y
        //stage.setHeight(700);//x---use Scene constructor instead
        stage.setScene(scene);//set and assign it to application window/stage
    }
    //============================================= SET UP BUTTON ACTIONS ===========================================================================================
    private void setupButtonActions(Stage stage) {
        //----------------------------------------- create -----------------------------------------
        //button object labeled "add" with "Add" displayed on it
        //syntax: Button labelbutton=new Button("buttondisplay")
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button searchButton = new Button("Search");
        Button deleteButton = new Button("Delete");
        Button showAllButton = new Button("Show All");
        //----------------------------------------- customize -----------------------------------------
        //set button bg colours and their text colours
        addButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        updateButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        searchButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        showAllButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        //----------------------------------------- invoke methods -----------------------------------------
        //method addContact is invoked when button with add label is clicked/upon event e
        addButton.setOnAction(e -> addContact());
        updateButton.setOnAction(e -> updateContact());
        searchButton.setOnAction(e -> searchContacts());
        deleteButton.setOnAction(e -> deleteContact());
        showAllButton.setOnAction(e -> showAllContacts());
        //----------------------------------------- set buttons -----------------------------------------
        //HBox-Horizontal Box-layout container-arranges in a horizontal line-left to right
        //show all buttons on the HBox w spacing of 10
        HBox buttonBox = new HBox(10, addButton, updateButton, searchButton, deleteButton, showAllButton);
        buttonBox.setPadding(new Insets(10));//set padding around hbox
        buttonBox.setAlignment(Pos.CENTER);//buttons should be aligned at the center
        //retrieve the root node of current scene associated with stage
        //typecast it to a BorderPane type
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setBottom(buttonBox);//set buttonbox at bottom of stage
    }
    //============================================= ADD CONTACTS ===========================================================================================
    private void addContact() {
        //---------------------------- get inputs -----------------------------------------------------
        //get name as input from nameField, as a string object
        // .getText() method -retrieves the text content of the nameField
        // .trim() removes leading and trailing whitespace from the text
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        //---------------------------- validate all fields -----------------------------------------------------
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            //--------------------enter all fields
            //user must enter all details to create a new contact
            showError("Please fill in all fields.");
            return;
        }
        for (Contact existingContact : contacts.values()) {//iterate through all contacts
            //---------------------no duplicate values
            //iterate through sortedContacts using existingContact as the iterating variable
            if (existingContact.getName().equals(name)) {
                showError("Contact with the same name already exists.");
                return;
            }
            if (existingContact.getPhone().equals(phone)) {
                //checks if currently entered phone matches w any existingcontact's phone
                showError("Contact with the same phone number already exists.");
                return;
            }
            if (existingContact.getEmail().equals(email)) {
                //checks if currently entered email matches w any existingcontact's email
                showError("Contact with the same email address already exists.");
                return;
            }
        }
        //----------------------valid phone number/email
        if (!isValidPhoneNumber(phone)) {
            showError("Invalid phone number. It must have exactly 10 digits.");
            return;
        }
        if (!isValidEmail(email)) {
            showError("Invalid email address.");
            return;
        }
        //---------------------if all conditions for new contact match, create new contact
        Contact contact = new Contact(name, phone, email);//new contact object created and initialized w values by user from textfields
        contacts.put(name, contact);//added to contacts map using name as key
        //update the displayed contacts in the contactTable
        contactList.setAll(contacts.values());//sets the content of contactList to the contacts in the treemap
        // ---------------------clear all fields after adding
        nameField.clear();
        phoneField.clear();
        emailField.clear();
    }
    //============================================= UPDATE CONTACTS ===========================================================================================
    private void updateContact() {
        //---------------------------- get inputs -----------------------------------------------------
        String name = nameField.getText().trim();//same as addcontacts
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            //--------------------enter all fields
            //user must enter all details to create a new contact
            showError("Please fill in all fields.");
            return;
        }
        if (!contacts.containsKey(name)) {
            //---------------------enter existing name
            //if name is not present in the map, it cant be updated
            showError("Contact not found.");
            return;
        }
        //----------------------valid phone number/email
        if (!isValidPhoneNumber(phone)) {
            showError("Invalid phone number. It must have exactly 10 digits.");
            return;
        }
        if (!isValidEmail(email)) {
            showError("Invalid email address.");
            return;
        }
        //---------------------if all conditions for new contact match, create new contact
        Contact contact = new Contact(name, phone, email);//new contact object created and initialized w values by user from textfields
        contacts.put(name, contact);//updated contact put into the contacts map using the same name as the key-effectively updates the existing contact in the map
        contactList.setAll(contacts.values());//sets the content of contactList to the contacts in the treemap
        //---------------------clear all fields after adding
        nameField.clear();
        phoneField.clear();
        emailField.clear();
    }
    //============================================= DELETE CONTACTS ===========================================================================================
    private void deleteContact() {
        //get name as input from nameField
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            //-------------------------enter name
            showError("Please enter the name of the contact to delete.");
            return;
        }
        if (!contacts.containsKey(name)) {
            //-------------------------enter existing name
            showError("Contact not found.");
            return;
        }
        //-----------------------------remove contact
        Contact contact = contacts.get(name);//retrieve the contact from the contacts map based on the given name
        contacts.remove(name);//removes this contact from the treemap using name as key
        //update the displayed contacts in the contactTable
        contactList.setAll(contacts.values());//sets the content of contactList to the contacts in the treemap
        //---------------------clear all fields after deleting
        nameField.clear();
        phoneField.clear();
        emailField.clear();
    }
    //============================================= SEARCH CONTACTS ===========================================================================================
    private void searchContacts() {
        String query = searchField.getText().trim();
        ////results=list of objects of type contact
        List<Contact> results;//to store the search results
        if (query.isEmpty()) {
            //-----------------enter name/phone number
            showError("Please enter a name or phone number to search.");
            return;
        }
        //--------------------phone search or name search
        if (Character.isDigit(query.charAt(0)) && query.length() == 10) {
            //if query is a number, invoke seach by phone method
            results = searchByPhone(query);
        } else {
            //if query is non-digit, invoke search by name method
            results = searchByName(query);
        }
        //-------------------- show results
        if (results.isEmpty()) {
            //if the methods return no result value, no contact was found
            showError("No matching contacts found.");
        } else {
            //else we update the observable list connected to the tableview with the matching contacts stored in result
            contactList.setAll(results);
        }
    }
    //----------------------------------- SEARCH by name ------------------------
    private List<Contact> searchByName(String name) {
        //return list of contacts from func to which the query has been passed
        List<Contact> results = new ArrayList<>();//creates an array to store contacts that match search criteria
        String lowercaseName = name.toLowerCase();//convert entered query to do case insensitive search
        for (Contact contact : contacts.values()) {//for each contact in map's values
            //contact.getName()-retrieves name of contact object
            //.toLowerCase()-converts name to all lowercase
            //.contains(lowercaseName)-checks if this lowercase name contains the lowercase search query
            //returns true- if it does contain, meaning there is a match
            if (contact.getName().toLowerCase().contains(lowercaseName)) {
                results.add(contact);//add method to add an element to list
            }
        }
        return results;
    }
    //----------------------------------- SEARCH by number ----------------------
    private List<Contact> searchByPhone(String phoneNumber) {
        //return list of contacts from func to which the query has been passed
        List<Contact> results = new ArrayList<>();
        for (Contact contact : contacts.values()) {//for each contact in map's values
            //retrieve phone of contact object and check if it matches with query phoneno
            if (contact.getPhone().contains(phoneNumber)) {
                results.add(contact);//add method to add an element to list
            }
        }
        return results;
    }
    //============================================= DISPLAY ALL CONTACTS ===========================================================================================
    private void showAllContacts() {
        contactList.setAll(contacts.values());//sets the content of contactList to the contacts in the treemap
    }
    //============================================= VALIDATION ===========================================================================================
    private boolean isValidPhoneNumber(String phone) {//returns bool value
        //"\\d" as a regular expression matches any digit
        //{10} makes sure that there are exactly 10 consecutive digits for validation
        return phone.matches("\\d{10}");
    }
    private boolean isValidEmail(String email) {
        //returns bool value after checking if email string has '@' and '.'
        boolean validemail= email.contains("@")&&email.contains(".");
        return validemail;
    }
    //-------------------------------------- ERRORS DIALOG BOX--------------------
    private void showError(String message) {//pass the error msg to be displayed
        Alert alert = new Alert(Alert.AlertType.ERROR);//alert class(dialog box to display msgs) object created of alert type error
        //with alert of type error, we get a default error icon and an OK button
        alert.setTitle("Error");
        alert.setHeaderText(null);//showed Error header text even if it's already there in the title
        alert.setContentText(message);//display the msg passed as parameter
        //alert.show();xxx
        alert.showAndWait();//dialog box will show up and wait for our response (either ok or close it) and THEN continue the code
    }
    //============================================= CONTACTS CLASS ===========================================================================================
    class Contact {//blueprint of contact objects
        //each contact has a name, phone and email attribute of type string
        private String name;
        private String phone;
        private String email;
        public Contact(String name, String phone, String email) {
            //constructor for contacts
            this.name = name;
            this.phone = phone;
            this.email = email;
        }
        //to create JavaFX StringProperty objects to be used  with JavaFX's ObservableList--- to bind UI components- TableView and ObservableList
        //we create a StringProperty for each property of a Contact
        //to ensure that any changes to the StringProperty are immediately reflected in the table
        //SimpleStringProperty is basically used to create observable properties
        public StringProperty nameProperty() {
            return new SimpleStringProperty(name);
        }//create and return a new StringProperty based on the name property of Contact

        public StringProperty phoneProperty() {
            return new SimpleStringProperty(phone);
        }

        public StringProperty emailProperty() {
            return new SimpleStringProperty(email);
        }
        //3 getter methods which upon calling, return the value of that particular attribute
        //for retrieval of that attribute's data from a given contact object
        public String getName() {
            return name;
        }
        public String getPhone() {
            return phone;
        }
        public String getEmail() {
            return email;
        }
    }
}