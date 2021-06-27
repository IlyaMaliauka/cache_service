package guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.annotations.Countable;
import utilities.annotations.Timed;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class LruCacheService {

    private int capacity;
    private static final Logger LOGGER = LoggerFactory.getLogger(LruCacheService.class);
    private final Cache<Integer, String> cache;

     {
        cache = CacheBuilder.newBuilder()
                .maximumSize(capacity)
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .removalListener((RemovalListener<Integer, String>) entry -> {
                    if (entry.wasEvicted()) {
                        LOGGER.info("Removed excessive cache value. Removal cause: " + entry.getCause());
                    }
                })
                .recordStats()
                .build();
    }

    public LruCacheService(int capacity) {
        this.capacity = capacity;
    }

    public String getStats() {
        return cache.stats().toString();
    }

    /**
     * adds an entry to the cache
     * @param key of a cache entry
     * @param value of a cache entry
     */
    @Timed
    @Countable
    public void put(int key, String value) {
        cache.put(key, value);
        LOGGER.info("Just inserted new value into cache with {} key.", key);
    }

    public String get(int key) throws ExecutionException {
        return cache.get(key, () -> null);
    }
}
