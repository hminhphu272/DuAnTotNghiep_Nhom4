<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Danh sách hóa đơn</h2><span class="muted">Thanh toán đặt phòng</span></div>
    <div class="table-wrap"><table>
        <thead><tr><th>Mã HĐ</th><th>Đặt phòng</th><th>Khách</th><th>Phòng</th><th>Nhân viên</th><th>Ngày lập</th><th>Tổng tiền</th><th>Phương thức</th></tr></thead>
        <tbody><c:forEach var="i" items="${invoices}"><tr>
            <td>#${i.id}</td><td>#${i.datPhongId}</td><td>${i.tenKhach}</td><td>${i.soPhong}</td><td>${i.tenNhanVien}</td>
            <td><fmt:formatDate value="${i.ngayLap}" pattern="dd/MM/yyyy HH:mm"/></td>
            <td><b><fmt:formatNumber value="${i.tongTien}" type="number" groupingUsed="true"/> đ</b></td><td>${i.phuongThuc}</td>
        </tr></c:forEach></tbody>
    </table></div>
</main>
<%@ include file="../include/footer.jsp" %>
