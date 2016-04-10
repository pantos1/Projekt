package projekt;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Piotr on 07.04.2016.
 */
public class MapaPanel extends JPanel{

    public MapaPanel(String img){
        this.img = new ImageIcon(img).getImage();
        Dimension size = new Dimension(this.img.getWidth(null), this.img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(new BorderLayout());
    }

    public void paintComponent(Graphics g){
        g.drawImage(img,0,0, null);
    }

    private Image img;
}
