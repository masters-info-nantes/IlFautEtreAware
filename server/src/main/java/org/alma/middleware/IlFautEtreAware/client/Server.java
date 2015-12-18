package org.alma.middleware.IlFautEtreAware.client;

import java.io.File;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.alma.middleware.IlFautEtreAware.common.IClient;
import org.alma.middleware.IlFautEtreAware.common.IServer;
import org.alma.middleware.IlFautEtreAware.common.ITopic;
import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 * Created by Maxime on 02/10/2015.
 */
public class Server extends UnicastRemoteObject implements IServer, Serializable {

    private final static String DB_TOPIC_LIST = "topics";

    private HashMap<String, ITopic> topics = new HashMap<String, ITopic>();
    private ArrayList<IClient> clients = new ArrayList<IClient>();
    private DB db;
    private Set<String> dbTopics;

    public Server() throws RemoteException {
        super();

        this.db = DBMaker.newFileDB(new File("storage.db"))
                .closeOnJvmShutdown()
                .transactionDisable()
                .make();

        this.dbTopics = this.db.getHashSet(DB_TOPIC_LIST);
        if(db.exists(DB_TOPIC_LIST)) {
            for(String topicName : this.dbTopics) {
                ITopic topic = new Topic(topicName,this.db);
                this.topics.put(topicName, topic);
            }
        }
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
        Topic topic = (Topic) topics.get(t.getName());
        topic.removeClient(client);
        client.removeSubscribedTopic(t);
        System.out.println("[Server] Unsubscribe topic "+t.getName()+" : "+client.getName());
    }

    public void createTopic (IClient client, String name) throws RemoteException {
        Topic t = new Topic(name, db);
        if(!topics.containsKey(name)){
        	topics.put(name, t);
            this.dbTopics.add(name);
        	for(IClient c : clients) {
                c.newTopic(t);
            }
            System.out.println("[Server] New topic \""+name+"\" created");
        }
    }

}
