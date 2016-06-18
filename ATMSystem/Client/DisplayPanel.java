import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.text.DecimalFormat;

import javax.swing.JLabel;


/**
 * This class deals with objects on the display panel in which constantly changes depending on the state
 * of the ATMClientProtocol class, in which is a parameter passed into this class which this class
 * refers to. Objects on the panel are messages to the user, allowing the user to make withdrawals, check
 * their balance, deposit cash and change pin number
 * 
 * @author Jamie Brindle (06352322)
 */
public class DisplayPanel {

    protected ATMClientProtocol atmCP;
    protected JLabel mainMessage, mainMessage2, mainMessage3, subMessage1, subMessage2, subMessage3, subMessage4,
            subMessage5, subMessage6, subMessage7, subMessage8, hurry1, hurry2, hurry3, hurry4;
    protected int panelWidth, panelHeight;
    protected TextArea mainTextArea;
    protected Dimension d, d2, d3;
    protected Font mainMessageFont, mainMessageFont2, mainMessageFont3, subMessageFont;

    /**
     * Constructor of the DisplayPanel class
     * @param atmCP The ATMClientProtocol class which calls this class
     */
    public DisplayPanel(ATMClientProtocol atmCP) {
        this.atmCP = atmCP;

        // mainMessages

        mainMessage = new JLabel();
        mainMessage.setHorizontalAlignment(JLabel.CENTER);

        mainMessage2 = new JLabel();
        mainMessage2.setHorizontalAlignment(JLabel.CENTER);

        mainMessage3 = new JLabel();
        mainMessage3.setHorizontalAlignment(JLabel.CENTER);

        // panelSizes

        panelWidth = atmCP.atmGUI.display.getWidth();
        panelHeight = atmCP.atmGUI.display.getHeight();

        // mainTextArea

        mainTextArea = new TextArea("", 25, 15, 3);
        mainTextArea.setBounds(panelWidth / 2 - 90 / 2, 200, 90, 25);
        mainTextArea.addKeyListener(atmCP);

        // subMessages

        subMessage1 = new JLabel();		
        subMessage1.setHorizontalAlignment(JLabel.LEFT);
        int sub1Y = atmCP.atmGUI.left1.getY() + 5;

        subMessage1.setBounds(0, sub1Y, 225, 20);

        subMessage2 = new JLabel();
        subMessage2.setHorizontalAlignment(JLabel.LEFT);
        int sub2Y = atmCP.atmGUI.left2.getY() + 5;

        subMessage2.setBounds(0, sub2Y, 225, 20);

        subMessage3 = new JLabel();
        subMessage3.setHorizontalAlignment(JLabel.LEFT);
        int sub3Y = atmCP.atmGUI.left3.getY() + 5;

        subMessage3.setBounds(0, sub3Y, 225, 20);

        subMessage4 = new JLabel();
        subMessage4.setHorizontalAlignment(JLabel.LEFT);
        int sub4Y = atmCP.atmGUI.left4.getY() + 5;

        subMessage4.setBounds(0, sub4Y, 225, 20);

        subMessage5 = new JLabel();
        subMessage5.setHorizontalAlignment(JLabel.RIGHT);
        int sub5Y = atmCP.atmGUI.right1.getY() + 5;

        subMessage5.setBounds(0, sub5Y, panelWidth, 20);

        subMessage6 = new JLabel();
        subMessage6.setHorizontalAlignment(JLabel.RIGHT);
        int sub6Y = atmCP.atmGUI.right2.getY() + 5;

        subMessage6.setBounds(0, sub6Y, panelWidth, 20);

        subMessage7 = new JLabel();
        subMessage7.setHorizontalAlignment(JLabel.RIGHT);
        int sub7Y = atmCP.atmGUI.right3.getY() + 5;

        subMessage7.setBounds(0, sub7Y, panelWidth, 20);

        subMessage8 = new JLabel();		
        subMessage8.setHorizontalAlignment(JLabel.RIGHT);
        int sub8Y = atmCP.atmGUI.right4.getY() + 5;		

        subMessage8.setBounds(0, sub8Y, panelWidth, 20);

        // mainFonts

        mainMessageFont = new Font(mainMessage.getFont().getName(),
                mainMessage.getFont().getStyle(), 18);

        mainMessageFont2 = new Font(mainMessage.getFont().getName(),
                mainMessage.getFont().getStyle(), 12);

        mainMessageFont3 = new Font(mainMessage.getFont().getName(),
                subMessage1.getFont().getStyle(), 13);

        subMessageFont = new Font(subMessage1.getFont().getName(),
                subMessage1.getFont().getStyle(), 14);

        // set sub messages to fonts

        subMessage1.setFont(subMessageFont);
        subMessage2.setFont(subMessageFont);
        subMessage3.setFont(subMessageFont);
        subMessage4.setFont(subMessageFont);
        subMessage5.setFont(subMessageFont);
        subMessage6.setFont(subMessageFont);
        subMessage7.setFont(subMessageFont);
        subMessage8.setFont(subMessageFont);

        // Add components
        atmCP.atmGUI.display.add(mainMessage);
        atmCP.atmGUI.display.add(mainMessage2);
        atmCP.atmGUI.display.add(mainMessage3);
        atmCP.atmGUI.display.add(mainTextArea);
        atmCP.atmGUI.display.add(subMessage1);
        atmCP.atmGUI.display.add(subMessage2);
        atmCP.atmGUI.display.add(subMessage3);
        atmCP.atmGUI.display.add(subMessage4);
        atmCP.atmGUI.display.add(subMessage5);
        atmCP.atmGUI.display.add(subMessage6);
        atmCP.atmGUI.display.add(subMessage7);
        atmCP.atmGUI.display.add(subMessage8);

        // pack the frame
        ATMGUI.frame.pack();	

    }

