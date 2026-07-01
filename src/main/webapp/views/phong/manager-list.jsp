<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.hotel.entity.Phong" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Quản lý phòng</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<h2>Quản lý phòng</h2>
<p><a href="<%=request.getContextPath()%>/admin/room/create">Thêm phòng</a></p>
<table border="1" cellpadding="5" cellspacing="0">
    <tr><th>ID</th><th>Số phòng</th><th>Loại</th><th>Giá</th><th>Trạng thái</th><th>Ghi chú</th><th>Chức năng</th></tr>
    <%
        List<Phong> rooms = (List<Phong>) request.getAttribute("rooms");
        if (rooms != null) for (Phong p : rooms) {
    %>
    <tr>
        <td><%=p.getId()%></td><td><%=p.getSoPhong()%></td><td><%=p.getTenLoai()%></td>
        <td><%=p.getGiaPhong()%></td><td><%=p.getTrangThai()%></td><td><%=p.getGhiChu()%></td>
        <td>
            <a href="<%=request.getContextPath()%>/admin/room/edit?id=<%=p.getId()%>">Sửa</a> |
            <a href="<%=request.getContextPath()%>/admin/room/delete?id=<%=p.getId()%>" onclick="return confirm('Xóa phòng?')">Xóa</a>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>
