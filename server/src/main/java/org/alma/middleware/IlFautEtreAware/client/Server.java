package org.alma.middleware.IlFautEtreAware.client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.alma.middleware.IlFautEtreAware.common.IClient;
import org.alma.middleware.IlFautEtreAware.common.IServer;
import org.alma.middleware.IlFautEtreAware.common.ITopic;

/**
 * Created by Maxime on 02/10/2015.
 */
public class Server extends UnicastRemoteObject implements IServer, Serializable {

    private HashMap<String, ITopic> topics = new HashMap<String, ITopic>();
    private ArrayList<IClient> clients = new ArrayList<IClient>();

    public Server() throws RemoteException {
        super();
    }

    public void login(IClient client) {
        clients.add(client);
    }

    public List<ITopic> getTopics() throws RemoteException {
        ArrayList<ITopic> t = new ArrayList<ITopic>();
        for(ITopic topic : topics.values()) {
            t.add(topic);
        }
        return t;
    }

    @Override
    public List<String> getTopicsName() throws RemoteException {
        ArrayList<String> names = new ArrayList<String>();
        for(ITopic t : topics.values()) {
            names.add(t.getName());
        }
        return names;
    }

    public ITopic getTopic(String topicName) throws RemoteException {
        return topics.get(topicName);
    }

    public ITopic topicSubscribe(IClient client, ITopic t) throws RemoteException {
        Topic topic = (Topic) topics.get(t.getName());
        topic.addClient(client);
        client.addSubscribedTopic(t);
        System.out.println("[Server] Subscribe topic "+t.getName()+" : "+client.getName());
        return topic;
    }

    public void topicUnsubscribe(IClient client, ITopic t) throws RemoteException {
        Topic topic = (Topic)topics.get(t.getName());
        topic.removeClient(client);
        client.removeSubscribedTopic(t);
        System.out.println("[Server] Unsubscribe topic "+t.getName()+" : "+client.getName());
    }
    public void topicDeleted(IClient client, ITopic t) throws RemoteException {
        Topic topic = (Topic)topics.get(t.getName());
        topic.removeClient(client);
        client.removeSubscribedTopic(t);
        System.out.println("[Server] Topic "+t.getName()+" deleted : "+client.getName());
    }

    public void createTopic (IClient client, String name) throws RemoteException {
        Topic t = new Topic(name);
        if(!topics.containsKey(name)){
        	topics.put(name, t);
        	for(IClient c : clients) {
                c.newTopic(t);
            }
            System.out.println("[Server] New topic \""+name+"\" created");
        }
    }

}
