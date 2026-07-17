<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DuAnTotNghiep_Nhom4 Hotel</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=3">
</head>
<body>

<div class="top-strip">
    <div class="top-strip-inner">
        <span>Đặt phòng nhanh</span>
        <span>Phòng sạch, gọn, hiện đại</span>
        <span>Lễ tân xử lý nhanh</span>
        <span>Báo cáo rõ ràng cho quản lý</span>
    </div>
</div>

<header class="site-header">
    <div class="nav">
        <a class="brand" href="${pageContext.request.contextPath}/home">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="Logo">
            <span>Nhóm 4 Hotel</span>
        </a>

        <nav class="menu">
            <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
            <a href="${pageContext.request.contextPath}/rooms">Phòng</a>

            <c:if test="${not empty sessionScope.authUser}">
                <a href="${pageContext.request.contextPath}/bookings">Đặt phòng</a>
            </c:if>

            <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.vaiTro == 'Quản lý'}">
                <a href="${pageContext.request.contextPath}/admin/rooms">Quản lý phòng</a>
                <a href="${pageContext.request.contextPath}/admin/bookings">Quản lý đặt phòng</a>
                <a href="${pageContext.request.contextPath}/admin/types">Loại phòng</a>
                <a href="${pageContext.request.contextPath}/admin/reports">Báo cáo</a>
            </c:if>

            <c:if test="${not empty sessionScope.authUser && (sessionScope.authUser.vaiTro == 'Lễ tân' || sessionScope.authUser.vaiTro == 'Quản lý')}">
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