<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Quản lý phòng</h2><a class="btn" href="${pageContext.request.contextPath}/admin/rooms/form">Thêm phòng</a></div>
    <c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
    <form class="form" method="get" action="${pageContext.request.contextPath}/admin/rooms">
        <div class="form-grid">
            <div class="form-group"><label>Từ khóa</label><input name="keyword" value="${param.keyword}" placeholder="Số phòng, loại, ghi chú"></div>
            <div class="form-group"><label>Trạng thái</label><select name="status"><option value="">Tất cả</option><option>Trống</option><option>Đã đặt</option><option>Đang sử dụng</option><option>Bảo trì</option></select></div>
        </div><br><button class="btn" type="submit">Lọc</button>
    </form><br>
    <div class="table-wrap"><table>
        <thead><tr><th>ID</th><th>Số phòng</th><th>Loại</th><th>Giá</th><th>Trạng thái</th><th>Ghi chú</th><th>Thao tác</th></tr></thead>
        <tbody><c:forEach var="r" items="${rooms}"><tr>
            <td>${r.id}</td><td>${r.soPhong}</td><td>${r.tenLoai}</td><td><fmt:formatNumber value="${r.giaPhong}" type="number" groupingUsed="true"/> đ</td><td>${r.trangThai}</td><td>${r.ghiChu}</td>
            <td><a class="btn small" href="${pageContext.request.contextPath}/admin/rooms/form?id=${r.id}">Sửa</a> <a class="btn small danger" href="${pageContext.request.contextPath}/admin/rooms/delete?id=${r.id}" onclick="return confirm('Xóa phòng này?')">Xóa</a></td>
        </tr></c:forEach></tbody>
    </table></div>
</main>
<%@ include file="../include/footer.jsp" %>
