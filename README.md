# Bank System - Logging & Observability Report

Dự án tuân thủ tiêu chuẩn chất lượng mã nguồn (Google Java Style) và tích hợp hệ thống giám sát nghiệp vụ.

## 1. Công nghệ sử dụng
* **Framework:** Maven
* **Style Check:** Checkstyle (Google Java Style)
* **Logging Facade:** SLF4J
* **Logging Implementation:** Logback

## 2. Chiến lược Logging (Rationale)
Hệ thống loại bỏ hoàn toàn `System.out.println` để chuyển sang Logging có cấu trúc nhằm tối ưu hóa khả năng quan sát (Observability).

### Các cấp độ Log được chọn:
* **DEBUG:** Ghi lại chi tiết luồng xử lý nội bộ (ví dụ: bắt đầu đọc tệp, quá trình phân tách chuỗi).
* **INFO:** Xác nhận các nghiệp vụ thành công (ví dụ: thêm khách hàng mới, nạp/rút tiền hoàn tất).
* **WARN:** Cảnh báo các tình huống dữ liệu không lý tưởng nhưng chưa gây lỗi (ví dụ: tham số đầu vào bị null).
* **ERROR:** Ghi lại các ngoại lệ khiến giao dịch thất bại (ví dụ: lỗi IO, số dư không đủ).

### Các điểm dữ liệu ghi lại:
* **Định danh:** `accountNumber`, `idNumber` (để truy vết chính xác đối tượng lỗi).
* **Trạng thái tài chính:** `initialBalance`, `finalBalance` (phục vụ đối soát biến động số dư).
* **Ngữ cảnh lỗi:** Thông báo cụ thể từ Exception (`e.getMessage()`).

## 3. Quy trình CI/CD (GitHub Actions)
Hệ thống tự động hóa việc xác minh build thông qua các bước:
1. **Checkout:** Lấy mã nguồn mới nhất.
2. **Setup JDK:** Thiết lập môi trường Java 11.
3. **Maven Build:** Chạy pha `test` và `package`.
4. **Artifacts:** Lưu trữ file `.jar` sau khi build thành công.