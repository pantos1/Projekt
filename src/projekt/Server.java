package projekt;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by Kuba on 2016-06-13.
 */

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Vector<ServerService> clients = new Vector<ServerService>();
    private Properties props;
    private int port = 9877;

/**
     * Konstruktor, który służy do ustawienia serwera na odpowiednim porcie oraz lokalnym adresie ip.
     * @param p Ustawienia serwera
     */

    public Server(Properties p) {
        props = p;
        port = Integer.parseInt(props.getProperty("port"));
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("PORT ====> " + port);
        } catch (IOException e) {
            System.err.println("Error starting Server.");
            System.exit(1);
        }
        new Thread(this).start();
}
    public void run() {
        while (true)
            try {
                System.out.println("Czekam");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowy użytkownik");
                ServerService clientService = new ServerService(clientSocket, this, props.getProperty("wyniki"));
                addClientService(clientService);
            } catch (IOException e) {
                System.err.println("Error accepting connection. "
                        + "Client will not be served...");
            }
    }
/**
     * addClientService
     * Metoda odpowiadająca za łączenie klientów z serwerem
     * @param clientService Klient, który wysyła żądanie łączenia z serwerem.
     */
    synchronized void addClientService(ServerService clientService)
            throws IOException {
        clientService.init();
        clients.addElement(clientService);
        new Thread(clientService).start();
        System.out.println("Klienci zostali dodani: " + clients.size());
    }
   /**
     * removeClientService
     * Metoda, która odpowiada za zakończenie połączenia między klientem a serwerem
     * @param clientService Klient, którego połączenie z serwerem jest zamykane.
     */

    synchronized void removeClientService(ServerService clientService) {
        clients.removeElement(clientService);
        clientService.close();
        System.out.println("Klienci zostali usunięci " + clients.size());
    }
//disonnect

    public static void main(String args[]) {
        Properties props = new Properties();
        String propsFile = "server/server.txt";
        try {
            props.load(new FileInputStream(propsFile));
        } catch (Exception e) {
            props.put("port", "9877");
            props.put("wyniki", "server/wyniki.txt");
        }
        try {
            props.store(new FileOutputStream(propsFile), null);
        } catch (Exception e) {
        }
        new Server(props);
    }
}