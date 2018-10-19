package HelloWorld;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 *
 * A client implementation that makes request to a server responsible for managing plane tickets and lodgings.
 */
public class CliImpl extends UnicastRemoteObject implements InterfaceCli {

    private final InterfaceServ server;
    final CommandParser commandParser;
;    
    CliImpl(InterfaceServ server, Scanner scanner) throws RemoteException {
        this.server = server;
        this.commandParser = new CommandParser(this, server, scanner);
    }

    @Override
    public void printNotification(String str) {
        System.out.println(str);
    }
}