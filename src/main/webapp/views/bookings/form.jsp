<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="form">
        <h2>Đặt phòng</h2>
        <c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
        <c:if test="${empty room}"><div class="empty">Không tìm thấy phòng.</div></c:if>
        <c:if test="${not empty room}">
            <p>Phòng <b>${room.soPhong}</b> - ${room.tenLoai}. Giá: <b><fmt:formatNumber value="${room.giaPhong}" type="number" groupingUsed="true"/> đ/đêm</b></p>
            <form method="post" action="${pageContext.request.contextPath}/booking/create">
                <input type="hidden" name="roomId" value="${room.id}">
                <div class="form-grid">
                    <div class="form-group"><label>Ngày nhận</label><input type="date" name="ngayNhan" required></div>
                    <div class="form-group"><label>Ngày trả</label><input type="date" name="ngayTra" required></div>
                </div><br>
                <button class="btn" type="submit">Xác nhận đặt phòng</button>
            </form>
        </c:if>
    </div>
</main>
<%@ include file="../include/footer.jsp" %>
