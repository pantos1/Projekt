package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Created by Kuba on 2016-04-09.
 */
/**
 * Klasa charakteryzująca gracza i określająca jego parametry.
 */
public class Gracz implements Runnable {

    /**
     * Konstruktor klasy Gracz
     *
     * @param name     - Okresla imie gracza
     * @param fileName - Okresla scieżkę do pliku z ikoną gracza.
     * @param panel -  Referencja na obiekt klasy MapaPanel.
     */
    public Gracz(String name, String fileName, MapaPanel panel) {
        this.name = name;
        icon = new ImageIcon(fileName);
        hp = 100;
        x = panel.getWidth()/2;
        y = panel.getHeight()-icon.getIconHeight();
        this.panel = panel;
    }

    /**
     * Funkcja rysująca postać gracza.
     *
     * @param g Kontekst graficzny.
     */
    void draw(Graphics g) {
        g.drawImage(icon.getImage(), x, y, null);
    }

    /**
     * Funkcja, która zwraca poziom zycia gracza
     */
    int getHP() {
        return hp;
    }

    /**
     * Funkcja, która zwraca imie gracza
     */
    public String getName() {
        return name;
    }

    /**
     * Funkcja zwracająca położenie x gracza.
     * @return Współrzędna x gracza.
     */
    int getX(){return x;}

    /**
     * Funkcja zwracająca położenie y gracza.
     * @return Współrzędna y gracza.
     */
    int getY(){return y;}

    /**
     * Pole przechowujące położenie x gracza.
     */
    private int x;
    /**
     * Pole przechowujące położenie y gracza.
     */
    private int y;
    /**
     * Pole przechowujące prędkość gracza w kierunku x.
     */
    private int dx = 10;
    /**
     * Pole przechowujące kierunek poruszania się gracza wzdłuż osi ox.
     */
    private int xDirection;
    /**
     * Referencja na obiekt klasy MapaPanel, w którym gracz jest tworzony.
     */
    MapaPanel panel;

    /**
     * Obiekt klasy ImageIcon przechowujący ikonę gracza.
     */
    private ImageIcon icon;

    /**
     * Wątek, dzięki któremu możemy sterować wykonywaniem funkcji <i>run()<i> w klasie.
     */
    private Thread kicker;

    /**
     * String przechowujący imię gracza.
     */
    private String name;
    /**
     * Pole przechowujące punkty zdobyte przez gracza.
     */
    private int score;
    /**
     * Pole przechwoujące liczbę punktów życia gracza.
     */
    private int hp;

    /**
     * Zmienna używana do określenia czy ma być wykonywany ruch w lewą stronę.
     */
    boolean left;
    /**
     * Zmienna używana do określenia czy ma być wykonywany ruch w prawą stronę.
     */
    boolean right;

    /**
     * Funkcja określająca ruch gracza. Po zmianie parametru <i>left</i> albo <i>right</i> przez MapaPanel następuje
     * zmiana wartości pola <i>xDirection</i> na odpowiednio -1 i 1. Funkcja sprawdza również, czy gracz nie wykracza
     * poza wielkość panelu.
     */
    private void move() {
        xDirection=0;
        if (left && x>=0) {
            xDirection = -1;
        } else if (right && x+icon.getIconWidth()<panel.getWidth()) {
            xDirection = 1;
        }
        x += xDirection*dx;
    }

    /**
     * Przeciążona metoda <i>run()</i> aktualizująca położenie gracza.
     */
    @Override
    public void run() {
        while (kicker == Thread.currentThread()) {
            move();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    /**
     * Rozpoczyna wątek kicker, co powoduje uruchomienie pętli w funkcji <i>run()</i>.
     */
    public void startLocationUpdateThread(){
        (kicker = new Thread(this)).start();
    }

    /**
     * Przypisuje wątkowi kicker wartość null, dzięki czemu zatrzymana jest pętla w funkcji run().
     */
    public void stopLocationUpdateThread(){
        kicker = null;
    }
}