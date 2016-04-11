package projekt;
import java.awt.EventQueue;

/**
 * Created by Kuba on 2016-04-04.
 */
/**
 * Klasa, która inicjalizuje menu główne gry. W nowym wątku tworzone są nowe obiekty klas
 * MenuFrame i MenuPanel
 */
public class Test {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuFrame frame=new MenuFrame();
                MenuPanel menu = new MenuPanel("img/background.jpg");
                frame.add(menu);
                frame.pack();
                frame.setVisible(true);

            }
        });
    }
}
