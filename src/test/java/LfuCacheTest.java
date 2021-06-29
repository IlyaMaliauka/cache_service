import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import simplejava.LfuCacheService;

public class LfuCacheTest {

    private LfuCacheService lfuCache;

    @Before
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("aspects.xml");
        lfuCache = (LfuCacheService) context.getBean("lfuCache");
    }

    @Test
    public void testPutMethod() {
        lfuCache.put(1, "testValue");
        Assert.assertNotNull(lfuCache.get(1));
        Assert.assertEquals("", lfuCache.get(2));
        lfuCache.put(1, "anotherValue");
        Assert.assertEquals( "anotherValue", lfuCache.get(1));
    }

    @Test
    public void testCacheSize() {
        for (int i=0; i<=1500; i++) {
            lfuCache.put(i, RandomStringUtils.random(5));
        }
        Assert.assertEquals("", lfuCache.get(1));
    }
}
