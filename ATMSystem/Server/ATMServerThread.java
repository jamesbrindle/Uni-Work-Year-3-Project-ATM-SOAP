import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


/**
 * This class is created and used to allow multiple client connections to the server. A new thread
 * is made each time a client connects and it calls an instance of the ATMServerProtocol class in which
 * this class is passed into as a parameter so the ATMServerProtocol which deals with and processes data
 * from the client refers back to this class. when the clients wishes to disconnect, the thread is
 * then stopped and removed from the server
 * 
 *  @author Jamie Brindle (06352322)
 */
public class ATMServerThread extends Thread {
    protected Socket socket = null;  
    protected String fromClient;
    protected PrintWriter out;
    protected ATMServerProtocol atmServerProtocol;
    protected Store store; // store containing array of accounts
    protected ServerOptions so; // Class containing methods to retrieve server info from a text file
    protected boolean exitStatus;
    protected ArrayList<String> activeAccounts;
    
    /**
     * The ATMServerThread constructor
     * @param socket the socket this class is presently dealing with
     * @param store actually brought from the ATMServer class, it's the store of accounts presently dealing with
     * @param activeAccounts an array list of currently active accounts, used to prevent multiple active accounts
     */  
    public ATMServerThread(Socket socket, Store store, ArrayList<String> activeAccounts) {
        super("ATMServerThread");
        this.socket = socket;
        atmServerProtocol = new ATMServerProtocol(this);
        this.store = store;
        this.activeAccounts = activeAccounts;
        so = new ServerOptions(); // Class which contains methods for retrieving 
        // server info from a text file
        exitStatus = false; // preset exit status to false, assuming no fault exists with the connection
    }    

    /**
     * Runs the ATMServerThread
     */  
    public void run() {    	

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            atmServerProtocol.processInput("waitForAccount"); // set client state to wait for account number
	    
            while ((fromClient = in.readLine()) != null) {	
            	
                System.out.println("From Client: " + fromClient);
            	
                atmServerProtocol.processInput(fromClient); 
	    		    	    	  
                if (fromClient.equalsIgnoreCase("EXIT")) {
	    					
                    break;
                }
            }
            
            out.close();
            in.close();
            socket.close();
	  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
