package utilities.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class MeasurementAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementAspect.class);

    /**
     * @param joinPoint joint point
     * @return proceed
     * @throws Throwable exception
     */
    @Around("@annotation(utilities.annotations.Timed)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object proceed = joinPoint.proceed();
        long executionTime = System.nanoTime() - start;
        LOGGER.info(joinPoint.getSignature() + " executed in " + executionTime + " nanos");
        return proceed;
    }
}
