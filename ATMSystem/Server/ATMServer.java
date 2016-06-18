import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


/**
 * The ATMServer class is the main class which runs the server side of the ATM system and creates new server
 * threads to deals with each client upon connection request, which in turn calls an instance of the
 * ATMServerProtocol class which deals and processes data from the client. This ATMServer class acts
 * as a listener for any clients that wish to connect
 * 
 * @author Jamie Brindle (06352322)
 */
public class ATMServer {
    protected static ServerOptions so; // Class which contains methods for retrieving 
    // server info from a text file
    protected Store store; // A class which contains methods for retrieving account information from
    // an array list and storeing / retrieving this array list from a data file
    protected ArrayList<String> activeAccounts; // An array list of accounts that are currenlty open (in use)
    
    /**
     * The ATMServer constructor
     * @throws IOException input output exception
     */
    public ATMServer() throws IOException {
        so = new ServerOptions();
        ServerSocket serverSocket = null;
        boolean listening = true;
         
        store = new Store();
        activeAccounts = new ArrayList<String>(); // an array list of active accounts, used to avoid multiple active clients
        store = store.fileIn(so.getDataFileLocation());
        // cast the database to a store
         
        try {
            serverSocket = new ServerSocket(so.getPortNumber());
            System.out.println("Listening on port " + so.getPortNumber());
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + so.getPortNumber());
            System.exit(-1);
        }

        while (listening) { 
            new ATMServerThread(serverSocket.accept(), this.store, this.activeAccounts).start();
        }
        System.out.println("Client Accepted");
         
        serverSocket.close();
    }
    
    /**
     * Main method to run the class
     * @throws IOException input output exception
     */
    public static void main(String[] args) throws IOException {
        new ATMServer();
    }
}

