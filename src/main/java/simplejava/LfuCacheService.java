package simplejava;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.annotations.Countable;
import utilities.annotations.Timed;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LfuCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LfuCacheService.class);
    private static final int DEFAULT_MAX_CAPACITY = 500;
    private final int capacity;
    private final Map<Integer, AccessRate> timeAndFrequency = new HashMap<>();
    private final Map<Integer, String> values = new HashMap<>();

    public LfuCacheService(int capacity) {
        this.capacity = capacity;
    }

    public LfuCacheService() {
        this.capacity = DEFAULT_MAX_CAPACITY;
    }
    /**
     * add new entry to cache
     *
     * @param key   entry key
     * @param value an integer
     */
    @Timed
    @Countable
    public synchronized void put(int key, String value) {
        String valueToAdd = values.get(key);
        if (valueToAdd == null) {
            if (timeAndFrequency.size() == capacity) {
                Integer kickedKey = getKickedKey();
                values.remove(kickedKey);
                timeAndFrequency.remove(kickedKey);
                LOGGER.info("Removed least frequent used excessive entry from cache by key: {}", kickedKey);
            }
            timeAndFrequency.put(key, new AccessRate(key, 1, System.nanoTime()));
        } else {
            AccessRate accessRate = timeAndFrequency.get(key);
            accessRate.hitCount += 1;
            accessRate.lastTime = System.nanoTime();
        }
        values.put(key, value);
    }

    /**
     * get the value from cache by key
     *
     * @param key of the value in cache
     * @return value from cache or empty string
     */
    public synchronized String get(int key) {
        String value = values.get(key);
        if (value != null) {
            AccessRate accessRate = timeAndFrequency.get(key);
            accessRate.hitCount += 1;
            accessRate.lastTime = System.nanoTime();
            return value;
        }
        return StringUtils.EMPTY;
    }

    private Integer getKickedKey() {
        AccessRate min = Collections.min(timeAndFrequency.values());
        return min.key;
    }
}
