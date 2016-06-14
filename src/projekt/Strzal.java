package projekt;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa, ktora charakteryzuje strzal i okresla jego parametry.
 */
public class Strzal implements Runnable {

    /**
     * Konstruktor klasy Strzal
     *
     * @param fileName - Określa ścieżkę do pliku, w którym znajduje się ikona strzału.
     * @param panel - referencja na obiekt klasy MapaPanel
     */

    public Strzal(String fileName, MapaPanel panel) {
        icon = new ImageIcon(fileName);
        this.panel = panel;
        x = panel.gracz.getCenter();
        y = panel.gracz.getY();
    }
/*
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
     * Obiekt klasy ImageIcon przechowujący ikonę gracza.
     */
    ImageIcon icon;
    /**
     * Referencja na obiekt klasy MapaPanel, w którym jest tworzony strzał.
     */
    MapaPanel panel;
    /**
     * Pole, które przechowuje położenie x strzału
     */
    public int x;
     /**
     * Pole, które przechowuje położenie y strzału
     */
    public int y;
    /**
     * Pole, które przechowuje prędkość strzału w kierunku y.
     */
    private int dy = -10;
     /**
     * Pole, które przechowuje moc pocisku.
     */
    private int moc;
    /**
     * Wątek, dzięki któremu możemy sterować wykonywaniem funkcji <i>run()<i> w klasie.
     */
    private Thread kicker;

    /**
     * Funkcja, która odpowiada za poruszanie się pocisku. Jeżeli strzał zostanie wywołany (wciśniemy spację) 
     * zacznie się on poruszać z miejsca, w którym znajduje się gracz. 
     */

    private void move() {
        if(!isVisible) {
            x = panel.gracz.getCenter();
            y = panel.gracz.getY();
        }
        else{
            y+=dy;
        }
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
