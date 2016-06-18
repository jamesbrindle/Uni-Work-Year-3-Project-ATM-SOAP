import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * Used to create account records and to set and get account record field 
 * information using ‘getter’ and ‘setter’ methods
 * @author Jamie Brindle (06352322)
 */
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    protected int accountNo;
    private int pinNo;
    private String title;
    private String firstName;
    private String lastName;
    private String firstLineAddress;
    private String secondLineAddress;
    private String city;
    private String county;
    private String postCode;
    private String telephone;
    private double maxDayWithdrawel;
    private double balance;
    private double amountWithdrawnToday;
    private boolean isLocked;
    private long dateLastWithdrawn;
    private ArrayList<String> transactions;
    private NumeralFunctions nf;
	
    /**
     * Account constructor.
     * Also instantiate certain fields that are not necessarily required
     * @param accountNo The account number required as a primary key or unique identifier
     */
    public Account(int accountNo) {
        this.accountNo = accountNo;
        this.secondLineAddress = "";
        this.telephone = "";
        this.balance = 0.00;
        this.maxDayWithdrawel = 0.00;
        this.isLocked = false;
        this.transactions = null;
        this.amountWithdrawnToday = 0.00;
        this.dateLastWithdrawn = 0;
        this.nf = new NumeralFunctions();
    }
	
    /**
     * Getter method for accountNo
     * @return accountNo Account record account number integer
     */
    public int getAccountNo() {
        return accountNo;
    }
	
    /**
     * Getter method for pinNo
     * @return pinNo Account record pin number integer
     */
    public int gettPinNo() {
        return pinNo;
    }
	
    /**
     * Getter method for title
     * @return title Account record name title string
     */
    public String getTitle() {
        return title;
    }
	
    /**
     * Getter method for firstName
     * @return firstName Account record first name string
     */
    public String getFirstName() {
        return firstName;
    }
	
    /**
     * Getter method for lastName
     * @return lastName Account record last name string
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Getter method for firstLineAddress
     * @return firstLineAddress Account record account number string
     */
    public String getFirstLineAddress() {
        return firstLineAddress;
    }
	
    /**
     * Getter method for secondLineAddress
     * @return secondLineAddress Account record second name string
     */
    public String getSecondLineAddress() {
        return secondLineAddress;
    }
	
    /**
     * Getter method for city
     * @return city Account record city string
     */
    public String getCity() {
        return city;
    }
	
    /**
     * Getter method for county
     * @return county Account record county string
     */
    public String getCounty() {
        return county;
    }
	
    /**
     * Getter method for postCode
     * @return postCode Account record post code string
     */
    public String getPostCode() {
        return postCode;
    }
	
    /**
     * Getter method for telephone
     * @return telephone Account record telephone number string
     */
    public String getTelephone() {
        return telephone;
    }
	
    /**
     * Getter method for maxDayWithdrawel
     * @return maxDayWithdrawel Account record maximum daily withdrawal limit double
     */
    public double getMaxDayWithDrawel() {
        return maxDayWithdrawel;
    }
	
    /**
     * Getter method for balance
     * @return balance Account record current balance double
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Getter method for isLocked
     * @return isLocked Account record is account locked boolean
     */
    public boolean getIsAccountLocked() {
        return isLocked;
    }
	
    /**
     * Getter method for transactions
     * @return transactions Account record transaction array list
     */
    public ArrayList<String> getTransactions() {
        return transactions;
    }
    
    /**
     * Getter method for dateLastWithdrawn
     * @return dateLastWithdrawn Account date of last withdrawn in the form of a long
     */
    public long getDateLastWithdrawn() {    	
        return dateLastWithdrawn;
    }
    
    /**
     * Getter method for amountWithdrawnToday
     * @return amountWithdrawnToday Account amount withdrawn today double
     */
    public double getAmountWithdrawnToday() {
        return amountWithdrawnToday;
    }
	
    // ------------------------------------------------------------------------
	
    /**
     * Setter method for account record pin number
     * @param pinNo The pin number integer
     */
    public void setPinNo(int pinNo) {
        this.pinNo = pinNo;
    }
	
    /**
     * Setter method for account record title
     * @param title the name title string
     */
    public void setTitle(String title) {
        this.title = title;
    }
	
    /**
     * Setter method for account record first name
     * @param firstName the first name string
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
	
    /**
     * Setter method for account record last name
     * @param lastName the last name string
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * Setter method for account record first line of address
     * @param firstLineAddress the first line of address string
     */
    public void setFirstLineAddress(String firstLineAddress) {
        this.firstLineAddress = firstLineAddress;
    }
	
    /**
     * Setter method for account record second line of address
     * @param secondLineAddress the second line of the address string
     */
    public void setSecondLineAddress(String secondLineAddress) {
        this.secondLineAddress = secondLineAddress;
    }
	
    /**
     * Setter method for account record city
     * @param city the city string
     */
    public void setCity(String city) {
        this.city = city;
    }
	
    /**
     * Setter method for account record county
     * @param county the county string
     */
    public void setCounty(String county) {
        this.county = county;
    }
	
    /**
     * Setter method for account record post code
     * @param postCode the post code string
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
	
    /**
     * Setter method for account record telephone
     * @param telephone the telephone number string
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
	
    /**
     * Setter method for account record maximum daily withdrawal limit
     * @param maxDayWithdrawel the maximum daily withdrawal limit double
     */
    public void setMaxDayWithDrawel(double maxDayWithdrawel) {
        this.maxDayWithdrawel = maxDayWithdrawel;
    }
	
    /**
     * Setter method for account record balance
     * @param balance the balance double
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public void setAccountLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
	
    /**
     * Setter method for account record transaction list
     * @param transactions the transactions array list
     */
    public void setTransactions(ArrayList<String> transactions) {
        this.transactions = transactions;
    }

    /**
     * Adds a string to the transaction array list, which will be a transaction entry
     * @param aTransaction the transactions string
     */
    public void addTransaction(String aTransaction) {
        this.transactions.add(aTransaction);
    }
    
    /**
     * Setter method for account date of last withdrawn
     * @param dateLastWithdrawn the date last withdrawn long
     */
    public void setDateLastWithdrawn(long dateLastWithdrawn) {
        this.dateLastWithdrawn = dateLastWithdrawn;
    }
    
    /**
     * Setter method for account amount withdrawn today
     * @param amountWithdrawnToday the amount withdrawn today double
     */
    public void setAmountWithdrawnToday(double amountWithdrawnToday) {
        this.amountWithdrawnToday = amountWithdrawnToday;
    }
    
    // ---------------------------------------------------------------------------	
	    
    /**
     * A toString like method allowing the printing of all account fields in
     * a more readable way
     * @return A string consisting of all the fields of the account record in
     * a more usable way
     */
    public String accountToString() {
        this.nf = new NumeralFunctions();
        String accountString = "";
		
        accountString = accountString + "Account No: " + accountNo + "\n";
        accountString = accountString + "Pin No: " + pinNo + "\n\n";
        accountString = accountString + "Name: " + title + " " + firstName + " "
                + lastName + "\n\n";
        accountString = accountString + "Address: \n" + firstLineAddress + "\n";
		
        if (!(secondLineAddress.equalsIgnoreCase(""))) {
            accountString = accountString + secondLineAddress + "\n";
        }
									  
        accountString = accountString + city + "\n" + county + "\n" + postCode
                + "\n\n";
		
        if (!(telephone.equalsIgnoreCase(""))) {
            accountString = accountString + "Telephone: " + telephone + "\n\n";
        }
        
        accountString = accountString + "Amount Withdrawn Today: \u00A3"
                + nf.decimalFormatter(amountWithdrawnToday) + "\n";
				
        accountString = accountString + "Maximum Daily WithDrawel Limit: \u00A3"
                + nf.decimalFormatter(maxDayWithdrawel) + "\n";        
        if (dateLastWithdrawn == 0) {
            accountString = accountString + "Date Last Withdrawn: "
                    + "Never Withdrawn" + "\n";
        } else {
            Date date = new Date(0);

            date.setTime(dateLastWithdrawn);
            accountString = accountString + "Date Last Withdrawn: "
                    + date.toString() + "\n";
        }       
        accountString = accountString + "Balance: \u00A3"
                + nf.decimalFormatter(balance) + "\n";
        accountString = accountString + "Account Locked: " + isLocked + "\n\n";
        accountString = accountString + "Account Transactions: \n";
		
        if (transactions.size() == 0) {
            accountString = accountString + "There are no current transactions";
        } else {
            for (int i = 0; i < transactions.size(); i++) {
                accountString = accountString + transactions.get(i).toString()
                        + "\n";
            }
        }
		
        accountString = accountString + "\n------------------------------";
		
        return accountString;
		
    }
	
}
