<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Quản lý loại phòng</h2><span class="muted">Thêm, sửa, khóa loại phòng</span></div>
    <div class="detail">
        <div class="form">
            <h3>${empty edit ? 'Thêm loại phòng' : 'Sửa loại phòng'}</h3>
            <form method="post" action="${pageContext.request.contextPath}/admin/types/save">
                <input type="hidden" name="id" value="${edit.id}">
                <div class="form-group"><label>Tên loại</label><input name="tenLoai" value="${edit.tenLoai}" required></div><br>
                <div class="form-group"><label>Mô tả</label><textarea name="moTa">${edit.moTa}</textarea></div><br>
                <div class="form-grid"><div class="form-group"><label>Sức chứa</label><input type="number" name="sucChua" value="${edit.sucChua}" required></div><div class="form-group"><label>Giá cơ bản</label><input type="number" name="giaCoBan" value="${edit.giaCoBan}" required></div></div><br>
                <div class="form-group"><label>Trạng thái</label><select name="trangThai"><option value="1">Hoạt động</option><option value="0">Ngừng</option></select></div><br>
                <button class="btn" type="submit">Lưu</button>
            </form>
        </div>
        <div class="table-wrap"><table>
            <thead><tr><th>ID</th><th>Tên loại</th><th>Sức chứa</th><th>Giá</th><th>Trạng thái</th><th></th></tr></thead>
            <tbody><c:forEach var="t" items="${types}"><tr><td>${t.id}</td><td>${t.tenLoai}</td><td>${t.sucChua}</td><td><fmt:formatNumber value="${t.giaCoBan}" type="number" groupingUsed="true"/> đ</td><td>${t.trangThai ? 'Hoạt động' : 'Ngừng'}</td><td><a class="btn small" href="${pageContext.request.contextPath}/admin/types?editId=${t.id}">Sửa</a> <a class="btn small danger" href="${pageContext.request.contextPath}/admin/types/delete?id=${t.id}">Khóa</a></td></tr></c:forEach></tbody>
        </table></div>
    </div>
</main>
<%@ include file="../include/footer.jsp" %>
