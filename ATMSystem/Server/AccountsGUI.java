import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * Used as the main class which builds the Account Records GUI
 * the AccountsGUIButtonManager and the AccountsGUIListener classes
 * are also used for the purpose of expanding this class using association
 * in which is where most of the complex work is done
 * @author Jamie Brindle (06352322)
 */
public class AccountsGUI extends JFrame implements WindowListener {
	
    private static final long serialVersionUID = 1L;
    private Container container;
    static JFrame frame;
    
    protected AccountsGUIButtonManager accountsGBM;
    protected AccountsGUIListener accountsGUIListener;
	
    // panels
	
    private JPanel accountInfo, transInfo, searchBar, navigation;
	
    // layouts
	
    private GridBagConstraints accountInfoLayout, transInfoLayout, navigationLayout;
	
    // accountInfoElements
	
    private JLabel accountNoLabel, titleLabel, firstNameLabel, lastNameLabel,
            firstLineAddrLabel, secondLineAddrLabel, cityLabel, countyLabel,
            postCodeLabel, telephoneLabel, pinNumberLabel, transactionListLabel;
	
    protected JTextField accountNoTextField, titleTextField, firstNameTextField, lastNameTextField,
            firstLineAddrTextField, secondLineAddrTextField, cityTextField, countyTextField,
            postCodeTextField, telephoneTextField, pinNumberTextField;
	
    // transInfoElements
	
    private JLabel balanceLabel, amountWithdrawnTodayLabel, maxDayWithdrawLabel, accountLockedLabel;		
    protected JTextField balanceTextField, amountWithdrawnTodayTextField, maxDayWithdrawTextField;		
    protected JTextArea transactionListTextArea;	
    protected JCheckBox accountLockedCheckBox;
    protected JScrollPane transactionListScrollPane; 
	
    // searchBarElements
	
    private JLabel searchBarLabel;	
    protected JTextField searchBarTextField;
    protected JButton searchBarSearchButton;
	
    // navigationElements
	
    protected JButton firstNavButton, lastNavButton, nextNavButton, previousNavButton,
            newRecordNavButton, saveNavButton, editNavButton, removeNavButton;
	
    private Color red = new Color(255, 0, 0);
    protected JLabel warningLabel;
    protected String warningMessage;
    protected boolean isEditing = false;
	
    // menu Elements
	
    protected JMenuBar menuBar;
    protected JMenu menu;
    protected JMenuItem menuItem1, menuItem2, menuItem3, menuItem4;
    
    /**
     * AccountsGUI Constructor
     */
    public AccountsGUI() {
        runGUI();
	             
    }
    
    /**
     * Builds the standard GUI visuals (text field, labels, 
     * buttons and text area objects)
     */
    public void runGUI() {
        frame = new JFrame("Accounts Manager GUI");
        frame.addWindowListener(this);
    	 
        accountsGBM = new AccountsGUIButtonManager(this);
        accountsGUIListener = new AccountsGUIListener(this);
    	
        container = frame.getContentPane();
        container.setLayout(new BorderLayout());
         
        accountInfo = new JPanel(new GridBagLayout());
        accountInfoLayout = new GridBagConstraints();
         
        transInfo = new JPanel(new GridBagLayout());
        transInfoLayout = new GridBagConstraints();
         
        searchBar = new JPanel(new FlowLayout());
         
        navigation = new JPanel(new GridBagLayout());
        navigationLayout = new GridBagConstraints();
         
        LoadMenu(); // Frames drop down menu (i.e 'File Open')
        loadAccountInfoElements(); // Account holder (client) personal info
        loadTransInfoElements(); // Regarding balance, max withdrawal limit, transactions
         
        warningMessage = "No File Loaded";
        loadSearchBarElements(); // Used to search through account records
        loadNavigationElements(); // Used to navigate through account records
         
        setTextFieldsEditable(false); // can't edit info
        
        container.add(searchBar, BorderLayout.NORTH);
        container.add(accountInfo, BorderLayout.WEST);
        container.add(transInfo, BorderLayout.EAST);
        container.add(navigation, BorderLayout.SOUTH);
         
        setNavButtonsEnabled(false); // can't navigate when no file open
         
        frame.setJMenuBar(menuBar); // add menu to frame
        frame.setResizable(false); // don't allow to resize the frame
         
        // centre frame on screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
         
        frame.setLocation(screenWidth / 4, screenHeight / 4);
        frame.pack();
        frame.setVisible(true);
        
        this.addWindowListener(new WindowAdapter() {

            /**
             * Add a window listener thread
             */
            public void windowClosing(WindowEvent e) {

                dispose();
                System.exit(0);
				
            }
        });
         
    }
    
