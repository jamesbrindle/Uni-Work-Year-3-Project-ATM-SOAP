import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.Timer;


/**
 * The ATMClientProtocol class deals with any processing that needs to be done with data received from
 * the server (externally) or within it self (internally), such as button clicks and any information that need
 * not be sent to the server right away. The protocol will then send messages to the server,
 * update the display panel area (what the user sees) and changes any global variables specific to a particular
 * account. A lot of work on the client side of the system is done within this class
 * 
 * @author Jamie Brindle (06352322)
 */
public class ATMClientProtocol implements ActionListener, KeyListener {

    protected ATMClient atmClient;
    protected ATMGUI atmGUI;
    protected DisplayPanel dp;
    private Timer timer1, timer2, timer3;
    private int timeAmount1, timeAmount2;
    protected int pinAttempts = 0;
    
    // ATM System States
    private boolean onEnterAccountScreen = false;
    
    protected boolean onMainScreen, onBalanceScreen, onWithdrawCashScreen, onWithdrawError,
            onEnterPinScreen, onDepositCashScreen, onDepositError, onEnterNewPinScreen,
            onReEnterNewPinScreen, pinsMatch;
    protected boolean problemConnectingToServer = false;
    
    // Pin Change Validation
    protected String newPin, reEnteredNewPin;
    
    // Account Details
    protected String pinNo, balance, maxDayWithdraw, ifLocked, 
            amountWithdrawnToday, dateLastWithdrawn, actualMaxWithdraw;
	
