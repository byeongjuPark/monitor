package com.hnptech.monitor.core.storage;

import com.hnptech.monitor.core.ApiMetricsStorage;
import com.hnptech.monitor.core.model.ApiMetrics;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ConcurrentHashMapStorage implements ApiMetricsStorage {

  private final Map<String, ApiMetrics> metricsMap = new ConcurrentHashMap<>();
  private final ExecutorService executor = Executors.newSingleThreadExecutor();

  @Override
  public void recordMetric(String apiId, long duration, int threshold) {
    // 비동기 방식으로 메트릭을 저장하여 API 응답 시간에 영향을 주지 않도록 함
    executor.execute(() -> {
      metricsMap.compute(apiId, (k, v) -> {
        if (v == null) {
          v = new ApiMetrics();
        }
        // 실행 횟수와 총 응답시간 업데이트
        v.addExecution(duration);
        // 현재 호출의 응답 시간이 임계치를 초과하면 과부하 상태 설정
        v.setOverloaded(duration > threshold);
        return v;
      });
    });
  }

  @Override
  public Map<String, ApiMetrics> getAllMetrics() {
    // 안전하게 현재 메트릭 데이터를 반환 (복사본 제공)
    return new ConcurrentHashMap<>(metricsMap);
  }
}

