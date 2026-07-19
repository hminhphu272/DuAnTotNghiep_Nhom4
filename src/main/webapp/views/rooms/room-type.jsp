<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title">
        <h2>Quản lý loại phòng</h2>
        <span class="muted">Thêm, sửa và ẩn/hiện loại phòng</span>
    </div>

    <div class="form">
        <h3 style="margin-top:0">${empty editType ? 'Thêm loại phòng mới' : 'Cập nhật loại phòng'}</h3>
        <form method="post" action="${pageContext.request.contextPath}/admin/types/save">
            <c:if test="${not empty editType}"><input type="hidden" name="id" value="${editType.id}"></c:if>
            <div class="form-grid">
                <div class="form-group">
                    <label>Tên loại phòng</label>
                    <input type="text" name="tenLoai" value="${editType.tenLoai}" required maxlength="100" placeholder="VD: Deluxe Moon">
                </div>
                <div class="form-group">
                    <label>Sức chứa (khách)</label>
                    <input type="number" name="sucChua" value="${editType.sucChua}" min="1" required>
                </div>
                <div class="form-group">
                    <label>Giá cơ bản (đ/đêm)</label>
                    <input type="number" name="giaCoBan" value="${editType.giaCoBan}" min="0" step="1000" required>
                </div>
                <div class="form-group">
                    <label>Trạng thái</label>
                    <label style="display:flex;align-items:center;gap:8px;font-weight:400">
                        <input type="checkbox" name="trangThai" value="1" style="width:auto"
                               ${(empty editType || editType.trangThai) ? 'checked' : ''}>
                        Đang hoạt động (hiển thị cho khách)
                    </label>
                </div>
                <div class="form-group" style="grid-column:1/-1">
                    <label>Mô tả</label>
                    <textarea name="moTa" maxlength="255">${editType.moTa}</textarea>
                </div>
            </div>
            <br>
            <button class="btn" type="submit">${empty editType ? 'Thêm mới' : 'Lưu thay đổi'}</button>
            <c:if test="${not empty editType}">
                <a class="btn secondary" href="${pageContext.request.contextPath}/admin/types">Hủy</a>
            </c:if>
        </form>
    </div>
    <br>

    <c:choose>
        <c:when test="${empty types}">
            <div class="empty panel">Chưa có loại phòng nào.</div>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Tên loại</th>
                            <th>Mô tả</th>
                            <th>Sức chứa</th>
                            <th>Giá cơ bản</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${types}">
                            <tr>
                                <td>${t.tenLoai}</td>
                                <td>${t.moTa}</td>
                                <td>${t.sucChua} khách</td>
                                <td><fmt:formatNumber value="${t.giaCoBan}" type="number" groupingUsed="true"/> đ</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${t.trangThai}"><span class="status">Đang hoạt động</span></c:when>
                                        <c:otherwise><span class="status bad">Đã ẩn</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="actions">
                                    <a class="btn small secondary" href="${pageContext.request.contextPath}/admin/types?id=${t.id}">Sửa</a>
                                    <a class="btn small danger" href="${pageContext.request.contextPath}/admin/types/delete?id=${t.id}" onclick="return confirm('Ẩn loại phòng &quot;${t.tenLoai}&quot;?');">Ẩn</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</main>
<%@ include file="../include/footer.jsp" %>