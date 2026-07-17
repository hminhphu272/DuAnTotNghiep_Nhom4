<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="detail">
        <div class="hero-panel">
            <span class="sale-badge">Thành viên mới</span>
            <h2>Tạo tài khoản Nhóm 4 Hotel</h2>
            <p>Chỉ mất chưa đầy 1 phút. Sau khi đăng ký, bạn có thể đặt phòng và quản lý đơn của mình ngay.</p>
            <div class="panel-features">
                <span>✓ Miễn phí, không mất phí thành viên</span>
                <span>✓ Lưu lại lịch sử đặt phòng</span>
                <span>✓ Hủy đơn dễ dàng khi cần</span>
            </div>
        </div>

        <div class="login-box form" style="margin:0">
            <h2>Đăng ký khách hàng</h2>
            <c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
            <form method="post" action="${pageContext.request.contextPath}/register">
                <div class="form-group"><label>Họ tên</label><input name="hoTen" required></div><br>
                <div class="form-group"><label>Email</label><input type="email" name="email" required></div><br>
                <div class="form-group"><label>Số điện thoại</label><input name="phone"></div><br>
                <div class="form-group"><label>Mật khẩu</label><input type="password" name="password" required></div><br>
                <button class="btn" type="submit">Đăng ký</button>
                <a class="btn secondary" href="${pageContext.request.contextPath}/login">Đã có tài khoản</a>
            </form>
        </div>
    </div>
</main>
<%@ include file="../include/footer.jsp" %>