    /**
     * Loads the objects regarding the search bar
     */
    public void loadSearchBarElements() {
        searchBarLabel = new JLabel(
                "Please Enter Name or Account Number to Search");
        searchBar.add(searchBarLabel);
    	
        searchBarTextField = new JTextField(15);
        searchBar.add(searchBarTextField);
    	
        searchBarSearchButton = new JButton("Search");
        searchBarSearchButton.addActionListener(accountsGUIListener);
        searchBar.add(searchBarSearchButton);
    	
    }
    
    /**
     * Loads the objects regarding personal account information
     */
    public void loadAccountInfoElements() {
        accountInfoLayout.anchor = GridBagConstraints.FIRST_LINE_START;
    	
        // labels    	
    	
        accountNoLabel = new JLabel("Account Number");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 0;    	
        accountInfo.add(accountNoLabel, accountInfoLayout);
    	
        pinNumberLabel = new JLabel("Pin Number");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 1;
        accountInfo.add(pinNumberLabel, accountInfoLayout);
    	    	
        titleLabel = new JLabel("Title");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 2;
        accountInfo.add(titleLabel, accountInfoLayout);
    	    	
        firstNameLabel = new JLabel("First Name");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 3;
        accountInfo.add(firstNameLabel, accountInfoLayout);
    	
        lastNameLabel = new JLabel("Last Name");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 4;
        accountInfo.add(lastNameLabel, accountInfoLayout);
    	
        firstLineAddrLabel = new JLabel("First Line Address");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 5;
        accountInfo.add(firstLineAddrLabel, accountInfoLayout);
    	 
        secondLineAddrLabel = new JLabel("Second Line Address");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 6;
        accountInfo.add(secondLineAddrLabel, accountInfoLayout);
    	
        cityLabel = new JLabel("City");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 7;
        accountInfo.add(cityLabel, accountInfoLayout);
    	
        countyLabel = new JLabel("County");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 8;
        accountInfo.add(countyLabel, accountInfoLayout);
    	
        postCodeLabel = new JLabel("Post Code");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 9;
        accountInfo.add(postCodeLabel, accountInfoLayout);
    	
        telephoneLabel = new JLabel("Telephone");
        accountInfoLayout.gridx = 0;
        accountInfoLayout.gridy = 10;
        accountInfo.add(telephoneLabel, accountInfoLayout);	
    	
        // text fields   	
		
        accountInfoLayout.insets = new Insets(0, 10, 0, 10);
    	
        accountNoTextField = new JTextField(10);
        accountNoTextField.setEditable(false);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 0;
        accountInfo.add(accountNoTextField, accountInfoLayout);
    	
        pinNumberTextField = new JTextField(10);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 1;
        accountInfo.add(pinNumberTextField, accountInfoLayout);
    	
        titleTextField = new JTextField(10);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 2;
        accountInfo.add(titleTextField, accountInfoLayout);
    	
        firstNameTextField = new JTextField(15);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 3;
        accountInfo.add(firstNameTextField, accountInfoLayout);
    	
        lastNameTextField = new JTextField(15);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 4;
        accountInfo.add(lastNameTextField, accountInfoLayout);
    	
        firstLineAddrTextField = new JTextField(20);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 5;
        accountInfo.add(firstLineAddrTextField, accountInfoLayout);
		
        secondLineAddrTextField = new JTextField(20);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 6;
        accountInfo.add(secondLineAddrTextField, accountInfoLayout);
		
        cityTextField = new JTextField(15);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 7;
        accountInfo.add(cityTextField, accountInfoLayout);
		
        countyTextField = new JTextField(15);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 8;
        accountInfo.add(countyTextField, accountInfoLayout);
    	
        postCodeTextField = new JTextField(10);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 9;
        accountInfo.add(postCodeTextField, accountInfoLayout);
    	
        telephoneTextField = new JTextField(10);
        accountInfoLayout.gridx = 1;
        accountInfoLayout.gridy = 10;
        accountInfo.add(telephoneTextField, accountInfoLayout);
		
    }
    
