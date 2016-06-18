import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Used to retrieve client options from a text file such as computer name and port number,
 * The text file makes it easy for a user to quickly change important variables such as these, which
 * are likely to change when moving the server to another machine
 * 
 * @author Jamie Brindle (06352322)
 */
public class ClientOptions {
    private String[] input;
    protected String textFileLocation;
			 
    @SuppressWarnings("deprecation")
    public ClientOptions() {
        input = new String[8];
        textFileLocation = "clientOptions.txt";
		 
        File file = new File(textFileLocation);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
	        
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);
			  
            for (int n = 0; dis.available() != 0; n++) {
                input[n] = dis.readLine();        	
            }
	            
            fis.close();
            bis.close();
            dis.close();
	            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
	           
        } catch (IOException e) {
            e.printStackTrace();
	           
        }
		 
    }
	
    /**
     * Used to retrieve the server computer name from a textFile
     *
     */
    public String getComputerName() {		      
        String computerName = input[3].toString();     
        
        return computerName;       
    }

    /**
     * Used to retrieve the server port number from a text file
     *
     */
    public int getPortNumber() {
        int portNumberInt = 0;
					      
        String portNumber = input[6].toString();
		  
        portNumberInt = Integer.parseInt(portNumber);
        return portNumberInt;
    }
}

