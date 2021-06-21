package guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import simple_java.LfuCacheService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class LruCacheService {

    private static int evictions;
    private static long size;
    private static final Logger logger = LogManager.getLogger(LfuCacheService.class);
    private final Cache<Integer, String> cache;

    public LruCacheService(int capacity) {
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(capacity)
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .removalListener((RemovalListener<Integer, String>) entry -> {
                    if (entry.wasEvicted()) {
                        evictions++;
                        logger.info("Removed excessive cache value. Total evictions: " + evictions + ". Removal cause: " + entry.getCause());
                    }
                })
                .recordStats()
                .build();
        BasicConfigurator.configure();
    }

    public long getSize() {
        return size;
    }

    public String getStats() {
        return cache.stats().toString();
    }

    public void put(int key, String value) {
        long startTime = System.nanoTime();
        cache.put(key, value);
        long timeElapsed = System.nanoTime() - startTime;
        logger.info("Just inserted new value into cache. Key: " + key + ". Time elapsed in nanos: " + timeElapsed);
        size = cache.size();
    }

    public String get(int key) throws ExecutionException {
        return cache.get(key, () -> null);
    }

}
