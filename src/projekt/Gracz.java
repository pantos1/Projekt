package projekt;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Created by Kuba on 2016-04-09.
 */
public class Gracz{

    public Gracz(String name, String fileName){
        this.name = name;
        icon = new ImageIcon(fileName);
    }

    public ImageIcon getIcon(){
        return icon;
    }

    private double x;

    private ImageIcon icon;

    private double speed;
    private double levelSpeed;

    //levelSpeed = speed*mapNumber;

    private String name;
    private double score;

    private boolean left;
    private boolean right;

    public Gracz(String name){
        this.name=name;
    }

    public void update() {
        // nowa pozycja gracza

        if (left) {
            x -= levelSpeed;
            if (x < -levelSpeed) {
                x = 0;
            }
        } else if (right) {
            x += levelSpeed;
            if (x > levelSpeed) {
                //? x = map.width;
            }
        }

    }
}
