import guava.LruCacheService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

public class LruCacheTest {

    @Test
    public void testLruCache() {
        LruCacheService cacheService = new LruCacheService(100000);
        for (int i=1; i<=101000; i++) {
            cacheService.put(i, RandomStringUtils.randomAlphabetic(5));
        }
    }

    @Test
    public void testCacheStats() {
        LruCacheService cacheService = new LruCacheService(100000);
        for (int i=1; i<=101000; i++) {
            cacheService.put(i, RandomStringUtils.randomAlphabetic(5));
        }
        Assert.assertTrue(cacheService.getStats().contains("1000"));
    }
}
