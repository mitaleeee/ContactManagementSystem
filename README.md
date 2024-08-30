Java Contact Management System with GUI
This project is a Contact Management System built using JavaFX, providing a graphical user interface (GUI) to manage contacts effectively. The application allows users to add, update, delete, search, and display contacts in a user-friendly table format. Contacts are stored in a CSV file, making it easy to save and load the data.

Features
Add Contacts: Easily add new contacts with name, phone number, and email.
Update Contacts: Modify existing contact details.
Delete Contacts: Remove contacts from the list.
Search Contacts: Search for contacts by name or phone number.
Display All Contacts: View all saved contacts in a table format.
File Handling: Contacts are saved to and loaded from a CSV file (contact.csv).
Error Handling: Provides user-friendly error messages for invalid inputs or operations.
Technologies Used
JavaFX: For creating the graphical user interface.
Java Collections: Utilizes TreeMap and ObservableList for efficient data management.
File Handling: Uses BufferedReader, FileReader, and FileWriter for reading and writing contacts to a CSV file.
JavaFX Controls: Various controls like TableView, TextField, Button, etc., are used for user interaction.
How to Use
Add a Contact

Enter the name, phone number, and email in the respective fields.
Click on the "Add" button to save the contact.
Update a Contact

Enter the name of the contact you want to update.
Modify the details and click on the "Update" button.
Delete a Contact

Enter the name of the contact you want to delete.
Click on the "Delete" button.
Search for a Contact

Enter a name or phone number in the search field.
Click on the "Search" button to display matching contacts.
Show All Contacts

Click on the "Show All" button to display all saved contacts.
File Handling
Save Contacts: Contacts are automatically saved to contact.csv when the application is closed.
Load Contacts: Contacts are loaded from contact.csv when the application starts.
Error Handling
Displays error messages for the following:
Missing or empty fields when adding or updating contacts.
Duplicate contacts based on name, phone number, or email.
Invalid phone number format (must be exactly 10 digits).
Invalid email format.
