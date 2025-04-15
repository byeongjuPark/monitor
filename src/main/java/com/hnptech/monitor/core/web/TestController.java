package com.hnptech.monitor.core.web;

import com.hnptech.monitor.core.annotation.MonitorLoad;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @MonitorLoad(apiId = "sampleApi", threshold = 500)
  @GetMapping("/sample")
  public String sampleEndpoint() {
    // 비즈니스 로직 수행
    return "OK";
  }
  @MonitorLoad(apiId = "threshold-5", threshold = 5)
  @GetMapping("/th")
  public String thresholdEndpoint() {
    return "OK";
  }

}
