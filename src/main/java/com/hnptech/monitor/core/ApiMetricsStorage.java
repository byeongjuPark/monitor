package com.hnptech.monitor.core;

import com.hnptech.monitor.core.model.ApiMetrics;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface ApiMetricsStorage {
  /**
   * API 호출 후 응답시간 및 임계치정보 기록
   *
   * @param apiId 호출한 API 식별자
   * @param duration 실행 소요 시간 (ms)
   * @param threshold 임계치 (ms)
   */
  void recordMetric(String apiId, long duration, int threshold);

  /**
   * 현재까지 기록된 모든 API 메트릭 정보를 반환
   *
   * @return API ID를 키로, ApiMetrics 객체가 값인 Map
   */
  Map<String, ApiMetrics> getAllMetrics();
}

