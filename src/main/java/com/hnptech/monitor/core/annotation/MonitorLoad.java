package com.hnptech.monitor.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MonitorLoad {
  String apiId() default "";
  int threshold() default 1000; // 기본 임계값 1초(1000ms)
}
