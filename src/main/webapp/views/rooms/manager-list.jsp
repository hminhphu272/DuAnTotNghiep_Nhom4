<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title">
        <h2>Quản lý phòng</h2>
        <span class="muted">Thêm, sửa, xóa và theo dõi trạng thái từng phòng</span>
    </div>

    <c:if test="${not empty error}">
        <div class="empty panel" style="color:#a93131">${error}</div><br>
    </c:if>

    <form class="form" method="get" action="${pageContext.request.contextPath}/admin/rooms">
        <div class="form-grid">
            <div class="form-group">
                <label>Từ khóa</label>
                <input type="text" name="keyword" value="${param.keyword}" placeholder="Số phòng, loại phòng, ghi chú...">
            </div>
            <div class="form-group">
                <label>Loại phòng</label>
                <select name="loaiId">
                    <option value="">Tất cả</option>
                    <c:forEach var="type" items="${types}">
                        <option value="${type.id}" ${param.loaiId == type.id ? 'selected' : ''}>${type.tenLoai}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Trạng thái</label>
                <select name="status">
                    <option value="">Tất cả</option>
                    <option value="Trống" ${param.status == 'Trống' ? 'selected' : ''}>Trống</option>
                    <option value="Đã đặt" ${param.status == 'Đã đặt' ? 'selected' : ''}>Đã đặt</option>
                    <option value="Đang sử dụng" ${param.status == 'Đang sử dụng' ? 'selected' : ''}>Đang sử dụng</option>
                    <option value="Bảo trì" ${param.status == 'Bảo trì' ? 'selected' : ''}>Bảo trì</option>
                </select>
            </div>
            <div class="form-group" style="justify-content:end"><button class="btn" type="submit">Lọc</button></div>
        </div>
    </form>
    <br>

    <a class="btn" href="${pageContext.request.contextPath}/admin/rooms/form">+ Thêm phòng mới</a>
    <br><br>

    <c:choose>
        <c:when test="${empty rooms}">
            <div class="empty panel">Không có phòng phù hợp.</div>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Số phòng</th>
                            <th>Loại phòng</th>
                            <th>Sức chứa</th>
                            <th>Giá phòng</th>
                            <th>Trạng thái</th>
                            <th>Ghi chú</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="room" items="${rooms}">
                            <tr>
                                <td>${room.soPhong}</td>
                                <td>${room.tenLoai}</td>
                                <td>${room.sucChua} khách</td>
                                <td><fmt:formatNumber value="${room.giaPhong}" type="number" groupingUsed="true"/> đ</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${room.trangThai == 'Trống'}"><span class="status">${room.trangThai}</span></c:when>
                                        <c:when test="${room.trangThai == 'Bảo trì'}"><span class="status bad">${room.trangThai}</span></c:when>
                                        <c:otherwise><span class="status warn">${room.trangThai}</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${room.ghiChu}</td>
                                <td class="actions">
                                    <a class="btn small secondary" href="${pageContext.request.contextPath}/admin/rooms/form?id=${room.id}">Sửa</a>
                                    <a class="btn small danger" href="${pageContext.request.contextPath}/admin/rooms/delete?id=${room.id}" onclick="return confirm('Xóa phòng ${room.soPhong}?');">Xóa</a>
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