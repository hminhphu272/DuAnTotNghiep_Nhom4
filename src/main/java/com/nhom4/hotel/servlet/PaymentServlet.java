package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.dao.HoaDonDAO;
import com.nhom4.hotel.entity.HoaDon;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet dành cho lễ tân/quản lý: xem danh sách hóa đơn và xác nhận thanh toán.
 * - GET  /invoices : danh sách hóa đơn (lọc theo từ khóa/trạng thái).
 * - POST /invoices : xác nhận thanh toán 1 hóa đơn -> đổi trạng thái hóa đơn + đơn đặt phòng.
 */
@WebServlet("/invoices")
public class PaymentServlet extends HttpServlet {
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = ParamUtil.getString(req, "keyword");
        String status = ParamUtil.getString(req, "status");
        req.setAttribute("invoices", hoaDonDAO.search(keyword, status));
        req.getRequestDispatcher("/views/frontdesk/invoices.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = ParamUtil.getInt(req, "id");
        String phuongThuc = ParamUtil.getString(req, "phuongThuc");
        HoaDon hoaDon = id == null ? null : hoaDonDAO.findById(id);

        if (hoaDon == null || "Đã thanh toán".equals(hoaDon.getTrangThai())) {
            resp.sendRedirect(req.getContextPath() + "/invoices?error=1");
            return;
        }

        hoaDonDAO.markPaid(hoaDon.getId(), phuongThuc.isBlank() ? "Tiền mặt" : phuongThuc);
        datPhongDAO.updateStatus(hoaDon.getDatPhongId(), "Đã thanh toán");
        resp.sendRedirect(req.getContextPath() + "/invoices?success=1");
    }
}