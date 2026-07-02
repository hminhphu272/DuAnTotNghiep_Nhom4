<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Danh sách phòng</h2><span class="muted">Tìm kiếm và đặt phòng trực tuyến</span></div>
    <form class="form" method="get" action="${pageContext.request.contextPath}/rooms">
        <div class="form-grid">
            <div class="form-group"><label>Ngày nhận</label><input type="date" name="ngayNhan" value="${param.ngayNhan}"></div>
            <div class="form-group"><label>Ngày trả</label><input type="date" name="ngayTra" value="${param.ngayTra}"></div>
            <div class="form-group"><label>Loại phòng</label><select name="loaiId"><option value="">Tất cả</option><c:forEach var="type" items="${types}"><option value="${type.id}" ${param.loaiId == type.id ? 'selected' : ''}>${type.tenLoai}</option></c:forEach></select></div>
            <div class="form-group" style="justify-content:end"><button class="btn" type="submit">Lọc phòng</button></div>
        </div>
    </form><br>
    <c:choose>
        <c:when test="${empty rooms}"><div class="empty panel">Không có phòng phù hợp.</div></c:when>
        <c:otherwise>
            <div class="grid">
                <c:forEach var="room" items="${rooms}" varStatus="st">
                    <div class="card">
                        <img class="room-img" src="${pageContext.request.contextPath}/images/room-${(st.index % 3) + 1}.svg" alt="Phòng ${room.soPhong}">
                        <div class="card-body">
                            <span class="status">${room.trangThai}</span>
                            <h3>Phòng ${room.soPhong}</h3>
                            <p>${room.tenLoai} - tối đa ${room.sucChua} khách</p>
                            <p class="price"><fmt:formatNumber value="${room.giaPhong}" type="number" groupingUsed="true"/> đ/đêm</p>
                            <a class="btn" href="${pageContext.request.contextPath}/room/detail?id=${room.id}">Chi tiết</a>
                            <c:if test="${not empty sessionScope.authUser}"><a class="btn secondary" href="${pageContext.request.contextPath}/booking/create?roomId=${room.id}">Đặt phòng</a></c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</main>
<%@ include file="../include/footer.jsp" %>
