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

    /**
     * Funkcja zwracająca właściwość <i>numberOfEnemies</i> zapisaną w obiekcie klasy
     * @return liczba przeciwników zapisana w polu <i>numberOfEnemies</i> pliku konfiguracyjnego
     */
    public int getNumberOfEnemies(){
        return Integer.parseInt(getProperty("numberOfEnemies"));
    }

    /**
     * Funkcja zwracająca ścieżkę dostępu do obrazka będącego tłem mapy.
     * @return Scieżka dostępu do obrazka będąceo tłem mapy
     */
    public String getBackground(){
        return getProperty("background");
    }

}
