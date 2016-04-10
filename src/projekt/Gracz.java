package projekt;

import javax.swing.*;

/**
 * Created by Kuba on 2016-04-09.
 */
public class Gracz {

    private double x;

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
