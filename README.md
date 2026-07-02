# DuAnTotNghiep_Nhom4 - Website quản lý khách sạn

## Công nghệ
- Java 17
- Maven Dynamic Web Project
- Jakarta Servlet/JSP/JSTL
- JDBC + SQL Server
- Tomcat 10.1 hoặc Tomcat 11

## Tài khoản demo
- Quản lý: admin@hotel.vn / 123456
- Lễ tân: letan@hotel.vn / 123456
- Khách hàng: khach@hotel.vn / 123456

## Cài đặt CSDL
1. Mở SQL Server Management Studio.
2. Mở file `DuAnTotNghiep_Nhom4.sql`.
3. Chạy toàn bộ script để tạo database, bảng và dữ liệu mẫu.
4. Mở file `src/main/java/com/nhom4/hotel/util/JdbcUtil.java`.
5. Sửa USER và PASSWORD đúng với SQL Server của máy bạn nếu khác `sa / 123456`.

## Import Eclipse
1. Eclipse -> File -> Import.
2. Chọn Maven -> Existing Maven Projects.
3. Chọn thư mục `DuAnTotNghiep_Nhom4`.
4. Bấm Finish.
5. Add project vào Tomcat rồi Run.
6. Truy cập: `http://localhost:8080/DuAnTotNghiep_Nhom4/home`.

## Chức năng chính
- Đăng ký, đăng nhập, đăng xuất.
- Xem, tìm kiếm phòng và đặt phòng.
- Khách hàng xem/hủy đặt phòng.
- Lễ tân xác nhận đặt phòng, nhận phòng, trả phòng, lập hóa đơn.
- Quản lý thêm/sửa/xóa phòng, quản lý loại phòng và xem báo cáo.

## Ghi chú làm nhóm bằng GitHub
- Không push thư mục `target/`.
- Mỗi thành viên nên làm trên branch riêng.
- Trước khi code, luôn pull code mới nhất từ nhánh chính.

## Bản FixDeploy
Bản này đã bổ sung mapping servlet trực tiếp trong `WEB-INF/web.xml` và cấu hình WTP/Maven cho Eclipse để hạn chế lỗi 404 do Tomcat không nhận annotation hoặc không deploy đúng thư mục webapp.

Nếu vẫn 404:
1. Delete Tomcat Server cũ trong tab Servers.
2. Tắt Eclipse.
3. Xóa `D:\eclipse_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0`, `tmp1`, `tmp2` nếu có.
4. Mở Eclipse, import project lại bằng `File -> Import -> Maven -> Existing Maven Projects`.
5. Chuột phải project -> Run As -> Run on Server.
