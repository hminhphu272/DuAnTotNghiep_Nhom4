<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Đăng nhập</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<h2>Đăng nhập</h2>
<p><%=request.getAttribute("message") == null ? "" : request.getAttribute("message")%></p>
<form method="post" action="<%=request.getContextPath()%>/login">
    Email:<br>
    <input name="email"><br><br>
    Mật khẩu:<br>
    <input type="password" name="matKhau"><br><br>
    <button type="submit">Đăng nhập</button>
</form>
<p>Tài khoản demo: quanly@hotel.com, letan@hotel.com, khach@hotel.com / 123456</p>
</body>
</html>
