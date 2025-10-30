

import javax.swing.ImageIcon;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AlienFlyweightFactory {
    private static final Map<String, AlienFlyweight> CACHE = new ConcurrentHashMap<>();

    public static AlienFlyweight getFlyweight(String resourcePath, Class<?> resourceOwner) {
        return CACHE.computeIfAbsent(resourcePath, key -> {
            ImageIcon ii = new ImageIcon(resourceOwner.getResource(resourcePath));
            return new AlienFlyweight(resourcePath, ii.getImage());
        });
    }

    public static void clearCache() {
        CACHE.clear();
    }
}