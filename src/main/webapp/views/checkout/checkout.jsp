<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="panel">
        <h2>Trả phòng và thanh toán</h2>
        <c:if test="${empty booking}"><div class="empty">Không tìm thấy đặt phòng.</div></c:if>
        <c:if test="${not empty booking}">
            <p>Khách hàng: <b>${booking.hoTenKhach}</b></p>
            <p>Phòng: <b>${booking.soPhong}</b> - ${booking.tenLoai}</p>
            <p>Thời gian: <fmt:formatDate value="${booking.ngayNhan}" pattern="dd/MM/yyyy"/> đến <fmt:formatDate value="${booking.ngayTra}" pattern="dd/MM/yyyy"/></p>
            <p class="price">Tổng tiền: <fmt:formatNumber value="${total}" type="number" groupingUsed="true"/> đ</p>
            <form method="post" action="${pageContext.request.contextPath}/checkout">
                <input type="hidden" name="bookingId" value="${booking.id}">
                <div class="form-group"><label>Phương thức thanh toán</label><select name="phuongThuc"><option>Tiền mặt</option><option>Chuyển khoản</option></select></div><br>
                <button class="btn" type="submit">Lập hóa đơn</button>
            </form>
        </c:if>
    </div>
</main>
<%@ include file="../include/footer.jsp" %>
