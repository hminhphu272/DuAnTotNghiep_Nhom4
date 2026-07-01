<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Thống kê</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<h2>Thống kê báo cáo</h2>
<h3>Doanh thu</h3>
<p>Tổng doanh thu: <b><%=request.getAttribute("doanhThu")%></b></p>

<h3>Tình trạng phòng</h3>
<table border="1" cellpadding="5" cellspacing="0">
    <tr><th>Trạng thái</th><th>Số lượng</th></tr>
    <%
        Map<String,Integer> phong = (Map<String,Integer>) request.getAttribute("phong");
        if (phong != null) for (Map.Entry<String,Integer> e : phong.entrySet()) {
    %>
    <tr><td><%=e.getKey()%></td><td><%=e.getValue()%></td></tr>
    <% } %>
</table>

<h3>Trạng thái đặt phòng</h3>
<table border="1" cellpadding="5" cellspacing="0">
    <tr><th>Trạng thái</th><th>Số lượng</th></tr>
    <%
        Map<String,Integer> datPhong = (Map<String,Integer>) request.getAttribute("datPhong");
        if (datPhong != null) for (Map.Entry<String,Integer> e : datPhong.entrySet()) {
    %>
    <tr><td><%=e.getKey()%></td><td><%=e.getValue()%></td></tr>
    <% } %>
</table>
</body>
</html>
