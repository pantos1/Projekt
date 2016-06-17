package projekt;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Kuba on 2016-06-13.
 */


/**
 * Klasa, która obsługuje komunikację z serwerem po stronie serwera.
 */

public class ServerService implements Runnable {
    private Server server;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private TreeMap<Integer, String> highScores;

    /**
     * ServerService
     * Konstruktor komunikacji z serwerem po klienta do serwera.
     *
     * @param userSocket Gniazdko klienta, który jest podłączony do serwera
     * @param server     Obiekt serwera
     */

    public ServerService(Socket userSocket, Server server, String wyniki) {
        this.server = server;
        clientSocket = userSocket;
        highScores = new TreeMap<Integer, String>();
        Scanner s = null;
        try {
            s = new Scanner(new File(wyniki));
            while (s.hasNext()) {
                highScores.put(Integer.parseInt(s.next()), s.next());
            }
            s.close();
        } catch (FileNotFoundException e) {
        }
    }

    /**
     * Metoda inicjujaca strumienie danych
     */

    void init() throws IOException {
        Reader reader = new InputStreamReader(clientSocket.getInputStream());
        input = new BufferedReader(reader);
        output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    /**
     * Metoda zamykajaca gniazdko dostepu dla klienta oraz strumienie
     */

    void close() {
        try {
            output.close();
            input.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem z zamknięciem połączenia.");
        } finally {
            output = null;
            input = null;
            clientSocket = null;
        }
    }

    /**
     * Funkcja obsługująca komunikację z klientem
     */

    public void run() {
        while (true) {
            String request = receive();
            StringTokenizer st = new StringTokenizer(request);
            String command = st.nextToken();
            if (command.equals(Protocol.LOGIN)) {
                System.out.println("Serwer otrzymał: " + Protocol.LOGIN);
                send(Protocol.LOGGEDIN);
            } else if (command.equals(Protocol.SENDMAP)) {
                System.out.println("Serwer otrzymał: " + command);
                Mapa mapa = new Mapa();
                try {
                    mapa.parsing("server/1.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send(Protocol.SENDINGMAP + " " + mapa.getProperty("numberOfEnemies") + " " + mapa.getProperty("time") + " " + mapa.getProperty("background"));
            } else if (command.equals(Protocol.SENDINGSCORE)) {
                System.out.println("Serwer otrzymał: " + command);
                putScore(st);
                sendHighScores();
            } else if (command.equals(Protocol.SENDHIGHSCORES)) {
                System.out.println("Serwer otrzymał: " + command);
                sendHighScores();
            } else if (command.equals(Protocol.LOGOUT)) {
                System.out.println("Serwer otrzymał: " + command);
                send(Protocol.LOGGEDOUT);
                break;
            } else if (command.equals(Protocol.STOP)) {
                System.out.println("Serwer otrzymał: " + command);
                send(Protocol.STOPPED);
                break;
            } else if (command.equals(Protocol.NULLCOMMAND)) {
                break;
            }
        }
        server.removeClientService(this);
    }

    /**
     * Metoda odbierajaca polecenia przychodzace od klienta.
     */

    private String receive() {
        try {
            return input.readLine();
        } catch (IOException e) {
            System.err.println("Błąd odczytu klienta");
        }
        return Protocol.NULLCOMMAND;
    }

    /**
     * Metoda wysylajaca polecenia do klienta.
     */

    public void send(String message) {
        output.println(message);
    }

    /**
     * Metoda przygotowująca do wysłania listę najlepszych wyników oraz wysyłająca ją.
     */
    void sendHighScores() {
        int i;
        String message = Protocol.SENDINGHIGHSCORES;
        for (i = 0; i <= highScores.size(); i++) {
            Map.Entry<Integer,String>me=highScores.pollLastEntry();
            message = message + " " + String.valueOf(me.getKey()) + " " + me.getValue();
        }
        System.out.println(message);
        send(message);
    }

    /**
     * Metoda umieszczająca nowy wynik w obiekcie TreeMap przechowującym najlepsze wyniki
     * @param st Referencja na obiekt typu StringTokenizer tokenizujący wiadomość z wynikiem
     */
    void putScore(StringTokenizer st) {
        highScores.put(Integer.parseInt(st.nextToken()), st.nextToken());
        if (highScores.size() > 10) {
            TreeMap<Integer, String> tmp = new TreeMap<Integer, String>();
            tmp.putAll(highScores);
            highScores.clear();
            int i;
            for (i = 0; i < 10; i++) {
                highScores.put(tmp.pollLastEntry().getKey(), tmp.pollLastEntry().getValue());
            }
        }
    }
}