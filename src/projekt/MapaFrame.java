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
     * Konstruktor obiektu klasy MapaFrame. Tworzy ramkę o tytule gry - "Władek Invaders". Nasłuchuje też zdarzeń
     * związań z minimalizacją okna i zatrzymuje wówczas wątek animacji gry. Wywołuje funkcję newGame odpowiedzialną
     * za stworzenie nowej gry.
     */
    public MapaFrame(String name, Klient klient, String sciezka){
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
        this.sciezka = sciezka;
        newGame(name, klient);
    }

    /**
     * Funkcja tworząca obiekt klasy Mapa na podstawie pliku konfiguracyjnego oraz obiekt klasy MapaPanel, będący planszą gry.
     * @param name Imię gracza.
     */
    void newGame(String name, Klient klient){
        Mapa _1 = new Mapa();
        try {
            _1.parsing("config/1.txt");
        } catch (IOException e) {
        }
        main = new MapaPanel(_1, name, this, klient, sciezka);
        add(main);
        pack();
        main.startAnimationThread();
    }

    /**
     * Referencja na obiekt klasy MapaPanel tworzony przez MapaFrame.
     */
    private MapaPanel main;
    /**
     * Scieżka do pliku przechowującego obrazek z bohaterem gry.
     */
    private String sciezka;
}
