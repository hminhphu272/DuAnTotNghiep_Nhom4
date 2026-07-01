<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Đăng ký</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<h2>Đăng ký khách hàng</h2>
<p><%=request.getAttribute("message") == null ? "" : request.getAttribute("message")%></p>
<form method="post" action="<%=request.getContextPath()%>/register">
    Họ tên:<br>
    <input name="hoTen" required><br><br>
    Email:<br>
    <input name="email" required><br><br>
    Mật khẩu:<br>
    <input type="password" name="matKhau" required><br><br>
    Số điện thoại:<br>
    <input name="soDienThoai"><br><br>
    <button type="submit">Đăng ký</button>
</form>
</body>
</html>
