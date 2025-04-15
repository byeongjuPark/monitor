package com.hnptech.monitor.core.excel;

import com.hnptech.monitor.core.model.ApiMetrics;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;

@Component
public class ExcelWriter {

  @Value("${monitoring.excel.path:/monitoring}")
  private String outputPath;

  public void writeMetrics(Map<String, ApiMetrics> metrics) {
    String fileName = getServerName() + "_" + LocalDate.now() + ".xlsx";
    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      XSSFSheet sheet = workbook.createSheet("API Metrics");
      int rowNum = 0;

      // 헤더 생성 (API ID, 호출 횟수, 총 응답시간, 평균 응답시간, 과부하 여부)
      var headerRow = sheet.createRow(rowNum++);
      headerRow.createCell(0).setCellValue("API ID");
      headerRow.createCell(1).setCellValue("호출 횟수");
      headerRow.createCell(2).setCellValue("총 응답시간(ms)");
      headerRow.createCell(3).setCellValue("평균 응답시간(ms)");
      headerRow.createCell(4).setCellValue("과부하");
      System.out.println(metrics.toString());
      // 데이터 기록
      for (var entry : metrics.entrySet()) {
        var row = sheet.createRow(rowNum++);
        System.out.println(entry.getKey());
        System.out.println(entry.getValue());
        System.out.println(entry.getValue().getExecutionCount());
        System.out.println(entry.getValue().getTotalTime());
        System.out.println(entry.getValue().getAvgTime());
        System.out.println(entry.getValue().isOverloaded() ? "과부하" : "정상");
        row.createCell(0).setCellValue(entry.getKey());
        row.createCell(1).setCellValue(entry.getValue().getExecutionCount());
        row.createCell(2).setCellValue(entry.getValue().getTotalTime());
        row.createCell(3).setCellValue(entry.getValue().getAvgTime());
        row.createCell(4).setCellValue(entry.getValue().isOverloaded() ? "과부하" : "정상");
      }

      Files.createDirectories(Paths.get(outputPath));
      try (FileOutputStream fos = new FileOutputStream(outputPath + "/" + fileName)) {
        workbook.write(fos);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String getServerName() {
    return System.getProperty("server.name", "default");
  }
}