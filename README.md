# DuAnTotNghiep_Nhom4

Website quản lý hệ thống khách sạn bằng Java Servlet, JSP và SQL Server.

## Tài khoản demo

- Quản lý: quanly@hotel.com / 123456
- Lễ tân: letan@hotel.com / 123456
- Khách hàng: khach@hotel.com / 123456

## Cách chạy

1. Chạy file `database/DuAnTotNghiep_Nhom4.sql` trong SQL Server.
2. Mở file `src/main/java/com/hotel/util/XJdbc.java` và sửa USERNAME/PASSWORD theo máy.
3. Import project vào Eclipse bằng Maven Existing Maven Projects.
4. Chạy bằng Tomcat 10 hoặc Tomcat 11.
5. Truy cập: `http://localhost:8080/DuAnTotNghiep_Nhom4`.

## Chức năng chính

- Đăng ký, đăng nhập, đăng xuất.
- Xem danh sách phòng, tìm kiếm phòng, xem chi tiết phòng.
- Đặt phòng, xem đặt phòng, hủy đặt phòng.
- Lễ tân xác nhận đặt phòng, nhận phòng, trả phòng và lập hóa đơn.
- Quản lý loại phòng, quản lý phòng.
- Thống kê doanh thu, tình trạng phòng, trạng thái đặt phòng.
