import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import simplejava.LfuCacheService;

public class LfuCacheTest {

    @Test
    public void testLfuCacheServiceServiceService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("aspects.xml");
        LfuCacheService cacheService = (LfuCacheService) context.getBean("cacheService");
        for (int i=1; i<=550; i++) {
            cacheService.put(i, RandomStringUtils.randomAlphabetic(5));
        }
    }
}
