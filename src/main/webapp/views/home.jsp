<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.hotel.entity.*" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Trang chủ</title></head>
<body>
<%@ include file="common/menu.jspf" %>
<h2>Danh sách phòng khách sạn</h2>
<form method="get" action="<%=request.getContextPath()%>/home">
    Tìm kiếm: <input name="keyword" value="<%=request.getAttribute("keyword")%>">
    <button type="submit">Tìm</button>
</form>
<br>
<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>ID</th><th>Số phòng</th><th>Loại phòng</th><th>Giá phòng</th><th>Trạng thái</th><th>Ghi chú</th><th>Chức năng</th>
    </tr>
    <%
        List<Phong> rooms = (List<Phong>) request.getAttribute("rooms");
        if (rooms != null) {
            for (Phong p : rooms) {
    %>
    <tr>
        <td><%=p.getId()%></td>
        <td><%=p.getSoPhong()%></td>
        <td><%=p.getTenLoai()%></td>
        <td><%=p.getGiaPhong()%></td>
        <td><%=p.getTrangThai()%></td>
        <td><%=p.getGhiChu()%></td>
        <td>
            <a href="<%=request.getContextPath()%>/room/detail?id=<%=p.getId()%>">Chi tiết</a>
            <% if (userMenu != null && "KHACH_HANG".equals(userMenu.getVaiTro()) && "TRONG".equals(p.getTrangThai())) { %>
                | <a href="<%=request.getContextPath()%>/booking/create?phongId=<%=p.getId()%>">Đặt phòng</a>
            <% } %>
        </td>
    </tr>
    <% }} %>
</table>
</body>
</html>
