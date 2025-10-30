import java.awt.Toolkit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Utility class that generates simple sound effects on the fly so the game can
 * run without external audio assets.
 */
public final class SoundManager {

    private static final float SAMPLE_RATE = 44_100f;

    private SoundManager() {
        // Utility class
    }

    private static void playTone(final double frequency, final int durationMs, final double volume) {
        byte[] toneBuffer = createToneBuffer(frequency, durationMs, volume);
        playBuffer(toneBuffer);
    }

    private static void playBuffer(final byte[] audioBuffer) {
        Runnable soundTask = () -> {
            AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, false);
            SourceDataLine line = null;

            try {
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();
                line.write(audioBuffer, 0, audioBuffer.length);
                line.drain();
            } catch (LineUnavailableException | IllegalArgumentException ex) {
                Toolkit.getDefaultToolkit().beep();
            } finally {
                if (line != null) {
                    line.stop();
                    line.close();
                }
            }
        };

        Thread playbackThread = new Thread(soundTask, "sound-effect");
        playbackThread.setDaemon(true);
        playbackThread.start();
    }

    private static byte[] createToneBuffer(double frequency, int durationMs, double volume) {
        int samples = (int) (SAMPLE_RATE * durationMs / 1000.0);
        byte[] buffer = new byte[samples * 2];
        double angularFrequency = 2.0 * Math.PI * frequency / SAMPLE_RATE;
        double amplitude = Math.min(Math.max(volume, 0.0), 1.0);

        for (int i = 0; i < samples; i++) {
            double sample = Math.sin(i * angularFrequency);
            short value = (short) (sample * amplitude * Short.MAX_VALUE);
            int index = i * 2;
            buffer[index] = (byte) (value & 0xFF);
            buffer[index + 1] = (byte) ((value >> 8) & 0xFF);
        }

        return buffer;
    }

    private static byte[] createNoiseBuffer(int durationMs, double volume) {
        int samples = (int) (SAMPLE_RATE * durationMs / 1000.0);
        byte[] buffer = new byte[samples * 2];
        double amplitude = Math.min(Math.max(volume, 0.0), 1.0);

        for (int i = 0; i < samples; i++) {
            double sample = (Math.random() * 2.0) - 1.0;
            short value = (short) (sample * amplitude * Short.MAX_VALUE);
            int index = i * 2;
            buffer[index] = (byte) (value & 0xFF);
            buffer[index + 1] = (byte) ((value >> 8) & 0xFF);
        }

        return buffer;
    }

    private static byte[] createExplosionBuffer(int durationMs, double volume) {
        int samples = (int) (SAMPLE_RATE * durationMs / 1000.0);
        byte[] buffer = new byte[samples * 2];
        double amplitude = Math.min(Math.max(volume, 0.0), 1.0);
        double durationSeconds = durationMs / 1000.0;

        for (int i = 0; i < samples; i++) {
            double time = i / SAMPLE_RATE;
            double attack = Math.min(1.0, time / 0.02);
            double decay = Math.exp(-3.5 * time / durationSeconds);
            double envelope = attack * decay;

            double noise = (Math.random() * 2.0) - 1.0;
            double rumble = Math.sin(2.0 * Math.PI * 55.0 * time);
            double crack = Math.sin(2.0 * Math.PI * 440.0 * time) * Math.exp(-8.0 * time);

            double sample = (noise * 0.55 + rumble * 0.35 + crack * 0.25) * envelope;
            sample = Math.max(-1.0, Math.min(1.0, sample));

            short value = (short) (sample * amplitude * Short.MAX_VALUE);
            int index = i * 2;
            buffer[index] = (byte) (value & 0xFF);
            buffer[index + 1] = (byte) ((value >> 8) & 0xFF);
        }

        return buffer;
    }

    public static void playShotSound() {
        playTone(880.0, 120, 0.7);
    }

    public static void playExplosionSound() {
        byte[] explosionBuffer = createExplosionBuffer(450, 0.9);
        playBuffer(explosionBuffer);
    }

    public static void playVictorySound() {
        playTone(523.25, 500, 0.8);
    }

    public static void playDefeatSound() {
        playTone(130.81, 500, 0.8);
    }
}