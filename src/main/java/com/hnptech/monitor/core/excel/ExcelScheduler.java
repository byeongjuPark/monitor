package com.hnptech.monitor.core.excel;

import com.hnptech.monitor.core.ApiMetricsStorage;
import com.hnptech.monitor.core.storage.ConcurrentHashMapStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExcelScheduler {

  @Autowired
  private ExcelWriter excelWriter;

  @Autowired
  private ConcurrentHashMapStorage metricsStorage;

  // 매 분마다 엑셀 파일 작성 (원하는 주기로 변경 가능)
  @Scheduled(cron = "*/3 * * * * *")
  public void scheduleExcelWrite() {
    System.out.println("scheduleExcelWrite");
    excelWriter.writeMetrics(metricsStorage.getAllMetrics());
  }
}

