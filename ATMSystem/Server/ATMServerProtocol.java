import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;


/**
 * The ATMServerProtocol class deals with and processes data received from the client and updates the data base
 * file and sends data to the atm client. This class is called from the ATMServerThread class which is passed
 * to this class as a parameter in which this class refers to. This class does the majority of the processing
 * work done by the server side of the ATM System
 * 
 * @author Jamie Brindle (06352322)
 */
public class ATMServerProtocol {
	
    // protected Store store = new Store();
    protected ServerOptions so = new ServerOptions(); // Class which contains methods for retrieving 
    // server info from a text file
    protected ATMServerThread atmST; // the ATM Server Thread presently dealing with
    protected String accountNo; // the account presently dealing with
    protected NumeralFunctions nf; // Class which contains method to manipulate and convert numbers and string
    protected int accountPointer; // a int acting as a pointing the the account presently dealing with
		
    /**
     * The ATMServerProtocol constructor
     * @param atmServerThread the calling class in which this class refers to
     */
    public ATMServerProtocol(ATMServerThread atmServerThread) {
        this.atmST = atmServerThread;
        nf = new NumeralFunctions();
    }	

    /**
     * Processes the input from the client and sends messages/data to the client and updates
     * the account database
     * @param fromClient the message/data received from the client in the form of a string
     */
    public void processInput(String fromClient) {
        if (fromClient.equalsIgnoreCase("waitForAccount")) {
            atmST.out.println("waitingForAccount");     
        } else if (fromClient.contains("enteredAccountNo:")) {
            if (accountExists(getData(fromClient))) {
        		
                if (atmST.store.accountArrayList.get(accountPointer).getIsAccountLocked()) {
                    atmST.out.println("accountAlreadyLocked");
                } else if (isAccountActive()) {
                    atmST.out.println("accountAlreadyActive");
                } else {
                    atmST.activeAccounts.add(nf.numberToString(accountPointer));
                    atmST.out.println(getAccountInfoPDU()); 
        		        			
                }        		
            } else {
                atmST.out.println("noAccountExists");
        		       		       		
            }        	
        } else if (fromClient.equalsIgnoreCase("lockAccount")) {
            removeFromActiveAccounts();
            atmST.store.accountArrayList.get(accountPointer).setAccountLocked(
                    true);
            atmST.store.fileOut(so.getDataFileLocation());
            
        } else if (fromClient.contains("newWithdrawalLimits")) {
            updateWithdrawalLimits(fromClient);
            
        } else if (fromClient.contains("withdrawing")) {
            removeFromActiveAccounts();
            updateBalanceFromWithdrawal(fromClient);
            
        } else if (fromClient.contains("depositing")) {
            removeFromActiveAccounts();
            updateBalanceFromDeposit(fromClient);
            
        } else if (fromClient.contains("changePin")) {
            atmST.store.accountArrayList.get(accountPointer).setPinNo(
                    nf.stringToInt(getData(fromClient)));
            atmST.store.fileOut(so.getDataFileLocation());
        	
        } else if (fromClient.contains("sessionFinished")) {
            removeFromActiveAccounts();
            
        } else if (fromClient.contains("EXIT")) {
            atmST.out.println("EXIT");
        }
        	
    }
    
    /**
     * Method to determine if the present account is already active on the server by looking
     * through the activeAccounts array list brought from the ATMServerThread class, which in turn
     * was brought in by the ATMServer class
     * @return true if account active, false otherwise
     */
    public boolean isAccountActive() {
        for (int i = 0; i < atmST.activeAccounts.size(); i++) {
            if (atmST.activeAccounts.get(i).equalsIgnoreCase(
                    nf.numberToString(accountPointer))) {
                return true;
            }
        }
        return false;
    	
    }
    
    /**
     * Method to remove the account presently dealing with from the active accounts array list
     * which originally resides from the ATMServer class
     */
    public void removeFromActiveAccounts() {
        for (int i = 0; i < atmST.activeAccounts.size(); i++) {
            if (atmST.activeAccounts.get(i).equalsIgnoreCase(
                    nf.numberToString(accountPointer))) {
                atmST.activeAccounts.remove(i);
            }
        }
    }
      
    /**
     * Method to get specific data from the account pointed to by the store and assign
     * to variables to allow an AccountInfoPDU string to be generated which will be sent to the client
     * @return outputString the AccountInfoPDU
     */
    public String getAccountInfoPDU() {
        int pinNumber = atmST.store.accountArrayList.get(accountPointer).gettPinNo();
        double balance = atmST.store.accountArrayList.get(accountPointer).getBalance();
        double maxDayWithdraw = atmST.store.accountArrayList.get(accountPointer).getMaxDayWithDrawel();
        boolean ifLocked = atmST.store.accountArrayList.get(accountPointer).getIsAccountLocked();
        double amountWithdrawnToday = atmST.store.accountArrayList.get(accountPointer).getAmountWithdrawnToday();
        long dateLastWithdrawn = atmST.store.accountArrayList.get(accountPointer).getDateLastWithdrawn();
		
        String outputString = "AccountInfoPDU:" + pinNumber + ":" + balance
                + ":" + maxDayWithdraw + ":" + ifLocked + ":"
                + amountWithdrawnToday + ":" + dateLastWithdrawn;
		
        return outputString;
    }

