package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.dao.PhongDAO;
import com.nhom4.hotel.entity.DatPhong;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet dành cho lễ tân: nhận phòng cho khách khi đơn đặt phòng đã được xác nhận.
 * - GET  /checkin : danh sách đơn "Đã xác nhận" đang chờ khách đến nhận phòng.
 * - POST /checkin : xác nhận nhận phòng cho 1 đơn -> đổi trạng thái đơn + trạng thái phòng.
 */
@WebServlet("/checkin")
public class CheckInServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = ParamUtil.getString(req, "keyword");
        req.setAttribute("bookings", datPhongDAO.search(keyword, "Đã xác nhận"));
        req.getRequestDispatcher("/views/frontdesk/checkin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = ParamUtil.getInt(req, "id");
        DatPhong booking = id == null ? null : datPhongDAO.findById(id);

        if (booking == null || !"Đã xác nhận".equals(booking.getTrangThai())) {
            resp.sendRedirect(req.getContextPath() + "/checkin?error=1");
            return;
        }

        datPhongDAO.updateStatus(booking.getId(), "Đã nhận phòng");
        phongDAO.updateStatus(booking.getPhongId(), "Đang sử dụng");
        resp.sendRedirect(req.getContextPath() + "/checkin?success=1");
    }
}