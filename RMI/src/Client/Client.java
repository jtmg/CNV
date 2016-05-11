package Client;

import java.rmi.Naming;

public class Client {

    public static void main (String argv[])
    {

        try
        {
            Rmi_Interface RmiServerIntf = (Rmi_Interface) Naming.lookup("rmi://localhost/Rmi");
            System.out.println(RmiServerIntf.addToList(46546));

        }catch (Exception e)
        {
            System.err.println("Client.Client exeception: "+e.toString());
            e.printStackTrace();
        }
    }

}
