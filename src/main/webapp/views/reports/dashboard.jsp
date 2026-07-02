<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Thống kê báo cáo</h2><span class="muted">Doanh thu, tình trạng phòng và đặt phòng</span></div>
    <form class="form" method="get" action="${pageContext.request.contextPath}/admin/reports">
        <div class="form-grid"><div class="form-group"><label>Từ ngày</label><input type="date" name="from" value="${param.from}"></div><div class="form-group"><label>Đến ngày</label><input type="date" name="to" value="${param.to}"></div></div><br><button class="btn" type="submit">Lọc báo cáo</button>
    </form><br>
    <div class="stats">
        <div class="stat-card"><span>Doanh thu</span><br><strong><fmt:formatNumber value="${revenue}" type="number" groupingUsed="true"/> đ</strong></div>
        <c:forEach var="s" items="${roomStats}"><div class="stat-card"><span>Phòng ${s.trangThai}</span><br><strong>${s.soLuong}</strong></div></c:forEach>
    </div><br>
    <div class="panel"><h3>Trạng thái đặt phòng</h3><div class="stats"><c:forEach var="s" items="${bookingStats}"><div class="stat-card"><span>${s.trangThai}</span><br><strong>${s.soLuong}</strong></div></c:forEach></div></div><br>
    <div class="table-wrap"><table>
        <thead><tr><th>Mã</th><th>Khách</th><th>Phòng</th><th>Ngày nhận</th><th>Ngày trả</th><th>Trạng thái</th></tr></thead>
        <tbody><c:forEach var="b" items="${bookings}"><tr><td>#${b.id}</td><td>${b.hoTenKhach}</td><td>${b.soPhong}</td><td><fmt:formatDate value="${b.ngayNhan}" pattern="dd/MM/yyyy"/></td><td><fmt:formatDate value="${b.ngayTra}" pattern="dd/MM/yyyy"/></td><td>${b.trangThai}</td></tr></c:forEach></tbody>
    </table></div>
</main>
<%@ include file="../include/footer.jsp" %>
