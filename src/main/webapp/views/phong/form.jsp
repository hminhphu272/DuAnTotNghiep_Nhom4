<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.hotel.entity.*" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Form phòng</title></head>
<body>
<%@ include file="../common/menu.jspf" %>
<%
    Phong room = (Phong) request.getAttribute("room");
    List<LoaiPhong> types = (List<LoaiPhong>) request.getAttribute("types");
    boolean edit = room != null;
%>
<h2><%=edit ? "Sửa phòng" : "Thêm phòng"%></h2>
<form method="post">
    <% if (edit) { %><input type="hidden" name="id" value="<%=room.getId()%>"><% } %>
    Số phòng:<br>
    <input name="soPhong" value="<%=edit ? room.getSoPhong() : ""%>" required><br><br>
    Loại phòng:<br>
    <select name="loaiPhongId">
        <% if (types != null) for (LoaiPhong lp : types) { %>
            <option value="<%=lp.getId()%>" <%=edit && room.getLoaiPhongId()==lp.getId() ? "selected" : ""%>><%=lp.getTenLoai()%></option>
        <% } %>
    </select><br><br>
    Giá phòng:<br>
    <input name="giaPhong" value="<%=edit ? room.getGiaPhong() : ""%>" required><br><br>
    Trạng thái:<br>
    <select name="trangThai">
        <option value="TRONG">TRONG</option>
        <option value="DA_DAT">DA_DAT</option>
        <option value="DANG_SU_DUNG">DANG_SU_DUNG</option>
        <option value="BAO_TRI">BAO_TRI</option>
    </select><br><br>
    Ghi chú:<br>
    <input name="ghiChu" value="<%=edit ? room.getGhiChu() : ""%>"><br><br>
    <button type="submit">Lưu</button>
</form>
</body>
</html>
