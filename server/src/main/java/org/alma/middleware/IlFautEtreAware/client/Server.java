package org.alma.middleware.IlFautEtreAware.client;

import org.alma.middleware.IlFautEtreAware.common.*;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maxime on 02/10/2015.
 */
public class Server extends UnicastRemoteObject implements IServer, Serializable {

    private HashMap<String, Topic> topics = new HashMap<String, Topic>();

    public Server() throws RemoteException {
        super();
    }

    public List<String> getTopics() throws RemoteException {
        return new ArrayList<String>(topics.keySet());
    }

    public ITopic topicSubscribe(IClient client, String name) throws RemoteException {
        Topic topic = topics.get(name);
        topic.addClient(client);
        System.out.println("[Server] Subscribe");
        return topic;
    }

    public void topicUnsubscribe(IClient client, String name) throws RemoteException {
        Topic topic = topics.get(name);
        topic.removeClient(client);
        System.out.println("[Server] Unsubscribe");
    }

    public void createTopic (IClient client, String name) throws RemoteException {
        Topic t = new Topic(name);
        topics.put(name, t);

        String url = "rmi://" + RMIConfig.SERVER_IP +":" + RMIConfig.SERVER_PORT + "/" + RMIConfig.APP_NAME + "/"+name;
        try {
            Naming.rebind(url, t);
        }
        catch (MalformedURLException e) {
            System.err.println("RMI: Object URL malformed");
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("[Server] New topic \""+name+"\" created");
    }

}
