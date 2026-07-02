<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <c:if test="${empty room}"><div class="empty panel">Không tìm thấy phòng.</div></c:if>
    <c:if test="${not empty room}">
        <div class="detail">
            <div class="card"><img class="room-img" style="height:360px" src="${pageContext.request.contextPath}/images/room-${(room.id % 3) + 1}.svg" alt="Phòng ${room.soPhong}"></div>
            <div class="panel">
                <span class="status">${room.trangThai}</span>
                <h2>Phòng ${room.soPhong} - ${room.tenLoai}</h2>
                <p>${room.moTaLoai}</p>
                <p>Sức chứa: <b>${room.sucChua} khách</b></p>
                <p>Ghi chú: ${room.ghiChu}</p>
                <p class="price"><fmt:formatNumber value="${room.giaPhong}" type="number" groupingUsed="true"/> đ/đêm</p>
                <c:choose>
                    <c:when test="${empty sessionScope.authUser}"><a class="btn" href="${pageContext.request.contextPath}/login">Đăng nhập để đặt phòng</a></c:when>
                    <c:otherwise><a class="btn" href="${pageContext.request.contextPath}/booking/create?roomId=${room.id}">Đặt phòng ngay</a></c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:if>
</main>
<%@ include file="../include/footer.jsp" %>
