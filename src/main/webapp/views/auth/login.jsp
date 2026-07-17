<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="detail">
        <div class="hero-panel">
            <span class="sale-badge">Đăng nhập thành viên</span>
            <h2>Chào mừng trở lại</h2>
            <p>Đăng nhập để đặt phòng nhanh, theo dõi đơn đặt phòng và nhận ưu đãi dành riêng cho thành viên Nhóm 4 Hotel.</p>
            <div class="panel-features">
                <span>✓ Đặt phòng chỉ trong vài giây</span>
                <span>✓ Theo dõi trạng thái đơn theo thời gian thực</span>
                <span>✓ Ưu đãi độc quyền cho thành viên</span>
            </div>
        </div>

        <div class="login-box form" style="margin:0">
            <h2>Đăng nhập</h2>
            <c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
            <c:if test="${not empty success}"><div class="alert success">${success}</div></c:if>
            <form method="post" action="${pageContext.request.contextPath}/login">
                <input type="hidden" name="returnUrl" value="${returnUrl}">
                <div class="form-group"><label>Email</label><input type="email" name="email" required></div><br>
                <div class="form-group"><label>Mật khẩu</label><input type="password" name="password" required></div><br>
                <button class="btn" type="submit">Đăng nhập</button>
                <a class="btn secondary" href="${pageContext.request.contextPath}/register">Tạo tài khoản</a>
            </form>
        </div>
    </div>
</main>
<%@ include file="../include/footer.jsp" %>