package projekt;

import java.awt.*;
import javax.swing.*;
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
                Przeciwnik[] przeciwnik = new Przeciwnik[3];
                int i;
                for(i=0; i<_1.getNumberOfEnemies(); i++){
                    przeciwnik[i] = new Przeciwnik(100*i, 100*i, "img/janusz.png");
                    main.add(new JLabel(przeciwnik[i].getIcon()));
                }
                frame.getContentPane().add(main);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
