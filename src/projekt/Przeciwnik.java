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
public class Przeciwnik{

    /**
     * Konstruktor klasy Przeciwnik
     * @param x Początkowe położenie w kierunku x
     * @param y Początkowe położenie w kierunku y
     * @param fileName Scieżka dostępu do pliku przechowującego ikonę przeciwnika.
     */
    public Przeciwnik(int x, int y, String fileName){
        this.x = x;
        this.y = y;
        icon = new ImageIcon(fileName);
    }

    /**
     * Funkcja zwracająca referencję do obiektu icon klasy ImageIcon przechowującego ikonę obiektu.
     * @return
     */
    public ImageIcon getIcon(){
        return icon;
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
}
