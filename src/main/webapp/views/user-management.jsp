<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">

<title>Quản Lý Người Dùng - Pleiades Hotel</title>

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<jsp:include page="include/header.jsp"/>

<div class="container">

    <div class="section-title">
        <div>
            <h2>Quản lý người dùng</h2>

            <p class="muted">
                Thêm, sửa, xóa tài khoản trong hệ thống
            </p>
        </div>
    </div>

    <!-- ================= FORM ================= -->

    <form
        action="${pageContext.request.contextPath}/admin/users"
        method="post"
        class="form">

        <input
            type="hidden"
            name="action"
            id="formAction"
            value="add">

        <input
            type="hidden"
            name="id"
            id="userId">

        <div class="form-grid">

            <div class="form-group">

                <label>Họ tên</label>

                <input
                    type="text"
                    name="hoTen"
                    id="hoTen"
                    required>

            </div>

            <div class="form-group">

                <label>Email</label>

                <input
                    type="email"
                    name="email"
                    id="email"
                    required>

            </div>

            <div class="form-group">

                <label>Mật khẩu</label>

                <input
                    type="password"
                    name="matKhau"
                    id="matKhau"
                    placeholder="Để trống sẽ dùng 123456">

            </div>

            <div class="form-group">

                <label>Số điện thoại</label>

                <input
                    type="text"
                    name="soDienThoai"
                    id="soDienThoai">

            </div>

            <div class="form-group">

                <label>Vai trò</label>

                <select
                    name="vaiTro"
                    id="vaiTro">

                    <option value="Quản lý">
                        Quản lý
                    </option>

                    <option value="Lễ tân">
                        Lễ tân
                    </option>

                    <option value="Khách hàng">
                        Khách hàng
                    </option>

                </select>

            </div>

            <div class="form-group">

    <label>Trạng thái</label>

    <label style="
        display:flex;
        align-items:center;
        gap:10px;
        font-weight:500;
        margin-top:12px;
        cursor:pointer;
    ">

        <input
            type="checkbox"
            id="trangThai"
            name="trangThai"
            checked
            style="
                width:18px;
                height:18px;
                margin:0;
                accent-color:#2563eb;
                flex:none;
            ">

        <span>Hoạt động</span>

    </label>

</div>

        </div>

        <div style="margin-top:20px">

            <button
                type="submit"
                id="btnSubmit"
                class="btn">

                Thêm thành viên

            </button>

            <button
                type="button"
                id="btnCancel"
                class="btn secondary"
                style="display:none"
                onclick="resetForm()">

                Hủy

            </button>

        </div>

    </form>

    <!-- ================= SEARCH ================= -->

<div style="margin-top:47px;">
    <form
        action="${pageContext.request.contextPath}/admin/users"
        method="get"
        class="search-box">

        <input
            type="text"
            name="keyword"
            value="${keyword}"
            placeholder="Nhập tên hoặc email">

        <button type="submit" class="btn">
            Tìm kiếm
        </button>

    </form>
</div>

    <!-- ================= TABLE ================= -->

    <div class="table-wrap">

        <table>

            <thead>

                <tr>

                    <th>ID</th>

                    <th>Họ tên</th>

                    <th>Email</th>

                    <th>SĐT</th>

                    <th>Vai trò</th>

                    <th>Trạng thái</th>

                    <th>Thao tác</th>

                </tr>

            </thead>

            <tbody>
            <c:forEach var="user" items="${userList}">

    <tr>

        <td>${user.id}</td>

        <td>${user.hoTen}</td>

        <td>${user.email}</td>

        <td>${user.soDienThoai}</td>

        <td>

            <span class="status ${user.vaiTro=='Quản lý' ? '' : 'warn'}">

                ${user.vaiTro}

            </span>

        </td>

        <td>

            <c:choose>

                <c:when test="${user.trangThai}">

                    Hoạt động

                </c:when>

                <c:otherwise>

                    Khóa

                </c:otherwise>

            </c:choose>

        </td>

        <td>

            <div class="actions">

                <button
                    type="button"
                    class="btn small"
                    style="background:#e99b2f"

                    onclick="fillToForm(
                    '${user.id}',
                    '${user.hoTen}',
                    '${user.email}',
                    '${user.soDienThoai}',
                    '${user.vaiTro}',
                    ${user.trangThai}
                    )">

                    Sửa

                </button>

                <a

                    class="btn danger small"

                    href="${pageContext.request.contextPath}/admin/users?action=delete&id=${user.id}"

                    onclick="return confirm('Bạn có chắc chắn muốn xóa người dùng này?')">

                    Xóa

                </a>

            </div>

        </td>

    </tr>

</c:forEach>

<c:if test="${empty userList}">

    <tr>

        <td colspan="7" class="empty">

            Không có dữ liệu.

        </td>

    </tr>

</c:if>

            </tbody>

        </table>

    </div>
    <script>

function fillToForm(
    id,
    hoTen,
    email,
    soDienThoai,
    vaiTro,
    trangThai){

    document.getElementById("formAction").value="update";

    document.getElementById("userId").value=id;

    document.getElementById("hoTen").value=hoTen;

    document.getElementById("email").value=email;

    document.getElementById("soDienThoai").value=soDienThoai;

    document.getElementById("vaiTro").value=vaiTro;

    document.getElementById("matKhau").value="";

    document.querySelector("input[name='trangThai']").checked=trangThai;

    document.getElementById("btnSubmit").innerHTML="Lưu chỉnh sửa";

    document.getElementById("btnCancel").style.display="inline-block";

    window.scrollTo({
        top:0,
        behavior:"smooth"
    });

}

function resetForm(){

    document.getElementById("formAction").value="add";

    document.getElementById("userId").value="";

    document.getElementById("hoTen").value="";

    document.getElementById("email").value="";

    document.getElementById("matKhau").value="";

    document.getElementById("soDienThoai").value="";

    document.getElementById("vaiTro").value="Quản lý";

    document.querySelector("input[name='trangThai']").checked=true;

    document.getElementById("btnSubmit").innerHTML="Thêm thành viên";

    document.getElementById("btnCancel").style.display="none";

}

</script>

</div>

<jsp:include page="include/footer.jsp"/>

</body>

</html>