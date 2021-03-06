package projekt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Klasa przechowująca właściwości pojedynczej mapy.
 */
public class Mapa extends Properties {
    /**
     * Funkcja parsująca wejściowy plik konfiguracyjny.
     * @param fileName Scieżka dostępu do pliku konfiguracyjnego.
     * @throws IOException
     */
    public void parsing(String fileName) throws IOException {
        load((Reader) new FileReader(fileName));
    }
}
