package projekt;

import javax.swing.*;

/**
 * Klasa tworząca główną ramkę okna mapy(gry).
 */
public class MapaFrame extends JFrame {
    /**
     * Konstruktor obiektu klasy MapaFrame. Tworzy ramkę o tytule gry - "Władek Invaders".
     */
    public MapaFrame(){
        super("Władek Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
}
