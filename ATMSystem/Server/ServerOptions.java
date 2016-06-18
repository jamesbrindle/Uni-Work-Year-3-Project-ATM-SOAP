import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Used to retrieve server options from a text file such as computer name, data file location and port number,
 * The text file makes it easy for a user to quickly change important variables such as these, which
 * are likely to change when moving the server to another machine
 * 
 * @author Jamie Brindle (06352322)
 */
public class ServerOptions {
    private String[] input;
    protected String textFileLocation;
			 
    @SuppressWarnings("deprecation")
    public ServerOptions() {
        input = new String[11];
        textFileLocation = "serverOptions.txt";
		 
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
     * Used to retrieve the data file location (database) from a textFile
     *
     */
    public String getDataFileLocation() {
        String dataFileLocation = input[6].toString();
		                
        return dataFileLocation;       
    }
	
    /**
     * Used to retrieve the server port number from a text file
     *
     */
    public int getPortNumber() {
        int portNumberInt = 0;
					      
        String portNumber = input[3].toString();
		  
        portNumberInt = Integer.parseInt(portNumber);
        return portNumberInt;
    }
}

