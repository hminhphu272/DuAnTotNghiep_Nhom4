package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.dao.HoaDonDAO;
import com.nhom4.hotel.dao.PhongDAO;
import com.nhom4.hotel.entity.DatPhong;
import com.nhom4.hotel.entity.HoaDon;
import com.nhom4.hotel.entity.NguoiDung;
import com.nhom4.hotel.util.AuthUtil;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@WebServlet(urlPatterns = {"/checkout", "/invoices"})
public class CheckoutServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private final PhongDAO phongDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/invoices".equals(req.getServletPath())) {
            req.setAttribute("invoices", hoaDonDAO.findAll());
            req.getRequestDispatcher("/views/invoices/list.jsp").forward(req, resp);
            return;
        }
        Integer id = ParamUtil.getInt(req, "id");
        DatPhong booking = id == null ? null : datPhongDAO.findById(id);
        req.setAttribute("booking", booking);
        req.setAttribute("total", calculateTotal(booking));
        req.getRequestDispatcher("/views/checkout/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        NguoiDung staff = AuthUtil.getUser(req.getSession(false));
        Integer bookingId = ParamUtil.getInt(req, "bookingId");
        DatPhong booking = bookingId == null ? null : datPhongDAO.findById(bookingId);
        if (booking != null && hoaDonDAO.findByDatPhongId(bookingId) == null) {
            HoaDon invoice = new HoaDon();
            invoice.setDatPhongId(bookingId);
            invoice.setNhanVienId(staff.getId());
            invoice.setTongTien(calculateTotal(booking));
            invoice.setPhuongThuc(ParamUtil.getString(req, "phuongThuc"));
            hoaDonDAO.insert(invoice);
            datPhongDAO.updateStatus(bookingId, "Đã thanh toán");
            phongDAO.updateStatus(booking.getPhongId(), "Trống");
        }
        resp.sendRedirect(req.getContextPath() + "/invoices");
    }

    private BigDecimal calculateTotal(DatPhong booking) {
        if (booking == null || booking.getGiaPhong() == null) return BigDecimal.ZERO;
        long days = ChronoUnit.DAYS.between(booking.getNgayNhan().toLocalDate(), booking.getNgayTra().toLocalDate());
        if (days < 1) days = 1;
        return booking.getGiaPhong().multiply(BigDecimal.valueOf(days));
    }
}
