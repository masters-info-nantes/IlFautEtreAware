package org.alma.middleware.IlFautEtreAware.client;

import org.alma.middleware.IlFautEtreAware.common.Message;
import org.alma.middleware.IlFautEtreAware.common.RMIConfig;

import javax.management.remote.rmi.RMIServer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.security.Permission;

/**
 * Created by Maxime on 02/10/2015.
 */
public class ServerApplication {

    public static void main(String [] args) {
        System.out.println("Running client");

        try {
            Server server = new Server();

            // create a Security Manager that allow everything
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager() {
                    @Override
                    public void checkPermission(Permission perm) {
                        return;
                    }
                });
            }

            LocateRegistry.createRegistry(RMIConfig.SERVER_PORT);

            String url = "rmi://" + RMIConfig.SERVER_IP +":" + RMIConfig.SERVER_PORT + "/" + RMIConfig.APP_NAME + "/server";
            try {
                Naming.rebind(url, server);
            }
            catch (MalformedURLException e) {
                System.err.println("RMI: Object URL malformed");
                e.printStackTrace();
            }


            while(true);
        } catch(RemoteException ex) {
            System.err.println("Can't create remote server object");
            ex.printStackTrace();
        }

    }
}