    /**
     * Loads the objects regarding financial account information
     */
    public void loadTransInfoElements() {
        transInfoLayout.anchor = GridBagConstraints.FIRST_LINE_START;
    	
        balanceLabel = new JLabel("Balance");
        transInfoLayout.gridx = 0;
        transInfoLayout.gridy = 0;    	
        transInfo.add(balanceLabel, transInfoLayout);
    	
        amountWithdrawnTodayLabel = new JLabel("Amount Withdrawn Today");
        transInfoLayout.gridx = 0;
        transInfoLayout.gridy = 1;    	
        transInfo.add(amountWithdrawnTodayLabel, transInfoLayout);
    	
        maxDayWithdrawLabel = new JLabel("Maximum Daily Withdrawel Limit");
        transInfoLayout.gridx = 0;
        transInfoLayout.gridy = 2;
        transInfoLayout.insets = new Insets(0, 0, 0, 10);
        transInfo.add(maxDayWithdrawLabel, transInfoLayout);
        transInfoLayout.insets = new Insets(0, 0, 2, 0);
    	
        transactionListLabel = new JLabel("Transaction History:");
        transInfoLayout.gridx = 0;
        transInfoLayout.gridy = 4;        
        transInfo.add(transactionListLabel, transInfoLayout);
        
        accountLockedLabel = new JLabel("Account Locked?");
        transInfoLayout.gridx = 0;
        transInfoLayout.gridy = 3;
        transInfoLayout.insets = new Insets(0, 0, 10, 0);
        transInfo.add(accountLockedLabel, transInfoLayout);
        transInfoLayout.insets = new Insets(0, 0, 0, 0);
    	
        balanceTextField = new JTextField(10);
        transInfoLayout.gridx = 1;
        transInfoLayout.gridy = 0;    	
        transInfo.add(balanceTextField, transInfoLayout);
    	
        amountWithdrawnTodayTextField = new JTextField(10);
        transInfoLayout.gridx = 1;
        transInfoLayout.gridy = 1;    	
        transInfo.add(amountWithdrawnTodayTextField, transInfoLayout);
    	
        maxDayWithdrawTextField = new JTextField(10);
        transInfoLayout.gridx = 1;
        transInfoLayout.gridy = 2;    	
        transInfo.add(maxDayWithdrawTextField, transInfoLayout);
        
        accountLockedCheckBox = new JCheckBox();
        transInfoLayout.gridx = 1;
        transInfoLayout.gridy = 3;
        transInfo.add(accountLockedCheckBox, transInfoLayout);
    	
        transactionListTextArea = new JTextArea(8, 30);
        transactionListTextArea.setEditable(false);
        transactionListScrollPane = new JScrollPane(transactionListTextArea);
        transInfoLayout.gridx = 0;
        transInfoLayout.gridy = 5;
        transInfoLayout.gridwidth = 2;
        transInfo.add(transactionListScrollPane, transInfoLayout);
    	
    }
    
