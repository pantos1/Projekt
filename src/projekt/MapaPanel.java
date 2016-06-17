package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


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
    public MapaPanel(Mapa mapa, String name, MapaFrame frame, Klient klient, String sciezka){
        this.frame = frame;
        this.klient = klient;
        img = new ImageIcon(mapa.getProperty("background")).getImage();
        przeciwnicy = new ArrayList<Przeciwnik>();
        strzaly= new ArrayList<Strzal>();
		strzalyprzeciwnik = new ArrayList<StrzalPrzeciwnik>();

        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);

        gracz = new Gracz(name, sciezka, this);

        imie = new JLabel(name);
        imie.setFont(new Font("Verdana",1,16));
        add(imie, BorderLayout.LINE_START);

        hp = new JLabel("HP:"+String.valueOf(gracz.getHP()));
        hp.setFont(new Font("Verdana",1,16));
        add(hp, BorderLayout.LINE_START);

        score = new JLabel("Wynik:"+String.valueOf(gracz.score));
        score.setFont(new Font("Verdana",1,16));
        add(score, BorderLayout.LINE_START);

        czas = new JLabel(String.valueOf(mapa.getProperty("time"))+"s");
        czas.setFont(new Font("Verdana",1,16));
        add(czas, BorderLayout.LINE_START);

        int i;
        for(i=0; i<Integer.parseInt(mapa.getProperty("numberOfEnemies")); i++){
            przeciwnicy.add(new Przeciwnik("img/janusz.png", this));
            przeciwnicy.get(i).startLocationUpdateThread();
			strzalyprzeciwnik.add(new StrzalPrzeciwnik("img/klapek.png", this, przeciwnicy.get(i)));
            strzalyprzeciwnik.get(i).startLocationUpdateThread();
            przeciwnicy.get(i).strzalp = strzalyprzeciwnik.get(i);
        }

        addKeyListener(this);
        setFocusable(true);
        gracz.startLocationUpdateThread();

        strzaly.add(new Strzal("img/klapek2.png", this, gracz));
        strzaly.get(0).startLocationUpdateThread();

        timer = new Timer();
        timerTask = new TimerTask(){
            int time = Integer.parseInt(mapa.getProperty("time"));
            public void run() {
                czas.setText(String.valueOf(time)+"s");
                time--;
                if(time<0) gameOver();
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    /**
     * Przeciążona metoda paintComponent rysująca obrazek <i>img</i> w tle, gracza, strzał oraz przeciwników..
     * @param g Kontekst graficzny
     */
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(img,0,0, null);
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            przeciwnicy.get(i).draw(g);
        }
        gracz.draw(g);
        int j;
        for(j=0;j<strzaly.size();j++){
            strzaly.get(j).draw(g);
        }
		for(j=0;j<strzalyprzeciwnik.size();j++){
        strzalyprzeciwnik.get(j).draw(g);
        }
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
            Strzal strzal = new Strzal("img/klapek2.png", this, gracz);
            strzaly.add(strzal);
            strzal.startLocationUpdateThread();
            strzal.isVisible=true;
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

    void detectColissions(){
        int i,j;
        for(i=0;i<przeciwnicy.size();i++)
            for (j = 0; j < strzaly.size(); j++) {
                try {
                    if (przeciwnicy.get(i).hasCollided(strzaly.get(j))) {
                        kill(przeciwnicy.get(i));
                        kill(strzaly.get(j));
                        updateScore();
                    }
                }catch(IndexOutOfBoundsException e){
                }catch (NullPointerException e) {

                }
            }
        if(przeciwnicy.isEmpty()) nextLevel();
		for (j = 0; j < strzalyprzeciwnik.size(); j++) {
            try {
                if (gracz.hasCollided(strzalyprzeciwnik.get(j))) {
                    strzalyprzeciwnik.get(j).reset();
                    strzalyprzeciwnik.get(j).isVisible=false;
                    updateHP();
                    if(gracz.hp<=0) gameOver();
                }
            }catch(IndexOutOfBoundsException e){
            }catch (NullPointerException e){

            }
	}
	}
    /**
     * Funkcja, która usuwa przeciwnika.
     * @param przeciwnik - referencja na obiekt klasy Przeciwnik
     */
    void kill(Przeciwnik przeciwnik){
        przeciwnik.isVisible=false;
        przeciwnik.stopLocationUpdateThread();
        przeciwnicy.remove(przeciwnik);
    }
	/**
     * Funkcja, która usuwa strzał gracza.
     * @param strzal - referencja na obiekt klasy Strzal
     */
    void kill(Strzal strzal){
        strzal.isVisible=false;
        strzal.stopLocationUpdateThread();
        strzaly.remove(strzal);

    }
	/**
     * Funkcja, która usuwa strzał przeciwnika.
     * @param strzalp - referencja na obiekt klasy Strzalp
     */
    void kill(StrzalPrzeciwnik strzalp){
        strzalp.isVisible=false;
        strzalp.stopLocationUpdateThread();
        strzalyprzeciwnik.remove(strzalp);

    }
	/**
     * Funkcja odpowiadająca za odświeżanie wyniku osiagniętego przez gracza. Uruchamia się po każdym trafieniu przeciwnika.
     */
    void updateScore(){
        gracz.score++;
        score.setText("Wynik:"+gracz.score);
    }
    /**
     * Funkcja odpowiadająca za odświeżanie poziomu życia gracza. Uruchamia się po każdym trafieniu pociskiem od Przeciwnika.
     */
    void updateHP(){
        gracz.hp-=10;
        hp.setText("HP:"+gracz.hp);
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
     * Obiekt klasy JLabel wyświetlający aktualny wynik gracza.
     */
    private JLabel score;
    /**
     * Obiekt klasy JLabel wyświetlający czas pozostały do ukończenia gry.
     */
    private JLabel czas;
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
    private Klient klient;
    /**
     * Referencja na obiekt klasy Strzal, który jest rysowany w MapaPanel.
     */
    private ArrayList <Strzal> strzaly;
	private ArrayList <StrzalPrzeciwnik> strzalyprzeciwnik;
    private Timer timer;
    private TimerTask timerTask;
    /**
     * Przeciążona metoda <i>run()</i> która wywołuje metodę repaint i odpowiada za sterowanie rysowaniem obiektów.
     * Usypia również wątek.
     */
    @Override
    public void run() {
        while(kicker == Thread.currentThread()){
            repaint();
            detectColissions();
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
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            przeciwnicy.get(i).startLocationUpdateThread();
        }
        for(i=0;i<strzaly.size();i++){
            strzaly.get(i).startLocationUpdateThread();
        }
        try {
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }catch (IllegalStateException e){

        }
    }

    /**
     * Funkcja zatrzymująca wątek rysowania animacji oraz wywołująca funkcje zatrzymujące wątki aktualizujące
     * położenie gracza, strzału i przeciwników.
     */
    void stopAnimationThread(){
        kicker = null;
        gracz.stopLocationUpdateThread();
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            przeciwnicy.get(i).stopLocationUpdateThread();
        }
        for(i=0;i<strzaly.size();i++){
            strzaly.get(i).stopLocationUpdateThread();
        }
        timer.cancel();
    }

    /**
     * Funkcja wywoływana na koniec gry, zatrzymująca wątek animacji oraz otwierająca okno dialogowe pytające o rozpoczęcie
     * nowej gry.
     */
    void gameOver(){
        stopAnimationThread();
        timer.cancel();
        String[] options = new String[] {"Tak", "Nie"};
        int d = JOptionPane.showOptionDialog(this, "Czy chcesz zagrać jeszcze raz?", "Koniec gry",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        if (d == JOptionPane.NO_OPTION){
            frame.dispose();
        }
        else if (d == JOptionPane.YES_OPTION){
            frame.remove(this);
            frame.newGame(gracz.getName(),klient);
        }
    }

    /**
     * Metoda wywoływana po pomyślnym zakończeniu gry. Przekazuje za pomocą obiektu klasy Klient wynik z rozgrywki do serwera
     * oraz wyświetla informację o najlepszych wynikach.
     */
    void nextLevel(){
        stopAnimationThread();
        timer.cancel();
        timerTask.cancel();
        int wynik = gracz.score+Integer.parseInt(czas.getText().replace("s",""));
        klient.sendScore(gracz.getName(),wynik);

        try {
            Desktop desktop=Desktop.getDesktop();
            desktop.open(new File("txt/wyniki.txt"));
        } catch (Exception w) {
        }

        String[] options = new String[] {"Tak", "Nie"};
        int d = JOptionPane.showOptionDialog(this, "Czy chcesz zagrać jeszcze raz?", "Koniec gry",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        if (d == JOptionPane.NO_OPTION){
            frame.dispose();
        }
        else if (d == JOptionPane.YES_OPTION){
            frame.remove(this);
            frame.newGame(gracz.getName(),klient);
        }
    }
}