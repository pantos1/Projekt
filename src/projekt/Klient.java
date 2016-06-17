package projekt;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;

/*
 * Created by Kuba on 2016-06-14.
 */

/*
 * Klasa klienta Władek Invaders, służy do pobierania wyników oraz ewentualnego przesyłania wyników na serwer.
 */

public class Klient implements Runnable{
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private InetAddress host;
    private int port = 9877;

/*
     * Konstruktor tworzący obiekt komunikacji klienta w grze.
     * Służy on do utworzenia gniazdka klienta oraz wysłanie wstępnej wiadomości na serwer - w zależności od tego działania
     * Informuje się aplikację czy działamy w trybie online czy offline.
     * @param host - Adres serwera
     * @param port - Port, na którym tworzymy gniazdko klienta
 */

    public Klient(InetAddress host, int port) throws Exception {
        try {
            this.host = host;
            this.port = port;
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            throw new Exception("Unknown host.");
        } catch (IOException e) {
            throw new Exception("IO exception while connecting to the server.");
        } catch (NumberFormatException e) {
            throw new Exception("Port value must be a number.");
        }

        init();
        new Thread(this).start();
        send(Protocol.LOGIN);
        }
        /*
         * Metoda inicjujaca strumienie danych od i do klienta.
        */

        public void init() {
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            } catch (IOException ex) {
            }
        }

    /*
     * Cialo dzialania obslugi komunikacji z serwerem po stronie klienta.
     */

    public void run() {
        while (true)
            try {
                String command = input.readLine();
                if (!handleCommand(command)) {
                    input.close();
                    output.close();
                    socket.close();
                    break;
                }
            } catch (IOException e) {
            }
        output = null;
        input = null;
        synchronized (this) {
            socket = null;
        }
    }
/*
     * Metoda, która określa kolejność i sposób komunikacji klient-serwer.
     * @param command Polecenie przeslane ze strony serwera.
*/

    private boolean handleCommand(String command) {
        StringTokenizer st = new StringTokenizer(command);
        String cd = st.nextToken();
        if (command.equals(Protocol.LOGGEDIN)) {
            send(Protocol.SENDMAP);
        }
        else if(cd.equals(Protocol.LOGGEDOUT))
        {
            System.out.println("Klient otrzymał: "+command);
            System.exit(0);
        }
        else if (cd.equals(Protocol.SENDINGMAP)){
            System.out.println("Klient otrzymał: "+command);
            parseMap(st);
        }
        else if (cd.equals(Protocol.SENDINGHIGHSCORES)){
            System.out.println("Klient otrzymał: "+command);
            parseHighScores(st);
        }
        else if (cd.equals(Protocol.NULLCOMMAND))
        {
            System.out.println("Klient otrzymał: "+command);
            return false;
        }
        return true;
    }

    /**
     * Metoda wysyłająca żądanie SENDINGCORE, informujące serwer żeby przygotował się do pobrania nowego wyniku.
     * @param name Nick gracza
     * @param wynik Wynik gracza
     */
    void sendScore(String name, int wynik){
        send((Protocol.SENDINGSCORE)+" "+wynik+" "+name);
    }

    /**
     * Metoda wysyłająca żądanie SENDHIGHSCORES.
     */
    void sendHighScores(){
        send(Protocol.SENDHIGHSCORES);
    }
    /**
     * Metoda parsująca wiadomość z listą najlepszych wyników
     * @param st Referencja na obiekt typu StringTokenizer który tokenizuje wiadomość
     */
    void parseHighScores(StringTokenizer st){
        String lines[] = new String[10];
        int i=0;
        while(st.hasMoreTokens()){
            String _1 = st.nextToken();
            String _2 = st.nextToken();
            if((!(_1==null)) && (!(_2==null))){
                lines[i] = _1+" "+_2;
                i++;
            }
        }
        List<String> list = Arrays.asList(lines);
        try{
            Files.write(Paths.get("txt/wyniki.txt"), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda parsująca wiadomość z mapą
     * @param st Referencja na obiekt typu StringTokenizer który tokenizuje wiadomość
     */
    void parseMap(StringTokenizer st){
        String lines[] = new String[3];
        lines[0]="numberOfEnemies "+st.nextToken();
        lines[1]="time "+st.nextToken();
        lines[2]="background "+st.nextToken();
        List<String> list = Arrays.asList(lines);
        try{
            Files.write(Paths.get("config/1.txt"), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
     * Metoda wysylajaca polecenia do serwera.
     * @param command Polecenie wysylane do serwera.
*/

    public void send(String command) {
        output.println(command);
    }
}
