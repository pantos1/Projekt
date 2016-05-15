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
     * liczbą punktów życia. Tworzy i dodaje do listy liczbę przeciwników zgodną z plikiem konfiguracyjnym. Tworzy obiekt klasy Strzał,
     * który jest jednak początkowo niewidoczny.
     * @param mapa Obiekt klasy Mapa która jest obecnie załadowana.
     * @param name Imię gracza do przekazania do konstruktora obiektu klasy Gracz
     * @param frame Referencja do obiektu klasy MapaFrame, w którym został stoworzony panel.
     */
    public MapaPanel(Mapa mapa, String name, MapaFrame frame){
        this.frame = frame;
        img = new ImageIcon(mapa.getBackground()).getImage();
        przeciwnicy = new ArrayList<Przeciwnik>();
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);

        gracz = new Gracz(name, "img/gracz.png", this);

        imie = new JLabel(name);
        imie.setFont(new Font("Verdana",1,16));
        add(imie, BorderLayout.LINE_START);

        hp = new JLabel("HP:"+String.valueOf(gracz.getHP()));
        hp.setFont(new Font("Verdana",1,16));
        add(hp, BorderLayout.LINE_START);

        int i;
        for(i=0; i<mapa.getNumberOfEnemies(); i++){
            przeciwnicy.add(new Przeciwnik("img/janusz.png", this));
            przeciwnicy.get(i).startLocationUpdateThread();
        }

        addKeyListener(this);
        setFocusable(true);
        gracz.startLocationUpdateThread();

        strzal = new Strzal("img/strzal.png", this);
        strzal.startLocationUpdateThread();
    }

    /**
     * Przeciążona metoda paintComponent rysująca obrazek <i>img</i> w tle.
     * @param g Kontekst graficzny
     */
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(img,0,0, null);
    }

    /**
     * Przeciążona metoda paint, rysująca gracza, strzał oraz przeciwników.
     * @param g Kontekst graficzny.
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            przeciwnicy.get(i).draw(g);
        }
        gracz.draw(g);
		strzal.draw(g);
    }

    /**
     * Przeciążona metoda keyPressed, która wychwytuje wciśnięcie przycisków prawej i lewej strzałki oraz spacji,
     * aby poinformować gracza i strzał o zdarzeniu.
     * @param evt Zdarzenie wychwycone przez KeyListener.
     */
    @Override
    public void keyPressed(KeyEvent evt){
        if (evt.getKeyCode() == KeyEvent.VK_LEFT){
            gracz.left = true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_RIGHT){
            gracz.right = true;
        }
		else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            strzal.isVisible = true;
        }
    };

    /**
     * Przeciążona metoda keyReleased, która wychwytuje koniec wciśnięcia przycisków prawej i lewej strzałki oraz spacji,
     * aby poinformować gracza o zdarzeniu.
     * @param evt Zdarzenie wychwycone przez KeyListener.
     */
    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            gracz.left = false;
        } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            gracz.right = false;
        }
		else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {

        }
    }

    /**
     * Przeciążona metoda keyTyped, niewykorzystywana w programie.
     * @param evt Zdarzenie wychwycone przez KeyListener.
     */
    @Override
    public void keyTyped(KeyEvent evt) {

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
    Gracz gracz;
    /**
     * Obrazek będący tłem mapy.
     */
    private Image img;
    /**
     * Lista przechowująca wszystkie obiekty klasy Przeciwnik dla danego poziomu.
     */
    private ArrayList <Przeciwnik> przeciwnicy;
    /**
     * Wątek, dzięki któremu możemy sterować wykonywaniem funkcji <i>run()<i> w klasie.
     */
    Thread kicker = null;
    /**
     * Referencja na obiekt klasy MapaFrame, który tworzy obiekt tej klasy.
     */
    private MapaFrame frame;
    /**
     * Referencja na obiekt klasy Strzal, który jest rysowany w MapaPanel.
     */
    private Strzal strzal;

    /**
     * Przeciążona metoda <i>run()</i> która wywołuje metodę repaint i odpowiada za sterowanie rysowaniem obiektów.
     * Usypia również wątek.
     */
    @Override
    public void run() {
        while(kicker == Thread.currentThread()){
            repaint();
            try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Funkcja rozpoczynająca wątek rysowania animacji oraz wywołująca funkcje rozpoczynające wątki aktualizujące
     * położenie gracza, strzału i przeciwników.
     */
    void startAnimationThread(){
        (kicker = new Thread(this)).start();
        gracz.startLocationUpdateThread();
        strzal.startLocationUpdateThread();
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            przeciwnicy.get(i).startLocationUpdateThread();
        }
    }

    /**
     * Funkcja zatrzymująca wątek rysowania animacji oraz wywołująca funkcje zatrzymujące wątki aktualizujące
     * położenie gracza, strzału i przeciwników.
     */
    void stopAnimationThread(){
        kicker = null;
        gracz.stopLocationUpdateThread();
        strzal.stopLocationUpdateThread();
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            przeciwnicy.get(i).stopLocationUpdateThread();
        }
    }

    /**
     * Funkcja wywoływana na koniec gry, zatrzymująca wątek animacji oraz otwierająca okno dialogowe pytające o rozpoczęcie
     * nowej gry.
     */
    void gameOver(){
        stopAnimationThread();
        String[] options = new String[] {"Tak", "Nie"};
        int d = JOptionPane.showOptionDialog(this, "Czy chcesz zagrać jeszcze raz?", "Koniec gry",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        if (d == JOptionPane.NO_OPTION){
            System.exit(0);
        }
        else if (d == JOptionPane.YES_OPTION){
            frame.remove(this);
            frame.newGame(gracz.getName());
        }
    }
}
