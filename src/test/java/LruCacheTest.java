import guava.LruCacheService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LruCacheTest {

    private LruCacheService lruCache;

    @Before
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("aspects.xml");
        lruCache = (LruCacheService) context.getBean("lruCache");
    }

    @Test
    public void testPutMethod() {
        lruCache.put(1, "testValue");
        Assert.assertNotNull(lruCache.get(1));
        Assert.assertNull(lruCache.get(2));
        lruCache.put(1, "anotherValue");
        Assert.assertEquals( "anotherValue", lruCache.get(1));
    }

    @Test
    public void testCacheSize() {
        for (int i=0; i<=150000; i++) {
            lruCache.put(i, RandomStringUtils.random(5));
        }
        Assert.assertNull(lruCache.get(1));
    }
}
