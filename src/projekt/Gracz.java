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
     * @param fileName - Okresla scieżka do pliku z ikon gracza.
     */
    public Gracz(String name, String fileName) {
        this.name = name;
        icon = new ImageIcon(fileName);
        hp = 100;
        x = 500;
    }

    /**
     * Funkcja rysująca postać gracza.
     *
     * @param g Kontekst graficzny.
     */
    public void draw(Graphics g) {
        g.drawImage(icon.getImage(), x, 750, null);
    }

    /**
     * Funkcja, która zwraca referencje do obiektu przechowujacego ikone gracza
     */

    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * Funkcja, która zwraca poziom zycia gracza
     */
    public int getHP() {
        return hp;
    }

    /**
     * Funkcja, która zwraca imie gracza
     */
    public String getName() {
        return name;
    }

    protected int x;
    protected int dx = 10;

    private ImageIcon icon;

    private double levelSpeed;

    //levelSpeed = speed*mapNumber;

    private String name;
    private double score;
    private int hp;

    protected boolean left;
    protected boolean right;

    public Gracz(String name) {
        this.name = name;
    }

    public void move() {
        // nowa pozycja gracza
        if (left) {
            x -= dx;
            /*if (x < -levelSpeed) {
                x = 0;
            }*/
        } else if (right) {
            x += dx;
            /*if (x > levelSpeed) {
                //? x = map.width;
            }*/
        }
    }

    @Override
    public void run() {
        while (true) {
            move();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}