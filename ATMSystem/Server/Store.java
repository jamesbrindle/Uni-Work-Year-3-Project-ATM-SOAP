import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Used to store account records in an array list, in which can be placed within a file,
 * extracted from a file and used to navigate through account records with the AccountGUI class.
 * The ATMServer uses this class to retrieve and store account information for use with the ATMClient
 * 
 * @author Jamie Brindle (06352322)
 */
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    protected ArrayList<Account> accountArrayList;
    private int currentAccountIndex;
	
    /**
     * Constructs the store, creates an array list for account records to be stored
     */
    public Store() {
        accountArrayList = new ArrayList<Account>();
	
    }	
	
    /**
     * Add an account to the array list
     * @param account The account to add
     */
    public void addAccount(Account account) {
        accountArrayList.add(account);
    }
	
    /**
     * removes an account from the array list
     * @param account The account to be removed
     */
    public void removeAccount(Account account) {
        accountArrayList.remove(account);
    }
	
    /**
     * Returns the account corresponding the the currentAccountIndex
     * which are navigated through by the firstAccountPointer, lastAccountPointer,
     * previousAccountPointer and nextAccountPointer methods
     * @return returns the current record
     */
    public Account currentAccount() {
        if (accountArrayList.size() == 0) {
            return null;
        } else {
            return accountArrayList.get(currentAccountIndex);
        }
    }
	
    /**
     * points to first account which is that of the first object in the array
     */
    public void firstAccountPointer() {
        currentAccountIndex = 0;
    }
	
    /**
     * points to the last account which is that of the size of the array -1
     */
    public void lastAccountPointer() {
        currentAccountIndex = accountArrayList.size() - 1;
    }
	
    /**
     * Points to the next account pointer, which increments the currentAccountIndex
     */
    public void nextAccountPointer() {
        if (currentAccountIndex < accountArrayList.size() - 1) {
            currentAccountIndex++;
        }
    }
	
    /**
     * Points to the previous account record, which decrements the currentAccountIndex
     */
    public void previousAccountPointer() {
        if (currentAccountIndex > 0) {
            currentAccountIndex--;
        }
    }
	
    /**
     * Checks if the current pointer is set at the last record
     * @return boolean true if is pointed at last record, false otherwise
     */
    public boolean isLast() {
        if (currentAccountIndex == accountArrayList.size() - 1) {
            return true;
        } else {
            return false;
        }
    }
	
    /**
     * Checks if the current pointer is set at the first record
     * @return boolean true if is pointed at first record, false otherwise
     */
    public boolean isFirst() {
        if (currentAccountIndex == 0) {
            return true;
        } else {
            return false;
        }
    }
	
    /**
     * Checks if the store is empty
     * @return boolean true if store is empty, false otherwise
     */
    public boolean isEmpty() {
        if (accountArrayList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Saves the store as an object and saves it within a file
     * @param fileName the file name the file is to be saved as
     * @exception e if an error exists in object streaming and writing the file
     * @exception ioe if an error exists in flushing and closing the object output stream
     */
    public void fileOut(String fileName) {
        ObjectOutputStream oos = null; // initialise objectOutputStream

        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName)); // set output stream
            oos.writeObject(Store.this); // write the store to a file
        } catch (Exception e) { // if any errors occur catch them
            String errMessage = e.getMessage();

            System.out.println("Error: " + errMessage); // print out the error message to console
        } finally {
            if (oos != null) {
                try {
                    oos.flush(); // flush object output stream (clear memory)
                } catch (IOException ioe) {}

                try {
                    oos.close(); // close object output stream
                } catch (IOException ioe) {}
            }
        }
	    
    }
	
    /**
     * Opens a file and casts the object found within that file as a store
     * @param fileName the file name the file is to be opened
     * @return returns the store found within the file
     * @exception e if any errors exists in reading and casting the object
     * @exception ioe if an errors exists in closing the object input stream
     */
    public Store fileIn(String fileName) {
        ObjectInputStream oi = null; // initialise object input stream
        Store temp = null; // initialise a new store called temp

        try { // try to retrieve the object from the file
            FileInputStream fi = new FileInputStream(fileName);

            oi = new ObjectInputStream(fi);		
            temp = (Store) oi.readObject(); // cast the Store retrieved from the file to the temp Store
        } catch (Exception e) { // if any errors occur, catch them
            String error = e.getMessage();

            System.out.println("Error: " + error); // print out an error message to the console
        } finally {
            if (oi != null) {
                try {
                    oi.close(); // close object input stream
                } catch (IOException ioe) {}
            }
        }
        return temp; // return the temp store
    
    }
	
    /**
     * A method which prints all the account records found within a store
     * to the screen in a way that the user can understand
     */
    public void displayAll() {
        for (int i = 0; i < accountArrayList.size(); i++) {
            System.out.println(accountArrayList.get(i).accountToString());
        }
		
    }
	
}
