<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.hotel.entity.LoaiPhong" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Loại phòng</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<h2>Quản lý loại phòng</h2>
<form method="post">
    Tên loại: <input name="tenLoai" required>
    Mô tả: <input name="moTa">
    Sức chứa: <input name="sucChua" required>
    Giá cơ bản: <input name="giaCoBan" required>
    <button type="submit">Thêm</button>
</form>
<br>
<table border="1" cellpadding="5" cellspacing="0">
    <tr><th>ID</th><th>Tên loại</th><th>Mô tả</th><th>Sức chứa</th><th>Giá cơ bản</th><th>Trạng thái</th></tr>
    <%
        List<LoaiPhong> list = (List<LoaiPhong>) request.getAttribute("list");
        if (list != null) for (LoaiPhong lp : list) {
    %>
    <tr>
        <td><%=lp.getId()%></td><td><%=lp.getTenLoai()%></td><td><%=lp.getMoTa()%></td>
        <td><%=lp.getSucChua()%></td><td><%=lp.getGiaCoBan()%></td><td><%=lp.isTrangThai()%></td>
    </tr>
    <% } %>
</table>
</body>
</html>
