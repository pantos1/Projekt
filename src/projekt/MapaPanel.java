package projekt;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Piotr on 07.04.2016.
 */
public class MapaPanel extends JPanel{

    public MapaPanel(Mapa mapa, Gracz gracz){
        this.gracz = gracz;
        img = new ImageIcon(mapa.getBackground()).getImage();
        przeciwnicy = new ArrayList<Przeciwnik>();
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        int i;
        for(i=0; i<mapa.getNumberOfEnemies(); i++){
            przeciwnicy.add(new Przeciwnik(100*i, 100*i, "img/janusz.png"));
        }
    }

    public void paintComponent(Graphics g){
        g.drawImage(img,0,0, null);
    }

    public void drawEnemies(Graphics g){
        int i;
        for(i=0;i<przeciwnicy.size();i++){
            g.drawImage(przeciwnicy.get(i).getIcon().getImage(),przeciwnicy.get(i).getX(),przeciwnicy.get(i).getY(),null);
        }
    }

    public void drawPlayer (Graphics g){
        g.drawImage(gracz.getIcon().getImage(),500,750,null);
    }

    public void paint(Graphics g){
        super.paint(g);
        drawEnemies(g);
        drawPlayer(g);
    }
    private Gracz gracz;
    private Image img;
    private ArrayList <Przeciwnik> przeciwnicy;
}
