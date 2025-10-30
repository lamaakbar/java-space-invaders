

import java.awt.Image;

public final class AlienFlyweight {
    private final String resourcePath;
    private final Image sprite;

    public AlienFlyweight(String resourcePath, Image sprite) {
        this.resourcePath = resourcePath;
        this.sprite = sprite;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public Image getSprite() {
        return sprite;
    }
}