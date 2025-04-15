package com.hnptech.monitor.core.web;

import com.hnptech.monitor.core.ApiMetricsStorage;
import com.hnptech.monitor.core.model.ApiMetrics;
import com.hnptech.monitor.core.storage.ConcurrentHashMapStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class DashboardController {

  @Autowired
  private ConcurrentHashMapStorage apiMetricsStorage;

  @GetMapping("/monitoring/dashboard")
  public String dashboard(Model model) {
    Map<String, ApiMetrics> metrics = apiMetricsStorage.getAllMetrics();
    model.addAttribute("metrics", metrics);
    return "dashboard";
  }
}
