import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import simple_java.LfuCacheService;

public class LfuCacheTest {

    @Test
    public void testLfuCacheServiceServiceService() {
        LfuCacheService cacheService = new LfuCacheService(500);
        for (int i=1; i<=550; i++) {
            cacheService.put(i, RandomStringUtils.randomAlphabetic(5));
        }
        Assert.assertNull(cacheService.get(2));
    }
}
