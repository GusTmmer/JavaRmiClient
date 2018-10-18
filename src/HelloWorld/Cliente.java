package HelloWorld;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * The Main class. Creates an instance of client, gets input from the user and makes requests to the server.
 */
public class Cliente {
    
    public static void main (String[] args) {
        
        try {
            Registry nameService = LocateRegistry.getRegistry(2000);

            InterfaceServ server = (InterfaceServ) nameService.lookup("Trivago");

            Scanner scanner = new Scanner(System.in);
            CliImpl client = new CliImpl(server, scanner);
            
            while(true) {
                client.commandParser.parseCommand(scanner.nextLine());
            }

            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
