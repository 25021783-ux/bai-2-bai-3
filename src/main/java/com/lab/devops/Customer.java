package com.lab.devops;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp đại diện cho một khách hàng của ngân hàng.
 * Lưu trữ thông tin cá nhân và danh sách các tài khoản sở hữu.
 */
public class Customer {

  private long idNumber;
  private String fullName;
  private List<Account> accountList;

  /**
   * Khởi tạo một khách hàng mới.
   *
   * @param idNumber số định danh của khách hàng
   * @param fullName họ và tên đầy đủ của khách hàng
   */
  public Customer(long idNumber, String fullName) {
    this.idNumber = idNumber;
    this.fullName = fullName;
    this.accountList = new ArrayList<>();
  }

  /**
   * Trả về số định danh của khách hàng.
   *
   * @return số định danh idNumber
   */
  public long getIdNumber() {
    return idNumber;
  }

  /**
   * Thiết lập số định danh cho khách hàng.
   *
   * @param idNumber số định danh mới
   */
  public void setIdNumber(long idNumber) {
    this.idNumber = idNumber;
  }

  /**
   * Trả về họ tên đầy đủ của khách hàng.
   *
   * @return chuỗi họ tên
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Thiết lập họ tên cho khách hàng.
   *
   * @param fullName họ tên mới
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * Trả về danh sách tài khoản của khách hàng.
   *
   * @return danh sách các đối tượng Account
   */
  public List<Account> getAccountList() {
    return accountList;
  }

  /**
   * Thiết lập danh sách tài khoản.
   *
   * @param accountList danh sách tài khoản mới
   */
  public void setAccountList(List<Account> accountList) {
    this.accountList = accountList;
  }

  /**
   * Thêm một tài khoản mới vào danh sách của khách hàng.
   *
   * @param account đối tượng tài khoản cần thêm
   */
  public void addAccount(Account account) {
    if (account != null) {
      accountList.add(account);
    }
  }

  /**
   * Xây dựng chuỗi thông tin chi tiết của khách hàng và các tài khoản.
   *
   * @return chuỗi thông tin khách hàng định dạng chuẩn
   */
  public String getCustomerInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append("Số CMND: ").append(idNumber)
            .append(". Họ tên: ").append(fullName).append(".\n");

    for (int i = 0; i < accountList.size(); i++) {
      sb.append(accountList.get(i).getTransactionHistory());
      if (i < accountList.size() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString().trim();
  }
}