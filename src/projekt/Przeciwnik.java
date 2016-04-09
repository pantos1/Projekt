package projekt;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Created by Piotr on 09.04.2016.
 */
public class Przeciwnik extends Properties {

    public Przeciwnik(double x, double y, String fileName){
        this.x = x;
        this.y = y;
        icon = new ImageIcon(fileName);
    }

    public void parsing(String fileName) throws IOException {
        load((Reader) new FileReader(fileName));
    }
    public ImageIcon getIcon(){
        return icon;
    }
    protected double x;
    protected double y;
    protected ImageIcon icon;
}
