<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Quản lý đặt phòng</h2><a class="btn secondary" href="${pageContext.request.contextPath}/rooms">Đặt thêm phòng</a></div>
    <c:if test="${sessionScope.authUser.vaiTro == 'Lễ tân' || sessionScope.authUser.vaiTro == 'Quản lý'}">
        <form class="form" method="get" action="${pageContext.request.contextPath}/bookings">
            <div class="form-grid">
                <div class="form-group"><label>Từ khóa</label><input name="keyword" value="${param.keyword}" placeholder="Tên khách, email, số phòng"></div>
                <div class="form-group"><label>Trạng thái</label><select name="status"><option value="">Tất cả</option><option>Chờ xác nhận</option><option>Đã xác nhận</option><option>Đã nhận phòng</option><option>Đã hủy</option><option>Đã thanh toán</option></select></div>
            </div><br><button class="btn" type="submit">Tìm kiếm</button>
        </form><br>
    </c:if>
    <div class="table-wrap">
        <table>
            <thead><tr><th>Mã</th><th>Khách hàng</th><th>Phòng</th><th>Ngày nhận</th><th>Ngày trả</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
            <tbody>
            <c:forEach var="b" items="${bookings}">
                <tr>
                    <td>#${b.id}</td>
                    <td>${b.hoTenKhach}<br><span class="muted">${b.emailKhach}</span></td>
                    <td>${b.soPhong}<br><span class="muted">${b.tenLoai}</span></td>
                    <td><fmt:formatDate value="${b.ngayNhan}" pattern="dd/MM/yyyy"/></td>
                    <td><fmt:formatDate value="${b.ngayTra}" pattern="dd/MM/yyyy"/></td>
                    <td><span class="status">${b.trangThai}</span></td>
                    <td>
                        <div class="actions">
                            <c:if test="${(sessionScope.authUser.vaiTro == 'Lễ tân' || sessionScope.authUser.vaiTro == 'Quản lý') && b.trangThai == 'Chờ xác nhận'}">
                                <form method="post" action="${pageContext.request.contextPath}/booking/status"><input type="hidden" name="id" value="${b.id}"><input type="hidden" name="action" value="confirm"><button class="btn small" type="submit">Xác nhận</button></form>
                            </c:if>
                            <c:if test="${(sessionScope.authUser.vaiTro == 'Lễ tân' || sessionScope.authUser.vaiTro == 'Quản lý') && b.trangThai == 'Đã xác nhận'}">
                                <form method="post" action="${pageContext.request.contextPath}/booking/status"><input type="hidden" name="id" value="${b.id}"><input type="hidden" name="action" value="checkin"><button class="btn small warn" type="submit">Nhận phòng</button></form>
                            </c:if>
                            <c:if test="${(sessionScope.authUser.vaiTro == 'Lễ tân' || sessionScope.authUser.vaiTro == 'Quản lý') && b.trangThai == 'Đã nhận phòng'}">
                                <a class="btn small" href="${pageContext.request.contextPath}/checkout?id=${b.id}">Trả phòng</a>
                            </c:if>
                            <c:if test="${b.trangThai == 'Chờ xác nhận' || b.trangThai == 'Đã xác nhận'}">
                                <form method="post" action="${pageContext.request.contextPath}/booking/status"><input type="hidden" name="id" value="${b.id}"><input type="hidden" name="action" value="cancel"><button class="btn small danger" type="submit">Hủy</button></form>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
<%@ include file="../include/footer.jsp" %>
