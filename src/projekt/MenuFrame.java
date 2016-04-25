package projekt;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Created by Kuba on 2016-04-04.
 */
 
/**
 * Klasa MenuFrame dziedziczy po klasie JFrame. Odpowiada ona za stworzenie ramki menu wewnątrz
 * której znajdzie się wewnętrzny panel z właściwym menu.
 */
public class MenuFrame extends JFrame{

    /**
     * Konstruktor klasy MenuFrame w którym tworzony jest również obiekt klasy MenuPanel.
     */
     
    public MenuFrame() {
        super("Władek Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(145, 110);
        MenuPanel menu = new MenuPanel("img/background.jpg");
        add(menu);
        pack();
    }

}
