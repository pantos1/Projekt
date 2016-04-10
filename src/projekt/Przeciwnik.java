package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Created by Piotr on 09.04.2016.
 */
public class Przeciwnik extends JLabel {

    public Przeciwnik(int x, int y, String fileName){
        point = new Point(x, y);
        this.setLocation(point);
        icon = new ImageIcon(fileName);
    }

    public void parsing(String fileName) throws IOException {
        properties.load((Reader) new FileReader(fileName));
    }
    public ImageIcon getIcon(){
        return icon;
    }

    protected Properties properties;
    protected Point point;
    protected ImageIcon icon;
}
