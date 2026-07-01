<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.hotel.entity.DatPhong" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Quản lý đặt phòng</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<h2>Quản lý đặt phòng</h2>
<table border="1" cellpadding="5" cellspacing="0">
    <tr><th>ID</th><th>Khách hàng</th><th>Phòng</th><th>Ngày nhận</th><th>Ngày trả</th><th>Trạng thái</th><th>Chức năng</th></tr>
    <%
        List<DatPhong> list = (List<DatPhong>) request.getAttribute("list");
        if (list != null) for (DatPhong dp : list) {
    %>
    <tr>
        <td><%=dp.getId()%></td><td><%=dp.getTenKhach()%></td><td><%=dp.getSoPhong()%></td><td><%=dp.getNgayNhan()%></td><td><%=dp.getNgayTra()%></td><td><%=dp.getTrangThai()%></td>
        <td>
            <% if ("CHO_XAC_NHAN".equals(dp.getTrangThai())) { %>
                <a href="<%=request.getContextPath()%>/staff/booking/confirm?id=<%=dp.getId()%>">Xác nhận</a> |
                <a href="<%=request.getContextPath()%>/staff/booking/cancel?id=<%=dp.getId()%>">Hủy</a>
            <% } %>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>
