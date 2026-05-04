package com.lab.devops;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho một giao dịch tài chính trong hệ thống ngân hàng.
 * Lưu trữ thông tin về loại giao dịch, số tiền và biến động số dư.
 */
public class Transaction {

  private static final Logger logger = LoggerFactory.getLogger(Transaction.class);

  /** Loại giao dịch: Nạp tiền vào tài khoản vãng lai. */
  public static final int TYPE_DEPOSIT_CHECKING = 1;
  /** Loại giao dịch: Rút tiền từ tài khoản vãng lai. */
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  /** Loại giao dịch: Nạp tiền vào tài khoản tiết kiệm. */
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  /** Loại giao dịch: Rút tiền từ tài khoản tiết kiệm. */
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Khởi tạo một giao dịch mới.
   *
   * @param type loại giao dịch
   * @param amount số tiền giao dịch
   * @param initialBalance số dư trước giao dịch
   * @param finalBalance số dư sau giao dịch
   */
  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  /**
   * Trả về loại giao dịch.
   *
   * @return mã loại giao dịch
   */
  public int getType() {
    return type;
  }

  /**
   * Thiết lập loại giao dịch.
   *
   * @param type mã loại giao dịch cần thiết lập
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Trả về số tiền giao dịch.
   *
   * @return số tiền giao dịch
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Thiết lập số tiền giao dịch.
   *
   * @param amount số tiền cần thiết lập
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /**
   * Trả về số dư ban đầu trước khi giao dịch.
   *
   * @return số dư ban đầu
   */
  public double getInitialBalance() {
    return initialBalance;
  }

  /**
   * Thiết lập số dư ban đầu.
   *
   * @param initialBalance số dư ban đầu cần thiết lập
   */
  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  /**
   * Trả về số dư cuối cùng sau khi giao dịch.
   *
   * @return số dư cuối cùng
   */
  public double getFinalBalance() {
    return finalBalance;
  }

  /**
   * Thiết lập số dư cuối cùng.
   *
   * @param finalBalance số dư cuối cùng cần thiết lập
   */
  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Chuyển đổi mã loại giao dịch sang chuỗi mô tả tiếng Việt.
   *
   * @param transactionType mã loại giao dịch
   * @return chuỗi mô tả loại giao dịch tương ứng
   */
  public static String getTypeString(int transactionType) {
    switch (transactionType) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Trả về tóm tắt thông tin giao dịch dưới dạng chuỗi định dạng.
   *
   * @return chuỗi tóm tắt giao dịch chi tiết
   */
  public String getTransactionSummary() {
    logger.debug("Bắt đầu xử lý tóm tắt giao dịch cho loại: {}", this.type);

    String typeStr = getTypeString(type);
    String initialStr = String.format(Locale.US, "%.2f", initialBalance);
    String amountStr = String.format(Locale.US, "%.2f", amount);
    String finalStr = String.format(Locale.US, "%.2f", finalBalance);

    return String.format("- Kiểu giao dịch: %s. Số dư ban đầu: $%s. Số tiền: $%s. Số dư cuối: $%s.",
            typeStr, initialStr, amountStr, finalStr);
  }
}