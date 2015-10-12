package org.alma.middleware.IlFautEtreAware.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import org.alma.middleware.IlFautEtreAware.common.IServer;
import org.alma.middleware.IlFautEtreAware.common.ITopic;
import org.alma.middleware.IlFautEtreAware.common.Message;
import org.alma.middleware.IlFautEtreAware.common.RMIConfig;

/**
 * Created by Maxime on 02/10/2015.
 */
public class ClientApplication {

    public static void main(String [] args) throws RemoteException {
        ArrayList<String> subscribeTopic = new ArrayList<String>();

        System.out.println("Running client");
        //Ajout de l'UI
        //TODO connect ui functions to base functions
        //TODO add jfx jar in resources for making css files available
        //Connection con = new Connection();
        //con.launchApp(args);
        
        IServer server = null;
        Client client = new Client();
        try {
            server = (IServer) Naming.lookup("rmi://" + RMIConfig.SERVER_IP + ":" + RMIConfig.SERVER_PORT + "/" + RMIConfig.APP_NAME);
        }
        catch (NotBoundException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            System.err.println("RMI: Retrieve malformed URL");
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        String userName = "user"+(int)(Math.random()*100);
        client.setName(userName);
        server.login(client);
        String topicName = "test"+(int)(Math.random()*100);
        server.createTopic(client, topicName);
        //server.topicSubscribe(client, topicName);
        subscribeTopic.add(topicName);

        for(int i = 0; i<server.getTopics().size(); i++ ) {
            System.out.println("["+i+"] "+server.getTopics().get(i).getName());
        }
        Scanner scanner = new Scanner(System.in);
        int index;
        int ok = 0;
        do {
            index = scanner.nextInt();
            if (index != -1) {
                ok = index;
                server.topicSubscribe(client, server.getTopics().get(index));
                subscribeTopic.add(server.getTopics().get(index).getName());
            }
        } while (index != -1);
        ITopic topic = server.getTopics().get(ok);
        for(Message message : topic.getMessages()) {
            System.out.println("["+topic.getName()+"]["+message.getAuthor()+"] : "+message.getMessage());
        }
        topic.broadcast(new Message(client.getName(),"hello"));


        while(true);
    }


}