    /**
     * Loads problem with server screen - sets component properties
     */
    
    public void loadIncorrectPinDigits() {
    	mainTextArea.setText("");
    	mainMessage2.setText("Make Sure Your Pin is 4 digits Long");
    	mainMessage2.setFont(mainMessageFont3);
    	d3 = mainMessage2.getMinimumSize();
    	mainMessage2.setBounds(0, 245, panelWidth, d3.height);
    	mainMessage2.setForeground(Color.red);
    	mainMessage2.setVisible(true);
    }
    
    public void loadProblemWithServerScreen() {
        clearDisplayPanel();
        mainMessage.setText("Problem Connecting To Server");		
        mainMessage2.setText("Please Try Again Later");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont);
        mainMessage.setForeground(Color.black);
        mainMessage2.setForeground(Color.black);
        d = mainMessage.getMinimumSize();
        d2 = mainMessage2.getMinimumSize();
        mainMessage.setBounds(0, 120, panelWidth, d.height);		
        mainMessage2.setBounds(0, 190, panelWidth, d2.height);
		
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);
    }
	
    /**
     * Loads enter account number screen - sets component properties
     */
    public void loadEnterAccountScreen() {
        clearDisplayPanel();
        mainMessage.setText("Please Enter Your Account Number");
        mainMessage3.setText("You May Use '4000001' for Testing Purposes");
        mainMessage.setFont(mainMessageFont);
        mainMessage3.setFont(mainMessageFont2);
        mainMessage.setForeground(Color.black);
        mainMessage3.setForeground(Color.black);
        d = mainMessage.getMinimumSize();		
        d3 = mainMessage3.getMinimumSize();	
        mainMessage.setBounds(0, 120, panelWidth, d.height);
        mainMessage3.setBounds(0, 280, panelWidth, d3.height);
        mainMessage.setVisible(true);
        mainMessage3.setVisible(true);
        mainTextArea.setVisible(true);
    }
	
    /**
     * Loads no account exists screen - sets component properties
     */
    public void loadNoAccountExistsScreen() {		
        clearDisplayPanel();
        mainMessage.setText("The Account Number You Entered Doesn't Exist");
        mainMessage2.setText("Please Try Again in a Moment");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont);
        mainMessage2.setForeground(Color.black);
        d2 = mainMessage2.getMinimumSize();
        mainMessage2.setBounds(0, 190, panelWidth, d2.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);		
    }
	
    /**
     * Loads account already locked screen- sets component properties
     */
    public void loadAccountAlreadyLocked() {
        clearDisplayPanel();
        mainMessage.setText("Sorry, This Account is Locked");
        mainMessage2.setText("Please Contact your Bank or Building Society");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont);
        mainMessage2.setForeground(Color.black);
        d2 = mainMessage2.getMinimumSize();
        mainMessage2.setBounds(0, 190, panelWidth, d2.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);

    }

    /**
     * Loads enter pin number screen - sets component properties
     */
    public void loadEnterPinNoScreen() {
        clearDisplayPanel();			
        mainMessage.setText("Please Enter Your Pin Number");
        mainMessage3.setText("You May Use '5678' for Testing Purposes");
        mainMessage.setVisible(true);
        mainMessage3.setVisible(true);
        mainTextArea.setVisible(true);		
    }
	
    /**
     * Loads re-enter pin number sceeen - sets component properties
     */
    public void loadReEnterPinScreen() {
        clearDisplayPanel();
		
        int pinAttemps = 3 - atmCP.pinAttempts;

        mainMessage2.setText(
                "Wrong Pin, Please Try Again: " + pinAttemps
                + " Attempts Remaining");
        mainMessage2.setForeground(Color.red);
        mainMessage2.setFont(mainMessageFont2);
        d2 = mainMessage2.getMinimumSize();
        mainMessage2.setBounds(0, 300, panelWidth, d2.height);	
        mainTextArea.setVisible(true);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);			
    }
	
    /**
     * Loads account now locked screen - sets component properties
     */
    public void loadAccountNowLocked() {
        clearDisplayPanel();
        mainMessage.setText("Sorry, Your Account is Now Locked");
        mainMessage2.setText("Please Contact Your Bank or Building Society");
        mainMessage2.setForeground(Color.black);
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont);
        d2 = mainMessage2.getMinimumSize();
        mainMessage2.setBounds(0, 190, panelWidth, d2.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);
    }
	
    /**
     * Loads main screen - sets component properties
     */
    public void loadMainScreen() {
        clearDisplayPanel();
        mainMessage.setText("Please Choice a Task");
        mainMessage.setFont(mainMessageFont);
        mainMessage.setBounds(0, 70, panelWidth, d.height);
		
        subMessage1.setText("<View Balance");
        subMessage1.setVisible(true);

        subMessage2.setText("<Withdraw Cash");
        subMessage2.setVisible(true);
		
        subMessage3.setText("<Deposit Cash");
        subMessage3.setVisible(true);
		
        subMessage4.setText("<Change Pin");
        subMessage4.setVisible(true);
		
        subMessage8.setText("Eject Card>");
        subMessage8.setVisible(true);
		
        mainMessage.setVisible(true);
    }
	
    /**
     * Loads goodbye message screen - sets component properties
     */
    public void loadGoodbyeMessage() {
        clearDisplayPanel();			
        mainMessage.setText("Thankyou for Using This Virtual ATM Machine");
        mainMessage2.setText("GoodBye...");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont);
        mainMessage2.setForeground(Color.black);
        d = mainMessage.getMinimumSize();
        d2 = mainMessage2.getMinimumSize();
        mainMessage.setBounds(0, 120, panelWidth, d.height);
        mainMessage2.setBounds(0, 190, panelWidth, d2.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);
    }
	
    /**
     * Loads balance screen - sets component properties
     */
    public void loadBalance() {
        clearDisplayPanel();	
		
        mainMessage.setText("Your Current Balance is:");
        mainMessage2.setText(
                "\u00A3" + getCurrencyFormat(atmCP.balance));
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont);
        mainMessage2.setForeground(Color.black);
        d = mainMessage.getMinimumSize();
        d2 = mainMessage2.getMinimumSize();
        mainMessage.setBounds(0, 120, panelWidth, d.height);
        mainMessage2.setBounds(0, 190, panelWidth, d2.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);
			
        subMessage4.setText("<Go Back");
        subMessage8.setText("Eject Card>");
        subMessage4.setVisible(true);
        subMessage8.setVisible(true);
    }
	
    /**
     * Loads withdraw cash screen - sets component properties
     */
    public void loadWithdrawCashScreen() {
        clearDisplayPanel();			
        mainMessage.setText("Please Select or Type the Amount to Withdraw");
        mainMessage2.setText(
                "You're Able to Withdraw a Maximum of " + "\u00A3"
                + getCurrencyFormat(atmCP.actualMaxWithdraw)
                + " Today");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont2);
        mainMessage2.setForeground(Color.black);
        d2 = mainMessage2.getMinimumSize();
        mainMessage.setBounds(0, 50, panelWidth, d.height);
        mainMessage2.setBounds(0, 110, panelWidth, d2.height);
        mainMessage2.setVisible(true);
        mainMessage.setVisible(true);
        mainTextArea.setVisible(true);
		
        subMessage1.setText("<" + "\u00A3" + "10");
        subMessage2.setText("<" + "\u00A3" + "20");
        subMessage3.setText("<" + "\u00A3" + "40");
        subMessage4.setText("<Go Back");
        subMessage5.setText("\u00A3" + "60>");
        subMessage6.setText("\u00A3" + "80>");
        subMessage7.setText("\u00A3" + "100>");
        subMessage8.setText("Eject Card>");		
		
        subMessage1.setVisible(true);
        subMessage2.setVisible(true);
        subMessage3.setVisible(true);
        subMessage4.setVisible(true);
        subMessage5.setVisible(true);
        subMessage6.setVisible(true);
        subMessage7.setVisible(true);
        subMessage8.setVisible(true);
    }
	
    /**
     * Loads goodbye screen after withdrawal - sets component properties
     */
    public void loadWithdrawnGoodbyeScreen() {
        clearDisplayPanel();		
        mainMessage.setText("Thankyou for Using This Virtual ATM Machine");
        mainMessage2.setText("Be Sure to Take Your Card and Cash, Goodbye...");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont);
        mainMessage2.setForeground(Color.black);
        d = mainMessage.getMinimumSize();
        d2 = mainMessage2.getMinimumSize();
        mainMessage.setBounds(0, 120, panelWidth, d.height);
        mainMessage2.setBounds(0, 190, panelWidth, d.height);
        mainMessage.setVisible(true);	
        mainMessage2.setVisible(true);
    }
	
    /**
     * Loads goodbye screen after deposit - sets component properties
     */
    public void loadDepositedGoodbyeScreen() {
        clearDisplayPanel();		
        mainMessage.setText("Thankyou for Using This Virtual ATM Machine");
        mainMessage2.setText("Your Deposit Was Successful, Goodbye...");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont);
        mainMessage2.setForeground(Color.black);
        d = mainMessage.getMinimumSize();
        d2 = mainMessage2.getMinimumSize();
        mainMessage.setBounds(0, 120, panelWidth, d.height);
        mainMessage2.setBounds(0, 190, panelWidth, d.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);
    }
	
    /**
     * Loads withdraw cash error screen - sets component properties
     */
    public void loadWithdrawingError() {
        clearDisplayPanel();			
        mainMessage.setText("Please Re-Enter an Amount");
        mainMessage2.setText(
                "The Amount You Entered is Too High or Not In Multiples of "
                        + "\u00A3" + "10");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont2);
        mainMessage2.setForeground(Color.black);
        d = mainMessage.getMinimumSize();
        d2 = mainMessage2.getMinimumSize();
        mainMessage.setBounds(0, 120, panelWidth, d.height);
        mainMessage2.setBounds(0, 190, panelWidth, d2.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);
    }
	
    /**
     * Loads deposit error screen - sets component properties
     */
    public void loadDepositingError() {
        clearDisplayPanel();			
        mainMessage.setText("Please Re-Enter an Amount");
        mainMessage2.setText(
                "The Amount You Entered is Not In Multiples of " + "\u00A3"
                + "5");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont3);
        mainMessage2.setForeground(Color.black);
        d = mainMessage.getMinimumSize();
        d2 = mainMessage2.getMinimumSize();
        mainMessage.setBounds(0, 120, panelWidth, d.height);
        mainMessage2.setBounds(0, 190, panelWidth, d2.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);
    }
	
    /**
     * Loads deposit cash screen screen - sets component properties
     */
    public void loadDepositCashScreen() {
        clearDisplayPanel();			
        mainMessage.setText(
                "Type the Amount To Deposit and Place the Money in the Envelope");
        mainMessage2.setText(
                "Then Place the Envelope Back into the Machine and Press 'Done'");
        mainMessage3.setText(
                "Only Insert Notes into the Machine and Enter Multiples of "
                        + "\u00A3" + "5");
        mainMessage.setFont(mainMessageFont3);
        mainMessage2.setFont(mainMessageFont3);
        mainMessage3.setFont(mainMessageFont2);
        mainMessage2.setForeground(Color.black);
        mainMessage3.setForeground(Color.red);
        d2 = mainMessage2.getMinimumSize();
        d3 = mainMessage3.getMaximumSize();
        mainMessage.setBounds(0, 50, panelWidth, d.height);
        mainMessage2.setBounds(0, 110, panelWidth, d2.height);
        mainMessage3.setBounds(0, 250, panelWidth, d3.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);
        mainMessage3.setVisible(true);
        mainTextArea.setVisible(true);
		
        subMessage1.setText("<Done");
        subMessage4.setText("<Go Back");
        subMessage8.setText("Eject Card>");		
		
        subMessage1.setVisible(true);
        subMessage4.setVisible(true);
        subMessage8.setVisible(true);
    }
	
    /**
     * Loads enter new pin number screen - sets component properties
     */
    public void loadEnterNewPinScreen() {
        clearDisplayPanel();
        mainMessage.setText("Please Enter your New Desired Pin Number");
        mainMessage3.setText("Your Pin Number Must Be 4 Digits Long");
        mainMessage.setFont(mainMessageFont);
        mainMessage3.setFont(mainMessageFont3);
        mainMessage.setForeground(Color.black);
        mainMessage3.setForeground(Color.black);
        d = mainMessage.getMinimumSize();		
        d3 = mainMessage3.getMinimumSize();	
        mainMessage.setBounds(0, 80, panelWidth, d.height);
        mainMessage3.setBounds(0, 130, panelWidth, d3.height);	
        mainMessage.setVisible(true);
        mainMessage3.setVisible(true);
        mainTextArea.setVisible(true);
 	
    }

    /**
     * Loads re-enter new pin number screen - sets component properties
     */
    public void loadReEnterNewPinScreen() {
    	mainTextArea.setText("");
        mainMessage.setText("Please Re-Enter Your New Desired Pin");
        mainMessage2.setVisible(false);
 		
    }

    /**
     * Loads pin change successful screen - sets component properties
     */
    public void loadChangePinSuccessful() {
        clearDisplayPanel();		
        mainMessage.setText("Your Pin Change Was Successful");
        d = mainMessage.getMinimumSize();
        mainMessage.setBounds(0, panelHeight / 2 - d.height / 2, panelWidth,
                d.height);
        mainMessage.setVisible(true);
    }
	
    /**
     * Loads pin change unsuccessful screen - sets component properties
     */
    public void loadChangePinUnSuccessful() {
        clearDisplayPanel();
        mainMessage.setText("Those Pins Do Not Match, Pin Not Changed");
        mainMessage2.setText("Please Try Again in a Moment");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont);
        mainMessage2.setForeground(Color.black);
        d2 = mainMessage2.getMinimumSize();
        mainMessage.setBounds(0, 120, panelWidth, d2.height);
        mainMessage2.setBounds(0, 190, panelWidth, d2.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);

    }
    
    /**
     * Loads account already active screen - sets component properties
     */
    public void loadAccountAlreadyActive() {
        clearDisplayPanel();
        mainMessage.setText("This Account Is Already In Use");
        mainMessage2.setText("Please Try Again Later");
        mainMessage.setFont(mainMessageFont);
        mainMessage2.setFont(mainMessageFont);
        mainMessage2.setForeground(Color.black);
        d2 = mainMessage2.getMinimumSize();
        mainMessage.setBounds(0, 120, panelWidth, d2.height);
        mainMessage2.setBounds(0, 190, panelWidth, d2.height);
        mainMessage.setVisible(true);
        mainMessage2.setVisible(true);
    }
	
    /**
     * This method takes a number in the form of a string and formats it
     * to have a minimum and maximum of 2 decimal places, typical of english
     * pound sterlin 
     * @param aString the string to be formatted
     * @return aString the formatted string
     */
    public String getCurrencyFormat(String aString) {
        double aDouble = Double.parseDouble(aString);
		
        DecimalFormat myFormatter = new DecimalFormat("#.00");

        aString = myFormatter.format(aDouble);
				   
        return aString;
    }
	
    /**
     * Sets sub messages to not visible
     */
    public void removeSubMessages() {
        subMessage1.setVisible(false);
        subMessage2.setVisible(false);
        subMessage3.setVisible(false);
        subMessage4.setVisible(false);
        subMessage5.setVisible(false);
        subMessage6.setVisible(false);
        subMessage7.setVisible(false);
        subMessage8.setVisible(false);

    }
	
    /**
     * Clears the display panel by setting all objects to not visible
     */
    public void clearDisplayPanel() {
        mainTextArea.setText("");
        mainMessage.setVisible(false);
        mainMessage2.setVisible(false);
        mainMessage3.setVisible(false);
        mainTextArea.setVisible(false);
        removeSubMessages();
    }
	
}
