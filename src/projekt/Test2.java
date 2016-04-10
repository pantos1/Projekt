package projekt;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa pełniąca funkcję inicjalizującą grę, tworzy nowy wątek. W tym wątku tworzy obiekt klasy Mapa na podstawie pliku konfiguracyjnego oraz
 * obiekt klasy Gracz na podstawie podanego imienia. Tworzy również okno gry, tworząc obiekty klasy MapaFrame oraz MapaPanel.
 */
public class Test2 {
    /**
     * Konstruktor obiektu klsay Test2.
     * @param name Imię gracza
     */
    public Test2(String name) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Mapa _1= new Mapa();
                Gracz gracz = new Gracz(name, "img/gracz.png");
                try {
                    _1.parsing("config/1.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MapaFrame frame = new MapaFrame();
                MapaPanel main = new MapaPanel(_1, gracz);
                frame.add(main);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
