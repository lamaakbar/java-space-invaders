import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Lama Akbar
 */
public class Sprite implements Cloneable {

        private boolean visible;
        private Image image;
        protected int x;
        protected int y;
        protected boolean dying;
        protected int dx;

        /*
         * Constructor
         */
        public Sprite() {
            visible = true;
        }

        public void die() {
            visible = false;
        }

        public boolean isVisible() {
            return visible;
        }

        protected void setVisible(boolean visible) {
            this.visible = visible;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public Image getImage() {
            return image;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }

        public void setDying(boolean dying) {
            this.dying = dying;
        }

        public boolean isDying() {
            return this.dying;
        }

        protected Sprite shallowClone() {
            try {
                return (Sprite) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError("Cloning not supported", e);
            }
        }

        /*
         * Prototype registry
         */
        private static final Map<String, Sprite> TYPE_TO_PROTOTYPE = new HashMap<String, Sprite>();

        static {
            // Preload default prototypes
            TYPE_TO_PROTOTYPE.put("alien", new Alien(0, 0));
            TYPE_TO_PROTOTYPE.put("shot", new Shot(0, 0));
            TYPE_TO_PROTOTYPE.put("bomb", new Bomb(0, 0));
        }

        public static void registerPrototype(String type, Sprite prototype) {
            if (type == null || prototype == null) {
                throw new IllegalArgumentException("type and prototype must be non-null");
            }
            TYPE_TO_PROTOTYPE.put(type.toLowerCase(), prototype);
        }

        private static Sprite cloneFromPrototype(String type) {
            Sprite prototype = TYPE_TO_PROTOTYPE.get(type.toLowerCase());
            if (prototype == null) {
                throw new IllegalArgumentException("Unknown sprite type: " + type);
            }
            return prototype.shallowClone();
        }

        public static Sprite createSprite(String type) {
            return cloneFromPrototype(type);
        }

        /*
         * Factory Method for creating sprites 
         */
        public static Sprite createSprite(String type, int x, int y) {
            Sprite sprite = cloneFromPrototype(type);
            if (sprite instanceof Shot) {
                ((Shot) sprite).initializeFromPlayerPosition(x, y);
                return sprite;
            }
            sprite.setX(x);
            sprite.setY(y);
            if (sprite instanceof Alien) {
                ((Alien) sprite).resetBombPosition();
            }
            return sprite;
        }
}