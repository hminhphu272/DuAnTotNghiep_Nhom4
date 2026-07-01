<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.hotel.entity.Phong" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Đặt phòng</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<h2>Đặt phòng</h2>
<p><%=request.getAttribute("message") == null ? "" : request.getAttribute("message")%></p>
<%
    Phong p = (Phong) request.getAttribute("room");
    if (p != null) {
%>
<p>Phòng: <b><%=p.getSoPhong()%></b> - <%=p.getTenLoai()%> - <%=p.getGiaPhong()%></p>
<form method="post" action="<%=request.getContextPath()%>/booking/create">
    <input type="hidden" name="phongId" value="<%=p.getId()%>">
    Ngày nhận:<br>
    <input type="date" name="ngayNhan" required><br><br>
    Ngày trả:<br>
    <input type="date" name="ngayTra" required><br><br>
    <button type="submit">Xác nhận đặt phòng</button>
</form>
<% } %>
</body>
</html>
