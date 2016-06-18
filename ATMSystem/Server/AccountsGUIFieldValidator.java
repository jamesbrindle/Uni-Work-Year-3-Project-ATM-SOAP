import java.text.DecimalFormat;
import java.util.regex.Pattern;


/**
 * Associated with the AccountsGUIListener class, this class is used to validate
 * that the data input by the user into the text fields is valid enough to be stored
 * in the database file and assists in preventing human input error
 * @author Jamie Brindle (06352322)
 */
public class AccountsGUIFieldValidator {
    protected AccountsGUIListener accountsGL;
    protected NumeralFunctions nf;
		
    /**
     * AccountGUIFieldValidator constructor taking in the AccountGUIListener class
     * as a parameter thus directly affecting it
     * @param GUI associating the AccountsGUIFieldValidator with the AccountsGUIListener
     * class
     */
    public AccountsGUIFieldValidator(AccountsGUIListener GUI) {
        this.accountsGL = GUI;	
        nf = new NumeralFunctions();
        
    }  
	
    /**
     * Validates the pin number field, in conjunction with the containsOnlyNumbers
     * method
     * @param aString The String needed to be checked
     * @return boolean - true is valid, false otherwise
     */
    public boolean validatePinNumber(String aString) {
	
        if (nf.containsOnlyNumbers(aString)) {
			
            if (nf.stringToInt(aString) >= 1000
                    && nf.stringToInt(aString) <= 9999) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
		
    }
	
    /**
     * Checks there are no invalid characters in a given text field
     * @param aString The string needed to be checked
     * @return boolean -true if no invalid characters, false otherwise
     */
    public boolean checkNoInvalidCharacters(String aString) {
		
        String invalidList = "!\"" + "\u00A3" + "$%^&*()_+-=`{}[]:@~;#<>?/\\";
        int count = 0;
				
        String[] temp1 = Pattern.compile("").split(invalidList);
        String[] temp2 = Pattern.compile("").split(aString);
		
        for (int i = 0; i < temp2.length; i++) {
            for (int y = 0; y < temp1.length; y++) {
                if (temp2[i].equalsIgnoreCase(temp1[y])) {
                    count++;
                }
            }
        }
		
        if (count > 1) {
            return false;
        } else {
            return true;
        }	
		
    }
    
    /**
     * Checks there are no invalid characters or numbers in a given text field
     * Used in conjunction with the checkNoInvalidCharacters method
     * @param aString The string needed to be checked
     * @return boolean -true if no invalid characters, false otherwise
     */
    public boolean validateStringsFieldsNumbersNotAllowed(String aString) {
        if (checkNoInvalidCharacters(aString) && !(nf.containsNumbers(aString))) {
            return true; 
        } else {
            return false;
        }
    }    
    
    /**
     * Checks there are no invalid characters in a given text field
     * Used in conjunction with the checkNoInvalidCharacters method
     * @param aString The string needed to be checked
     * @return boolean -true if no invalid characters, false otherwise
     */
    public boolean validateStringsFieldsNumbersAllowed(String aString) {
		
        if (checkNoInvalidCharacters(aString)) {
            return true; 
        } else {
            return false;
        }
    }
	
    /**
     * Checks there are only numbers and spaces in telephone text field
     * used in conjunction with the containsOnlyNumbersAndSpaces method
     * @param aString The string needed to be checked
     * @return boolean -true if no invalid characters, false otherwise
     */
    public boolean validateTelephone(String aString) {
		
        if (nf.containsOnlyNumbersAndSpaces(aString)) {
            return true;
        } else {
            return false;
        }
    }
	
    /**
     * Checks if a currency text field contains only currency characters
     * used in conjunction with the ContainsOnlyCurrencyDigits method
     * @param aString The string needed to be checked
     * @return boolean -true if no invalid characters, false otherwise
     */
    public boolean validateCurrency(String aString) {
		
        if (nf.containsOnlyCurrencyDigits(aString)) {
            return true;
        } else {
            return false;
        }
    }
	
    /**
     * Used to convert the string in a currency text field to a standard
     * double in which is the format it is stored in, in the database file
     * for example, ridding of the £ sign and in the double format 0.00
     * @param aString The string needed to be converting
     * @return double which is the format stored in the database text file
     */
    public double convertCurrency(String aString) {
		
        String tempString = "";
        String[] tempArray = Pattern.compile("\u00A3").split(aString);
        double tempDouble = 0.00;
		
        for (int i = 0; i < tempArray.length; i++) {
            tempString = tempString + tempArray[i];
        }
		
        tempDouble = Double.parseDouble(tempString);
		
        DecimalFormat twoDForm = new DecimalFormat("#.00");

        return Double.valueOf(twoDForm.format(tempDouble));

    }
	
}
