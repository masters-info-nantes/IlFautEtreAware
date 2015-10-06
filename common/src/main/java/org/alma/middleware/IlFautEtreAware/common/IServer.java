package org.alma.middleware.IlFautEtreAware.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Maxime on 02/10/2015.
 */
public interface IServer extends Remote {
    public List<String> getTopics() throws RemoteException;
    public ITopic topicSubscribe(IClient client, String name) throws RemoteException;
    public void topicUnsubscribe(IClient client, String name) throws RemoteException;
    public void createTopic (IClient client, String name) throws RemoteException;
}
