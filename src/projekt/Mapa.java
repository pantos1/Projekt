package projekt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Created by Piotr on 07.04.2016.
 */
public class Mapa extends Properties {

    public void parsing(String fileName) throws IOException {
        load((Reader) new FileReader(fileName));
    }
    public int getNumberOfEnemies(){
        return Integer.parseInt(getProperty("numberOfEnemies"));
    }

    public String getBackground(){
        return getProperty("background");
    }

}
