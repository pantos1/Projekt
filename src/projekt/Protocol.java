
package projekt;

/**
 * Created by Kuba on 2016-06-14.
 */

/**
 * Klasa, ktora definiuje polecenia komunikacji między serwerem a klientem.
 */

public class Protocol {
    static final String LOGIN = "Login";
    static final String LOGGEDIN = "LoggedIn";
    static final String LOGOUT = "Logout";
    static final String LOGGEDOUT = "LoggedOut";
    static final String SENDMAP = "SendMap";
    static final String SENDINGMAP = "SendingMap";
    static final String SENDINGSCORE = "SendingScore";
    static final String SENDHIGHSCORES = "SendHighScores";
    static final String SENDINGHIGHSCORES = "SendingHighScores";
    static final String STOP = "Stop";
    static final String STOPPED = "Stopped";
    static final String NULLCOMMAND = "NullCommand";
}

