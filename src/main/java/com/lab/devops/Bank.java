package com.lab.devops;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp quản lý danh sách khách hàng của ngân hàng.
 */
public class Bank {

  private static final Logger logger = LoggerFactory.getLogger(Bank.class);
  private static final String ID_PATTERN = "\\d{9}";
  private List<Customer> customerList;

  /**
   * Khởi tạo một đối tượng Bank mới với danh sách khách hàng trống.
   */
  public Bank() {
    this.customerList = new ArrayList<>();
  }

  /**
   * Trả về danh sách khách hàng hiện có.
   *
   * @return danh sách các đối tượng Customer
   */
  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Thiết lập danh sách khách hàng.
   *
   * @param customerList danh sách khách hàng mới cần thiết lập
   */
  public void setCustomerList(List<Customer> customerList) {
    if (customerList == null) {
      this.customerList = new ArrayList<>();
      logger.warn("Attempted to set a null customer list. Initialized an empty list instead.");
    } else {
      this.customerList = customerList;
      logger.info("Customer list has been updated with {} customers.", customerList.size());
    }
  }

  /**
   * Đọc danh sách khách hàng từ luồng đầu vào.
   *
   * @param inputStream luồng dữ liệu chứa thông tin khách hàng và tài khoản
   */
  public void readCustomerList(InputStream inputStream) {
    if (inputStream == null) {
      return;
    }

    logger.debug("Bắt đầu đọc dữ liệu từ InputStream...");

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      Customer currentCustomer = null;

      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }
        currentCustomer = processLine(line, currentCustomer);
      }
    } catch (IOException e) {
      logger.error("Lỗi khi đọc luồng dữ liệu: {}", e.getMessage());
    }
  }

  private Customer processLine(String line, Customer current) {
    int lastSpaceIndex = line.lastIndexOf(' ');
    if (lastSpaceIndex <= 0) {
      return current;
    }

    String token = line.substring(lastSpaceIndex + 1).trim();
    String nameOrData = line.substring(0, lastSpaceIndex).trim();

    if (token.matches(ID_PATTERN)) {
      Customer newCustomer = new Customer(Long.parseLong(token), nameOrData);
      customerList.add(newCustomer);
      return newCustomer;
    }

    if (current != null) {
      parseAndAddAccount(line, current);
    }
    return current;
  }

  private void parseAndAddAccount(String line, Customer current) {
    String[] parts = line.split("\\s+");
    if (parts.length < 3) {
      return;
    }

    try {
      long accNum = Long.parseLong(parts[0]);
      String type = parts[1];
      double bal = Double.parseDouble(parts[2]);

      if (Account.CHECKING_TYPE.equals(type)) {
        current.addAccount(new CheckingAccount(accNum, bal));
      } else if (Account.SAVINGS_TYPE.equals(type)) {
        current.addAccount(new SavingsAccount(accNum, bal));
      }
    } catch (NumberFormatException e) {
      logger.warn("Dòng dữ liệu tài khoản không hợp lệ: {}", line);
    }
  }

  /**
   * Trả về thông tin khách hàng được sắp xếp theo ID.
   *
   * @return chuỗi thông tin khách hàng
   */
  public String getCustomersInfoByIdOrder() {
    customerList.sort(Comparator.comparingLong(Customer::getIdNumber));
    return buildCustomerInfoString(customerList);
  }

  /**
   * Trả về thông tin khách hàng được sắp xếp theo tên.
   *
   * @return chuỗi thông tin khách hàng
   */
  public String getCustomersInfoByNameOrder() {
    List<Customer> copy = new ArrayList<>(customerList);
    copy.sort(Comparator.comparing(Customer::getFullName)
            .thenComparingLong(Customer::getIdNumber));
    return buildCustomerInfoString(copy);
  }

  /**
   * Phương thức hỗ trợ xây dựng chuỗi thông tin từ danh sách khách hàng.
   *
   * @param list danh sách khách hàng cần xử lý
   * @return chuỗi thông tin tổng hợp
   */
  private String buildCustomerInfoString(List<Customer> list) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      sb.append(list.get(i).getCustomerInfo());
      if (i < list.size() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}