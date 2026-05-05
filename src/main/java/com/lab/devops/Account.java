package com.lab.devops;
// test code lan 3
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp trừu tượng đại diện cho một tài khoản ngân hàng.
 * Cung cấp các chức năng cơ bản về quản lý số dư và lịch sử giao dịch.
 */
public abstract class Account                                                                                                                                                                                                                                                                                                                                                                                             {

  private static final Logger logger =                                                                                                                                                                                                                                              LoggerFactory.getLogger(Account.class);

  /** Loại tài khoản thanh toán. */
  public static final String CHECKING_TYPE = "CHECKING";
  /** Loại tài khoản tiết kiệm. */
  public static final String SAVINGS_TYPE = "SAVINGS";

  private long accountNumber;
  private double balance;
  protected List<Transaction> list;

  /**
   * Khởi tạo một tài khoản mới.
   *
   * @param accountNumber số tài khoản duy nhất
   * @param balance số dư ban đầu
   */
  public Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.list = new ArrayList<>();
  }

  /**
   * Trả về số tài khoản.
   *
   * @return số tài khoản
   */
  public long getAccountNumber() {
    return accountNumber;
  }

  /**
   * Thiết lập số tài khoản.
   *
   * @param accountNumber số tài khoản cần thiết lập
   */
  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * Trả về số dư hiện tại.
   *
   * @return số dư
   */
  public double getBalance() {
    return balance;
  }

  /**
   * Thiết lập số dư.
   *
   * @param balance số dư mới
   */
  protected void setBalance(double balance) {
    this.balance = balance;
  }

  /**
   * Trả về danh sách giao dịch.
   *
   * @return danh sách giao dịch
   */
  public List<Transaction> getTransactionList() {
    return list;
  }
  /**
   * Thiết lập danh sách giao dịch. Nếu danh sách truyền vào null, khởi tạo danh sách trống.
   *
   * @param transactionList danh sách giao dịch mới
   */

  public void setTransactionList(List<Transaction> transactionList) {
    if (transactionList == null) {
      this.list = new ArrayList<>();
    } else {
      this.list = transactionList;
    }
  }

  /**
   * Thực hiện nạp tiền vào tài khoản.
   *
   * @param amount số tiền cần nạp
   */
  public abstract void deposit(double amount);

  /**
   * Thực hiện rút tiền khỏi tài khoản.
   *
   * @param amount số tiền cần rút
   */
  public abstract void withdraw(double amount);

  /**
   * Xử lý logic nạp tiền nội bộ và cập nhật số dư.
   *
   * @param amount số tiền cần nạp
   * @throws InvalidFundingAmountException nếu số tiền nạp nhỏ hơn hoặc bằng 0
   */
  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
  }

  /**
   * Xử lý logic rút tiền nội bộ và cập nhật số dư.
   *
   * @param amount số tiền cần rút
   * @throws InvalidFundingAmountException nếu số tiền rút nhỏ hơn hoặc bằng 0
   * @throws InsufficientFundsException nếu số tiền rút vượt quá số dư hiện tại
   */
  protected void doWithdrawing(double amount)
          throws InvalidFundingAmountException, InsufficientFundsException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
  }

  /**
   * Thêm một giao dịch vào lịch sử.
   *
   * @param transaction đối tượng giao dịch cần thêm
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      list.add(transaction);
    }
  }

  /**
   * Trả về chuỗi mô tả lịch sử giao dịch.
   *
   * @return chuỗi lịch sử giao dịch
   */
  public String getTransactionHistory() {
    StringBuilder sb = new StringBuilder();
    sb.append("Lịch sử giao dịch của tài khoản ")
            .append(accountNumber)
            .append(":\n");

    for (int i = 0; i < list.size(); i++) {
      sb.append(list.get(i).getTransactionSummary());
      if (i < list.size() - 1) {
        sb.append("\n");
      }
    }

    logger.debug("Đã lấy lịch sử cho tài khoản: {}", accountNumber);
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Account)) {
      return false;
    }
    Account other = (Account) obj;
    return this.accountNumber == other.accountNumber;
  }

  @Override
  public int hashCode() {
    return (int) (accountNumber ^ (accountNumber >>> 32));
  }
}