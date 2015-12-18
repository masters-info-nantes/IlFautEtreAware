package org.alma.middleware.IlFautEtreAware.client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mapdb.*;

import org.alma.middleware.IlFautEtreAware.common.IClient;
import org.alma.middleware.IlFautEtreAware.common.ITopic;
import org.alma.middleware.IlFautEtreAware.common.Message;

/**
 * Created by Maxime on 06/10/2015.
 */
public class Topic extends UnicastRemoteObject implements Serializable, ITopic {

    public final static String DB_TOPIC = "Topic-";

    public Topic(String name, DB db) throws RemoteException {
        super();
        topicName = name;
        this.db = db;

        this.history = this.db.getHashMap(DB_TOPIC + this.topicName);
        if(this.db.exists(DB_TOPIC+this.topicName)) {
            for(int i=0 ; i<this.history.size() ; i++) {
                Message msg = this.history.get(i);
                this.messages.add(i, msg);
            }
        }
    }

    private DB db;
    private Map<Integer,Message> history;

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
        this.history.put(this.history.size(), message);
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

    public List<IClient> getClients() {
        return clients;
    }
}
