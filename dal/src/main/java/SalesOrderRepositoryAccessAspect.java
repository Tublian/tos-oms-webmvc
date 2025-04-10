
package dal;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.oms.repository.SalesOrderRepository;
import java.lang.UnsupportedOperationException;

@Aspect
@Component
public class SalesOrderRepositoryAccessAspect {

    @Around("execution(public * com.oms.repository.SalesOrderRepository.*(..))")
    public Object validateAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        boolean authorized = false;
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().contains("OrderService")) {
                authorized = true;
                break;
            }
        }
        if (!authorized) {
            throw new UnsupportedOperationException("Access to SalesOrderRepository is restricted to OrderService");
        }
        return joinPoint.proceed();
    }
}
