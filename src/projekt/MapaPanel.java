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
     * @param name Imię gracza do przekazania do konstruktora obiektu klasy Gracz
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
     * Przeciążona metoda paint, rysująca gracza oraz przeciwników.
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
		if(strzal.y<480 && strzal.y>-20){strzal.draw(g);}
    }

    @Override
    public void keyPressed(KeyEvent evt){
        if (evt.getKeyCode() == KeyEvent.VK_LEFT){
            gracz.left=true;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_RIGHT){
            gracz.right=true;
        }
		else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            strzal.spacja = true;
            Strzal strzal2 = new Strzal("/strzal.png", this);
        }
    };

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
    Thread kicker = null;
    private MapaFrame frame;

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

    void startAnimationThread(){
        (kicker = new Thread(this)).start();
        gracz.startLocationUpdateThread();
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            przeciwnicy.get(i).startLocationUpdateThread();
        }
    }

    void stopAnimationThread(){
        kicker = null;
        gracz.stopLocationUpdateThread();
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            przeciwnicy.get(i).stopLocationUpdateThread();
        }
    }
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
