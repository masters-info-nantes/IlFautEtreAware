package org.alma.middleware.IlFautEtreAware.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Maxime on 02/10/2015.
 */
public interface IServer extends Remote {

    public List<Message> getMessages() throws RemoteException;
    public void broadcast(Message message) throws RemoteException;
}
