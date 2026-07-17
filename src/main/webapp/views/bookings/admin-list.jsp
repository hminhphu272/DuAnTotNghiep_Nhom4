<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Quản lý đặt phòng</h2><span class="muted">Toàn bộ đơn đặt phòng của khách hàng</span></div>

    <form class="form" method="get" action="${pageContext.request.contextPath}/admin/bookings">
        <div class="form-grid">
            <div class="form-group">
                <label>Từ khóa</label>
                <input type="text" name="keyword" value="${param.keyword}" placeholder="Tên khách, email, số phòng...">
            </div>
            <div class="form-group">
                <label>Trạng thái</label>
                <select name="status">
                    <option value="">Tất cả</option>
                    <option value="Chờ xác nhận" ${param.status == 'Chờ xác nhận' ? 'selected' : ''}>Chờ xác nhận</option>
                    <option value="Đã xác nhận" ${param.status == 'Đã xác nhận' ? 'selected' : ''}>Đã xác nhận</option>
                    <option value="Đã nhận phòng" ${param.status == 'Đã nhận phòng' ? 'selected' : ''}>Đã nhận phòng</option>
                    <option value="Đã thanh toán" ${param.status == 'Đã thanh toán' ? 'selected' : ''}>Đã thanh toán</option>
                    <option value="Đã hủy" ${param.status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
                </select>
            </div>
            <div class="form-group" style="justify-content:end"><button class="btn" type="submit">Lọc</button></div>
        </div>
    </form>
    <br>

    <c:choose>
        <c:when test="${empty bookings}">
            <div class="empty panel">Không có đơn đặt phòng phù hợp.</div>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Mã đơn</th>
                            <th>Khách hàng</th>
                            <th>Email</th>
                            <th>Phòng</th>
                            <th>Loại phòng</th>
                            <th>Ngày nhận</th>
                            <th>Ngày trả</th>
                            <th>Trạng thái</th>
                            <th>Đổi trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="b" items="${bookings}">
                            <tr>
                                <td>#${b.id}</td>
                                <td>${b.hoTenKhach}</td>
                                <td>${b.emailKhach}</td>
                                <td>${b.soPhong}</td>
                                <td>${b.tenLoai}</td>
                                <td><fmt:formatDate value="${b.ngayNhan}" pattern="dd/MM/yyyy"/></td>
                                <td><fmt:formatDate value="${b.ngayTra}" pattern="dd/MM/yyyy"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${b.trangThai == 'Đã hủy'}"><span class="status warn">${b.trangThai}</span></c:when>
                                        <c:otherwise><span class="status">${b.trangThai}</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/admin/bookings/status" style="display:flex;gap:6px">
                                        <input type="hidden" name="id" value="${b.id}">
                                        <select name="trangThai">
                                            <option value="Chờ xác nhận" ${b.trangThai == 'Chờ xác nhận' ? 'selected' : ''}>Chờ xác nhận</option>
                                            <option value="Đã xác nhận" ${b.trangThai == 'Đã xác nhận' ? 'selected' : ''}>Đã xác nhận</option>
                                            <option value="Đã nhận phòng" ${b.trangThai == 'Đã nhận phòng' ? 'selected' : ''}>Đã nhận phòng</option>
                                            <option value="Đã thanh toán" ${b.trangThai == 'Đã thanh toán' ? 'selected' : ''}>Đã thanh toán</option>
                                            <option value="Đã hủy" ${b.trangThai == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
                                        </select>
                                        <button class="btn small" type="submit">Lưu</button>
                                    </form>
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