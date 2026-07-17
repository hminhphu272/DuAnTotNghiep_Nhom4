<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Đặt phòng của tôi</h2><span class="muted">Danh sách các đơn đặt phòng bạn đã tạo</span></div>

    <c:choose>
        <c:when test="${empty bookings}">
            <div class="empty panel">Bạn chưa có đơn đặt phòng nào. <a href="${pageContext.request.contextPath}/rooms">Đặt phòng ngay</a>.</div>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Phòng</th>
                            <th>Loại phòng</th>
                            <th>Ngày nhận</th>
                            <th>Ngày trả</th>
                            <th>Giá/đêm</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="b" items="${bookings}">
                            <tr>
                                <td>${b.soPhong}</td>
                                <td>${b.tenLoai}</td>
                                <td><fmt:formatDate value="${b.ngayNhan}" pattern="dd/MM/yyyy"/></td>
                                <td><fmt:formatDate value="${b.ngayTra}" pattern="dd/MM/yyyy"/></td>
                                <td class="price"><fmt:formatNumber value="${b.giaPhong}" type="number" groupingUsed="true"/> đ</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${b.trangThai == 'Đã hủy'}"><span class="status warn">${b.trangThai}</span></c:when>
                                        <c:otherwise><span class="status">${b.trangThai}</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${b.trangThai == 'Chờ xác nhận'}">
                                        <form method="post" action="${pageContext.request.contextPath}/booking/status" onsubmit="return confirm('Hủy đơn đặt phòng này?')">
                                            <input type="hidden" name="id" value="${b.id}">
                                            <button class="btn small warn" type="submit">Hủy đơn</button>
                                        </form>
                                    </c:if>
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