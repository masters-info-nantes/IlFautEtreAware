package org.alma.middleware.IlFautEtreAware.client;

import org.alma.middleware.IlFautEtreAware.common.Message;

import java.rmi.RemoteException;

/**
 * Created by Maxime on 02/10/2015.
 */
public class ClientApplication {

    public static void main(String [] args) {
        System.out.println("Running client");
        try {
            new Message();
            UI ui = new UI();
            ui.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
