import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;



/**
 * Created by josétiago on 31/03/2016.
 */
public class WebServer  {
    public static void main (String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/f.html", new MyHandler());
        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool()); // creates a default executor
        server.start();
		System.out.println("Server Started");


    }



    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            URI url = t.getRequestURI();
            String aux = url.getQuery();
            String[] vector = aux.split("=");
            String Response=null;
            IntFactorization obj = new IntFactorization();
            int i = 0;
            Response="Factoring " + vector[1] + "...";


            System.out.println("Factoring " + vector[1] + "...");
            ArrayList<BigInteger> factors = obj.calcPrimeFactors(new BigInteger(vector[1]));
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
            Response+="";
            t.sendResponseHeaders(200,Response.length());
            OutputStream os = t.getResponseBody();
            os.write(Response.getBytes());
            os.close();

        }
    }

}