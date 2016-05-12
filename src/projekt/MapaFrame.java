package projekt;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                super.windowIconified(e);
                main.stopAnimationThread();
            }
            public void windowDeiconified(WindowEvent e){
                super.windowDeiconified(e);
                main.startAnimationThread();
            }
        });
        newGame(name);
    }
    void newGame(String name){
        Mapa _1 = new Mapa();
        try {
            _1.parsing("config/1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        main = new MapaPanel(_1, name, this);
        add(main);
        pack();
        main.startAnimationThread();
    }

    private MapaPanel main;
}
