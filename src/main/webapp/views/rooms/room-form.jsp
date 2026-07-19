<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title">
        <h2>${empty room ? 'Thêm phòng mới' : 'Cập nhật phòng'}</h2>
        <span class="muted">Nhập đầy đủ thông tin phòng</span>
    </div>

    <div class="form">
        <form method="post" action="${pageContext.request.contextPath}/admin/rooms/save">
            <c:if test="${not empty room}"><input type="hidden" name="id" value="${room.id}"></c:if>
            <div class="form-grid">
                <div class="form-group">
                    <label>Số phòng</label>
                    <input type="text" name="soPhong" value="${room.soPhong}" required maxlength="20" placeholder="VD: 101">
                </div>
                <div class="form-group">
                    <label>Loại phòng</label>
                    <select name="loaiPhongId" required>
                        <c:forEach var="type" items="${types}">
                            <option value="${type.id}" ${room.loaiPhongId == type.id ? 'selected' : ''}>${type.tenLoai}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Giá phòng (đ/đêm)</label>
                    <input type="number" name="giaPhong" value="${room.giaPhong}" min="0" step="1000" required>
                </div>
                <div class="form-group">
                    <label>Trạng thái</label>
                    <select name="trangThai" required>
                        <option value="Trống" ${(empty room || room.trangThai == 'Trống') ? 'selected' : ''}>Trống</option>
                        <option value="Đã đặt" ${room.trangThai == 'Đã đặt' ? 'selected' : ''}>Đã đặt</option>
                        <option value="Đang sử dụng" ${room.trangThai == 'Đang sử dụng' ? 'selected' : ''}>Đang sử dụng</option>
                        <option value="Bảo trì" ${room.trangThai == 'Bảo trì' ? 'selected' : ''}>Bảo trì</option>
                    </select>
                </div>
                <div class="form-group" style="grid-column:1/-1">
                    <label>Ghi chú</label>
                    <textarea name="ghiChu" maxlength="255">${room.ghiChu}</textarea>
                </div>
            </div>
            <br>
            <button class="btn" type="submit">${empty room ? 'Thêm phòng' : 'Lưu thay đổi'}</button>
            <a class="btn secondary" href="${pageContext.request.contextPath}/admin/rooms">Hủy</a>
        </form>
    </div>
</main>
<%@ include file="../include/footer.jsp" %>