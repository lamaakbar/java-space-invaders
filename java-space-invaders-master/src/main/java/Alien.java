import javax.swing.ImageIcon;

/**
 * Alien class now uses Flyweight Pattern for shared image data.
 */
public class Alien extends Sprite {

    private Bomb bomb;
    private AlienFlyweight flyweight;
    private static final String DEFAULT_ALIEN_PATH = "/img/alien.png";

    /*
     * Constructors
     */
    public Alien(int x, int y) {
        this(x, y, DEFAULT_ALIEN_PATH);
    }

    public Alien(int x, int y, String resourcePath) {
        this.x = x;
        this.y = y;
        this.bomb = new Bomb(x, y);

        // Get shared flyweight (shared image resource)
        this.flyweight = AlienFlyweightFactory.getFlyweight(resourcePath, this.getClass());

        // Use shared image from flyweight
        setImage(flyweight.getSprite());
    }

    public void act(int direction) {
        this.x += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

    @Override
    protected Sprite shallowClone() {
        Alien clone = (Alien) super.shallowClone();
        clone.bomb = new Bomb(this.bomb.getX(), this.bomb.getY());
        clone.bomb.setDestroyed(this.bomb.isDestroyed());
        clone.setImage(this.getImage()); // keep same shared image
        // reuse same flyweight reference
        clone.flyweight = this.flyweight;
        return clone;
    }

    public void resetBombPosition() {
        if (bomb == null) {
            bomb = new Bomb(this.x, this.y);
        } else {
            bomb.setX(this.x);
            bomb.setY(this.y);
        }
    }
}
