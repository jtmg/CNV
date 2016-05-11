package Server;
import Client.Rmi_Interface;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class Server extends UnicastRemoteObject implements Rmi_Interface {
    public Server() throws RemoteException {
    }

    ArrayList<Integer> array = new ArrayList<>();
    public static void main (String argv[]) throws RemoteException
    {
        try
        {
            Server obj = new Server();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost/Rmi", obj);
        }catch (Exception e)
        {
            System.err.println("Server Exception: "+e.toString());
            e.printStackTrace();
        }
        System.out.println("Server started..");
        System.out.println("Listening..");
    }
    @Override
    public String addToList(int a) {
        array.add(a);
        System.out.println(array.toString());
        return "success!";
    }


}