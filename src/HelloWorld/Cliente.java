/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
            
            cliImpl.invoke("SUP YO!");
            
            cliImpl.invoke("OMG it works");
            
            cliImpl.invoke("OK bye.");
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
