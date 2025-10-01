import javax.swing.ImageIcon;

/**
 * 
 * @author 
 */
public class Alien extends Sprite {

    private Bomb bomb;
    private final String alien = "/img/alien.png";

    /*
     * Constructor
     */
    public Alien(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(alien));
        setImage(ii.getImage());

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