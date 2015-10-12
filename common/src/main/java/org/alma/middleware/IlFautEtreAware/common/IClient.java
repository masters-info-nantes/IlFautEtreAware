package org.alma.middleware.IlFautEtreAware.common;

import java.rmi.*;

/**
 * Created by Maxime on 02/10/2015.
 */
public interface IClient extends Remote {
    public void newMessage(Message message, String topic) throws RemoteException;
    public void newTopic(ITopic topicName) throws RemoteException;
    public String getName() throws RemoteException;
}
