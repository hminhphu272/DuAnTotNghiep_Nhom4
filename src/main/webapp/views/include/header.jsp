<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pleiades Hotel</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=4">
</head>
<body>

<header class="site-header">
    <div class="nav">
        <a class="brand" href="${pageContext.request.contextPath}/home">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="Logo">
            <span>Pleiades Hotel</span>
        </a>

        <nav class="menu">
            <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
            <a href="${pageContext.request.contextPath}/rooms">Phòng</a>

            <c:if test="${not empty sessionScope.authUser}">
                <a href="${pageContext.request.contextPath}/bookings">Đặt phòng</a>
            </c:if>

            <%-- Khối menu dành riêng cho tài khoản Quản lý --%>
            <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.vaiTro == 'Quản lý'}">
                <a href="${pageContext.request.contextPath}/admin/rooms">Quản lý phòng</a>
                <a href="${pageContext.request.contextPath}/admin/bookings">Quản lý đặt phòng</a>
                <a href="${pageContext.request.contextPath}/admin/types">Loại phòng</a>
                
                <%-- Thêm mới đường dẫn Quản lý người dùng --%>
                <a href="${pageContext.request.contextPath}/admin/users">Quản lý người dùng</a>
                
                <%-- Giữ nguyên Báo cáo đồng bộ đường dẫn Servlet của bạn (/admin/reports) --%>
                <a href="${pageContext.request.contextPath}/admin/reports">Báo cáo thống kê</a>
            </c:if>

            <%-- Khối menu dành cho Lễ tân hoặc Quản lý --%>
            <c:if test="${not empty sessionScope.authUser && (sessionScope.authUser.vaiTro == 'Lễ tân' || sessionScope.authUser.vaiTro == 'Quản lý')}">
                <a href="${pageContext.request.contextPath}/checkin">Nhận phòng</a>
                <a href="${pageContext.request.contextPath}/checkout">Trả phòng</a>
                <a href="${pageContext.request.contextPath}/invoices">Hóa đơn</a>
            </c:if>

            <c:choose>
                <c:when test="${empty sessionScope.authUser}">
                    <a class="outline" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                    <a class="active" href="${pageContext.request.contextPath}/register">Đăng ký</a>
                </c:when>
                <c:otherwise>
                    <span class="hello">Xin chào, ${sessionScope.authUser.hoTen}</span>
                    <a class="outline" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </div>
</header>