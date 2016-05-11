package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Rmi_Interface extends Remote {

    String addToList (int a)  throws RemoteException;
}
