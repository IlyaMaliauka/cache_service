package utilities.listeners;

import com.google.common.cache.RemovalNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemovalListener<K,V> implements com.google.common.cache.RemovalListener<K,V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemovalListener.class);

    @Override
    public void onRemoval(RemovalNotification entry) {
        if (entry.wasEvicted()) {
            LOGGER.info("Removed excessive cache value. Removal cause: " + entry.getCause());
        }
    }
}
