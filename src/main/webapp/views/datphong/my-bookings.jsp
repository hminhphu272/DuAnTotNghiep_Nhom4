<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.hotel.entity.DatPhong" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Đặt phòng của tôi</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<h2>Đặt phòng của tôi</h2>
<table border="1" cellpadding="5" cellspacing="0">
    <tr><th>ID</th><th>Phòng</th><th>Ngày nhận</th><th>Ngày trả</th><th>Trạng thái</th><th>Chức năng</th></tr>
    <%
        List<DatPhong> list = (List<DatPhong>) request.getAttribute("list");
        if (list != null) for (DatPhong dp : list) {
    %>
    <tr>
        <td><%=dp.getId()%></td><td><%=dp.getSoPhong()%></td><td><%=dp.getNgayNhan()%></td><td><%=dp.getNgayTra()%></td><td><%=dp.getTrangThai()%></td>
        <td>
            <% if ("CHO_XAC_NHAN".equals(dp.getTrangThai())) { %>
                <a href="<%=request.getContextPath()%>/booking/cancel?id=<%=dp.getId()%>">Hủy</a>
            <% } %>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>
