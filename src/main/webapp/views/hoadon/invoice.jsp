<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.hotel.entity.HoaDon" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Hóa đơn</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<h2>Hóa đơn thanh toán</h2>
<%
    HoaDon hd = (HoaDon) request.getAttribute("invoice");
    if (hd != null) {
%>
<p>Mã hóa đơn: <%=hd.getId()%></p>
<p>Mã đặt phòng: <%=hd.getDatPhongId()%></p>
<p>Khách hàng: <%=hd.getTenKhach()%></p>
<p>Phòng: <%=hd.getSoPhong()%></p>
<p>Ngày lập: <%=hd.getNgayLap()%></p>
<p>Tổng tiền: <b><%=hd.getTongTien()%></b></p>
<p>Phương thức: <%=hd.getPhuongThuc()%></p>
<p>Trạng thái: <%=hd.getTrangThai()%></p>
<% } else { %>
<p>Chưa có hóa đơn.</p>
<% } %>
</body>
</html>
