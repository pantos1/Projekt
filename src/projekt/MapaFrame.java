package projekt;

import javax.swing.*;
import java.io.IOException;

/**
 * Klasa tworząca główną ramkę okna mapy(gry).
 */
public class MapaFrame extends JFrame {
    /**
     * Konstruktor obiektu klasy MapaFrame. Tworzy ramkę o tytule gry - "Władek Invaders". Tworzy również obiekty klas Mapa, Gracz i MapaPanel.
     */
    public MapaFrame(String name){
        super("Władek Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Mapa _1 = new Mapa();
        Gracz gracz = new Gracz(name, "img/gracz.png");
        try {
            _1.parsing("config/1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        MapaPanel main = new MapaPanel(_1, gracz);
        add(main);
        pack();
        new Thread(main).start();
    }
}
