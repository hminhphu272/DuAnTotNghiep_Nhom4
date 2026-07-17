package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.dao.PhongDAO;
import com.nhom4.hotel.entity.DatPhong;
import com.nhom4.hotel.entity.NguoiDung;
import com.nhom4.hotel.entity.Phong;
import com.nhom4.hotel.util.AuthUtil;
import com.nhom4.hotel.util.DateUtil;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Servlet dành cho khách hàng: tạo đặt phòng, xem danh sách đặt phòng của mình, hủy đặt phòng.
 * - /booking/create : GET hiển thị form đặt phòng cho 1 phòng, POST tạo đơn đặt phòng.
 * - /bookings        : GET danh sách đặt phòng của khách hàng đang đăng nhập.
 * - /booking/status   : POST đổi trạng thái đơn (khách chỉ được hủy đơn của chính mình).
 */
@WebServlet(urlPatterns = {"/booking/create", "/bookings", "/booking/status"})
public class BookingServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        NguoiDung user = AuthUtil.getUser(req.getSession(false));

        if ("/booking/create".equals(path)) {
            Integer roomId = ParamUtil.getInt(req, "roomId");
            Phong room = roomId == null ? null : phongDAO.findById(roomId);
            if (room == null) {
                resp.sendRedirect(req.getContextPath() + "/rooms");
                return;
            }
            req.setAttribute("room", room);
            req.setAttribute("ngayNhan", ParamUtil.getString(req, "ngayNhan"));
            req.setAttribute("ngayTra", ParamUtil.getString(req, "ngayTra"));
            req.getRequestDispatcher("/views/bookings/create.jsp").forward(req, resp);
            return;
        }

        // /bookings: danh sách đặt phòng của khách hàng đang đăng nhập
        req.setAttribute("bookings", datPhongDAO.findByKhachHang(user.getId()));
        req.getRequestDispatcher("/views/bookings/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        NguoiDung user = AuthUtil.getUser(req.getSession(false));

        if ("/booking/create".equals(path)) {
            createBooking(req, resp, user);
            return;
        }

        // /booking/status: khách hàng hủy đơn của chính mình
        Integer id = ParamUtil.getInt(req, "id");
        DatPhong booking = id == null ? null : datPhongDAO.findById(id);
        if (booking != null && booking.getKhachHangId() == user.getId()
                && "Chờ xác nhận".equals(booking.getTrangThai())) {
            datPhongDAO.updateStatus(booking.getId(), "Đã hủy");
        }
        resp.sendRedirect(req.getContextPath() + "/bookings");
    }

    private void createBooking(HttpServletRequest req, HttpServletResponse resp, NguoiDung user) throws IOException {
        Integer roomId = ParamUtil.getInt(req, "roomId");
        LocalDate ngayNhan = ParamUtil.getDate(req, "ngayNhan");
        LocalDate ngayTra = ParamUtil.getDate(req, "ngayTra");
        Phong room = roomId == null ? null : phongDAO.findById(roomId);

        String error = null;
        if (room == null) {
            error = "Phòng không tồn tại.";
        } else if (ngayNhan == null || ngayTra == null) {
            error = "Vui lòng chọn đầy đủ ngày nhận và ngày trả phòng.";
        } else if (!ngayTra.isAfter(ngayNhan)) {
            error = "Ngày trả phòng phải sau ngày nhận phòng.";
        } else if (ngayNhan.isBefore(LocalDate.now())) {
            error = "Ngày nhận phòng không được ở quá khứ.";
        } else if (datPhongDAO.hasOverlap(roomId, ngayNhan, ngayTra, null)) {
            error = "Phòng đã có người đặt trong khoảng thời gian này, vui lòng chọn ngày khác.";
        }

        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("room", room);
            req.setAttribute("ngayNhan", req.getParameter("ngayNhan"));
            req.setAttribute("ngayTra", req.getParameter("ngayTra"));
            req.getRequestDispatcher("/views/bookings/create.jsp").forward(req, resp);
            return;
        }

        DatPhong booking = new DatPhong();
        booking.setKhachHangId(user.getId());
        booking.setPhongId(roomId);
        booking.setNgayNhan(DateUtil.sqlDate(ngayNhan));
        booking.setNgayTra(DateUtil.sqlDate(ngayTra));
        booking.setTrangThai("Chờ xác nhận");
        datPhongDAO.insert(booking);

        resp.sendRedirect(req.getContextPath() + "/bookings");
    }
}