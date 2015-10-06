package org.alma.middleware.IlFautEtreAware.client;

import org.alma.middleware.IlFautEtreAware.common.IClient;
import org.alma.middleware.IlFautEtreAware.common.Message;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Maxime on 06/10/2015.
 */
public class Client extends UnicastRemoteObject implements IClient, Serializable {

    public Client() throws RemoteException {
        super();
    }

    @Override
    public void newMessage(Message message, String topic) throws RemoteException {
        System.out.println("["+topic+"]["+message.getAuthor()+"] : "+message.getMessage());
    }

}
