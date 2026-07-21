package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.dao.HoaDonDAO;
import com.nhom4.hotel.dao.PhongDAO;
import com.nhom4.hotel.email.EmailService;
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

@WebServlet("/checkout")
public class CheckOutServlet extends HttpServlet {

    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String keyword = ParamUtil.getString(req, "keyword");
        req.setAttribute("bookings", datPhongDAO.findReadyForCheckout(keyword));
        req.getRequestDispatcher("/views/frontdesk/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        NguoiDung user = AuthUtil.getUser(req.getSession(false));
        Integer id = ParamUtil.getInt(req, "id");

        DatPhong booking = (id == null) ? null : datPhongDAO.findById(id);

        if (booking == null
                || !"Đã nhận phòng".equals(booking.getTrangThai())
                || hoaDonDAO.findByDatPhongId(booking.getId()) != null) {

            resp.sendRedirect(req.getContextPath() + "/checkout?error=1");
            return;
        }

        long soDem = DateUtil.nights(
                booking.getNgayNhan().toLocalDate(),
                booking.getNgayTra().toLocalDate());

        BigDecimal tongTien = booking.getGiaPhong()
                .multiply(BigDecimal.valueOf(soDem));

        HoaDon hoaDon = new HoaDon();
        hoaDon.setDatPhongId(booking.getId());
        hoaDon.setNhanVienId(user.getId());
        hoaDon.setTongTien(tongTien);
        hoaDon.setTrangThai("Chưa thanh toán");

        hoaDonDAO.insert(hoaDon);

        // Gửi email xác nhận trả phòng
        if (booking.getEmailKhach() != null
                && !booking.getEmailKhach().isBlank()) {

            try {

                String html = """
                        <html>
                        <body style="font-family: Arial">

                            <h2>Trả phòng thành công</h2>

                            <p>Xin chào <b>%s</b>,</p>

                            <p>Cảm ơn bạn đã sử dụng dịch vụ của khách sạn.</p>

                            <table border="1" cellpadding="8" cellspacing="0">
                                <tr>
                                    <td><b>Phòng</b></td>
                                    <td>%s</td>
                                </tr>
                                <tr>
                                    <td><b>Ngày nhận</b></td>
                                    <td>%s</td>
                                </tr>
                                <tr>
                                    <td><b>Ngày trả</b></td>
                                    <td>%s</td>
                                </tr>
                                <tr>
                                    <td><b>Tổng tiền</b></td>
                                    <td>%s VNĐ</td>
                                </tr>
                            </table>

                            <br>

                            <p>Hẹn gặp lại bạn trong lần tới!</p>

                        </body>
                        </html>
                        """.formatted(
                        booking.getHoTenKhach(),
                        booking.getSoPhong(),
                        booking.getNgayNhan(),
                        booking.getNgayTra(),
                        tongTien.toPlainString()
                );

                EmailService.send(
                        booking.getEmailKhach(),
                        "Xác nhận trả phòng",
                        html
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Cập nhật trạng thái phòng
        phongDAO.updateStatus(booking.getPhongId(), "Trống");

        resp.sendRedirect(req.getContextPath() + "/invoices?success=1");
    }
}