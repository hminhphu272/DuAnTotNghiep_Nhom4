<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.hotel.entity.Phong" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Chi tiết phòng</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<h2>Chi tiết phòng</h2>
<%
    Phong p = (Phong) request.getAttribute("room");
    if (p != null) {
%>
<p>Số phòng: <b><%=p.getSoPhong()%></b></p>
<p>Loại phòng: <%=p.getTenLoai()%></p>
<p>Giá phòng: <%=p.getGiaPhong()%></p>
<p>Trạng thái: <%=p.getTrangThai()%></p>
<p>Ghi chú: <%=p.getGhiChu()%></p>
<% if (userMenu != null && "KHACH_HANG".equals(userMenu.getVaiTro()) && "TRONG".equals(p.getTrangThai())) { %>
    <a href="<%=request.getContextPath()%>/booking/create?phongId=<%=p.getId()%>">Đặt phòng này</a>
<% } %>
<% } %>
</body>
</html>
