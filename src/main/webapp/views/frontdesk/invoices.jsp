<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<main class="container">
    <div class="section-title"><h2>Hóa đơn &amp; thanh toán</h2><span class="muted">Danh sách hóa đơn đã lập cho khách</span></div>

    <c:if test="${param.success == '1'}"><div class="alert success">Xác nhận thanh toán thành công.</div></c:if>
    <c:if test="${param.error == '1'}"><div class="alert error">Không thể xác nhận thanh toán cho hóa đơn này.</div></c:if>

    <form class="form" method="get" action="${pageContext.request.contextPath}/invoices">
        <div class="form-grid">
            <div class="form-group">
                <label>Từ khóa</label>
                <input type="text" name="keyword" value="${param.keyword}" placeholder="Tên khách, số phòng...">
            </div>
            <div class="form-group">
                <label>Trạng thái</label>
                <select name="status">
                    <option value="">Tất cả</option>
                    <option value="Chưa thanh toán" ${param.status == 'Chưa thanh toán' ? 'selected' : ''}>Chưa thanh toán</option>
                    <option value="Đã thanh toán" ${param.status == 'Đã thanh toán' ? 'selected' : ''}>Đã thanh toán</option>
                </select>
            </div>
            <div class="form-group" style="justify-content:end"><button class="btn" type="submit">Lọc</button></div>
        </div>
    </form>
    <br>

    <c:choose>
        <c:when test="${empty invoices}">
            <div class="empty panel">Không có hóa đơn phù hợp.</div>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Mã HĐ</th>
                            <th>Khách hàng</th>
                            <th>Phòng</th>
                            <th>Ngày lập</th>
                            <th>Tổng tiền</th>
                            <th>Nhân viên lập</th>
                            <th>Trạng thái</th>
                            <th>Thanh toán</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="hd" items="${invoices}">
                            <tr>
                                <td>#${hd.id}</td>
                                <td>${hd.tenKhach}</td>
                                <td>${hd.soPhong}</td>
                                <td><fmt:formatDate value="${hd.ngayLap}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td class="price"><fmt:formatNumber value="${hd.tongTien}" type="number" groupingUsed="true"/> đ</td>
                                <td>${hd.tenNhanVien}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${hd.trangThai == 'Đã thanh toán'}"><span class="status">${hd.trangThai}</span></c:when>
                                        <c:otherwise><span class="status warn">${hd.trangThai}</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${hd.trangThai != 'Đã thanh toán'}">
                                        <form method="post" action="${pageContext.request.contextPath}/invoices" style="display:flex;gap:6px" onsubmit="return confirm('Xác nhận đã thu tiền hóa đơn #${hd.id}?')">
                                            <input type="hidden" name="id" value="${hd.id}">
                                            <select name="phuongThuc">
                                                <option value="Tiền mặt">Tiền mặt</option>
                                                <option value="Chuyển khoản">Chuyển khoản</option>
                                                <option value="Thẻ">Thẻ</option>
                                            </select>
                                            <button class="btn small ghost" type="submit">Thanh toán</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</main>
<%@ include file="../include/footer.jsp" %>