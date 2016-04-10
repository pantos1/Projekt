package projekt;
import java.awt.EventQueue;

/**
 * Created by Kuba on 2016-04-04.
 */

public class Test {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuFrame frame=new MenuFrame();
                MenuPanel menu = new MenuPanel("src/plaza.jpg");
                frame.add(menu);
                frame.pack();
                frame.setVisible(true);

            }
        });
    }
}
