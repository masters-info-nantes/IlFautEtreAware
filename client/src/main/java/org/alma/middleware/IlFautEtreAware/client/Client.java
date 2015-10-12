package org.alma.middleware.IlFautEtreAware.client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.alma.middleware.IlFautEtreAware.common.IClient;
import org.alma.middleware.IlFautEtreAware.common.ITopic;
import org.alma.middleware.IlFautEtreAware.common.Message;

/**
 * Created by Maxime on 06/10/2015.
 */
public class Client extends UnicastRemoteObject implements IClient, Serializable {

    private String name = "";

    public Client() throws RemoteException {
        super();
    }

    @Override
    public void newMessage(Message message, String topic) throws RemoteException {
        System.out.println("[" + topic + "][" + message.getAuthor() + "] : " + message.getMessage());
    }

    @Override
    public void newTopic(ITopic topic) throws RemoteException {
        System.out.println("["+topic.getName()+"] : has created");
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
