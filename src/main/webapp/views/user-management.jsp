<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Người Dùng - Nhóm 4 Hotel</title>
    <!-- Nhúng file CSS phong cách đồng bộ gốc của dự án -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Nhúng Header mẫu chung của nhóm -->
    <jsp:include page="include/header.jsp" />
    
    <!-- Sử dụng class container đồng bộ layout -->
    <div class="container">
        
        <!-- Tiêu đề trang dùng cấu trúc chuẩn của nhóm -->
        <div class="section-title">
            <div>
                <h2>Quản lý người dùng</h2>
                <p class="muted" style="margin: 5px 0 0 0;">Thêm, sửa, xoá tài khoản thành viên trong hệ thống khách sạn</p>
            </div>
        </div>
        
        <!-- Form nhập liệu: Dùng class .form, .form-grid, .form-group đồng bộ CSS nhóm -->
        <form action="${pageContext.request.contextPath}/admin/users" method="post" class="form" style="margin-bottom: 30px;">
            <input type="hidden" name="action" value="add" id="formAction">
            <input type="hidden" name="id" id="userId">
            
            <div class="form-grid" style="margin-bottom: 16px;">
                <div class="form-group">
                    <label for="hoTen">Họ và tên</label>
                    <input type="text" name="hoTen" id="hoTen" placeholder="Nhập họ và tên người dùng" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Địa chỉ Email</label>
                    <input type="email" name="email" id="email" placeholder="name@example.com" required>
                </div>
            </div>
            
            <div class="form-group" style="margin-bottom: 20px; max-width: 50%; padding-right: 7px;">
                <label for="vaiTro">Quyền hạn hệ thống</label>
                <select name="vaiTro" id="vaiTro">
                    <option value="Admin">Quản lý (Admin)</option>
                    <option value="User">Khách hàng (User)</option>
                </select>
            </div>
            
            <!-- Khu vực nút bấm sử dụng class .btn, .btn.secondary chuẩn -->
            <div style="display: flex; gap: 10px;">
                <button type="submit" id="btnSubmit" class="btn">Thêm thành viên</button>
                <button type="button" id="btnCancel" class="btn secondary" style="display: none;" onclick="resetForm()">Huỷ thao tác</button>
            </div>
        </form>

        <!-- Bảng hiển thị: Dùng class .table-wrap bọc ngoài thẻ table như code mẫu CSS nhóm -->
        <div class="table-wrap">
            <table>
                <thead>
                    <tr>
                        <th width="10%">Mã dòng</th>
                        <th width="30%">Tên người dùng</th>
                        <th width="30%">Email hệ thống</th>
                        <th width="15%">Quyền hành</th>
                        <th width="15%">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td><b>#${user.id}</b></td>
                            <td>${user.hoTen}</td>
                            <td>${user.email}</td>
                            <td>
                                <!-- Sử dụng class .status và .status.warn của nhóm để đổi màu phân quyền -->
                                <span class="status ${user.vaiTro == 'Admin' ? '' : 'warn'}">
                                    ${user.vaiTro}
                                </span>
                            </td>
                            <td>
                                <!-- Sử dụng class .actions và .btn.small để đồng bộ giao diện nút bấm trong bảng -->
                                <div class="actions">
                                    <button class="btn small small-edit" style="background-color: #e99b2f;" onclick="fillToForm('${user.id}', '${user.hoTen}', '${user.email}', '${user.vaiTro}')">Sửa</button>
                                    <a href="${pageContext.request.contextPath}/admin/users?action=delete&id=${user.id}" class="btn danger small" onclick="return confirm('Bạn có chắc chắn muốn xoá người dùng này không?')">Xoá</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty userList}">
                        <tr>
                            <td colspan="5" class="empty">Không tìm thấy thông tin thành viên nào trong hệ thống.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Tác vụ bổ trợ điền dữ liệu bằng Javascript -->
    <script>
        function fillToForm(id, hoTen, email, vaiTro) {
            document.getElementById('formAction').value = 'update';
            document.getElementById('userId').value = id;
            document.getElementById('hoTen').value = hoTen;
            document.getElementById('email').value = email;
            document.getElementById('vaiTro').value = vaiTro;
            
            document.getElementById('btnSubmit').innerText = 'Lưu chỉnh sửa';
            document.getElementById('btnCancel').style.display = 'inline-block';
            
            // Cuộn mượt màn hình lên form nhập liệu để sửa
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }

        function resetForm() {
            document.getElementById('formAction').value = 'add';
            document.getElementById('userId').value = '';
            document.getElementById('hoTen').value = '';
            document.getElementById('email').value = '';
            document.getElementById('vaiTro').value = 'Admin';
            
            document.getElementById('btnSubmit').innerText = 'Thêm thành viên';
            document.getElementById('btnCancel').style.display = 'none';
        }
    </script>

    <!-- Nhúng Footer mẫu chung của nhóm -->
    <jsp:include page="include/footer.jsp" />
</body>
</html>