package projekt;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Klasa tworząca panel na ktorym będą rysowane obiekty gry - przeciwnicy, gracz, strzał.
 */
public class MapaPanel extends JPanel{
    /**
     * Konstruktor klasy, ładuje obrazek będący tłem mapy. Ustawia wymiary panelu. Tworzy i ustawia napisy z imieniem gracza i
     * liczbą punktów życia. Tworzy i dodaje do listy liczbę przeciwników zgodną z plikiem konfiguracyjnym.
     * @param mapa Obiekt klasy Mapa która jest obecnie załadowana.
     * @param gracz Obiekt klasy Gracz który obecnie uczestniczy w grze.
     */
    public MapaPanel(Mapa mapa, Gracz gracz){
        this.gracz = gracz;
        img = new ImageIcon(mapa.getBackground()).getImage();
        przeciwnicy = new ArrayList<Przeciwnik>();
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);

        imie = new JLabel(gracz.getName());
        imie.setFont(new Font("Verdana",1,16));
        add(imie, BorderLayout.LINE_START);

        hp = new JLabel("HP:"+String.valueOf(gracz.getHP()));
        hp.setFont(new Font("Verdana",1,16));
        add(hp, BorderLayout.LINE_START);


        int i;
        for(i=0; i<mapa.getNumberOfEnemies(); i++){
            przeciwnicy.add(new Przeciwnik(100*i, 100*i, "img/janusz.png"));
        }
    }

    /**
     * Przeciążona metoda paintComponent rysująca obrazek <i>img</i> w tle.
     * @param g Kontekst graficzny
     */
    public void paintComponent(Graphics g){
        g.drawImage(img,0,0, null);
    }

    /**
     * Funkcja rysująca w pętli wszystkich przeciwników przechowywanych w ArrayList przeciwnicy.
     * @param g Kontekst graficzny
     */
    public void drawEnemies(Graphics g){
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            g.drawImage(przeciwnicy.get(i).getIcon().getImage(),przeciwnicy.get(i).getX(),przeciwnicy.get(i).getY(),null);
        }
    }

    /**
     * Funkcja rysująca postać gracza.
     * @param g Kontekst graficzny.
     */
    public void drawPlayer (Graphics g){
        g.drawImage(gracz.getIcon().getImage(),500,750,null);
    }

    /**
     * Przeciążona metoda paint, wywołująca metody drawPlayer oraz drawEnemies.
     * @param g Kontekst graficzny.
     */
    public void paint(Graphics g){
        super.paint(g);
        drawEnemies(g);
        drawPlayer(g);
    }

    /**
     * Obiekt klasy JLabel przechowujący liczbę punktów życia.
     */
    private JLabel hp;
    /**
     * Obiekt klasy JLabel przechowujący imię gracza.
     */
    private JLabel imie;
    /**
     * Aktualnie uczestniczący w grze gracz.
     */
    private Gracz gracz;
    /**
     * Obrazek będący tłem mapy.
     */
    private Image img;
    /**
     * Lista przechowująca wszystkie obiekty klasy Przeciwnik dla danego poziomu.
     */
    private ArrayList <Przeciwnik> przeciwnicy;
}
