package projekt;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

import static java.lang.Math.abs;

/**
 * Created by Kuba on 2016-06-16.
 */
/**
 * Klasa, ktora charakteryzuje strzal przeciwnika i okresla jego parametry.
 */
public class StrzalPrzeciwnik implements Runnable{
    /**
     * Konstruktor klasy StrzalPrzeciwnik
     *
     * @param fileName - Określa ścieżkę do pliku, w którym znajduje się ikona strzału.
     * @param panel - referencja na obiekt klasy MapaPanel
     * @param przeciwnik - referencja na obiekty klasy Przeciwnik
     */
    public StrzalPrzeciwnik(String fileName, MapaPanel panel, Przeciwnik przeciwnik) {
        icon = new ImageIcon(fileName);
        this.panel = panel;
        this.przeciwnik = przeciwnik;
        x = przeciwnik.getCenter();
        y = (przeciwnik.getY());
        timer = new java.util.Timer();
        timerTask = new TimerTask(){
            public void run() {
                if(!isVisible){
                    Random random = new Random();
                    int i = random.nextInt(10);
                    if(i>7) isVisible = true;
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    /**
     * Pole, które określa czy strzał jest widoczny na mapie
     */
    public boolean isVisible=false;

    /**
     * Funkcja rysująca pocisk.
     *
     * @param g Kontekst graficzny.
     */
    void draw(Graphics g) {
        if(isVisible){
            g.drawImage(icon.getImage(), x, y, null);
        }
    }

    /**
     * Funkcja, która odpowiada za resetowanie położenia pocisku i ustawianie go w miejscu, w którym znajduje się przeciwnik.
     */
    void reset(){
        x = przeciwnik.getCenter();
        y = przeciwnik.getY();
    }

    /**
     * Obiekt klasy ImageIcon przechowujący ikonę gracza.
     */
    ImageIcon icon;

    /**
     * Referencja na obiekt klasy MapaPanel, w którym jest tworzony strzał.
     */
    MapaPanel panel;

    /**
     * Referencja na obiekt klasy Przeciwnik.
     */
    Przeciwnik przeciwnik;

    /**
     * Pole, które przechowuje położenie x strzału
     */
    public int x;

    /**
     * Pole, które przechowuje położenie y strzału.
     */
    public int y;

    /**
     * Pole, które przechowuje prędkość strzału w kierunku y.
     */
    private int dy = 4;

    /**
     * Wątek, dzięki któremu możemy sterować wykonywaniem funkcji <i>run()<i> w klasie.
     */
    private Thread kicker;

    /**
     * Pola odpowiadające za pracę timera.
     */
    private Timer timer;
    private TimerTask timerTask;

    /**
     * Funkcja, która odpowiada za poruszanie się pocisku. Jeżeli strzał zostanie wywołany (wciśniemy spację)
     * zacznie się on poruszać z miejsca, w którym znajduje się gracz.
     */

    private void move() {
        if(!isVisible) {
            x = przeciwnik.getCenter();
            y = przeciwnik.getY();
        }
        else y+=dy;
        if(y>panel.getHeight())isVisible=false;
    }
    /**
     * Przeciążona metoda run() aktualizująca położenie strzału.
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