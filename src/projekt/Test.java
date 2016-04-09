package projekt;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Piotr on 07.04.2016.
 */
public class Test {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Mapa _1= new Mapa();
                try {
                    _1.parsing("config/1.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MapaFrame frame = new MapaFrame();
                MapaPanel main = new MapaPanel(_1.getBackground());
                frame.getContentPane().add(main);
                frame.setMaximumSize(main.getMaximumSize());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
