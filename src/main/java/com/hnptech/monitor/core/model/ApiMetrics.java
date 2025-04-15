package com.hnptech.monitor.core.model;

public class ApiMetrics {
  private long executionCount = 0;
  private long totalTime = 0;
  private boolean overloaded = false;

  public synchronized void addExecution(long duration) {
    executionCount++;
    totalTime += duration;
  }

  public long getExecutionCount() {
    return executionCount;
  }

  public long getTotalTime() {
    return totalTime;
  }

  public double getAvgTime() {
    return (executionCount == 0) ? 0 : (double) totalTime / executionCount;
  }

  public boolean isOverloaded() {
    return overloaded;
  }

  public void setOverloaded(boolean overloaded) {
    this.overloaded = overloaded;
  }
}
