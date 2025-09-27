import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 * Player (Singleton) - refactored from original Player class
 */
public class Player extends Sprite implements Commons {

    private static volatile Player instance;

    private final int START_Y = 400;
    private final int START_X = 270;

    private final String player = "/img/craft.png";
    private int width;

    /**
     * Private constructor - Singleton
     */
    private Player() {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(player));

        width = ii.getImage().getWidth(null);

        setImage(ii.getImage());
        setX(START_X);
        setY(START_Y);
    }

    /**
     * Thread-safe lazy initialization of the singleton Player instance
     */
    public static Player getInstance() {
        if (instance == null) {
            synchronized (Player.class) {
                if (instance == null) {
                    instance = new Player();
                }
            }
        }
        return instance;
    }

    public void act() {
        x += dx;
        if (x <= 2)
            x = 2;
        if (x >= BOARD_WIDTH - 2 * width)
            x = BOARD_WIDTH - 2 * width;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
