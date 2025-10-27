public class SoundAdapter implements SoundEffect {

    private final AudioEngine audioEngine;

    public SoundAdapter() {
        this.audioEngine = new AudioEngine();
    }

    @Override
    public void playShootSound() {
        audioEngine.play("shoot.wav");
    }

    @Override
    public void playExplosionSound() {
        audioEngine.play("explosion.wav");
    }
}
