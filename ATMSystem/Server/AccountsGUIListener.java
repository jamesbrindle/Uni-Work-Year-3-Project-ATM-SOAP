import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


/**
 * Associated with the AccountGUI this class is used as the action listener
 * for the AccountGUI and in which the most complex tasks are performed. This class
 * also associates the AccountGUIFieldValidator class
 * @author Jamie Brindle (06352322)
 */
public class AccountsGUIListener  implements ActionListener, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected AccountsGUI accountsGUI;
    protected AccountsGUIFieldValidator accountsGV;
    private aDialog exitDialog, removeDialog, fileExistsDialog;
    private JFileChooser fc, fc2;
    private File file, saveAs;
    protected Store store, tempStore;
    private String currentFileName;
    private int validationOK;
    private boolean newRecord;
    private boolean saveAttempt = false;
    private boolean didSearch = false;
    protected NumeralFunctions nf;
	
    /**
     * AccountGUIListener constructor taking in the AccountGUI class
     * as a parameter thus directly affecting it
     * @param GUI associating the AccountsGUIListener with the AccountsGUI
     * class
     */
    public AccountsGUIListener(AccountsGUI GUI) {
        this.accountsGUI = GUI;
        accountsGV = new AccountsGUIFieldValidator(this);
        store = new Store();
        nf = new NumeralFunctions();
	          
    } 
	
    /**
     * Redefines actionPerformed, all actionListeners in the AccountGUI class
     * are dealt with here, such as what happens when a button is pressed
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
		
        if (e.getSource() == accountsGUI.menuItem4) {
            store = new Store();
            accountsGUI.setIsEmpty(true);
            accountsGUI.changeWarningMessage(
                    "Be sure to save the store before exiting");
            currentFileName = "Unsaved.dat";
            setRecord();
        } // When the 'new store' menu item is pressed from the file menu, create new store
	
        if (e.getSource() == accountsGUI.menuItem1) {
            openFile();
        } // When the 'open file' menu item is pressed from the file menu
		
        if (e.getSource() == accountsGUI.menuItem2) {
            saveFileAs();
        } // When the  'save store as' menu item is pressed from the file menu
		
        if (e.getSource() == accountsGUI.menuItem3) {
            exitDialog = new aDialog(new JFrame(), "Exit",
                    "Are you sure you wish to exit?");

            if (exitDialog.getChoice() == 1) {
                accountsGUI.dispose();
                System.exit(0);
               
            }          
        }// When the 'exit' menu item is pressed from the file menu, exit frame
        
        if (e.getSource() == accountsGUI.nextNavButton) {
            store.nextAccountPointer();
            // set appropriate navigation buttons enabled mode
            accountsGUI.setIsFirst(store.isFirst());
            accountsGUI.setIsLast(store.isLast());
            accountsGUI.setIsEmpty(false);
            setRecord();
        } // when the 'next' record pointer button is pressed, point to next record
		
        if (e.getSource() == accountsGUI.previousNavButton) {
            store.previousAccountPointer();
            // set appropriate navigation buttons enabled mode
            accountsGUI.setIsFirst(store.isFirst());
            accountsGUI.setIsLast(store.isLast());
            accountsGUI.setIsEmpty(false);
            setRecord();
        } // when the 'previous' record pointer button is pressed, point to previous record
		
        if (e.getSource() == accountsGUI.firstNavButton) {
            store.firstAccountPointer();
            // set appropriate navigation buttons enabled mode
            accountsGUI.setIsFirst(store.isFirst());
            accountsGUI.setIsLast(store.isLast());
            accountsGUI.setIsEmpty(false);
            setRecord();
        } // when the 'first' record pointer button is pressed, point to first record
		
        if (e.getSource() == accountsGUI.lastNavButton) {
            store.lastAccountPointer();
            // set appropriate navigation buttons enabled mode
            accountsGUI.setIsFirst(store.isFirst());
            accountsGUI.setIsLast(store.isLast());
            accountsGUI.setIsEmpty(false);
            setRecord();
        } // when the 'last' record pointer button is pressed, point to last record
		
        if (e.getSource() == accountsGUI.removeNavButton) {
            // create a 'are you sure' dialogue
            removeDialog = new aDialog(new JFrame(), "Remove",
                    "Are you sure you want to delete the current record?");
			
            if (removeDialog.getChoice() == 1) { // if yes pressed
            	
                if (didSearch) { // has there been a previous search request
                    Store tempStore2 = new Store();

                    store = store.fileIn(currentFileName);
            		
                    for (int i = 0; i < store.accountArrayList.size(); i++) {
                        if (!(store.accountArrayList.get(i).getAccountNo()
                                == nf.stringToInt(
                                        accountsGUI.accountNoTextField.getText()))) {
                            tempStore2.accountArrayList.add(
                                    store.accountArrayList.get(i));
                        }
                    } // create new store, put everything in it except desired record to remove
            		
                    store = tempStore2;
                    saveFile();
                    store.firstAccountPointer();
            		
                    // set appropriate navigation buttons enabled mode
                    accountsGUI.setIsFirst(store.isFirst());
                    accountsGUI.setIsLast(store.isLast());
                    accountsGUI.searchBarTextField.setText("");
                    setRecord(); // update frame
                    didSearch = false; // research search requested
                    
                } else { // if no search requested, remove record and update frame
            	
                    store.removeAccount(store.currentAccount());
                    saveFile();
                            
                    if (store.isEmpty()) {
                        accountsGUI.setIsEmpty(true);            	
                        accountsGUI.changeWarningMessage(
                                "There are no account records in this store");
                        setRecord();
                
                    } else {
                
                        if (store.isFirst()) {
                            store.nextAccountPointer();
                            // set appropriate navigation buttons enabled mode
                            accountsGUI.setIsFirst(store.isFirst());
                            accountsGUI.setIsLast(store.isLast());
                            accountsGUI.setIsEmpty(false);
                            setRecord();
                        } else {
                            store.lastAccountPointer();
                            // set appropriate navigation buttons enabled mode
                            accountsGUI.setIsFirst(store.isFirst());
                            accountsGUI.setIsLast(store.isLast());
                            accountsGUI.setIsEmpty(false);
                            setRecord();
                	
                        }
                    }
                }
                
            }
        } // when the 'remove' record button is pressed, remove record, save store
		
        if (e.getSource() == accountsGUI.editNavButton) {
            accountsGUI.isEditing = true;
            accountsGUI.setTextFieldsEditable(true);
            accountsGUI.changeWarningMessage(
                    "Be sure to click the save button when alterations are complete");
            accountsGUI.setEditNavButtonMode(true);
        } // when 'edit' button is pressed, make text fields editable
		
        if (e.getSource() == accountsGUI.newRecordNavButton) {
            // new record button changes to cancel button when editing or creating new record...
            if (accountsGUI.isEditing) { // if is editing
                newRecord = false;
                accountsGUI.setTextFieldsEditable(false);
                accountsGUI.changeWarningMessage(
                        "use the navigation bar below to move through records, create, modify or remove");
                accountsGUI.setEditNavButtonMode(false);
				
                if (store.isEmpty()) { // if there are no records
                    accountsGUI.setIsEmpty(true); 
                    accountsGUI.changeWarningMessage(
                            "There are no account records in this store");
                } else {
                    // set appropriate navigation buttons enabled mode
                    accountsGUI.setIsFirst(store.isFirst());
                    accountsGUI.setIsLast(store.isLast());
                }
                if (saveAttempt) { // some validation errors exists in text fields, need to recheck
                    store.lastAccountPointer(); // point to record just created
                    store.removeAccount(store.currentAccount()); // remove record with error
                    store.lastAccountPointer();
                    accountsGUI.setIsFirst(store.isFirst());
                    accountsGUI.setIsLast(store.isLast());
                    setRecord();
                    if (store.isEmpty()) {
                        accountsGUI.changeWarningMessage(
                                "There are no account records in this store");
                        accountsGUI.setIsEmpty(true);
                    }
                } else {
                    setRecord();
                }
						
            } else { // reset search requested, if new record created, update frame
                didSearch = false;
                newRecord = true;
                accountsGUI.setTextFieldsEditable(true);
                accountsGUI.changeWarningMessage(
                        "Be sure to click the save button when accounts details are complete");
                accountsGUI.setEditNavButtonMode(true);
                accountsGUI.setIsEmpty(true);
                setRecord();
						
            }
        } // when the new record button has been pressed
		
        if (e.getSource() == accountsGUI.saveNavButton) {
			
            if (accountsGUI.isEditing == true && newRecord == false) { // if is editing but not new record
                validationOK = 0; // reset validation count
                validateAndStore(); // validate all fields
                if (validationOK == 14) { // if validation all came back ok
                    accountsGUI.setTextFieldsEditable(false);
                    accountsGUI.setEditNavButtonMode(false);
                    // set appropriate navigation buttons enabled mode
                    accountsGUI.setIsFirst(store.isFirst());
                    accountsGUI.setIsLast(store.isLast());
                    accountsGUI.changeWarningMessage(
                            "use the navigation bar below to move through records, create, modify or remove");
					
                    if (didSearch) { // if a search was previously requested
                        Store tempStore2 = new Store();
                        Store tempStore3 = new Store();
						
                        tempStore3.accountArrayList.add(store.currentAccount());
                        store = store.fileIn(currentFileName);
	            		
                        for (int i = 0; i < store.accountArrayList.size(); i++) {
                            if (!(store.accountArrayList.get(i).getAccountNo()
                                    == nf.stringToInt(
                                            accountsGUI.accountNoTextField.getText()))) {
                                tempStore2.accountArrayList.add(
                                        store.accountArrayList.get(i));
                            }
                        } // create store, add all records except the one saving
	            		
                        // then add the edited record to the store
                        tempStore2.accountArrayList.add(
                                tempStore3.accountArrayList.get(0));
       
                        store = tempStore2;
	            		
                    }
                    saveFile();				
					
                } // when the save button has been pressed
		
            } else if (newRecord) { // if a new record has been created
                ArrayList<String> tempArray; 

                tempArray = new ArrayList<String>();
                store.addAccount(
                        new Account(
                                nf.stringToInt(
                                        accountsGUI.accountNoTextField.getText())));
                store.lastAccountPointer();
                store.currentAccount().setTransactions(tempArray);
                validationOK = 0;
                validateAndStore();
                if (validationOK == 14) {
                    accountsGUI.setTextFieldsEditable(false);
                    accountsGUI.setEditNavButtonMode(false);
                    // set appropriate navigation buttons enabled mode
                    accountsGUI.setIsFirst(store.isFirst());
                    accountsGUI.setIsLast(store.isLast());
                    accountsGUI.changeWarningMessage(
                            "use the navigation bar below to move through records, " +
                            "create, modify or remove");
                    saveFile();
                    store = store.fileIn(currentFileName);
                    newRecord = false;
                    store.lastAccountPointer();
				
                }
            }
					
        } // create new account, validate all fields, save store
		
        if (e.getSource() == accountsGUI.searchBarSearchButton) {
            store = store.fileIn(currentFileName); // reset search
            tempStore = new Store();
			
            if (accountsGUI.searchBarTextField.getText().equalsIgnoreCase("")
                    || accountsGUI.searchBarTextField.getText() == null) { // if field not empty
				
                if (didSearch) { // if search already requested
                    didSearch = false;
                    store = store.fileIn(currentFileName); // reset search
				
                    // set appropriate navigation buttons enabled mode
                    accountsGUI.setIsFirst(store.isFirst());
                    accountsGUI.setIsLast(store.isLast());
                    setRecord();
                }
            } else {
			
                if (nf.containsOnlyNumbers(
                        accountsGUI.searchBarTextField.getText())) {
                    // if field contains only numbers, then search on account number
                						
                    for (int i = 0; i < store.accountArrayList.size(); i++) {
                   				
                        if (nf.numberToString(store.accountArrayList.get(i).getAccountNo()).contains(
                                accountsGUI.searchBarTextField.getText())) {
                            tempStore.accountArrayList.add(
                                    store.accountArrayList.get(i));
                            
                        } // create new store with matching search results
                    }
                    didSearch = true; // show search was requested
                    store = tempStore;
                    // set appropriate navigation buttons enabled mode
                    accountsGUI.setIsFirst(store.isFirst());
                    accountsGUI.setIsLast(store.isLast());
                    setRecord();
                    if (store.isEmpty()) {
                        store = store.fileIn(currentFileName);
                        accountsGUI.changeWarningMessage(
                                "There are no records matching your search");
                    }
                } else { // then search on last name			
                    for (int i = 0; i < store.accountArrayList.size(); i++) {
                        if (store.accountArrayList.get(i).getLastName().toLowerCase().indexOf(
                                accountsGUI.searchBarTextField.getText().toLowerCase())
                                        > -1) {
                            tempStore.accountArrayList.add(
                                    store.accountArrayList.get(i));
					
                        } // create new store with matching search results
                    }
                    didSearch = true; // show search was requested
                    store = tempStore;
                    accountsGUI.setIsFirst(store.isFirst());
                    accountsGUI.setIsLast(store.isLast());
                    setRecord();
                    if (store.isEmpty()) {
                        accountsGUI.setIsEmpty(true);
                        accountsGUI.changeWarningMessage(
                                "There are no records matching your search");
                    }
			
                }
            }
			
        }
			
    } // if search button pressed, if empty reset store to original file date, else

    // create new store with matching search results
	
    /**
     * Opens up a file browser dialogue in which the user can selected a file containing the store
     * which holds the accounts database, in which that store is cast to a store within
     * the AccountsGUI class
     * @exception k If any errors exists in loading the file, display message
     */
    public void openFile() {
        int returnVal = 0;

        fc = new JFileChooser(); // file chooser browser
        TextFilter tf = new TextFilter(".dat"); // look for only .dat files

        fc.setCurrentDirectory(new File(".")); // set current directory to current working directory (where class file located)
        fc.setFileFilter(tf);
        returnVal = fc.showOpenDialog(accountsGUI.menuItem1);
       
        if (returnVal == JFileChooser.APPROVE_OPTION) { // if a file is selected
        	
            try {
            	
                file = fc.getSelectedFile(); // load the file             
                
                store = store.fileIn(file.getName()); // cast the object found inside the file to the temp store
             
                currentFileName = file.getName(); // update a string to use for saving and opening in the future
              
                store.firstAccountPointer(); // go the first record on the store
              
                setRecord(); // display the record on the frame
                
                if (store.isEmpty()) {
                    accountsGUI.changeWarningMessage(
                            "There are no account records in this store");
                    accountsGUI.setIsEmpty(true);
                    accountsGUI.newRecordNavButton.setEnabled(true);
                } else {               
                                
                    accountsGUI.changeWarningMessage(
                            "use the navigation bar below to move through records, create, modify or remove");
                    accountsGUI.setNavButtonsEnabled(true);
                    accountsGUI.setIsFirst(store.isFirst());
                    accountsGUI.setIsLast(store.isLast());
                
                }

            } catch (Exception k) { // if error loading file
                System.out.println("could not load file");
                accountsGUI.changeWarningMessage("Error loading file");
                accountsGUI.setIsEmpty(true);
            	
            }
        }
    } // opens a file browser dialogue and sets the store to that found within the selected file
	
    /**
     * Opens up a file browser dialogue in which the user can selected the location
     * and name of the file where the store will be held containing the account records
     * database
     * @exception k if any errors exist in saving the file, display a message
     */
    public void saveFileAs() {
	    
        int returnVal3 = 0;

        fc2 = new JFileChooser();
        TextFilter tf3 = new TextFilter(".dat"); // default file to .dat

        fc2.setCurrentDirectory(new File(".")); // look first in current directory
        fc2.setFileFilter(tf3);
        returnVal3 = fc2.showSaveDialog(accountsGUI.menuItem2);

        if (returnVal3 == JFileChooser.APPROVE_OPTION) {
            try {
                saveAs = fc2.getSelectedFile();

                if (saveAs.exists()) { // if file exists, ask to overwrite or not
                    fileExistsDialog = new aDialog(new JFrame(),
                            "File Name Already Exists",
                            "Would you like to overwrite the existing file?");

                    if (fileExistsDialog.getChoice() == 1) {
                        store.fileOut(saveAs.getName());
                        currentFileName = saveAs.getName();
                        
                    } else if (fileExistsDialog.getChoice() == 0) { // if user doesn't want to overwrite...
                        fileExistsDialog.setChoice(1);
                        saveFile(); // then recall this method again to open file chooser to enter a different file name
                    }
                } else { // if file doesn't exist
                    store.fileOut(saveAs.getName());  
                    currentFileName = saveAs.getName();
                    setRecord();
                }
            } catch (Exception k) {
                accountsGUI.changeWarningMessage("Could Not Save File");
                System.out.println("Could not save file");
            }
        }
    } // saves the file to a choosen name and directory
	
    /**
     * Saves current store in the file name selected when first opening the file
     */
    public void saveFile() {
	    
        store.fileOut(currentFileName);
    }
	
    /**
     * Updates the frame's text fields with the appropriate data of the current
     * account record in a store
     */
    public void setRecord() {
        if (store.isEmpty() == false && newRecord == false) {
            accountsGUI.accountNoTextField.setText(
                    nf.numberToString(store.currentAccount().getAccountNo()));
            accountsGUI.pinNumberTextField.setText(
                    nf.numberToString(store.currentAccount().gettPinNo()));
            accountsGUI.titleTextField.setText(store.currentAccount().getTitle());
            accountsGUI.firstNameTextField.setText(
                    store.currentAccount().getFirstName());
            accountsGUI.lastNameTextField.setText(
                    store.currentAccount().getLastName());
            accountsGUI.firstLineAddrTextField.setText(
                    store.currentAccount().getFirstLineAddress());
            accountsGUI.secondLineAddrTextField.setText(
                    store.currentAccount().getSecondLineAddress());
            accountsGUI.cityTextField.setText(store.currentAccount().getCity());
            accountsGUI.countyTextField.setText(
                    store.currentAccount().getCounty());
            accountsGUI.postCodeTextField.setText(
                    store.currentAccount().getPostCode());
            accountsGUI.telephoneTextField.setText(
                    store.currentAccount().getTelephone());
            accountsGUI.balanceTextField.setText(
                    "\u00A3"
                            + nf.decimalFormatter(
                                    store.currentAccount().getBalance()));
            accountsGUI.amountWithdrawnTodayTextField.setText(
                    "\u00A3"
                            + nf.decimalFormatter(
                                    store.currentAccount().getAmountWithdrawnToday()));
            accountsGUI.maxDayWithdrawTextField.setText(
                    "\u00A3"
                            + nf.decimalFormatter(
                                    store.currentAccount().getMaxDayWithDrawel()));	
            accountsGUI.accountLockedCheckBox.setSelected(
                    store.currentAccount().getIsAccountLocked());
				
            ArrayList<String>tempArrayList = store.currentAccount().getTransactions();
            String tempString = "";
		
            if (tempArrayList.size() == 0 || tempArrayList == null) {
                tempString = tempString + "There are no current transactions";
            } else {
                for (int i = 0; i < tempArrayList.size(); i++) {
                    tempString = tempString + tempArrayList.get(i).toString()
                            + "\n";
                } 
            } // iterate through transactions array list
		
            accountsGUI.transactionListTextArea.setText(tempString);
        } else if (store.isEmpty() == true && newRecord == false) { // if store is empty
            accountsGUI.accountNoTextField.setText("");
            accountsGUI.pinNumberTextField.setText("");
            accountsGUI.titleTextField.setText("");
            accountsGUI.firstNameTextField.setText("");
            accountsGUI.lastNameTextField.setText("");
            accountsGUI.firstLineAddrTextField.setText("");
            accountsGUI.secondLineAddrTextField.setText("");
            accountsGUI.cityTextField.setText("");
            accountsGUI.countyTextField.setText("");
            accountsGUI.postCodeTextField.setText("");
            accountsGUI.telephoneTextField.setText("");
            accountsGUI.balanceTextField.setText("");
            accountsGUI.amountWithdrawnTodayTextField.setText("");
            accountsGUI.maxDayWithdrawTextField.setText("");
            accountsGUI.accountLockedCheckBox.setSelected(false);
            accountsGUI.transactionListTextArea.setText("");
        } else if (newRecord) { // if new record selected, set fields appropriately
            createNewRecord();
        }		
		
    }
	
    /**
     * Sets up the frame's text fields in order to add a new account record. As the
     * account number is the records unique identifier, this method makes sure there
     * are no duplicate account numbers. If there are no records, this method creates a
     * starting number. Also a random pin number is generated, a default withdrawal limit
     * and the balance set to 0 however these can be changed at any time
     */
    public void createNewRecord() {
        if (store.isEmpty()) {
            accountsGUI.accountNoTextField.setText("4000001");
        } else {
            int lastAccountNumber = store.accountArrayList.get(store.accountArrayList.size() - 1).getAccountNo();

            accountsGUI.accountNoTextField.setText(
                    nf.numberToString(lastAccountNumber + 1));
        }
		
        Random generator = new Random();
        int randomNumber = generator.nextInt(9999) + 1000;
        int actualPinNumber = 0;
			
        if (randomNumber > 9999) {
            actualPinNumber = randomNumber - 1000;
			
        } else {
            actualPinNumber = randomNumber;
        }
				
        accountsGUI.pinNumberTextField.setText(
                nf.numberToString(actualPinNumber));
        accountsGUI.titleTextField.setText("");
        accountsGUI.firstNameTextField.setText("");
        accountsGUI.lastNameTextField.setText("");
        accountsGUI.firstLineAddrTextField.setText("");
        accountsGUI.secondLineAddrTextField.setText("");
        accountsGUI.cityTextField.setText("");
        accountsGUI.countyTextField.setText("");
        accountsGUI.postCodeTextField.setText("");
        accountsGUI.telephoneTextField.setText("");
        accountsGUI.balanceTextField.setText("\u00A3" + "0.00");
        accountsGUI.amountWithdrawnTodayTextField.setText("\u00A3" + "0.00");
        accountsGUI.maxDayWithdrawTextField.setText("\u00A3" + "200.00");
        accountsGUI.accountLockedCheckBox.setSelected(false);
        accountsGUI.transactionListTextArea.setText("");
    }
	
    /**
     * Validates each text field in the frame and stores each field. This method
     * validates that there are no invalid numbers or characters in each field and that
     * certain fields are required to be entered. An error message will be produced if
     * invalids are found, requesting the user re-attempt the input. The store will not
     * be saved otherwise
     */
    public void validateAndStore() {
        if (accountsGV.validatePinNumber(
                accountsGUI.pinNumberTextField.getText())) {
            store.currentAccount().setPinNo(
                    nf.stringToInt(accountsGUI.pinNumberTextField.getText()));
            validationOK++;
            saveAttempt = false;
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure the pin number field is 4 digits long between 1000 and 9999");
        }
        if (accountsGV.validateStringsFieldsNumbersNotAllowed(
                accountsGUI.titleTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setTitle(accountsGUI.titleTextField.getText());
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the title field (i.e. $%^&) or numebrs");
        }
        if (accountsGV.validateStringsFieldsNumbersNotAllowed(
                accountsGUI.firstNameTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setFirstName(
                    accountsGUI.firstNameTextField.getText());
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the first name field (i.e. $%^&)");
        }
        if (accountsGV.validateStringsFieldsNumbersNotAllowed(
                accountsGUI.lastNameTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            saveAttempt = false;
            store.currentAccount().setLastName(
                    accountsGUI.lastNameTextField.getText());
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the last name field (i.e. $%^&)");
        }
        if (accountsGV.validateStringsFieldsNumbersAllowed(
                accountsGUI.firstLineAddrTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setFirstLineAddress(
                    accountsGUI.firstLineAddrTextField.getText());
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the first line address field (i.e. $%^&)");
        }
        if (accountsGV.validateStringsFieldsNumbersAllowed(
                accountsGUI.secondLineAddrTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setSecondLineAddress(
                    accountsGUI.secondLineAddrTextField.getText());
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the second line address field (i.e. $%^&)");
        }
        if (accountsGV.validateStringsFieldsNumbersNotAllowed(
                accountsGUI.cityTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setCity(accountsGUI.cityTextField.getText());
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the city field (i.e. $%^&) or numbers");
        }
        if (accountsGV.validateStringsFieldsNumbersNotAllowed(
                accountsGUI.countyTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setCounty(
                    accountsGUI.countyTextField.getText());
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the county field (i.e. $%^&) or numbers");
        }
        if (accountsGV.validateStringsFieldsNumbersAllowed(
                accountsGUI.postCodeTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setPostCode(
                    accountsGUI.postCodeTextField.getText());
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the post code field (i.e. $%^&)");
        }
        if (accountsGV.validateTelephone(
                accountsGUI.telephoneTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setTelephone(
                    accountsGUI.telephoneTextField.getText());
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the telephone field (i.e. $%^&)");
        }
		
        if (accountsGV.validateCurrency(accountsGUI.balanceTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setBalance(
                    accountsGV.convertCurrency(
                            accountsGUI.balanceTextField.getText()));
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the balance field (i.e. $%^&)");
        }
        if (accountsGV.validateCurrency(
                accountsGUI.amountWithdrawnTodayTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setAmountWithdrawnToday(
                    accountsGV.convertCurrency(
                            accountsGUI.amountWithdrawnTodayTextField.getText()));
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the max withdrawal field (i.e. $%^&)");
        }
        if (accountsGV.validateCurrency(
                accountsGUI.maxDayWithdrawTextField.getText())) {
            validationOK++;
            saveAttempt = false;
            store.currentAccount().setMaxDayWithDrawel(
                    accountsGV.convertCurrency(
                            accountsGUI.maxDayWithdrawTextField.getText()));
        } else {
            validationOK--;
            saveAttempt = true;
            accountsGUI.changeWarningMessage(
                    "Make sure there are no invalid characters in the max withdrawal field (i.e. $%^&)");
        }
        store.currentAccount().setAccountLocked(
                accountsGUI.accountLockedCheckBox.isSelected());
        
        if (accountsGUI.firstNameTextField.getText().equalsIgnoreCase("")
                || accountsGUI.titleTextField.getText().equalsIgnoreCase("")
                	|| accountsGUI.lastNameTextField.getText().equalsIgnoreCase("")
                		|| accountsGUI.firstLineAddrTextField.getText().equalsIgnoreCase("")
                        	|| accountsGUI.postCodeTextField.getText().equalsIgnoreCase("")
                                || accountsGUI.cityTextField.getText().equalsIgnoreCase("")
                                        || accountsGUI.countyTextField.getText().equalsIgnoreCase("")) 
        {
            validationOK--;
            accountsGUI.changeWarningMessage(
                    "You must at least fill in the title, name, " +
                    "first line address, city, county and post code fields");
            saveAttempt = true;
        } else {
            validationOK++;
            saveAttempt = false;
			
        }

    }
}

