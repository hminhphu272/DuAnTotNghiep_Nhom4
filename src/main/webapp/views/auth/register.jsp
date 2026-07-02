<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="login-box form">
        <h2>Đăng ký khách hàng</h2>
        <c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/register">
            <div class="form-group"><label>Họ tên</label><input name="hoTen" required></div><br>
            <div class="form-group"><label>Email</label><input type="email" name="email" required></div><br>
            <div class="form-group"><label>Số điện thoại</label><input name="phone"></div><br>
            <div class="form-group"><label>Mật khẩu</label><input type="password" name="password" required></div><br>
            <button class="btn" type="submit">Đăng ký</button>
        </form>
    </div>
</main>
<%@ include file="../include/footer.jsp" %>
