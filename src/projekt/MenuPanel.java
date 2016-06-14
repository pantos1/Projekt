package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import sun.audio.*;

/**
 * Created by Kuba on 2016-04-10.
 */

/**
 * Klasa MenuPanel dziedziczy po klasie JPanel oraz implementuje interfejs ActionListener slużący do
 * nasłuchiwania akcji ze strony użytkownika. Klasa ta odpowiada za właściwe menu gry.
 */
public class MenuPanel extends JPanel implements ActionListener{
    /*
    * utworzenie typu enum zawierającego akcje wywoływane po wciśnięciu każdego z przycisków
    */
     private enum Actions {
        b1, b2, b3, b4, b5, b6
    }
    /*
    * Obiekt klasy Desktop, który umożliwi wywołanie pliku tekstowego na ekranie po naciśnięciu odpowiedniego buttona
    */
    private Desktop desktop;
    /*
    * obiekty, zawierające ścieżki dostępu do odpowiednich plików tekstowych
    */
    private String pathwyniki = "txt/wyniki.txt";
    private String pathzasady = "txt/zasady.txt";
    private String pathautorzy = "txt/autorzy.txt";
    /**
     * W konstruktorze klasy MenuPanel tworzone są poszczególne panele odpowiadające za wygląd menu gry.
     * @param img - plik ze zdjęciem, które zostanie ustawione jako tło menu
     * Najpierw tworzony jest panel menu z parametrem GridLayout. To on odpowiada za to jak rozłożone są
     * wszystkie elementy. Innym ważnym panelem jest panel title, w którym wpisany jest tytuł gry (jako
     * obiekt klasy JLabel), a także but, do którego dodane są kolejne buttony.
     * Przezroczystość wszystkich paneli ustawiona jest jako false, żebywidoczny był obrazek mający być
     * tłem menu.
     */
    public MenuPanel (String img){
        desktop = Desktop.getDesktop();
        
        this.img=new ImageIcon(img).getImage();

        JPanel menu = new JPanel(new GridLayout(2,3));
        JPanel emp1 = new JPanel();
        JPanel emp2 = new JPanel();
        JPanel emp3 = new JPanel();
        JPanel emp4 = new JPanel();
        JPanel but = new JPanel(new GridLayout(6, 1));
        JPanel title = new JPanel();

        add(menu);
        menu.add(emp1);
        menu.add(title, BorderLayout.NORTH);
        menu.add(emp2);
        menu.add(emp3);
        menu.add(but, BorderLayout.CENTER);
        menu.add(emp4);


        JLabel jlabel = new JLabel("Władek Invaders");
        jlabel.setFont(new Font("Verdana",1,20));
        title.add(jlabel);

        JButton b1 = new JButton("       Nowa Gra       ");
        b1.setActionCommand(Actions.b1.name());
        b1.addActionListener(this);
        JButton b2 = new JButton("Najlepsze Wyniki");
        b2.setActionCommand(Actions.b2.name());
        b2.addActionListener(this);
        JButton b3 = new JButton("     Zasady Gry     ");
        b3.setActionCommand(Actions.b3.name());
        b3.addActionListener(this);
        JButton b4 = new JButton("          Opcje          ");
        b4.setActionCommand(Actions.b4.name());
        b4.addActionListener(this);
        JButton b5 = new JButton("        Autorzy        ");
        b5.setActionCommand(Actions.b5.name());
        b5.addActionListener(this);
        JButton b6 = new JButton("     Opuść Grę     ");
        b6.setActionCommand(Actions.b6.name());
        b6.addActionListener(this);
        
        but.add(b1);
        but.add(b2);
        but.add(b3);
        but.add(b4);
        but.add(b5);
        but.add(b6);
        b1.setAlignmentY(Component.CENTER_ALIGNMENT);

        //menu.setBackground(Color.yellow);
        menu.setOpaque(false);
        but.setOpaque(false);
        emp1.setOpaque(false);
        emp2.setOpaque(false);
        emp3.setOpaque(false);
        emp4.setOpaque(false);
        title.setOpaque(false);

    }
    /**
     * Funkcja inicjalizującą grę, tworzy nowy wątek. W tym wątku tworzy obiekt klasy Mapa na podstawie pliku konfiguracyjnego oraz
     * obiekt klasy Gracz na podstawie podanego imienia. Tworzy również okno gry, tworząc obiekty klasy MapaFrame.
     */
    protected void initGame(String name) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MapaFrame frame = new MapaFrame(name);
                frame.setVisible(true);
                }
        });
    }
    /**
     * Funkcja rysująca, jej zadaniem jest ustawienie obrazka przedstawiającego plażę jako tło menu.
     * @param g - obiekt klasy JComponent służący do pobierania grafiki bezpośrednio z pliku
     */

    public void paintComponent(Graphics g){
        g.drawImage(img, 0, 0, null);
    }
    /*
    * Obiekt, który jest obrazkiem stanowiącym tło
    */
    private Image img;
 
        /* public static void music() {
        AudioPlayer MGP = AudioPlayer.player;
        AudioStream BGM;
        AudioData MD;
        ContinuousAudioDataStream loop = null;
        try {
            BGM = new AudioStream(new FileInputStream("C:\\Users\\Kuba\\IdeaProjects\\Projekt_PROZE\\audio2.wav"));
        MD = BGM.getData();
            loop = new ContinuousAudioDataStream(MD);
        }catch(IOException error){JOptionPane.showMessageDialog(null, "Blad otwarcia pliku");}
        MGP.start(loop);
    }
*/

   /**
     * Przeciążenie metody actionPerformed umożliwia zdefiniowanie akcji, która nastąpi po naciśnięciu
     * na przycisk b1 - "Nowa Gra". Wyskakuje wtedy okienko dialogowe z prośbą o podanie imienia gracza,
     * a po zatwierdzeniu przechodzi do pierwszej planszy gry.
     * Akcje b2, b3 i b5 powodują otwarcie odpowiednich plików tekstowych na ekranie.
     * Po naciśnięciu na ostatni z buttonów wywołane zostaje okienko z pytaniem czy gracz na pewno chce opuścić grę. 
     * Wprzypadku odpowiedzi twierdzącej okienko z grą jest wyłączane.
     * @param e - określa zdarzenie, które nastąpiło
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == Actions.b1.name()) {
            JOptionPane d = new JOptionPane(null);
            String inputValue = JOptionPane.showInputDialog(null, "Podaj imie", "Stwórz gracza", JOptionPane.QUESTION_MESSAGE);
            d.setSize(300, 200);
            d.setVisible(true);
            if (inputValue != null) {
                initGame(inputValue);
            }
        }
        if (e.getActionCommand() == Actions.b2.name()) {
            try {
                desktop.open(new File(pathwyniki));
            } catch (Exception w) {
                JOptionPane.showMessageDialog(null, "Blad otwarcia pliku");
            }
        }
        if (e.getActionCommand() == Actions.b3.name()) {
            try {
                desktop.open(new File(pathzasady));
            } catch (Exception w) {
                JOptionPane.showMessageDialog(null, "Blad otwarcia pliku");
            }
        }
        if (e.getActionCommand() == Actions.b4.name()) {
            //music();
        }

        if (e.getActionCommand() == Actions.b5.name()) {
            try {
                desktop.open(new File(pathautorzy));
            } catch (Exception w) {
                JOptionPane.showMessageDialog(null, "Blad otwarcia pliku");
            }

        }


        if (e.getActionCommand() == Actions.b6.name()) {
            String[] options = new String[] {"Tak", "Nie"};
            int d1 = JOptionPane.showOptionDialog(null, "Czy na pewno chcesz zamknąć program?", "Kończenie pracy", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            if (d1 == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
            else if (d1 == JOptionPane.NO_OPTION) {
            }
        }
    }
}
