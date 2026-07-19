<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="include/header.jsp" %>
<section class="hero">
    <div class="hero-inner">
        <div>
            <span class="eyebrow">Pleiades hotel</span>
            <h1>Không gian nghỉ dưỡng tiện nghi, dịch vụ lưu trú chuyên nghiệp</h1>
            <p>Tọa lạc tại vị trí đắc địa với lối kiến trúc hiện đại, Pleiades Hotel mang đến không gian lưu trú lý tưởng cho cả khách du lịch và khách công tác. Chúng tôi cam kết cung cấp hệ thống phòng nghỉ đa dạng, không gian yên tĩnh cùng dịch vụ chăm sóc khách hàng tận tâm, chu đáo trong suốt kỳ nghỉ của bạn.</p>
            <div class="pill-row">
                <a class="btn" href="${pageContext.request.contextPath}/rooms">Xem phòng</a>
                <a class="btn secondary" href="${pageContext.request.contextPath}/login">Đăng nhập hệ thống</a>
            </div>
        </div>
        <div class="hero-panel">
            <span class="sale-badge">Ưu đãi đặt phòng trực tuyến</span>
            <h2>Pleiades Hotel</h2>
            <div class="panel-features">
				<span>✓ Miễn phí bữa sáng theo tiêu chuẩn</span>
                <span>✓ Hỗ trợ nhận/trả phòng nhanh chóng</span>
                <span>✓ Dịch vụ lễ tân & an ninh trực 24/7</span>
            </div>
            <a class="btn ghost" href="${pageContext.request.contextPath}/rooms">Xem danh sách phòng</a>
            <div class="panel-stats">
                <div class="mini-stat"><strong>${rooms.size()}</strong><span>Phòng hiển thị</span></div>
                <div class="mini-stat"><strong>${types.size()}</strong><span>Loại phòng</span></div>
            </div>
        </div>
    </div>
</section>
<form class="search-box" action="${pageContext.request.contextPath}/rooms" method="get">
    <div><label>Ngày nhận</label><input type="date" name="ngayNhan"></div>
    <div><label>Ngày trả</label><input type="date" name="ngayTra"></div>
    <div><label>Loại phòng</label><select name="loaiId"><option value="">Tất cả</option><c:forEach var="type" items="${types}"><option value="${type.id}">${type.tenLoai}</option></c:forEach></select></div>
    <div style="display:flex;align-items:end"><button class="btn" type="submit">Tìm phòng</button></div>
</form>

<main class="container">
    <div class="quick-links">
    <div class="quick-link">
        <div>🛏️</div>
        <div>
            <b>Không gian yên tĩnh</b>
            <span>Hệ thống phòng cách âm, trang thiết bị hiện đại tiêu chuẩn</span>
        </div>
    </div>

    <div class="quick-link">
        <div>📋</div>
        <div>
            <b>Thủ tục nhanh chóng</b>
            <span>Nhận và trả phòng tối giản, tiết kiệm thời gian của bạn</span>
        </div>
    </div>

    <div class="quick-link">
        <div>🔑</div>
        <div>
            <b>An ninh tuyệt đối</b>
            <span>Hệ thống khóa từ thông minh và đội ngũ trực ban 24/7</span>
        </div>
    </div>
</div>

    <div class="section-title"><h2>Phòng nổi bật</h2><a href="${pageContext.request.contextPath}/rooms">Xem tất cả</a></div>
    <div class="grid">
        <c:forEach var="room" items="${rooms}" varStatus="st" begin="0" end="5">
            <div class="card">
                <img class="room-img" src="${pageContext.request.contextPath}/images/room-${(st.index % 3) + 1}.svg" alt="Phòng ${room.soPhong}">
                <div class="card-body">
                    <span class="status">${room.trangThai}</span>
                    <h3>Phòng ${room.soPhong} - ${room.tenLoai}</h3>
                    <p class="muted">Sức chứa ${room.sucChua} khách. ${room.moTaLoai}</p>
                    <p class="price"><fmt:formatNumber value="${room.giaPhong}" type="number" groupingUsed="true"/> đ/đêm</p>
                    <a class="btn" href="${pageContext.request.contextPath}/room/detail?id=${room.id}">Xem chi tiết</a>
                </div>
            </div>
        </c:forEach>
    </div>
</main>
<%@ include file="include/footer.jsp" %>
