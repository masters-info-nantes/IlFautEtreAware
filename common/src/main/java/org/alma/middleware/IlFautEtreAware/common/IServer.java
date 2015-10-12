package org.alma.middleware.IlFautEtreAware.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Maxime on 02/10/2015.
 */
public interface IServer extends Remote {
    public void login(IClient client) throws RemoteException;
    public List<ITopic> getTopics() throws RemoteException;
    public ITopic getTopic(String topicName) throws RemoteException;
    public ITopic topicSubscribe(IClient client, ITopic t) throws RemoteException;
    public void topicUnsubscribe(IClient client, ITopic t) throws RemoteException;
    public void createTopic (IClient client, String name) throws RemoteException;
}
