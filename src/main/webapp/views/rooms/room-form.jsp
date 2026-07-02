<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="form">
        <h2>${empty room ? 'Thêm phòng' : 'Cập nhật phòng'}</h2>
        <form method="post" action="${pageContext.request.contextPath}/admin/rooms/save">
            <input type="hidden" name="id" value="${room.id}">
            <div class="form-grid">
                <div class="form-group"><label>Số phòng</label><input name="soPhong" value="${room.soPhong}" required></div>
                <div class="form-group"><label>Loại phòng</label><select name="loaiPhongId" required><c:forEach var="t" items="${types}"><option value="${t.id}" ${room.loaiPhongId == t.id ? 'selected' : ''}>${t.tenLoai}</option></c:forEach></select></div>
                <div class="form-group"><label>Giá phòng</label><input type="number" name="giaPhong" value="${room.giaPhong}" required></div>
                <div class="form-group"><label>Trạng thái</label><select name="trangThai"><option>Trống</option><option>Đã đặt</option><option>Đang sử dụng</option><option>Bảo trì</option></select></div>
                <div class="form-group" style="grid-column:1/-1"><label>Ghi chú</label><textarea name="ghiChu">${room.ghiChu}</textarea></div>
            </div><br>
            <button class="btn" type="submit">Lưu</button>
            <a class="btn secondary" href="${pageContext.request.contextPath}/admin/rooms">Quay lại</a>
        </form>
    </div>
</main>
<%@ include file="../include/footer.jsp" %>
