<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="include/header.jsp" %>
<section class="hero">
    <div class="hero-inner">
        <div>
            <span class="eyebrow">Nhom 4 hotel</span>
            <h1>Quản lý khách sạn rõ ràng, đặt phòng nhanh và dễ dùng mỗi ngày</h1>
            <p>Giao diện được làm gọn hơn theo phong cách website thật: màu sáng, điểm nhấn xanh ngọc và khối thông tin rõ ràng. Hệ thống hỗ trợ khách tìm phòng, đặt phòng, lễ tân xử lý nhận trả phòng và quản lý theo dõi báo cáo.</p>
            <div class="pill-row">
                <a class="btn" href="${pageContext.request.contextPath}/rooms">Xem phòng</a>
                <a class="btn secondary" href="${pageContext.request.contextPath}/login">Đăng nhập hệ thống</a>
            </div>
        </div>
        <div class="hero-panel">
            <span class="sale-badge">Hệ thống quản lý khách sạn</span>
            <h2>Nhóm 4 Hotel</h2>
            <div class="panel-features">
                <span>✓ Tìm phòng theo ngày</span>
                <span>✓ Quản lý đặt phòng</span>
                <span>✓ Giao diện dễ nhìn</span>
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
        <a class="quick-link" href="${pageContext.request.contextPath}/rooms"><div>🛏️</div><div><b>Xem danh sách phòng</b><span>Tìm nhanh phòng đang mở bán</span></div></a>
        <a class="quick-link" href="${pageContext.request.contextPath}/bookings"><div>📋</div><div><b>Quản lý đặt phòng</b><span>Theo dõi đơn đặt và trạng thái</span></div></a>
        <a class="quick-link" href="${pageContext.request.contextPath}/admin/reports"><div>📊</div><div><b>Báo cáo hoạt động</b><span>Dành cho quản lý khách sạn</span></div></a>
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
