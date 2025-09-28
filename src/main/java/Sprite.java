import java.awt.Image;

/**
 * 
 * @author Lama Akbar
 */
public class Sprite {

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

        /*
         * Factory Method for creating different types of sprites
         */
        public static Sprite createSprite(String type) {
            switch (type.toLowerCase()) {
                case "alien":
                    return new Alien(0, 0);
                case "shot":
                    return new Shot();
                case "bomb":
                    return new Bomb(0, 0);
                default:
                    throw new IllegalArgumentException("Unknown sprite type: " + type);
            }
        }

        /*
         * Factory Method for creating sprites with coordinates
         */
        public static Sprite createSprite(String type, int x, int y) {
            switch (type.toLowerCase()) {
                case "alien":
                    return new Alien(x, y);
                case "shot":
                    return new Shot(x, y);
                case "bomb":
                    return new Bomb(x, y);
                default:
                    throw new IllegalArgumentException("Unknown sprite type: " + type);
            }
        }
}