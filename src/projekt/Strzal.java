package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * Created by Kuba on 2016-05-14.
 */
/**
 * Klasa, kt?ra charakteryzuje strza? i okre?la jego parametry.
 */
public class Strzal implements Runnable {

    /**
     * Konstruktor klasy Strzal
     *
     * @param fileName - Określa ścieżkę do pliku, w którym znajduje się ikona strzału.
     */

    public Strzal(String fileName, MapaPanel panel) {
        icon = new ImageIcon(fileName);
        this.panel = panel;
    }

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

    private ImageIcon icon;
    MapaPanel panel;
    public int x;
    public int y;
    private int dy = -10;
    private int moc;

    private Thread kicker;

    /**
     * Funkcja, która odpowiada za poruszanie się pocisku.
     */

    private void move() {
        if(!isVisible) {
            x = panel.gracz.getX();
            y = panel.gracz.getY();
        }
        else{
            y+=dy;
        }
        if(y<0)isVisible=false;
    }

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

    public void startLocationUpdateThread(){
        (kicker = new Thread(this)).start();
    }

    public void stopLocationUpdateThread(){
        kicker = null;
    }
}