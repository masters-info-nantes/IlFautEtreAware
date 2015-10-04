package org.alma.middleware.IlFautEtreAware.client;

import org.alma.middleware.IlFautEtreAware.common.IServer;
import org.alma.middleware.IlFautEtreAware.common.ITopic;
import org.alma.middleware.IlFautEtreAware.common.Message;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maxime on 02/10/2015.
 */
public class Server extends UnicastRemoteObject implements IServer, Serializable {

    private ArrayList<Message> messages = new ArrayList<Message>();

    public Server() throws RemoteException {
        super();
    }

    public List<Message> getMessages() throws RemoteException {
        return messages;
    }

    public void broadcast(Message message) throws RemoteException {
        System.out.println("broadcast ["+message.getAuthor()+" : "+message.getMessage()+"]");
    }
}
