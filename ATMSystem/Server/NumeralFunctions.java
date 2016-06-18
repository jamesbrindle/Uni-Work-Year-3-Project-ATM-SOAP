import java.io.Serializable;
import java.text.DecimalFormat;


/**
 * This class is a collection of methods that allow the manipulation and conversion of strings and 
 * numbers whether they be integers, doubles or longs. This class is used to convert numbers to strings
 * and vice versa. This class also contains methods to check if a string only contains numbers, only
 * contains numbers and spaces and currency digits such a the pound symbol and also if a string doesn't
 * contain any invalid characters. It's quite a well used class by the AccountsGUIListener class and the
 * ATMServerProtocol class
 * 
 *  @author Jamie Brindle (06352322)
 */
public class NumeralFunctions implements Serializable {

    private static final long serialVersionUID = 2L;

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
     * Formats a double to that of typical English pound sterlin format
     * @param aDouble the double to be formatted
     * @return aString the formatted double in the correctly formatted form
     */
    public String decimalFormatter(double aDouble) {
        DecimalFormat d = new DecimalFormat("0.00");    	
        String aString = d.format(aDouble);

        return aString;
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
     * Checks if a string contains only numerals and spaces and not letters
     * @param aString the string needed to be checked
     * @return boolean - true - does contain only numerals, false otherwise
     */
    public boolean containsOnlyNumbersAndSpaces(String aString) {
        
        if (aString == null || aString.length() == 0) {
            return true;
        }
        
        for (int i = 0; i < aString.length(); i++) {

            if (!Character.isDigit(aString.charAt(i))
                    && aString.charAt(i) != ' ') {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if a string contains any number characters
     * @param aString the string needed to be checked
     * @return boolean - true - does contain numerals, false otherwise
     */
    public boolean containsNumbers(String aString) {
    	
        if (aString == null || aString.length() == 0) {
            return false;
        }
    	 
        for (int i = 0; i < aString.length(); i++) { 
            if (Character.isDigit(aString.charAt(i))) {
                return true;
            }
        }
        return false;
         
    }
	
    /**
     * Checks if a string contains only currency digits and not letters
     * @param aString the string needed to be checked
     * @return boolean - true - does contain only numerals, false otherwise
     */
    public boolean containsOnlyCurrencyDigits(String aString) {
        
        if (aString == null || aString.length() == 0) {
            return false;
        }
        
        for (int i = 0; i < aString.length(); i++) {

            if (!Character.isDigit(aString.charAt(i))
                    && aString.charAt(i) != '\u00A3' && aString.charAt(i) != '.') {
                return false;
            }
        }
        
        return true;
    }
}

