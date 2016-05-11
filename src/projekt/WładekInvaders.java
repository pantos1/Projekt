package projekt;
import javax.swing.*;
import java.awt.EventQueue;

/**
 * Created by Kuba on 2016-04-04.
 */
/**
 * Klasa, która inicjalizuje menu główne gry. W nowym wątku tworzony jest nowy obiekt klasy
 * MenuFrame
 */
public class WładekInvaders {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuFrame frame=new MenuFrame();
                frame.setVisible(true);
            }
        });
    }
}