    /**
     * Separates the data from the PDU identifier and returns only the first or only piece of data
     * @param aString The PDU String
     * @return items[1] The first or only piece of data (String)
     */
    public String getData(String aString) {
        String[] items = splitString(aString);

        return items[1];    
    }
    
    /**
     * Similar to the getData method. It splits the pieces of data from the PDU identifier but instead of
     * returning a single String, it returns an Array of all the pieces of data in the PDU, This is used
     * when more than one item of data is expected
     * @param aString The PDU String
     * @return items An array of data items
     */
    public String[] splitString(String aString) {
        String REGEX = ":";
		
        Pattern p = Pattern.compile(REGEX);
        String[] items = p.split(aString);
	    
        return items;
    }
    
    /**
     * Takes the input from the client, separates the data and updates the date last withdrawn
     * in account field and the amount withdrawn today field
     * @param withdrawelLimitsPDU the input from the client (string) containing the withdrawal limits data
     */
    public void updateWithdrawalLimits(String withdrawelLimitsPDU) {
        String[] items = splitString(withdrawelLimitsPDU);
    	
        atmST.store.accountArrayList.get(accountPointer).setDateLastWithdrawn(
                nf.stringToLong(items[1]));
        atmST.store.accountArrayList.get(accountPointer).setAmountWithdrawnToday(
                nf.stringToDouble(items[2]));
        atmST.store.fileOut(so.getDataFileLocation());
    
    }

    /**
     * Updates the balance, amount withdrawn today and date last withdrawn account fields from the data
     * in the withdrawalPDU string from the input from the client
     * It also adds a withdrawal transaction in the accounts transaction array list
     * @param withdrawelPDU the input from the client (string) containing the withdrawal amounts
     */
    public void updateBalanceFromWithdrawal(String withdrawelPDU) {    	
        String[] items = splitString(withdrawelPDU);
	    
        double withdrawn = atmST.store.accountArrayList.get(accountPointer).getBalance()
                - nf.stringToDouble(items[1]);
	 	    
        atmST.store.accountArrayList.get(accountPointer).setBalance(
                nf.stringToDouble(items[1]));
        atmST.store.accountArrayList.get(accountPointer).setAmountWithdrawnToday(
                nf.stringToDouble(items[2]));
        atmST.store.accountArrayList.get(accountPointer).setDateLastWithdrawn(
                nf.stringToLong(items[3]));   	
        atmST.store.accountArrayList.get(accountPointer).addTransaction(
                getDateFormat(items[3]) + " - Withdrawn: " + "\u00A3"
                + nf.decimalFormatter(withdrawn));
    	
        atmST.store.fileOut(so.getDataFileLocation());
    
    }
    
    /**
     * Updates the balance, amount withdrawn today and date last withdrawn account fields from the data
     * in the depositPDU string from the input from the client
     * It also adds a deposit transaction in the accounts transaction array list
     * @param depositPDU the input from the client (string) containing the deposit amounts
     */
    public void updateBalanceFromDeposit(String depositPDU) {    	
        String[] items = splitString(depositPDU);
	    
        double deposited = nf.stringToDouble(items[1])
                - atmST.store.accountArrayList.get(accountPointer).getBalance();
	 	    
        atmST.store.accountArrayList.get(accountPointer).setBalance(
                nf.stringToDouble(items[1]));    		
        atmST.store.accountArrayList.get(accountPointer).addTransaction(
                getDateFormat(items[2]) + " - Deposited: " + "\u00A3"
                + nf.decimalFormatter(deposited));
    	
        atmST.store.fileOut(so.getDataFileLocation());
    
    }
    
    /**
     * Formats the data from a long number into a readable data format
     * @param dateString the long number in the form of a string of the date to be formatted
     * @return dateString the formatted date in the form of a readable date string
     */
    public String getDateFormat(String dateString) {
        Date date = new Date(0);

        date.setTime(nf.stringToLong(dateString));
	    
        String DATE_FORMAT = "dd/MM/yyyy HH:MM:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);	
        Calendar calender = Calendar.getInstance();

        calender.setTime(date);
		
        dateString = sdf.format(calender.getTime());
        return dateString;
    }
    
    /**
     * Iterates through the account array list in the store to determine if a particular account
     * exists for the purpose of retrieving the account information
     * @param theAccountNo the entered account number input from the client
     * return boolean true if account exists, false otherwise
     */
    public boolean accountExists(String theAccountNo) { 
    	    	
        for (int i = 0; i < atmST.store.accountArrayList.size() - 1; i++) {
            if (atmST.store.accountArrayList.size() == 0) {
                return false;
            } else if (nf.numberToString(atmST.store.accountArrayList.get(i).getAccountNo()).equalsIgnoreCase(
                    theAccountNo)) {
                this.accountNo = theAccountNo;
                accountPointer = i;
                return true;
            }
        }
        return false;    	
    }    
}
