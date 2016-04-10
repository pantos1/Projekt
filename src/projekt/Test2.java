package projekt;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Piotr on 07.04.2016.
 */
public class Test2 {
    public Test2(String name) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Mapa _1= new Mapa();
                Gracz gracz = new Gracz(name, "img/gracz.png");
                try {
                    _1.parsing("config/1.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MapaFrame frame = new MapaFrame();
                MapaPanel main = new MapaPanel(_1, gracz);
                frame.add(main);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