    /**
     * The ATMClientProtocol constructor
     * @param atmClient The calling ATMClient class in which this ATMClientProtocol class will refer back to
     */
    public ATMClientProtocol(ATMClient atmClient) {
        this.atmClient = atmClient;
        atmGUI = new ATMGUI(this); // call instance of ATMGUI (with this specific class as a parameter)
        dp = new DisplayPanel(this); // call instance of DisplayPanel(with this specific class as a parameter)
        timeAmount1 = 5000; // Preset time amount of 5 seconds
        timeAmount2 = 7000; // Preset time amount of 7 seconds
		
        resetStates();
		
        // timer1 thread which is mainly in any fair well message or the change of pin messages
        timer1 = new Timer(timeAmount1, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				
                if (!onReEnterNewPinScreen) {
                    resetStates();
                    onEnterAccountScreen = true;
                    dp.loadEnterAccountScreen();
                    timer1.stop();
                } else {
                    if (pinsMatch) {
                        resetStates();
                        onMainScreen = true;
                        dp.loadMainScreen();
                        timer1.stop();
                    } else {
                        onReEnterNewPinScreen = false;
                        onEnterNewPinScreen = true;
                        dp.loadEnterNewPinScreen();
                        timer1.stop();
                    }
                }
                       
            }
            
        });	
		
        // timer2 thread mainly dealing with displaying fair well messages resulting from user error
        timer2 = new Timer(timeAmount2, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				
                resetStates();
                pinAttempts = 0;
                onEnterAccountScreen = true;
                dp.loadEnterAccountScreen();
                timer2.stop();
            }
        });	
		
        // same as timer3 but specific to withdrawing and depositing errors
        timer3 = new Timer(timeAmount1, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				
                if (onWithdrawError) {	
                    onWithdrawCashScreen = true;
                    dp.loadWithdrawCashScreen();
                    onWithdrawError = false;
                    timer3.stop();
                } else if (onDepositError) {
                    onDepositCashScreen = true;
                    dp.loadDepositCashScreen();
                    onDepositError = false;
                    timer3.stop();
                }
            }
        });	

    }
    
    /**
     * When the client wishes to terminate it's connection with the server thread a message will be sent
     * to the server thread telling it to shut down, however, if the client can't connect to the server
     * in the first place, if it tries to send the EXIT message an exception would be thrown, this deals
     * with the close operations, which is different when client connected and not connected
     */
    public void processExit() {
        if (atmClient.problemWithServer == false) {
            atmClient.out.println("sessionFinished");
        }
    }
	
    /**
     * Executes a particular task and/or processes data depending on the nature of the data received,
     * This class deals with the messages received from the server
     * @param fromServer The string of data received from the server
     */
    public void processServerMessage(String fromServer) {
		
        if (fromServer.equalsIgnoreCase("waitingForAccount")) {
            resetStates(); // reset the states to a 'no state' form
            onEnterAccountScreen = true;            
            dp.loadEnterAccountScreen();
            
        } else if (fromServer.equalsIgnoreCase("noAccountExists")) {
            dp.loadNoAccountExistsScreen();
            timer1.start(); // results in looping back to the waiting for account number state
									
        } else if (fromServer.contains("AccountInfoPDU:")) { // if Account information received
            resetStates();
            onEnterPinScreen = true;
            constructAccountInfo(fromServer); // get the data from within the accountInfoPDU string, cast to variables
            dp.loadEnterPinNoScreen();
            					
        } else if (fromServer.equalsIgnoreCase("accountAlreadyLocked")) {
            resetStates(); // reset the states to a 'no state' form
            dp.loadAccountAlreadyLocked();
            timer1.start(); // results in looping back to the waiting for account number state
        } else if (fromServer.equalsIgnoreCase("accountAlreadyActive")) {
            resetStates(); // reset the states to a 'no state' form
            dp.loadAccountAlreadyActive();
            timer1.start(); // results in looping back to the waiting for account number state
        } else if (fromServer.equalsIgnoreCase("goodByeMessage")) {
            resetStates(); // reset the states to a 'no state' form
            dp.loadGoodbyeMessage();
            timer2.start(); // results in looping back to the waiting for account number state			
        }
    }
	
    /**
     * Executes particular tasks and / or processes data that occur internally, which basically means
     * it mainly deals with the data that the user has entered, such as checking if the user knows their
     * pin number, or checking a user has entered a valid withdrawal amount. The information is already
     * stored internally which was received in the AccountInfoPDU, it also deals with sending data
     * to the server
     * @param fromSelf The string of data received from the client (mainly the DisplayPanel class)
     */
    public void processInternalMessage(String fromSelf) {
        if (fromSelf.equalsIgnoreCase("problemWithServer")) { // if there isn't a server problem...
            resetStates();
            problemConnectingToServer = true;
            dp.loadProblemWithServerScreen();
        } else if (fromSelf.contains("pinEntered:")) { // the the message is a pin number			
            if (!(getData(fromSelf).equalsIgnoreCase((pinNo)))) { // check the pin is correct				
                pinAttempts++;
                if (pinAttempts >= 3) { // Allow 3 wrong pin attempts then lock the account and return to 
                    // waiting for account number state                	
                    processInternalMessage("lockAccount");
                    timer2.start();
                } else { 
					
                    dp.loadReEnterPinScreen();
                }
            } else { // if pin correct
                pinAttempts = 0; // reset wrong pin entered count
                resetStates();
                onMainScreen = true; 
                dp.loadMainScreen();
            }			
        } else if (fromSelf.equalsIgnoreCase("lockAccount")) { // if the account is locked, tell user, reset states
            resetStates();
            atmClient.out.println("lockAccount");
            dp.loadAccountNowLocked();
            timer2.start();
        } else if (fromSelf.equalsIgnoreCase("withdrawingCash")) {
            if (stringToDouble(dp.mainTextArea.getText())
                    <= stringToDouble(actualMaxWithdraw)
                            && stringToDouble(dp.mainTextArea.getText()) % 10
                                    == 0) { // If the cash amount entered is less than the allowed
                // withdrawal limit, and is in multiples of �10, allow withdraw
                resetStates();
                
                // alter the balance
                balance = numberToString(
                        stringToDouble(balance)
                                - stringToDouble(dp.mainTextArea.getText()));
                amountWithdrawnToday = numberToString(
                        stringToDouble(amountWithdrawnToday)
                                + stringToDouble(dp.mainTextArea.getText()));
				
                // create new date for time of withdrawal
                Date date = new Date();
				
                // send server new balance and the amount withdrawn today
                atmClient.out.println(
                        "withdrawing:" + balance + ":" + amountWithdrawnToday
                        + ":" + numberToString(date.getTime()));
                dp.loadWithdrawnGoodbyeScreen(); // show fair well screen
                timer2.start(); // go back to waiting for account number
            } else { // if amount entered is too high or in the correct form, tell user, let them try again
                resetStates();
                onWithdrawError = true;
                dp.loadWithdrawingError();
                timer3.start();
            }
        } else if (fromSelf.equalsIgnoreCase("depositingCash")) {
            if (stringToDouble(dp.mainTextArea.getText()) % 5 == 0) { // if depositing amount in multiples of �5
                resetStates();
				
                // creates new date for time of deposit
                Date date = new Date();
				
                // update balance
                balance = numberToString(
                        stringToDouble(balance)
                                + stringToDouble(dp.mainTextArea.getText()));
                
                // send server new balance and time of deposit
                atmClient.out.println(
                        "depositing:" + balance + ":"
                        + numberToString(date.getTime()));
                dp.loadDepositedGoodbyeScreen();
                timer2.start(); // go back to waiting for account number screen
            } else { // show invalid deposit amount screen, let user try again
                resetStates();
                onDepositError = true;
                dp.loadDepositingError();
                timer3.start();
            }
        } else if (fromSelf.equalsIgnoreCase("changePin")) {
            if (newPin.equalsIgnoreCase(reEnteredNewPin)) { // if both entered pins are the same
                atmClient.out.println("changePin:" + newPin); // inform server of new pin number
                pinsMatch = true;
                dp.loadChangePinSuccessful();
                timer1.start(); // go back to main screen
            } else { // if pins do not match
                pinsMatch = false;
                dp.loadChangePinUnSuccessful();
                timer1.start(); // let user try again				
            }
        }
    }	
	
    /**
     * Implements and re-define the action performed method. I.e. It deals with what to do in the event
     * of a button click made on the ATM GUI and is dealt with here
     * @param e The object that stated an event occuring 
     */
    public void actionPerformed(ActionEvent e) {
        if (!problemConnectingToServer) { // if there isn't a connection problem
            if (!dp.mainTextArea.getText().equalsIgnoreCase("")) { // if the text area isn't empty
                if (e.getSource() == atmGUI.numEnter) { 
                    if (onEnterAccountScreen) {				
                        atmClient.out.println(
                                "enteredAccountNo:" + dp.mainTextArea.getText());	
                    } else if (onEnterPinScreen) {
                        if (dp.mainTextArea.getText().length() == 4) { // make sure pin number is 4 digits long
                            processInternalMessage(
                                    "pinEntered:" + dp.mainTextArea.getText());
                        }
                        else {
                        	dp.loadIncorrectPinDigits();
                        }
                    } else if (onWithdrawCashScreen) {
                        if (containsOnlyDoubleNumbers(dp.mainTextArea.getText())
                                && stringToDouble(dp.mainTextArea.getText())
                                        != 0.00) {
                            processInternalMessage("withdrawingCash");
                        }    					
                    } else if (onDepositCashScreen) {
                        if (containsOnlyDoubleNumbers(dp.mainTextArea.getText())
                                && stringToDouble(dp.mainTextArea.getText())
                                        != 0.00) {
                            processInternalMessage("depositingCash");
                        }
                    } else if (onReEnterNewPinScreen) {
                        if (dp.mainTextArea.getText().length() == 4
                                && containsOnlyNumbers(dp.mainTextArea.getText())) { // make sure pin is 4 digits long
                            reEnteredNewPin = dp.mainTextArea.getText();
                            processInternalMessage("changePin");
                        }
                        else {
                        	dp.loadIncorrectPinDigits();
                        }
                    } else if (onEnterNewPinScreen) {
                        if (dp.mainTextArea.getText().length() == 4
                                && containsOnlyNumbers(dp.mainTextArea.getText())) { // make sure pin is 4 digits long
                            newPin = dp.mainTextArea.getText();
                            onEnterNewPinScreen = false;
                            onReEnterNewPinScreen = true;
                            dp.loadReEnterNewPinScreen();
                        }
                        else {
                        	dp.loadIncorrectPinDigits();
                        }
                    }

                }
            }

            if (e.getSource() == atmGUI.num1) {			
                dp.mainTextArea.append("1");			
            }
            if (e.getSource() == atmGUI.num2) {

                dp.mainTextArea.append("2");

            }
            if (e.getSource() == atmGUI.num3) {

                dp.mainTextArea.append("3");				

            }
            if (e.getSource() == atmGUI.num4) {

                dp.mainTextArea.append("4");				

            }
            if (e.getSource() == atmGUI.num5) {

                dp.mainTextArea.append("5");				

            }
            if (e.getSource() == atmGUI.num6) {

                dp.mainTextArea.append("6");				

            }
            if (e.getSource() == atmGUI.num7) {

                dp.mainTextArea.append("7");				

            }
            if (e.getSource() == atmGUI.num8) {

                dp.mainTextArea.append("8");				

            }
            if (e.getSource() == atmGUI.num9) {

                dp.mainTextArea.append("9");				

            }
            if (e.getSource() == atmGUI.num0) {

                dp.mainTextArea.append("0");
            }
            if (e.getSource() == atmGUI.numClear) {
                dp.mainTextArea.setText(""); // clear text area
            }

            if (e.getSource() == atmGUI.numCancel) {
                if (!onEnterAccountScreen) {
                    atmClient.out.println("sessionFinished");
                    dp.loadGoodbyeMessage();
                    timer2.start();				
                } else { // if already on enter account number screen               	
                    resetStates();
                    onEnterAccountScreen = true;
                    dp.loadEnterAccountScreen();
                }
            }

            if (e.getSource() == atmGUI.left1) {
                if (onMainScreen) {
                    onMainScreen = false;
                    onBalanceScreen = true;
                    dp.loadBalance();
                } else if (onWithdrawCashScreen) {
                    dp.mainTextArea.setText("10");
                    processInternalMessage("withdrawingCash");
                } else if (onDepositCashScreen) {
                    if (!dp.mainTextArea.getText().equalsIgnoreCase("")
                            && containsOnlyDoubleNumbers(
                                    dp.mainTextArea.getText())
                                    && stringToDouble(dp.mainTextArea.getText())
                                            != 0.00) {
                        processInternalMessage("depositingCash");
                    }
                }
            }
            if (e.getSource() == atmGUI.left2) {
                if (onWithdrawCashScreen) {
                    dp.mainTextArea.setText("20");
                    processInternalMessage("withdrawingCash");
                } else if (onMainScreen) {
                    onMainScreen = false;
                    onWithdrawCashScreen = true;
                    updateWithdrawalLimits();
                    dp.loadWithdrawCashScreen();
                }

            }

            if (e.getSource() == atmGUI.left3) {
                if (onWithdrawCashScreen) {
                    dp.mainTextArea.setText("40");
                    processInternalMessage("withdrawingCash");
                } else if (onMainScreen) {
                    onMainScreen = false;
                    onDepositCashScreen = true;
                    dp.loadDepositCashScreen();
                }
            }

            if (e.getSource() == atmGUI.left4) {
                if (onMainScreen) {
                    onMainScreen = false;
                    onEnterNewPinScreen = true;
                    dp.loadEnterNewPinScreen();				
                } else if (onBalanceScreen || onDepositCashScreen
                        || onWithdrawCashScreen) {
                    resetStates();
                    onMainScreen = true;
                    dp.loadMainScreen();               
                }
            }

            if (e.getSource() == atmGUI.right1) {
                if (onWithdrawCashScreen) {
                    dp.mainTextArea.setText("60");
                    processInternalMessage("withdrawingCash");
                }
            }

            if (e.getSource() == atmGUI.right2) {
                if (onWithdrawCashScreen) {
                    dp.mainTextArea.setText("80");
                    processInternalMessage("withdrawingCash");
                }
            }

            if (e.getSource() == atmGUI.right3) {
                if (onWithdrawCashScreen) {
                    dp.mainTextArea.setText("100");
                    processInternalMessage("withdrawingCash");
                }
            }

            if (e.getSource() == atmGUI.right4) { // generally an 'eject card' or 'end session' button
                if (onMainScreen || onBalanceScreen || onWithdrawCashScreen
                        || onDepositCashScreen) {
                    atmClient.out.println("sessionFinished");
                    resetStates();
                    dp.loadGoodbyeMessage();
                    timer2.start();
                }
            }
        }
    }
	
    /**
     * The user has a daily withdrawal limit, like that of most banks, when the user connects, this
     * method checks if the user has withdrawn in the last 24 hours and if so, how much the user
     * has withdrawn and sets the users withdrawal limit for today accordingly
     */
    public void updateWithdrawalLimits() {
        Date date = new Date();

        if (stringToLong(dateLastWithdrawn)
                < date.getTime() - (1000 * 60 * 60 * 24)
                        && stringToLong(dateLastWithdrawn) > 0) { // if date of last withdrawal is more than 24 hours
            dateLastWithdrawn = numberToString(date.getTime()); // reset time and date to present
            amountWithdrawnToday = numberToString(0.00); // reset todays amount withdrawn
            atmClient.out.println(
                    "newWithdrawalLimits:" + dateLastWithdrawn + ":"
                    + amountWithdrawnToday); // inform the server
			
            /* If the balance is more than the daily maximum withdrawal limit, then the maximum withdrawal
             * limit is set to that of the maximum daily withdrawal limit, of mod 10
             */
            if (stringToDouble(balance) > stringToDouble(maxDayWithdraw)) {
							
                actualMaxWithdraw = numberToString(
                        getWithdrawalMod10(stringToDouble(maxDayWithdraw)));
                
                /* else, set the maximum withdrawal limit to that of the balance
                 */
            } else {
                actualMaxWithdraw = numberToString(
                        getWithdrawalMod10(stringToDouble(balance)));
            }			
        } else { // if the time and date of last withdrawal is not more than 24 hours, update withdrawn today
            actualMaxWithdraw = numberToString(
                    getWithdrawalMod10(
                            stringToDouble(maxDayWithdraw)
                                    - stringToDouble(amountWithdrawnToday)));
            
            dateLastWithdrawn = numberToString(date.getTime());
            atmClient.out.println(
                    "newWithdrawalLimits:" + dateLastWithdrawn + ":"
                    + amountWithdrawnToday);
        }
    }
	
    /**
     * Takes the AccountInfoPDU, gets the specific data out of it and assigns corresponding variables
     * @param aString The AccountInfoPDU String containing the account data
     */
    public void constructAccountInfo(String aString) {
        String[] items = splitString(aString);
	    
        pinNo = items[1];
        balance = items[2];
        maxDayWithdraw = items[3];
        ifLocked = items[4];
        amountWithdrawnToday = items[5];
        dateLastWithdrawn = items[6];		
    	    	
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
     * A Method that simply reset all of the ATMClientProtocol states
     */
    public void resetStates() {
        onEnterAccountScreen = false;
        onMainScreen = false;
        onBalanceScreen = false;
        onWithdrawCashScreen = false;
        onEnterPinScreen = false;
        onDepositCashScreen = false;
        onWithdrawError = false;
        onEnterNewPinScreen = false;
        onReEnterNewPinScreen = false;
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
     * Takes a double and returns the mod 10 value of that double in the form of a double.
     * This is used to set withdraw amounts, as most cash machines will only dispense cash
     * in multiples of \u00A3 10
     * @param aDouble The unformatted double amount
     * @return aDouble the formatted double amount
     */
    public double getWithdrawalMod10(double aDouble) {
        double tempDouble = aDouble % 10;

        aDouble = aDouble - tempDouble;
        return aDouble;
    }
	
    /**
     * Converts a number (in this case an integer) to a string
     * @param anInt the integer to be converted
     * @return anInt in string form
     */
    public String numberToString(int anInt) {
        return Integer.toString(anInt);   	
    }    
    
    /**
     * Converts a number (in this case a double) to a string
     * @param aDouble the double to be converted
     * @return aDouble in string form
     */
    public String numberToString(double aDouble) {
        return Double.toString(aDouble);  
    }
    
    /**
     * Converts a number (in this case a long) to a string
     * @param aLong the long to be converted
     * @return aLong in string form
     */
    public String numberToString(long aLong) {
        return Long.toString(aLong);  
    }

    /**
     * Converts a boolean to a string value of true or false
     * @param aBoolean The boolean to be converted
     * @return true if boolean true or false otherwise
     */
    public String booleanToString(boolean aBoolean) {
        if (aBoolean == true) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * Converts a string to an integer
     * @param aString The string to be converted
     * @return aString in integer form
     */
    public int stringToInt(String aString) {
        return Integer.parseInt(aString);
    }    

    /**
     * Converts a string to a long
     * @param aString The string to be converted
     * @return aString in long form
     */
    public long stringToLong(String aString) {
        return Long.parseLong(aString);
    }

    /**
     * Converts a string to a double
     * @param aString The string to be converted
     * @return aString in double form
     */
    public double stringToDouble(String aString) {
        return Double.parseDouble(aString);
    }
    
    /**
     * Checks if a string contains only numerals and not letters
     * @param aString the string needed to be checked
     * @return boolean - true - does contain only numerals, false otherwise
     */
    public boolean containsOnlyDoubleNumbers(String aString) {
        
        if (aString == null || aString.length() == 0) {
            return false;
        }
        
        for (int i = 0; i < aString.length(); i++) {

            if (!Character.isDigit(aString.charAt(i))
                    && aString.charAt(i) != '.') {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if a string contains only numerals and not letters
     * @param aString the string needed to be checked
     * @return boolean - true - does contain only numerals, false otherwise
     */
    public boolean containsOnlyNumbers(String aString) {
        
        if (aString == null || aString.length() == 0) {
            return false;
        }
        
        for (int i = 0; i < aString.length(); i++) {

            if (!Character.isDigit(aString.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Implements and overrides key event
     * @param k The object calling the key event
     */
	public void keyPressed(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_ENTER) {
			atmGUI.numEnter.doClick();			
		}
		
	}

	/**
     * Implements and overrides key event
     * @param k The object calling the key event
     */
	public void keyReleased(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_ENTER) {
			dp.mainTextArea.setText("");			
		}
		
	}

	/**
     * Implements and overrides key event
     * @param k The object calling the key event
     */
	public void keyTyped(KeyEvent k) {
				
	}

}