    /**
     * Loads the objects regarding account record navigation
     */
    public void loadNavigationElements() {
        navigationLayout.insets = new Insets(10, 2, 5, 2);
    	   	
        warningLabel = new JLabel(warningMessage);
        warningLabel.setForeground(red);
        navigationLayout.gridx = 0;
        navigationLayout.gridy = 0;
        navigationLayout.gridwidth = 8;
        navigation.add(warningLabel, navigationLayout);
    	
        navigationLayout.gridwidth = 1;
        firstNavButton = new JButton("<<");
        firstNavButton.addActionListener(accountsGUIListener);
        navigationLayout.gridx = 0;
        navigationLayout.gridy = 1;
        navigation.add(firstNavButton, navigationLayout);
    	
        previousNavButton = new JButton("<");
        previousNavButton.addActionListener(accountsGUIListener);
        navigationLayout.gridx = 1;
        navigationLayout.gridy = 1;
        navigation.add(previousNavButton, navigationLayout);
    	
        nextNavButton = new JButton(">");
        nextNavButton.addActionListener(accountsGUIListener);
        navigationLayout.gridx = 2;
        navigationLayout.gridy = 1;
        navigation.add(nextNavButton, navigationLayout);    	
    	
        lastNavButton = new JButton(">>");
        lastNavButton.addActionListener(accountsGUIListener);
        navigationLayout.gridx = 3;
        navigationLayout.gridy = 1;
        navigation.add(lastNavButton, navigationLayout);
    	
        newRecordNavButton = new JButton("New Record");
        newRecordNavButton.addActionListener(accountsGUIListener);
        navigationLayout.gridx = 4;
        navigationLayout.gridy = 1;
        navigation.add(newRecordNavButton, navigationLayout);
    	    	
        saveNavButton = new JButton("Save Record");
        saveNavButton.addActionListener(accountsGUIListener);
        saveNavButton.setEnabled(false);
        navigationLayout.gridx = 5;
        navigationLayout.gridy = 1;
        navigation.add(saveNavButton, navigationLayout);
		
        editNavButton = new JButton("Edit");
        editNavButton.addActionListener(accountsGUIListener);
        navigationLayout.gridx = 6;
        navigationLayout.gridy = 1;
        navigation.add(editNavButton, navigationLayout);
		
        removeNavButton = new JButton("Remove");
        removeNavButton.addActionListener(accountsGUIListener);
        navigationLayout.gridx = 7;
        navigationLayout.gridy = 1;
        navigation.add(removeNavButton, navigationLayout);
		
    }
    
    /**
     * Loads the frame's drop down menu, used to open, load and create stores
     * and to exit the frame
     */
    public void LoadMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        menuItem1 = new JMenuItem("Open Store"); // open a store (thus open a file)
        menuItem1.addActionListener(accountsGUIListener);
        
        menuItem2 = new JMenuItem("Save Store As");		
        menuItem2.addActionListener(accountsGUIListener);

        menuItem3 = new JMenuItem("Exit GUI"); // exit GUI, double check this is what the user wants, warn if store not saved
        menuItem3.addActionListener(accountsGUIListener);
        
        menuItem4 = new JMenuItem("New Store");
        menuItem4.addActionListener(accountsGUIListener);
        
