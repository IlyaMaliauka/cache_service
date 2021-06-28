package guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import annotations.annotations.Countable;
import annotations.annotations.Timed;
import listeners.EvictionListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class LruCacheService {

    private final int MAX_CAPACITY = 100000;
    private static final Logger LOGGER = LoggerFactory.getLogger(LruCacheService.class);
    private final Cache<Integer, String> cache;

     {
        cache = CacheBuilder.newBuilder()
                .maximumSize(MAX_CAPACITY)
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .removalListener(new EvictionListener<>())
                .recordStats()
                .build();
    }

    public LruCacheService() {
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
    public synchronized void put(int key, String value) {
        cache.put(key, value);
        LOGGER.info("Just inserted new value into cache with {} key.", key);
    }

    /**
     * @param key of a cache entry
     * @return the value of a cache entry
     */
    public synchronized String get(int key) {
        try {
            return cache.get(key, () -> null);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }
}
