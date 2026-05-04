package com.lab.devops;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AccountTest {

  @Test
  public void testBalance() {
    // Tạo một tài khoản với số dư 100.0
    CheckingAccount acc = new CheckingAccount(123456, 100.0);

    // DÒNG CỐ TÌNH GÂY LỖI: Ta mong đợi số dư là 999.0 nhưng thực tế chỉ có 100.0
    assertEquals(999.0, acc.getBalance(), 0.001);
  }
}