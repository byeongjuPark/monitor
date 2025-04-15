package com.hnptech.monitor.core.aspect;

import com.hnptech.monitor.core.ApiMetricsStorage;
import com.hnptech.monitor.core.annotation.MonitorLoad;
import com.hnptech.monitor.core.storage.ConcurrentHashMapStorage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoadMonitoringAspect {

  @Autowired
  private ConcurrentHashMapStorage storage;

  @Around("@annotation(monitorLoad)")
  public Object measure(ProceedingJoinPoint pjp, MonitorLoad monitorLoad) throws Throwable {
    long start = System.currentTimeMillis();
    System.out.println("start: " + start);
    try {
      return pjp.proceed();
    } finally {
      long duration = System.currentTimeMillis() - start;
      System.out.println("duration: "+duration);
      storage.recordMetric(
              monitorLoad.apiId(),
              duration,
              monitorLoad.threshold()
      );
    }
  }
}
