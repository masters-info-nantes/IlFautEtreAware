package org.alma.middleware.IlFautEtreAware.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Maxime on 02/10/2015.
 */
public interface ITopic extends Remote {
    public void broadcast(Message message) throws RemoteException;
    public ArrayList<Message> getMessages() throws RemoteException;
}
