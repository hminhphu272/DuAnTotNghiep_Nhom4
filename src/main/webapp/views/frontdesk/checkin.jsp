<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Nhận phòng</h2><span class="muted">Các đơn đã xác nhận, đang chờ khách đến nhận phòng</span></div>

    <c:if test="${param.success == '1'}"><div class="alert success">Nhận phòng thành công.</div></c:if>
    <c:if test="${param.error == '1'}"><div class="alert error">Không thể nhận phòng cho đơn này (đơn không tồn tại hoặc đã đổi trạng thái).</div></c:if>

    <form class="form" method="get" action="${pageContext.request.contextPath}/checkin">
        <div class="form-grid">
            <div class="form-group">
                <label>Từ khóa</label>
                <input type="text" name="keyword" value="${param.keyword}" placeholder="Tên khách, email, số phòng...">
            </div>
            <div class="form-group" style="justify-content:end"><button class="btn" type="submit">Tìm</button></div>
        </div>
    </form>
    <br>

    <c:choose>
        <c:when test="${empty bookings}">
            <div class="empty panel">Không có đơn nào đang chờ nhận phòng.</div>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Mã đơn</th>
                            <th>Khách hàng</th>
                            <th>Phòng</th>
                            <th>Loại phòng</th>
                            <th>Ngày nhận</th>
                            <th>Ngày trả</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="b" items="${bookings}">
                            <tr>
                                <td>#${b.id}</td>
                                <td>${b.hoTenKhach}</td>
                                <td>${b.soPhong}</td>
                                <td>${b.tenLoai}</td>
                                <td><fmt:formatDate value="${b.ngayNhan}" pattern="dd/MM/yyyy"/></td>
                                <td><fmt:formatDate value="${b.ngayTra}" pattern="dd/MM/yyyy"/></td>
                                <td><span class="status">${b.trangThai}</span></td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/checkin" onsubmit="return confirm('Xác nhận khách đã nhận phòng ${b.soPhong}?')">
                                        <input type="hidden" name="id" value="${b.id}">
                                        <button class="btn small" type="submit">Nhận phòng</button>
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