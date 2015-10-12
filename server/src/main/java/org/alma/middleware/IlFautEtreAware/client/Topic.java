package org.alma.middleware.IlFautEtreAware.client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.alma.middleware.IlFautEtreAware.common.IClient;
import org.alma.middleware.IlFautEtreAware.common.ITopic;
import org.alma.middleware.IlFautEtreAware.common.Message;

/**
 * Created by Maxime on 06/10/2015.
 */
public class Topic extends UnicastRemoteObject implements Serializable, ITopic {

    public Topic(String name) throws RemoteException {
        super();
        topicName = name;
    }

    private String topicName = "";
    private ArrayList<Message> messages = new ArrayList<Message>();
    private ArrayList<IClient> clients = new ArrayList<IClient>();

    @Override
    public String getName() throws RemoteException {
        return topicName;
    }

    public void broadcast(Message message) throws RemoteException {
        System.out.println("broadcast [" + message.getAuthor() + " : " + message.getMessage() + "]");
        messages.add(message);
        for (IClient c : clients) {
            c.newMessage(message, topicName);
        }
    }

    @Override
    public ArrayList<Message> getMessages() throws RemoteException {
        return messages;
    }


    public void addClient(IClient client) {
        clients.add(client);
    }

    public void removeClient(IClient client) {
        clients.remove(client);
    }
}
