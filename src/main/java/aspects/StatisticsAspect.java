package aspects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class StatisticsAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsAspect.class);
    private Map<Class<?>, Integer> counter = new HashMap<>();

    @Pointcut("@annotation(Countable)")
    private void allLogEventMethods() {}

    @AfterReturning("allLogEventMethods()")
    public void count(JoinPoint jp) {
        Class<?> clazz = jp.getTarget().getClass();
        if (!counter.containsKey(clazz)) {
            counter.put(clazz, 0);
        }
        counter.put(clazz, counter.get(clazz) + 1);
    }

    public Map<Class<?>, Integer> getCounter() {
        return Collections.unmodifiableMap(counter);
    }

    @AfterReturning("execution(* *.put(..))")
    public void outputCounter() {
        LOGGER.info("Evictions statistics. Number of evictions: ");
        for (Entry<Class<?>, Integer> entry : counter.entrySet()) {
            LOGGER.info("    " + entry.getKey().getSimpleName() + ": " + entry.getValue());
        }
    }

}