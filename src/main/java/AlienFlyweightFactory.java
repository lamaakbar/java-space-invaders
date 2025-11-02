
import java.util.HashMap;
import java.util.Map;

/**
 * Factory that manages and provides shared AlienFlyweight instances.
 * Ensures each unique image resource is only loaded once.
 */
public class AlienFlyweightFactory {

    // Cache of flyweights by resource path
    private static final Map<String, AlienFlyweight> flyweights = new HashMap<>();

    /**
     * Returns a shared AlienFlyweight for the given resource path.
     */
    public static AlienFlyweight getFlyweight(String resourcePath, Class<?> context) {
        if (!flyweights.containsKey(resourcePath)) {
            flyweights.put(resourcePath, new AlienFlyweight(resourcePath, context));
        }
        return flyweights.get(resourcePath);
    }

    // Optional: for testing or debugging
    public static int getFlyweightCount() {
        return flyweights.size();
    }
}
