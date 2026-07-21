package com.nhom4.hotel.servlet;

import java.io.IOException;

import com.nhom4.hotel.dao.NguoiDungDAO;
import com.nhom4.hotel.entity.NguoiDung;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/users")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {

            try {
                int id = Integer.parseInt(request.getParameter("id"));

                boolean ok = nguoiDungDAO.delete(id);

                System.out.println("Delete user = " + ok);

            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        String keyword = request.getParameter("keyword");

        if (keyword != null && !keyword.trim().isEmpty()) {

            request.setAttribute(
                    "userList",
                    nguoiDungDAO.searchByNameOrEmail(keyword.trim()));

            request.setAttribute("keyword", keyword);

        } else {

            request.setAttribute(
                    "userList",
                    nguoiDungDAO.getAll());
        }

        request.getRequestDispatcher("/views/user-management.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String matKhau = request.getParameter("matKhau");
        String soDienThoai = request.getParameter("soDienThoai");
        String vaiTro = request.getParameter("vaiTro");

        boolean trangThai =
                request.getParameter("trangThai") != null;

        if ("add".equals(action)) {

            NguoiDung nd = new NguoiDung();

            nd.setHoTen(hoTen);
            nd.setEmail(email);

            if (matKhau == null || matKhau.trim().isEmpty()) {
                nd.setMatKhau("123456");
            } else {
                nd.setMatKhau(matKhau.trim());
            }

            nd.setSoDienThoai(soDienThoai);
            nd.setVaiTro(vaiTro);
            nd.setTrangThai(trangThai);

            System.out.println("========== ADD USER ==========");
            System.out.println("Họ tên: " + nd.getHoTen());
            System.out.println("Email: " + nd.getEmail());
            System.out.println("Mật khẩu: " + nd.getMatKhau());
            System.out.println("SĐT: " + nd.getSoDienThoai());
            System.out.println("Vai trò: " + nd.getVaiTro());
            System.out.println("Trạng thái: " + nd.isTrangThai());

            boolean ok = nguoiDungDAO.add(nd);

            System.out.println("Add user = " + ok);

            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        if ("update".equals(action)) {

            try {

                int id = Integer.parseInt(request.getParameter("id"));

                NguoiDung nd = nguoiDungDAO.findById(id);

                if (nd != null) {

                    nd.setHoTen(hoTen);
                    nd.setEmail(email);
                    nd.setSoDienThoai(soDienThoai);
                    nd.setVaiTro(vaiTro);
                    nd.setTrangThai(trangThai);

                    if (matKhau != null && !matKhau.trim().isEmpty()) {
                        nd.setMatKhau(matKhau.trim());
                    }

                    boolean ok = nguoiDungDAO.update(nd);

                    System.out.println("Update user = " + ok);
                }

            } catch (Exception e) {

                e.printStackTrace();
            }

            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}