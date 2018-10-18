/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1729756
 */
public class CliImpl extends UnicastRemoteObject implements InterfaceCli {

    private final Registry refRegistry;
   
;    
    CliImpl(Registry refRegistry) throws RemoteException {
        
        this.refRegistry = refRegistry;
    }
    
    public void invoke(String str) {
        try {
            InterfaceServ remote;
            remote = (InterfaceServ) this.refRegistry.lookup("Trivago");
           
            remote.registraInteresse(null, this);
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(CliImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void echo(String str) throws RemoteException {
        System.out.println("Echo: " + str);
    }
}
