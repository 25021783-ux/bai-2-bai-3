package com.lab.devops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp tài khoản tiết kiệm với các quy định riêng về hạn mức rút tiền và số dư tối thiểu.
 */
public class SavingsAccount extends Account {

  private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);

  /** Hạn mức rút tiền tối đa mỗi lần. */
  public static final double MAX_WITHDRAW = 1000.0;
  /** Số dư tối thiểu bắt buộc duy trì trong tài khoản. */
  public static final double MIN_BALANCE = 5000.0;

  /**
   * Khởi tạo tài khoản tiết kiệm.
   *
   * @param accountNumber số tài khoản
   * @param balance số dư ban đầu
   */
  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    logger.debug("Đang xử lý nạp tiền cho tài khoản tiết kiệm...");
    double initialBalance = getBalance();

    try {
      doDepositing(amount);
      double finalBalance = getBalance();

      // Sử dụng hằng số từ lớp Transaction thay vì số 3
      Transaction transaction = new Transaction(
              Transaction.TYPE_DEPOSIT_SAVINGS,
              amount,
              initialBalance,
              finalBalance
      );
      addTransaction(transaction);

      logger.info("Nạp tiền thành công. Số dư mới: {}", finalBalance);
    } catch (InvalidFundingAmountException e) {
      logger.error("Lỗi nạp tiền: {}", e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();

    try {
      // Sử dụng hằng số thay vì Magic Numbers
      if (amount > MAX_WITHDRAW) {
        throw new InvalidFundingAmountException(amount);
      }
      if (initialBalance - amount < MIN_BALANCE) {
        throw new InsufficientFundsException(amount);
      }

      doWithdrawing(amount);
      double finalBalance = getBalance();

      // Sử dụng hằng số từ lớp Transaction thay vì số 4
      Transaction transaction = new Transaction(
              Transaction.TYPE_WITHDRAW_SAVINGS,
              amount,
              initialBalance,
              finalBalance
      );
      addTransaction(transaction);

      logger.info("[SAVINGS] Rút tiền thành công. Số dư còn lại: {}", finalBalance);
    } catch (InvalidFundingAmountException | InsufficientFundsException e) {
      logger.error("Giao dịch rút tiền thất bại: {}", e.getMessage());
    }
  }
}