package projekt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Created by Piotr on 07.04.2016.
 */
public class Mapa {

    public Mapa(){
        properties = new Properties();
    }

    public void parsing(String fileName) throws IOException {
        properties.load((Reader) new FileReader(fileName));
    }
    public void listProperties(){
        properties.list(System.out);
    }

    public String getBackground(){
        return properties.getProperty("tlo");
    }
    protected Properties properties;

}
