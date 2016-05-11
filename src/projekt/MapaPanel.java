package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Klasa tworząca panel na ktorym będą rysowane obiekty gry - przeciwnicy, gracz, strzał.
 */
public class MapaPanel extends JPanel implements KeyListener, Runnable{
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
            przeciwnicy.add(new Przeciwnik(100*i, 100*i, "img/janusz.png", this));
        }

        addKeyListener(this);
        setFocusable(true);
    }

    /**
     * Przeciążona metoda paintComponent rysująca obrazek <i>img</i> w tle.
     * @param g Kontekst graficzny
     */
    public void paintComponent(Graphics g){
        g.drawImage(img,0,0, null);
    }

    /**
     * Przeciążona metoda paint, rysująca gracza oraz przeciwników.
     * @param g Kontekst graficzny.
     */
    public void paint(Graphics g){
        super.paint(g);
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            przeciwnicy.get(i).draw(g);
        }
        gracz.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent evt){
        if (evt.getKeyCode() == KeyEvent.VK_LEFT){
            gracz.left=true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_RIGHT){
            gracz.right=true;
        }
    };

    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            gracz.left = false;
        } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            gracz.right = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent evt){
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

    @Override
    public void run() {
        while(true){
            repaint();
            gracz.move();
            int i;
            for(i=0;i<przeciwnicy.size();i++){
                przeciwnicy.get(i).move();
            }
            try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
