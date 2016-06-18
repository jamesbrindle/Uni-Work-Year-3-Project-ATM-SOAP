import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * The ATMClient class is the main class which runs the client side of the ATM system and calls instances
 * of the ATMGUI - The ATM graphical user interface, the ATMClientProtocol class - which deals with and processes
 * any inputs from the server, the ClientOptions class - a class which reads a text file for the appropriate
 * port number and computer to connect to. This class deals with connecting to and maintaining the connection
 * to the server via the use of sockets
 * 
 * @author Jamie Brindle (06352322)
 */
public class ATMClient {
	
    protected static ATMClient atmClient;
    protected ATMClientProtocol atmClientProtocol;
    Socket socket;  
    protected PrintWriter out; // Writes to the server     
    protected BufferedReader in; // Reads from the server   
    protected String fromServer;
    protected boolean problemWithServer = false;
    protected ClientOptions co; // Class which contains methods for retrieving client/server options from a text file
    
    /**
     * ATMClient constructor
     * @throws IOException - If there's a problem with the input/output
     */
    public ATMClient() throws IOException {
    	
        atmClientProtocol = new ATMClientProtocol(this);
        ClientOptions co = new ClientOptions();

        try {
            socket = new Socket(co.getComputerName(), co.getPortNumber());
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to " + co.getComputerName());
            problemWithServer = false;
                   
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + co.getComputerName());
            atmClientProtocol.processInternalMessage("problemWithServer"); // display connection problem
            problemWithServer = true;
                        
        } catch (IOException e) {
            System.err.println(
                    "Couldn't get I/O for the connection to: "
                            + co.getComputerName());
            atmClientProtocol.processInternalMessage("problemWithServer"); // display connection problem
            problemWithServer = true;
            
        }		
	
        if (!problemWithServer) {
	        	   
            while ((fromServer = in.readLine()) != null) {
                System.out.println("From Server: " + fromServer);
	        	
                atmClientProtocol.processServerMessage(fromServer);
	            
                if (fromServer.equals("EXIT")) {
                	
                    break;
                }
			  	         
            }
            out.close();
            in.close();
            socket.close();
        }			    
    }   
 
    /**
     * Main method to run the class
     * @throws IOException input output exception
     */
    public static void main(String[] args) throws IOException {     	
        atmClient = new ATMClient();        
    }
}
