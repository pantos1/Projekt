package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Klasa przechowująca dane o przeciwnikach
 */
public class Przeciwnik implements Runnable{

    /**
     * Konstruktor klasy Przeciwnik
     * @param fileName Scieżka dostępu do pliku przechowującego ikonę przeciwnika.
     * @param panel Referencja na obiekt klasy MapaPanel, który przechowuje obiekt klasy Przeciwnik.
     */
    public Przeciwnik(String fileName, MapaPanel panel){
        x = (int) (Math.random() * panel.getWidth());
        y = (int) (Math.random() * panel.getHeight()/3);
        icon = new ImageIcon(fileName);
        this.panel = panel;
    }
    /**
     * Funkcja rysująca przeciwnika.
     * @param g Kontekst graficzny
     */
    void draw(Graphics g){
        if(isVisible) g.drawImage(icon.getImage(),x,y,null);
    }

    /**
     * Funkcja aktualizująca położenie obiektu. Funkcja zmienia wartość kierunku ruchu <i>xDirection</i>
     * po dojściu do krawędzi obiektu MapaPanel. Wtedy wartość położenia <i>y</i> jest zmieniana o wartość <i>dy</i>.
     * Po wykryciu dojścia do dolnej krawędzi obiektu MapaPanel, wywoływana jest funkcja gameOver().
     */
    private void move(){
        if(x<=0){
            xDirection = 1;
            y+=dy;
        }
        else if(x+icon.getIconWidth()>panel.getWidth()) {
            xDirection = -1;
            y+=dy;
        }
        x+=xDirection*dx;
        if(y+icon.getIconHeight()>panel.gracz.getY()){
            panel.gameOver();
        }
    }

    boolean hasCollided(Strzal strzal){
        Rectangle me = new Rectangle();
        Rectangle other = new Rectangle();
        me.setBounds(x,y,icon.getIconWidth(),icon.getIconHeight());
        other.setBounds(strzal.x, strzal.y, strzal.icon.getIconWidth(),strzal.icon.getIconHeight());
        return me.intersects(other);
    }

    /**
     * Przeciążona metoda <i>run()</i> aktualizująca położenie przeciwnika.
     */
    @Override
    public void run() {
        while(kicker == Thread.currentThread()){
            move();
            try{
                Thread.sleep(10);
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    /**
     * Rozpoczyna wątek kicker, co powoduje uruchomienie pętli w funkcji <i>run()</i>.
     */
    void startLocationUpdateThread(){
        (kicker = new Thread(this)).start();
    }

    /**
     * Przypisuje wątkowi kicker wartość null, dzięki czemu zatrzymana jest pętla w funkcji run().
     */
    void stopLocationUpdateThread(){
        kicker = null;
    }

    /**
     * Pole przechowujące współrzędną x położenia obiektu
     */
    private int x;
    /**
     * Pole przechowujące współrzędną y położenia obiektu
     */
    private int y;
    /**
     * Pole przechowujace obiekt icon klasy ImageIcon zawierającej ikonę obiektu Przeciwnik.
     */
    private ImageIcon icon;
    /**
     * Prędkość poruszania się kierunku x.
     */
    private int dx = 5;
    /**
     * Prędkość poruszania się kierunku y.
     */
    private int dy = 20;
    /**
     * Kierunek poruszania się obiektu klasy przeciwnik. 1 - w prawo; -1 - w lewo
     */
    private int xDirection=1;
    /**
     * Referencja na obiekt klasy MapaPanel, w którym jest tworzony przeciwnik.
     */
    MapaPanel panel;
    /**
     * Wątek, dzięki któremu możemy sterować wykonywaniem funkcji <i>run()<i> w klasie.
     */
    private Thread kicker;
    boolean isVisible = true;
	StrzalPrzeciwnik strzalp;
	 /**
     * Funkcja zwracająca położenie y przeciwnika, jego dolnej krawędzi.
     * @return Współrzędna y przeciwnika.
     */
	int getY(){
        return y+icon.getIconHeight();
    }
    /**
     * Funkcja zwracająca środek ikony gracza w płaszczyźnie x
     * @return środek ikony gracza w płaszczyźnie x
     */
    int getCenter(){
        return x+(icon.getIconWidth()/2);
    }
}
