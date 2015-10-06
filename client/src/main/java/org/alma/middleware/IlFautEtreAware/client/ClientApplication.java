package org.alma.middleware.IlFautEtreAware.client;

import org.alma.middleware.IlFautEtreAware.common.IServer;
import org.alma.middleware.IlFautEtreAware.common.Message;
import org.alma.middleware.IlFautEtreAware.common.RMIConfig;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Maxime on 02/10/2015.
 */
public class ClientApplication {

    public static void main(String [] args) {
        System.out.println("Running client");
        IServer server = null;
        try {
            server = (IServer) Naming.lookup("rmi://" + RMIConfig.SERVER_IP + ":" + RMIConfig.SERVER_PORT + "/" + RMIConfig.APP_NAME + "/server");
        }
        catch (NotBoundException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            System.err.println("RMI: Retrieve malformed URL");
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Topics list: " + server.getMessages().toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            server.broadcast(new Message("Do","TARDIS!"));
            UI ui = new UI();
            ui.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
