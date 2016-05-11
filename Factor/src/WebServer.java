import com.sun.net.httpserver.HttpExchange;
import java.net.InetAddress;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.math.BigInteger;
import java.net.URI;

import java.net.UnknownHostException;
import java.util.ArrayList;


public class WebServer  {
    public static void main (String[] args)  throws Exception  {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/f.html", new MyHandler());
       // server.setExecutor((Executor) java.util.concurrent.Executors.defaultThreadFactory()); // creates a default executor

        server.setExecutor(null);
        server.start();
		System.out.println("Server Started");


    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Aqui");
                    URI url = t.getRequestURI();
                    String aux = url.getQuery();
                    String[] vector = aux.split("=");
                    String Response=null;
                    IntFactorization obj = new IntFactorization();
                    int i = 0;
                    Response="Factoring " + vector[1] + "...";


                    System.out.println("Factoring " + vector[1] + "...");
                    ArrayList<BigInteger> factors = null;
                    try {
                        factors = obj.calcPrimeFactors(new BigInteger(vector[1]));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    System.out.println("");
                    System.out.print("The prime factors of " + vector[1]  + " are ");
                    for (BigInteger bi: factors) {
                        i++;
                        System.out.print(bi.toString());
                        Response+=bi.toString();
                        if (i == factors.size()) {
                            System.out.println(".");
                            Response+=".";
                        } else {
                            System.out.print(", ");
                            Response+=", ";
                        }
                    }
                    System.out.println("");
                    InetAddress ip = null;
                    try {
                        ip = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    long threadId = Thread.currentThread().getId();
                    String parameters =ip.toString() + String.valueOf(threadId);
                    System.out.println("Thread ID:" + threadId);
                    System.out.println("Thread IDServer: " + parameters.hashCode());
                    Response+="";
                    try {
                        t.sendResponseHeaders(200,Response.length());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    OutputStream os = t.getResponseBody();
                    try {
                        os.write(Response.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }).start();

        }
    }

}