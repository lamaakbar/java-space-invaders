

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * Flyweight class that holds shared image data for Aliens.
 */
public class AlienFlyweight {

    private final Image sprite;

    public AlienFlyweight(String resourcePath, Class<?> context) {
        // Load the image once for all Aliens sharing the same path
        ImageIcon icon = new ImageIcon(context.getResource(resourcePath));
        this.sprite = icon.getImage();
    }

    public Image getSprite() {
        return sprite;
    }
}
