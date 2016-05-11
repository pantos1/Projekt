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
     * @param x Początkowe położenie w kierunku x
     * @param y Początkowe położenie w kierunku y
     * @param fileName Scieżka dostępu do pliku przechowującego ikonę przeciwnika.
     */
    public Przeciwnik(int x, int y, String fileName, MapaPanel panel){
        this.x = x;
        this.y = y;
        icon = new ImageIcon(fileName);
        width=icon.getIconWidth();
        height=icon.getIconHeight();
        this.panel = panel;
    }
    /**
     * Funkcja rysująca przeciwnika.
     * @param g Kontekst graficzny
     */
    public void draw(Graphics g){
        g.drawImage(icon.getImage(),x,y,null);
    }
    public void move(){
        if(x<0){
            xDirection=1;
        }
        else if(x+width>panel.getWidth()) {
            xDirection=-1;
        }
        x+=xDirection*dx;
    }

    @Override
    public void run() {
        while(true){
            move();
            try{
                Thread.sleep(10);
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }
    /**
     * Funkcja zwracająca współrzędną x położenia obiektu.
     * @return
     */
    public int getX(){
        return x;
    }

    /**
     * Funkcja zwracająca współrzędną y położenia obiektu.
     * @return
     */
    public int getY(){
        return y;
    }

    /**
     * Pole przechowujące współrzędną x położenia obiektu
     */
    protected int x;
    /**
     * Pole przechowujące współrzędną y położenia obiektu
     */
    protected int y;
    /**
     * Pole przechowujace obiekt icon klasy ImageIcon zawierającej ikonę obiektu Przeciwnik.
     */
    protected ImageIcon icon;
    protected int width;
    protected int height;
    protected int dx=5;
    protected int xDirection=1;
    protected MapaPanel panel;
}
