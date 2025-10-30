import javax.swing.ImageIcon;


/**
 *
 * @author
 */
public class Alien extends Sprite {

    private Bomb bomb;
    private final AlienFlyweight flyweight;
    private static final String DEFAULT_ALIEN_PATH = "/img/alien.png";

    /*
     * Constructor
     */
    public Alien(int x, int y) {
        this(x, y, DEFAULT_ALIEN_PATH);
    }

    public Alien(int x, int y, String resourcePath) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);

        // request shared flyweight. Use this.getClass() so resource loading matches original behavior.
        this.flyweight = AlienFlyweightFactory.getFlyweight(resourcePath, this.getClass());

        // set image on Sprite to the shared image
        setImage(flyweight.getSprite());
    }

    public void act(int direction) {
        this.x += direction;
    }

    /*
     * Getters & Setters
     */

    public Bomb getBomb() {
        return bomb;
    }

    @Override
    protected Sprite shallowClone() {
        Alien clone = (Alien) super.shallowClone();
        Bomb newBomb = new Bomb(this.bomb.getX(), this.bomb.getY());
        newBomb.setDestroyed(this.bomb.isDestroyed());
        clone.bomb = newBomb;
clone.setImage(this.getImage());

        // IMPORTANT: keep flyweight reference shared
        // clone.flyweight = this.flyweight; // not needed if flyweight is final; clone constructor path must supply same flyweight
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