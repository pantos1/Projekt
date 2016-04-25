package projekt;

import javax.swing.*;


/**
 * Created by Kuba on 2016-04-09.
 */
/**
 * Klasa charakteryzująca gracza i określająca jego parametry.
 */
public class Gracz{

    /**
     * Konstruktor klasy Gracz
     * @param name - Okresla imie gracza
     * @param fileName - Okresla scieżka do pliku z ikon gracza.
     */
    public Gracz(String name, String fileName){
        this.name = name;
        icon = new ImageIcon(fileName);
        hp = 100;
    }

    /**
     * Funkcja, która zwraca referencje do obiektu przechowujacego ikone gracza
     */

    public ImageIcon getIcon(){
        return icon;
    }

    /**
     * Funkcja, która zwraca poziom zycia gracza
     */
    public int getHP(){
        return hp;
    }
    /**
     * Funkcja, która zwraca imie gracza
     */
    public String getName(){
        return name;
    }

    private double x;

    private ImageIcon icon;

    private double speed;
    private double levelSpeed;

    //levelSpeed = speed*mapNumber;

    private String name;
    private double score;
    private int hp;

    private boolean left;
    private boolean right;

    public Gracz(String name){
        this.name=name;
    }
/*
    public void update() {
        // nowa pozycja gracza
        if (left) {
            x -= levelSpeed;
            if (x < -levelSpeed) {
                x = 0;
            }
        } else if (right) {
            x += levelSpeed;
            if (x > levelSpeed) {
                //? x = map.width;
            }
     }
    } */
}