        // adds each button to the frame
        menu.add(menuItem4);
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        
    }
    
    /**
     * Method used to order to easily change the standard warning message
     * @param message The warning message string
     */
    public void changeWarningMessage(String message) {
        warningMessage = message;
        warningLabel.setText(warningMessage);
    }
    
    /**
     * Boolean void method which sets text fields editable or uneditable
     * @param b if false - set fields uneditable, otherwise fields are editable
     */
    public void setTextFieldsEditable(boolean b) {
        if (b == false) {
            pinNumberTextField.setEditable(false);
            titleTextField.setEditable(false);
            firstNameTextField.setEditable(false); 
            lastNameTextField.setEditable(false);
            firstLineAddrTextField.setEditable(false); 
            secondLineAddrTextField.setEditable(false); 
            cityTextField.setEditable(false);
            countyTextField.setEditable(false);
            postCodeTextField.setEditable(false);
            telephoneTextField.setEditable(false);
            balanceTextField.setEditable(false);
            amountWithdrawnTodayTextField.setEditable(false);
            maxDayWithdrawTextField.setEditable(false);            
            searchBarTextField.setEnabled(true);
            accountLockedCheckBox.setEnabled(false);
            
        } else {
            pinNumberTextField.setEditable(true);
            titleTextField.setEditable(true);
            firstNameTextField.setEditable(true);
            lastNameTextField.setEditable(true);
            firstLineAddrTextField.setEditable(true);
            secondLineAddrTextField.setEditable(true);
            cityTextField.setEditable(true);
            countyTextField.setEditable(true);
            postCodeTextField.setEditable(true);
            telephoneTextField.setEditable(true);
            balanceTextField.setEditable(true);
            amountWithdrawnTodayTextField.setEditable(true); 
            maxDayWithdrawTextField.setEditable(true);            
            searchBarTextField.setEditable(true);
            accountLockedCheckBox.setEnabled(true);
        }
    }
    
    /**
     * Sets navigation buttons enabled
     * Calls accountsGUIButtonManager class, used to allow accountGUIListener class
     * to call accountsGUIButtonManager through the accountGUI class
     * @param b - boolean true - set enabled, disabled otherwise
     */
    public void setNavButtonsEnabled(boolean b) {
        accountsGBM.setNavButtonsEnabled(b);
    }
    
    /**
     * Sets navigation buttons to editing mode
     * Calls accountsGUIButtonManager class, used to allow accountGUIListener class
     * to call accountsGUIButtonManager through the accountGUI class
     * @param b - boolean true - set enabled, disabled otherwise
     */
    public void setEditNavButtonMode(boolean b) {
        accountsGBM.setEditNavButtonMode(b);
    }
    
    /**
     * Sets navigation buttons to last record mode
     * Calls accountsGUIButtonManager class, used to allow accountGUIListener class
     * to call accountsGUIButtonManager through the accountGUI class
     * @param b - boolean true - set enabled, disabled otherwise
     */
    public void setIsLast(boolean b) {
        accountsGBM.setIsLast(b);
    }
    
    /**
     * Sets navigation buttons to first record mode
     * Calls accountsGUIButtonManager class, used to allow accountGUIListener class
     * to call accountsGUIButtonManager through the accountGUI class
     * @param b - boolean true - set enabled, disabled otherwise
     */
    public void setIsFirst(boolean b) {
        accountsGBM.setIsFirst(b);
    }
    
    /**
     * Sets navigation buttons to store is empty mode
     * Calls accountsGUIButtonManager class, used to allow accountGUIListener class
     * to call accountsGUIButtonManager through the accountGUI class
     * @param b - boolean true - set enabled, disabled otherwise
     */
    public void setIsEmpty(boolean b) {
        accountsGBM.setIsEmpty(b);
    	
    }
    
    /**
     * Implements and redefines window closing event
     * @param e The window calling the event
     */
    public void windowClosing(WindowEvent e) {

        frame.dispose();
        System.exit(0);
    }

    /**
     * Implements and redefines window closed event
     * @param e The window calling the event
     */
    public void windowClosed(WindowEvent e) {}

    /**
     * Implements and redefines window Opened event
     * @param e The window calling the event
     */
    public void windowOpened(WindowEvent e) {}

    /**
     * Implements and redefines window iconified event
     * @param e The window calling the event
     */
    public void windowIconified(WindowEvent e) {}

    /**
     * Implements and redefines window deiconified event
     * @param e The window calling the event
     */
    public void windowDeiconified(WindowEvent e) {}

    /**
     * Implements and redefines window activated event
     * @param e The window calling the event
     */
    public void windowActivated(WindowEvent e) {}

    /**
     * Implements and redefines window deactivated event
     * @param e The window calling the event
     */
    public void windowDeactivated(WindowEvent e) {}
    
    /**
     * Main methods which runs the AccountsGUI class
     */
    public static void main(String[] args) {		   
        frame = new AccountsGUI();        
    }

}
