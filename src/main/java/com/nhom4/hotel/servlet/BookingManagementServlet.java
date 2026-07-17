package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet dành cho quản lý: xem toàn bộ đặt phòng của mọi khách hàng, lọc theo từ khóa/trạng thái,
 * và đổi trạng thái đơn (xác nhận, hủy...).
 * - /admin/bookings        : GET danh sách toàn bộ đặt phòng.
 * - /admin/bookings/status : POST đổi trạng thái 1 đơn.
 */
@WebServlet(urlPatterns = {"/admin/bookings", "/admin/bookings/status"})
public class BookingManagementServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = ParamUtil.getString(req, "keyword");
        String status = ParamUtil.getString(req, "status");
        req.setAttribute("bookings", datPhongDAO.search(keyword, status));
        req.getRequestDispatcher("/views/bookings/admin-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = ParamUtil.getInt(req, "id");
        String trangThai = ParamUtil.getString(req, "trangThai");
        if (id != null && !trangThai.isBlank()) {
            datPhongDAO.updateStatus(id, trangThai);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/bookings");
    }
}