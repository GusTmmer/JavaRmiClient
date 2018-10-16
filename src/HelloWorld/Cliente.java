/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1729756
 */
public class Cliente {
    
    public static void main (String[] args) {
        
        try {
            Registry referenciaServicoNomes = LocateRegistry.getRegistry(2000);
            CliImpl cliImpl = new CliImpl(referenciaServicoNomes);
            
            InterfaceServ s;
            s = (InterfaceServ) referenciaServicoNomes.lookup("Trivago");
            
            Scanner scanner = new Scanner(System.in);
            CommandParser parser = new CommandParser(s, scanner);
            
            while(true)
            {
                parser.parseCommand(scanner.nextLine());
                
            }            
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
