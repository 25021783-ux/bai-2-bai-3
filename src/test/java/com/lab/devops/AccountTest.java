package com.lab.devops;

import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AccountTest {

  @Test
  public void testFilePathHardcoded() {
    String folder = "logs";
    String file = "transaction.log";

    // CỐ TÌNH GÂY LỖI: Nối chuỗi bằng dấu gạch chéo ngược của Windows
    String actualPath = folder + "\\" + file;

    // Đường dẫn chuẩn mong đợi
    String expectedPath = Paths.get("logs", "transaction.log").toString();

    // So sánh
    assertEquals(expectedPath, actualPath);
  }
}