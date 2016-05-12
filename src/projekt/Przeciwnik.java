package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Klasa przechowująca dane o przeciwnikach
 */
public class Przeciwnik implements Runnable{

    /**
     * Konstruktor klasy Przeciwnik
     * @param fileName Scieżka dostępu do pliku przechowującego ikonę przeciwnika.
     * @param panel Referencja na obiekt klasy MapaPanel, który przechowuje obiekt klasy Przeciwnik.
     */
    public Przeciwnik(String fileName, MapaPanel panel){
        x = (int) (Math.random() * panel.getWidth());
        y = (int) (Math.random() * panel.getHeight()/3);
        icon = new ImageIcon(fileName);
        this.panel = panel;
    }
    /**
     * Funkcja rysująca przeciwnika.
     * @param g Kontekst graficzny
     */
    void draw(Graphics g){
        g.drawImage(icon.getImage(),x,y,null);
    }
    private void move(){
        if(x<=0){
            xDirection=1;
            y+=dy;
        }
        else if(x+icon.getIconWidth()>panel.getWidth()) {
            xDirection=-1;
            y+=dy;
        }
        x+=xDirection*dx;
        if(y+icon.getIconHeight()>panel.getHeight()){
            panel.gameOver();
        }
    }

    @Override
    public void run() {
        while(kicker == Thread.currentThread()){
            move();
            try{
                Thread.sleep(10);
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    void startLocationUpdateThread(){
        (kicker = new Thread(this)).start();
    }

    void stopLocationUpdateThread(){
        kicker = null;
    }

    /**
     * Pole przechowujące współrzędną x położenia obiektu
     */
    private int x;
    /**
     * Pole przechowujące współrzędną y położenia obiektu
     */
    private int y;
    /**
     * Pole przechowujace obiekt icon klasy ImageIcon zawierającej ikonę obiektu Przeciwnik.
     */
    private ImageIcon icon;
    private int dx = 5;
    private int dy = 20;
    private int xDirection=1;
    MapaPanel panel;
    private Thread kicker;
}
