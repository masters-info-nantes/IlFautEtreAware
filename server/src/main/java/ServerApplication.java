import org.alma.middleware.IlFautEtreAware.common.Message;

import java.rmi.RemoteException;

/**
 * Created by Maxime on 02/10/2015.
 */
public class ServerApplication {

    public static void main(String [] args) {
        System.out.println("Running server");
        try {
            new Message();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
