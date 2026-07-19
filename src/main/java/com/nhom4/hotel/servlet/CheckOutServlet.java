package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.dao.HoaDonDAO;
import com.nhom4.hotel.dao.PhongDAO;
import com.nhom4.hotel.entity.DatPhong;
import com.nhom4.hotel.entity.HoaDon;
import com.nhom4.hotel.entity.NguoiDung;
import com.nhom4.hotel.util.AuthUtil;
import com.nhom4.hotel.util.DateUtil;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Servlet dành cho lễ tân: trả phòng cho khách và tự động lập hóa đơn (chưa thanh toán).
 * - GET  /checkout : danh sách đơn "Đã nhận phòng" và chưa có hóa đơn, đang chờ trả phòng.
 * - POST /checkout : trả phòng cho 1 đơn -> phòng về "Trống" + tạo hóa đơn "Chưa thanh toán".
 */
@WebServlet("/checkout")
public class CheckOutServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = ParamUtil.getString(req, "keyword");
        req.setAttribute("bookings", datPhongDAO.findReadyForCheckout(keyword));
        req.getRequestDispatcher("/views/frontdesk/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        NguoiDung user = AuthUtil.getUser(req.getSession(false));
        Integer id = ParamUtil.getInt(req, "id");
        DatPhong booking = id == null ? null : datPhongDAO.findById(id);

        if (booking == null || !"Đã nhận phòng".equals(booking.getTrangThai())
                || hoaDonDAO.findByDatPhongId(booking.getId()) != null) {
            resp.sendRedirect(req.getContextPath() + "/checkout?error=1");
            return;
        }

        long soDem = DateUtil.nights(booking.getNgayNhan().toLocalDate(), booking.getNgayTra().toLocalDate());
        BigDecimal tongTien = booking.getGiaPhong().multiply(BigDecimal.valueOf(soDem));

        HoaDon hoaDon = new HoaDon();
        hoaDon.setDatPhongId(booking.getId());
        hoaDon.setNhanVienId(user.getId());
        hoaDon.setTongTien(tongTien);
        hoaDon.setTrangThai("Chưa thanh toán");
        hoaDonDAO.insert(hoaDon);

        phongDAO.updateStatus(booking.getPhongId(), "Trống");
        resp.sendRedirect(req.getContextPath() + "/invoices?success=1");
    }
}