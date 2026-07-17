<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Đặt phòng</h2><span class="muted">Phòng ${room.soPhong} - ${room.tenLoai}</span></div>

    <c:if test="${not empty error}">
        <div class="error panel">${error}</div>
    </c:if>

    <div class="panel">
        <p><strong>Phòng:</strong> ${room.soPhong} (${room.tenLoai})</p>
        <p><strong>Giá:</strong> <fmt:formatNumber value="${room.giaPhong}" type="number" groupingUsed="true"/> đ/đêm</p>
        <p><strong>Sức chứa:</strong> ${room.sucChua} khách</p>
    </div>
    <br>

    <form class="form" method="post" action="${pageContext.request.contextPath}/booking/create">
        <input type="hidden" name="roomId" value="${room.id}">
        <div class="form-grid">
            <div class="form-group">
                <label>Ngày nhận phòng</label>
                <input type="date" name="ngayNhan" value="${ngayNhan}" required>
            </div>
            <div class="form-group">
                <label>Ngày trả phòng</label>
                <input type="date" name="ngayTra" value="${ngayTra}" required>
            </div>
            <div class="form-group" style="justify-content:end">
                <button class="btn" type="submit">Xác nhận đặt phòng</button>
            </div>
        </div>
    </form>
</main>
<%@ include file="../include/footer.jsp" %>