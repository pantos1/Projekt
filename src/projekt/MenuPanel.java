package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Kuba on 2016-04-10.
 */

/**
 * Klasa MenuPanel dziedziczy po klasie JPanel oraz implementuje interfejs ActionListener slużący do
 * nasłuchiwania akcji ze strony użytkownika. Klasa ta odpowiada za właściwe menu gry.
 */
public class MenuPanel extends JPanel implements ActionListener{
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
        menu.add(title);
        menu.add(emp2);
        menu.add(emp3);
        menu.add(but);
        menu.add(emp4);


        JLabel jlabel = new JLabel("Władek Invaders");
        jlabel.setFont(new Font("Verdana",1,20));
        title.add(jlabel);

        JButton b1 = new JButton("       Nowa Gra       ");

        b1.addActionListener(this);
        JButton b2 = new JButton("Najlepsze Wyniki");
        JButton b3 = new JButton("     Zasady Gry     ");
        JButton b4 = new JButton("          Opcje          ");
        JButton b5 = new JButton("        Autorzy        ");
        JButton b6 = new JButton("     Opuść Grę     ");

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
        EventQueue.invokeLater(new Runnable() {
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
    private Image img;
    /**
     * Przeciążenie metody actionPerformed umożliwia zdefiniowanie akcji, która nastąpi po naciśnięciu
     * na przycisk b1 - "Nowa Gra". Wyskakuje wtedy okienko dialogowe z prośbą o podanie imienia gracza,
     * a po zatwierdzeniu przechodzi do pierwszej planszy gry.
     * @param e - określa zdarzenie, które nastąpiło
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane d=new JOptionPane(null);

        String inputValue = JOptionPane.showInputDialog(null, "Podaj imię", "Stwórz gracza", JOptionPane.QUESTION_MESSAGE);
        d.setSize(600, 400);
        d.setVisible(true);

        if(inputValue!=null){
            initGame(inputValue);
        }
    }
}
